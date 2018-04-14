package org.pengyr.tool.dialog.picker;

import android.support.annotation.NonNull;

public class ObjectPickerItem<T> implements PickerItem<String, T> {

    @NonNull final private T object;
    final private String name;
    final private PickerCallback<T> callback;

    public ObjectPickerItem(@NonNull T object, @NonNull ObjectPickItemNamer<T> option, PickerCallback<T> callback) {
        this.object = object;
        this.name = option.getName(object);
        this.callback = callback;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return object;
    }

    protected PickerCallback<T> getCallback() {
        return callback;
    }

    @Override public void onPickUp(int index) {
        if (getCallback() == null) return;
        getCallback().onItemPickup(index, getValue());
    }


    public interface ObjectPickItemNamer<T> {
        @NonNull String getName(T t);
    }

}
