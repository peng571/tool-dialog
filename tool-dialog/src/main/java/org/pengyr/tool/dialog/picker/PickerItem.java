package org.pengyr.tool.dialog.picker;

/**
 * Created by Peng on 2018/4/14.
 */

public interface PickerItem<N, V> {

    N getName();

    V getValue();

    void onPickUp(int index);

}
