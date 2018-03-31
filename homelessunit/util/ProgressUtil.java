package dev.momo.library.view.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;

import dev.momo.library.core.log.Logger;
import dev.momo.library.core.tool.ResourceHelper;
import dev.momo.library.view.R;

/**
 * The best practice is to not use ProgressDialogs, because they block the user from interacting with the app.
 */
public class ProgressUtil {

    private static final String TAG = ProgressUtil.class.getSimpleName();

    private final static String DUFAULT_MESSAGE = ResourceHelper.getString(R.string.processing);

    private ProgressDialog progressDialog = null;

    private boolean cancelable = false;

    private String progressMessage = DUFAULT_MESSAGE;


    public ProgressUtil() {
    }


    public ProgressUtil message(@StringRes int message) {
        return message(ResourceHelper.getString(message));
    }


    public ProgressUtil message(String message) {
        progressMessage = message;

        if (progressDialog != null) {
            progressDialog.setMessage(progressMessage);
        }
        return this;
    }

    public ProgressUtil cancelable(boolean b) {
        cancelable = b;
        if (progressDialog != null) {
            progressDialog.setCancelable(cancelable);
        }
        return this;
    }


    public void show(Context context) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(context, null, progressMessage, true);
        }
        progressDialog.setCancelable(cancelable);
        try {
            progressDialog.show();
        } catch (Exception e) {
            Logger.E(TAG, e);
        }
    }


    public void hide() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                Logger.E(TAG, e);
            } finally {
                progressDialog = null;
            }
        }
    }
}
