//package com.alchema.app.ui;
//
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//
//import com.nineoldandroids.animation.Animator;
//import com.nineoldandroids.animation.ObjectAnimator;
//import com.nineoldandroids.animation.PropertyValuesHolder;
//
//import dev.momo.library.core.log.Logger;
//import dev.momo.library.view.dialog.DialogFinishHolder;
//import dev.momo.library.view.dialog.DialogNoHolder;
//import dev.momo.library.view.dialog.DialogYesHolder;
//
///**
// * Created by momo peng on 2016/12/12.
// */
//
//public abstract class BaseDialogFragment extends DialogFragment
//        implements DialogInterface.OnShowListener {
//
//    private final static String TAG = BaseDialogFragment.class.getSimpleName();
//
//
//    /**
//     * For example.
//     * Create a new instance with argument.
//     */
//    //    private static BaseDialogFragment newInstance() {
//    //        BaseDialogFragment f = new BaseDialogFragment() {
//    //
//    //            @Override
//    //            protected int getWidth() {
//    //                return MATCH_PARENT;
//    //            }
//    //
//    //            @Override
//    //            protected int getHeight() {
//    //                return WRAP_CONTENT;
//    //            }
//    //
//    //            @Override
//    //            protected int getRequestCode() {
//    //                return 0;
//    //            }
//    //        };
//    //        Bundle args = new Bundle();
//    //        args.putString("key", "value");
//    //        f.setArguments(args);
//    //        return f;
//    //    }
//
//
//    /**
//     * The system calls this only when creating the layout in a dialog.
//     * Should not be call from other class
//     * Call newInstance to create dialog instead.
//     */
//    @Override
//    @Deprecated
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Do not override this method, unless needed to modify any dialog characteristics.
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setOnShowListener(this);
//        return dialog;
//    }
//
//
//    protected void onNext() {
//        if (getActivity() == null) return;
//
//        DialogYesHolder holder = null;
//        if (getActivity() instanceof DialogYesHolder)
//            holder = (DialogYesHolder) getActivity();
//        if (getTargetFragment() instanceof DialogYesHolder)
//            holder = (DialogYesHolder) getTargetFragment();
//
//        if (holder != null) {
//            holder.doOnDialogYesClick(getRequestCode());
//        }
//        dismiss();
//    }
//
//    protected void onCancel() {
//        onCancel(getDialog());
//        dismiss();
//    }
//
//    public void onShow(DialogInterface dialog) { }
//
//
//    /**
//     * will call dialogFinishHolder.onDialogCancel if have,
//     * then call dialogNoHolder.onNoClick if have
//     *
//     * @param dialog
//     */
//    @Override
//    public void onCancel(DialogInterface dialog) {
//        Logger.D(TAG, "onCancel");
//        if (getActivity() == null) return;
//
//        DialogFinishHolder holder = null;
//
//        if (getTargetFragment() instanceof DialogFinishHolder) {
//            holder = (DialogFinishHolder) getTargetFragment();
//        } else if (getActivity() instanceof DialogFinishHolder) {
//            holder = (DialogFinishHolder) getActivity();
//        }
//        if (holder != null) {
//            holder.doOnDialogCancel(getRequestCode());
//        } else {
//            DialogNoHolder noHolder = null;
//            if (getTargetFragment() instanceof DialogNoHolder) {
//                noHolder = (DialogNoHolder) getTargetFragment();
//            } else if (getActivity() instanceof DialogNoHolder) {
//                noHolder = (DialogNoHolder) getActivity();
//            }
//            if (noHolder != null) {
//                noHolder.doOnDialogNoClick(getRequestCode());
//            }
//        }
//        super.onCancel(dialog);
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // resize dialog to full screen
//        Dialog dialog = getDialog();
//        if (dialog == null) return;
//
//        Window window = dialog.getWindow();
//        if (window == null) {
//            Logger.WS(TAG, "get dialog window null");
//            return;
//        }
//
//
//        window.setLayout(getWidth(), getHeight());
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        // show pop-on anim
//        final View decorView = window.getDecorView();
//        if (decorView == null) {
//            Logger.WS(TAG, "get decorview null");
//            return;
//        }
//        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
//                PropertyValuesHolder.ofFloat("scaleX", 0f, 1.0f),
//                PropertyValuesHolder.ofFloat("scaleY", 0f, 1.0f),
//                PropertyValuesHolder.ofFloat("alpha", 0f, 1.0f));
//        scaleDown.setDuration(250);
//        scaleDown.start();
//    }
//
//
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        Logger.D(TAG, "onDismiss");
//        if (getDialog() == null) return;
//        if (getDialog().getWindow() == null) return;
//        if (getDialog().getWindow().getDecorView() == null) return;
//
//        // show pop-off anim
//        final View decorView = getDialog()
//                .getWindow()
//                .getDecorView();
//
//        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
//                PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0f),
//                PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0f),
//                PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f));
//        scaleDown.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                BaseDialogFragment.super.dismiss();
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
//        scaleDown.setDuration(200);
//        scaleDown.start();
//
//        if (getActivity() == null) return;
//        DialogFinishHolder holder = null;
//        // use fragment bigger then activity
//        if (getActivity() instanceof DialogFinishHolder) {
//            holder = (DialogFinishHolder) getActivity();
//        }
//        if (getTargetFragment() instanceof DialogFinishHolder) {
//            holder = (DialogFinishHolder) getTargetFragment();
//        }
//        if (holder != null) {
//            holder.doOnDialogDismiss(getRequestCode());
//        }
//    }
//
//    protected abstract int getWidth();
//
//    protected abstract int getHeight();
//
//    protected abstract int getRequestCode();
//
//}