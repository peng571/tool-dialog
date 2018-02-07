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

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * DialogFragment with two button and simple message
 * <p>
 * Usage
 * on activity: implement dialog interface to handle
 * on fragment: implement dialog interface to handle, and add setTargetFragment as dialog
 * (fragment will have higher priority then activity)
 * <p>
 * Event Handle
 * - Handle yes (positive) event with DialogYesHolder
 * - Handle no (negative) event with DialogNoHolder
 * - Handle cancel event with DialogFinishHolder.onDialogCancel or DialogNoHolder(only when not find DialogFinishHolder),
 * - Handle dismiss event with DialogFinishHolder.onDialogDismiss
 * <p>
 * Created on 2016/12/12.
 * Updated on 2017/4/27.
 * by momo peng
 */
public class YesNoDialog extends DialogFragment {

    private final static String TAG = YesNoDialog.class.getSimpleName();

    public int request = 0;

    /**
     * Create a new instance with argument.
     */
    public static YesNoDialog newInstance(@StringRes int messageRes, @StringRes int yesRes, @StringRes int noRes, int requset) {
        YesNoDialog f = new YesNoDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(DialogConstants.KEY_MESSAGE_RES, messageRes);
        args.putInt(DialogConstants.KEY_YES_RES, yesRes);
        args.putInt(DialogConstants.KEY_NO_RES, noRes);
        args.putInt(DialogConstants.KEY_REQUEST, requset);
        f.setArguments(args);
        return f;
    }

    public static YesNoDialog newInstance(@StringRes int messageRes, @StringRes int yesRes, int request) {
        return newInstance(messageRes, yesRes, R.string.no, request);
    }

    public static YesNoDialog newInstance(@StringRes int messageRes, int requset) {
        return newInstance(messageRes, R.string.yes, requset);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        int titleRes = bundle.getInt(DialogConstants.KEY_TITLE_RES, 0);
        int messageRes = bundle.getInt(DialogConstants.KEY_MESSAGE_RES, 0);
        int iconRes = bundle.getInt(DialogConstants.KEY_ICON_DRAWABLE, 0);
        int yesRes = bundle.getInt(DialogConstants.KEY_YES_RES, R.string.yes);
        int noRes = bundle.getInt(DialogConstants.KEY_NO_RES, R.string.no);
        request = bundle.getInt(DialogConstants.KEY_REQUEST, 0);

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


        DialogInterface.OnClickListener dialogClickListener = (DialogInterface dialog, int which) -> {
            if (getActivity() == null) return;
            switch (which) {
                case BUTTON_POSITIVE:
                    DialogYesHolder holderYes = null;
                    // use fragment bigger then activity
                    if (getActivity() instanceof DialogYesHolder)
                        holderYes = (DialogYesHolder) getActivity();
                    if (getTargetFragment() instanceof DialogYesHolder)
                        holderYes = (DialogYesHolder) getTargetFragment();
                    if (holderYes != null) {
                        holderYes.doOnDialogYesClick(request);
                    }
                    dialog.dismiss();
                    break;
                case BUTTON_NEGATIVE:
                    DialogNoHolder holderNo = null;
                    // use fragment bigger then activity
                    if (getActivity() instanceof DialogNoHolder)
                        holderNo = (DialogNoHolder) getActivity();
                    if (getTargetFragment() instanceof DialogNoHolder)
                        holderNo = (DialogNoHolder) getTargetFragment();

                    if (holderNo != null) {
                        holderNo.doOnDialogNoClick(request);
                    }
                    dialog.dismiss();
                    break;
            }
        };

        builder.setPositiveButton(yesRes, dialogClickListener)
                .setNegativeButton(noRes, dialogClickListener);
        return builder.create();
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        if (getActivity() == null) return;

        DialogFinishHolder holder = null;
        if (getActivity() instanceof DialogFinishHolder)
            holder = (DialogFinishHolder) getActivity();
        if (getTargetFragment() instanceof DialogFinishHolder)
            holder = (DialogFinishHolder) getTargetFragment();

        if (holder != null) {
            holder.doOnDialogCancel(request);
        } else {
            DialogNoHolder holderNo = null;
            // use fragment bigger then activity
            if (getActivity() instanceof DialogNoHolder)
                holderNo = (DialogNoHolder) getActivity();
            if (getTargetFragment() instanceof DialogNoHolder)
                holderNo = (DialogNoHolder) getTargetFragment();

            if (holderNo == null) return;
            holderNo.doOnDialogNoClick(request);
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
            holder.doOnDialogDismiss(request);
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
