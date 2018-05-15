package org.pengyr.tool.dialog.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.pengyr.tool.dialog.OKDialog;
import org.pengyr.tool.dialog.YesNoDialog;
import org.pengyr.tool.dialog.holder.DialogFinishHolder;
import org.pengyr.tool.dialog.holder.DialogNoHolder;
import org.pengyr.tool.dialog.holder.DialogYesHolder;
import org.pengyr.tool.dialog.picker.PickerCallback;
import org.pengyr.tool.dialog.picker.PickerDialog;
import org.pengyr.tool.dialog.picker.SimplePickerItem;
import org.pengyr.tool.dialog.sample.databinding.MainActivityBinding;


/**
 * Created by Peng on 2018/3/25.
 */

public class MainActivity extends AppCompatActivity implements DialogYesHolder, DialogNoHolder, DialogFinishHolder {

    private final static String TAG = MainActivity.class.getSimpleName();

    private MainActivityBinding binding;

    private final static int OK_RESULT = 1;
    private final static int YESNO_RESULT = 2;
    private final static int PICKER_RESULT = 3;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.okButton.setOnClickListener((v) -> showOKDialog());
        binding.yesnoButton.setOnClickListener((v) -> showYesNoDialog());
        binding.pickerButton.setOnClickListener((v) -> showPickerDialog());
        binding.picker2Button.setOnClickListener(v -> showPickerDialogWithCustomItem());
        binding.customButton.setOnClickListener((v) -> showCustomDialog());
    }

    @Override protected void onDestroy() {
        binding.unbind();
        super.onDestroy();
    }

    void showOKDialog() {
        // you can simply show an ok dialog with one line
        OKDialog.newInstance(R.string.ok_dialog_message, OK_RESULT).show(this);
    }

    void showYesNoDialog() {
        YesNoDialog.newInstance(R.string.yesno_dialog_message, YESNO_RESULT).show(this);
    }

    void showPickerDialog() {
        PickerCallback<String> onPick = (index, value) ->
                Toast.makeText(this, "Pick item [" + value + "] at index " + index, Toast.LENGTH_SHORT).show();
        PickerDialog.newInstance(R.string.picker_dialog_title, PICKER_RESULT)
                .addItem(new SimplePickerItem(getString(R.string.picker_item_1), onPick))
                .addItem(new SimplePickerItem(getString(R.string.picker_item_2), onPick))
                .addItem(new SimplePickerItem(getString(R.string.picker_item_3), onPick))
                .show(this);
    }

    void showPickerDialogWithCustomItem() {
        // TODO
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
    }

    void showCustomDialog() {
        CustomDialog.newInstance("I am a custom dialog, \nI can set with any layout.xml you like").show(this);
    }

    @Override public void doOnDialogNoClick(int request) {
        switch (request) {
            case YESNO_RESULT:
                Toast.makeText(this, "Yes no dialog result No", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override public void doOnDialogYesClick(int request) {
        switch (request) {
            case OK_RESULT:
                Toast.makeText(this, "Ok dialog result ok", Toast.LENGTH_SHORT).show();
                break;
            case YESNO_RESULT:
                Toast.makeText(this, "Yes no dialog result Yes", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override public void doOnDialogCancel(int request) {
        switch (request) {
            case OK_RESULT:
                Toast.makeText(this, "Ok dialog result cancel", Toast.LENGTH_SHORT).show();
                break;
            case YESNO_RESULT:
                Toast.makeText(this, "Yes no dialog result cancel", Toast.LENGTH_SHORT).show();
                break;
            case PICKER_RESULT:
                Toast.makeText(this, "Picker dialog result cancel", Toast.LENGTH_SHORT).show();
                break;
            case CustomDialog.CUSTOM_RESULT:
                Toast.makeText(this, "Custom dialog result cancel", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override public void doOnDialogDismiss(int request) {
        switch (request) {
            case OK_RESULT:
                Toast.makeText(this, "Ok dialog dismiss", Toast.LENGTH_SHORT).show();
                break;
            case YESNO_RESULT:
                Toast.makeText(this, "Yes no dialog dismiss", Toast.LENGTH_SHORT).show();
                break;
            case PICKER_RESULT:
                Toast.makeText(this, "Picker dialog dismiss", Toast.LENGTH_SHORT).show();
                break;
            case CustomDialog.CUSTOM_RESULT:
                Toast.makeText(this, "Custom dialog dismiss", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
