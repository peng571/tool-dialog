package org.pengyr.tool.dialog.picker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import org.pengyr.tool.dialog.BaseDialog;
import org.pengyr.tool.dialog.DialogConstants;

import java.util.ArrayList;


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
public class PickerDialog  extends BaseDialog {

    private static final String TAG = PickerDialog.class.getSimpleName();

    protected ArrayList<PickerItem> items;

    /**
     * A simple picker dialog without title and request
     *
     * @return PickerDialog
     */
    public static PickerDialog newInstance() {
        return newInstance(0, 0);
    }

    /**
     * @param titleRes title string res
     * @param request  dialog request code
     * @return PickerDialog
     */
    public static PickerDialog newInstance(@StringRes int titleRes, int request) {
        PickerDialog f = new PickerDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(DialogConstants.KEY_TITLE_RES, titleRes);
        args.putInt(DialogConstants.KEY_REQUEST, request);
        f.setArguments(args);
        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // get arguments
        request = getArguments().getInt(DialogConstants.KEY_REQUEST);
        int titleRes = getArguments().getInt(DialogConstants.KEY_TITLE_RES, 0);
        if (titleRes != 0) {
            builder.setTitle(titleRes);
        }

        // create item names array
        if (getCount() > 0) {
            String[] itemNames = new String[getCount()];
            for (int i = 0; i < getCount(); i++) {
                PickerItem item = getItem(i);
                if (item.getName() instanceof String) {
                    itemNames[i] = (String) item.getName();
                } else {
                    itemNames[i] = item.getName().toString();
                }
            }
            builder.setItems(itemNames, (DialogInterface dialog, int which) -> getItem(which).onPickUp(which));
        }
        return builder.create();
    }


    public PickerDialog addItem(PickerItem item) {
        getItems().add(item);
        return this;
    }


    protected @NonNull ArrayList<PickerItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public @NonNull PickerItem getItem(int position) throws IndexOutOfBoundsException {
        if (position < 0 || position >= items.size()) {
            throw new IndexOutOfBoundsException();
        }
        return items.get(position);
    }

    public int getCount() {
        return getItems().size();
    }

    @Override
    protected int getRequestCode() {
        Bundle bundle = getArguments();
        return bundle.getInt(DialogConstants.KEY_REQUEST, request);
    }

    @Override
    protected String getTagName() {
        return TAG;
    }
}

