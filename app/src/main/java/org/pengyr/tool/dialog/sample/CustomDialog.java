package org.pengyr.tool.dialog.sample;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pengyr.tool.dialog.BaseDialogFragment;
import org.pengyr.tool.dialog.sample.databinding.CustomDialogBinding;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Peng on 2018/5/15.
 */

public class CustomDialog extends BaseDialogFragment {

    final static String TAG = CustomDialog.class.getSimpleName();

    final static int CUSTOM_RESULT = 126;

    private final static String EXTRA_KEY_MESSAGE = "MESSAGE";


    /**
     * Create a new instance with argument.
     */
    public static CustomDialog newInstance(String message) {
        CustomDialog f = new CustomDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString(EXTRA_KEY_MESSAGE, message);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        CustomDialogBinding binding = DataBindingUtil.inflate(inflater, R.layout.custom_dialog, container, true);
        String message = getArguments().getString(EXTRA_KEY_MESSAGE, "");
        binding.messageView.setText(message);
        binding.okButton.setOnClickListener((View view) -> onNext());
        return binding.getRoot();
    }

    @Override
    protected int getWidth() {
        return MATCH_PARENT;
    }

    @Override
    protected int getHeight() {
        return WRAP_CONTENT;
    }

    @Override
    protected int getRequestCode() {
        return CUSTOM_RESULT;
    }

    @Override
    protected String getTagName() {
        return TAG;
    }

}
