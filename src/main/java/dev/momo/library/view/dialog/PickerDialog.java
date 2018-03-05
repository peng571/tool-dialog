package dev.momo.library.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import dev.momo.library.core.log.Logger;
import dev.momo.library.core.tool.ResourceHelper;
import dev.momo.library.view.R;

import static dev.momo.library.view.dialog.DialogConstants.KEY_REQUEST;
import static dev.momo.library.view.dialog.DialogConstants.KEY_TITLE_RES;


/**
 * DialogFragment with picker option
 * <p>
 * Usage
 * on activity: implement dialog interface to handle
 * on fragment: implement dialog interface to handle, and add setTargetFragment as dialog
 * <p>
 * Event Handle
 * - Handle pick event with PickerCallback in PickerItem
 * - Handle cancel event with DialogFinishHolder.onDialogCancel
 * - Handle dismiss event with DialogFinishHolder.onDialogDismiss
 */
public class PickerDialog extends DialogFragment {

    private static final String TAG = PickerDialog.class.getSimpleName();

    protected ArrayList<PickerItem<?>> items;

    public static PickerDialog newInstance() {
        return newInstance(0, 0);
    }

    public static PickerDialog newInstance(@StringRes int titleRes, int requset) {
        PickerDialog f = new PickerDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(DialogConstants.KEY_TITLE_RES, titleRes);
        args.putInt(DialogConstants.KEY_REQUEST, requset);
        f.setArguments(args);
        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ArrayList<PickerItem<?>> items = getItems();
        String[] itemNames = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemNames[i] = items.get(i).getName();
        }
        String title = "";
        if (getArguments() != null) {
            title = ResourceHelper.getString(getArguments().getInt(KEY_TITLE_RES, 0));
        }

        if (!title.isEmpty()) {
            TextView titleView = new TextView(getActivity());
            titleView.setTextColor(ResourceHelper.getColor(R.color.colorPrimaryDark));
            titleView.setTextSize(ResourceHelper.getDimenDp(R.dimen.font_size_large));
            titleView.setPadding(ResourceHelper.getDimenDp(R.dimen.spacing_large), ResourceHelper.getDimenDp(R.dimen.spacing_large), ResourceHelper.getDimenDp(R.dimen.spacing_normal), 0);
            titleView.setText(title);
            builder.setCustomTitle(titleView);
        }

        builder.setItems(itemNames, (DialogInterface dialog, int which) -> {
            PickerItem item = getItems().get(which);
            PickerCallback callback = item.getCallback();
            if (callback == null) return;
            callback.onPick(which, item.getValue());
        });
        return builder.create();
    }


    public PickerDialog addItem(PickerItem item) {
        getItems().add(item);
        return this;
    }


    public PickerDialog addItem(@StringRes int stringRes, PickerCallback<String> callback) {
        getItems().add(new PickerItem<String>(ResourceHelper.getString(stringRes), callback));
        return this;
    }


    public PickerDialog addItem(String name, PickerCallback<String> callback) {
        getItems().add(new PickerItem<String>(name, callback));
        return this;
    }

    public <T> PickerDialog addItem(T object, PickerItem.PickerOption<T> option, PickerCallback<T> callback) {
        getItems().add(new PickerItem<T>(object, option, callback));
        return this;
    }


    protected ArrayList<PickerItem<?>> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public PickerItem<?> getItem(int position) {
        if (position < 0 || position >= items.size()) return null;
        return items.get(position);
    }

    public int getCount() {
        return getItems().size();
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        if (getActivity() == null) return;
        DialogFinishHolder holder = null;

        // use fragment bigger then activity
        if (getActivity() instanceof DialogFinishHolder)
            holder = (DialogFinishHolder) getActivity();
        if (getTargetFragment() instanceof DialogFinishHolder)
            holder = (DialogFinishHolder) getTargetFragment();

        if (holder != null) {
            holder.doOnDialogCancel(getArguments().getInt(KEY_REQUEST));
        }
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (getActivity() == null) return;
        DialogFinishHolder holder = null;

        // use fragment bigger then activity
        if (getActivity() instanceof DialogFinishHolder)
            holder = (DialogFinishHolder) getActivity();
        if (getTargetFragment() instanceof DialogFinishHolder)
            holder = (DialogFinishHolder) getTargetFragment();

        if (holder != null) {
            holder.doOnDialogDismiss(getArguments().getInt(KEY_REQUEST));
        }
        super.onDismiss(dialog);
    }


    /**
     * Add code below into base fragment class could fix many fragment exception when change
     */
    private static final Field sChildFragmentManagerField;

    static {
        Field f = null;
        try {
            f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Logger.E(TAG, "Error getting mChildFragmentManager field", e);
        }
        sChildFragmentManagerField = f;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                Logger.E(TAG, "Error setting mChildFragmentManager field", e);
            }
        }
    }
    /**
     * End
     */
}

