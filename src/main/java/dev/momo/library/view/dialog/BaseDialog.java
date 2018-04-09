package dev.momo.library.view.dialog;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Field;

import dev.momo.library.core.log.Logger;

/**
 * (TBD)
 * <p>
 * Created on 2017/12/19.
 * Updated on --.
 * by momo peng
 */
public abstract class BaseDialog extends DialogFragment {

    private final static String TAG = BaseDialog.class.getSimpleName();

    private final static String SUBMIT_PRESSED = "SUBMIT_PRESSED";
    protected boolean submitPressed = false;

    private FragmentManager fm;

    protected int request;

    /**
     * Show dialog methods
     */
    public void show(AppCompatActivity activity) {
        Logger.D(TAG, "dialog show");
        if (submitPressed) {
            Logger.WS(TAG, "dialog already shown");
            return;
        }

        try {
            fm = activity.getFragmentManager();
            //            fm.popBackStackImmediate(TAG, POP_BACK_STACK_INCLUSIVE);
            submitPressed = true;
            show(fm, TAG);
        } catch (IllegalStateException ignored) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        }
    }

    public void show(Fragment fragment) {
        Logger.D(TAG, "dialog show");
        if (submitPressed) {
            Logger.WS(TAG, "dialog already shown");
            return;
        }

        try {
            fm = fragment.getFragmentManager();
            //            fm.popBackStackImmediate(TAG, POP_BACK_STACK_INCLUSIVE);
            submitPressed = true;
            setTargetFragment(fragment, getRequestCode());
            show(fm, getTagName());
        } catch (IllegalStateException ignored) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // avoid to call super.onSaveInstanceState(), will get a known bug since api 11
        outState.putBoolean(SUBMIT_PRESSED, submitPressed);
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            submitPressed = savedInstanceState.getBoolean(SUBMIT_PRESSED);
        }
    }

    /**
     * will call dialogYesHolder.onDialogYesClick if have,
     */
    protected void onNext() {
        if (getActivity() == null) return;

        DialogYesHolder holder = null;
        if (getActivity() instanceof DialogYesHolder)
            holder = (DialogYesHolder) getActivity();
        if (getTargetFragment() instanceof DialogYesHolder)
            holder = (DialogYesHolder) getTargetFragment();

        if (holder != null) {
            holder.doOnDialogYesClick(getRequestCode());
        }
        onDismiss(getDialog());
    }

    protected void onCancel() {
        onCancel(getDialog());
        onDismiss(getDialog());
    }


    /**
     * will call dialogFinishHolder.onDialogCancel if have,
     * then call dialogNoHolder.onNoClick if have
     *
     * @param dialog
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        Logger.D(TAG, "on onCancel");
        DialogFinishHolder holder = null;
        if (getTargetFragment() instanceof DialogFinishHolder) {
            holder = (DialogFinishHolder) getTargetFragment();
        } else if (getActivity() instanceof DialogFinishHolder) {
            holder = (DialogFinishHolder) getActivity();
        }
        if (holder != null) {
            holder.doOnDialogCancel(getRequestCode());
        } else {
            DialogNoHolder noHolder = null;
            if (getTargetFragment() instanceof DialogNoHolder) {
                noHolder = (DialogNoHolder) getTargetFragment();
            } else if (getActivity() instanceof DialogNoHolder) {
                noHolder = (DialogNoHolder) getActivity();
            }
            if (noHolder != null) {
                noHolder.doOnDialogNoClick(getRequestCode());
            }
        }
        super.onCancel(dialog);
    }

    /**
     * will call dialogFinishHolder.onDialogFinish if have,
     *
     * @param dialog
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        Logger.D(TAG, "on dismiss");
        submitPressed = false;
        if (getActivity() == null) return;
        DialogFinishHolder holder = null;
        // use fragment bigger then activity
        if (getActivity() instanceof DialogFinishHolder) {
            holder = (DialogFinishHolder) getActivity();
        }
        if (getTargetFragment() instanceof DialogFinishHolder) {
            holder = (DialogFinishHolder) getTargetFragment();
        }
        if (holder != null) {
            holder.doOnDialogDismiss(getRequestCode());
        }
        try {
            dismiss();
            if (fm != null) {
                fm.popBackStack();
            }
        } catch (IllegalStateException ignored) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        } finally {
            fm = null;
        }
    }

    protected abstract int getRequestCode();

    protected abstract String getTagName();

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
