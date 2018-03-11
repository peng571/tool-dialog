package dev.momo.library.view.dialog.picker;

/**
 * Created by Peng on 2017/3/23.
 */

public interface PickerCallback<T> {

    void onPick(int index, T value);

}
