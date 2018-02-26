package dev.momo.library.view.dialog.picker;


import android.support.annotation.StringRes;

import dev.momo.library.core.tool.ResourceHelper;
import dev.momo.library.view.dialog.picker.PickerCallback;

public class PickerItem {

    String name;
    PickerCallback callback;

    public PickerItem(@StringRes int nameRes, PickerCallback callback) {
        this(ResourceHelper.getString(nameRes), callback);
    }

    public PickerItem(String name, PickerCallback callback) {
        this.name = name;
        this.callback = callback;
    }
}
