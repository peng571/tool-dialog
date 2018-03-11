package dev.momo.library.view.dialog.picker;


import android.support.annotation.StringRes;

import dev.momo.library.core.tool.ResourceHelper;
import dev.momo.library.view.dialog.picker.PickerCallback;

public class PickerItem<T> {

    private T object;
    private String name;
    private PickerCallback callback;

    public PickerItem(@StringRes int nameRes, PickerCallback<String> callback) {
        this(ResourceHelper.getString(nameRes), callback);
    }

    public PickerItem(String name, PickerCallback<String> callback) {
        this.object = (T) name;
        this.name = name;
        this.callback = callback;
    }

    public PickerItem(T object, PickerOption<T> option, PickerCallback<T> callback) {
        this.object = object;
        this.name = option.getName(object);
        this.callback = callback;
    }

    public void click() {
        callback.onPick(0, getValue());
    }


    public String getName() {
        return name;
    }

    public T getValue() {
        return object;
    }


    public PickerCallback getCallback() {
        return callback;
    }

    public interface PickerOption<T> {
        String getName(T t);
    }

}
