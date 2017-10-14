package dev.momo.library.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;

import java.lang.reflect.Field;

import dev.momo.library.core.log.Logger;
import dev.momo.library.view.R;

import static dev.momo.library.view.dialog.DialogConstants.KEY_REQUEST;

/**
 * DialogFragment with one ok button and simple message
 * <p>
 * Usage
 * on activity: implement dialog interface to handle
 * on fragment: implement dialog interface to handle, and add setTargetFragment as dialog
 * <p>
 * Event Handle
 * - Handle ok event with DialogYesHolder
 * - Handle cancel event with DialogFinishHolder.onDialogCancel
 * - Handle dismiss event with DialogFinishHolder.onDialogDismiss
 */
public class OKDialog extends DialogFragment {

    private final static String TAG = OKDialog.class.getSimpleName();


    public static OKDialog newInstance(@StringRes int messageRes, @StringRes int yesRes, int request) {
        OKDialog f = new OKDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(DialogConstants.KEY_MESSAGE_RES, messageRes);
        args.putInt(DialogConstants.KEY_YES_RES, yesRes);
        args.putInt(KEY_REQUEST, request);
        f.setArguments(args);
        return f;
    }

    public static OKDialog newInstance(@StringRes int messageRes, int request) {
        return newInstance(messageRes, R.string.ok, request);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        int titleRes = bundle.getInt(DialogConstants.KEY_TITLE_RES, 0);
        int messageRes = bundle.getInt(DialogConstants.KEY_MESSAGE_RES, 0);
        int iconRes = bundle.getInt(DialogConstants.KEY_ICON_DRAWABLE, 0);
        int yesRes = bundle.getInt(DialogConstants.KEY_YES_RES, R.string.ok);
        final int request = bundle.getInt(KEY_REQUEST, 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (titleRes != 0) {
            builder.setTitle(titleRes);
        }
        if (messageRes != 0) {
            builder.setMessage(messageRes);
        }
        if (iconRes != 0) {
            builder.setIcon(iconRes);
        }

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getActivity() == null) return;
                DialogYesHolder holder = null;

                // use fragment bigger then activity
                if (getActivity() instanceof DialogYesHolder)
                    holder = (DialogYesHolder) getActivity();

                if (getTargetFragment() instanceof DialogYesHolder)
                    holder = (DialogYesHolder) getTargetFragment();

                if (holder == null) {
                    Logger.WS(TAG, "no holder to get request %d, on dialog ok click", request);
                    return;
                }
                holder.doOnDialogYesClick(request);
                dialog.dismiss();
            }
        };
        builder.setPositiveButton(yesRes, dialogClickListener);
        return builder.create();
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
