package org.pengyr.tool.dialog.picker;

import android.support.annotation.Nullable;

/**
 * Created by Peng on 2017/3/23.
 */

public interface PickerCallback<V> {

    /**
     *
     * @param index
     * @param value
     * @return
     */
    void onItemPickup(int index, @Nullable V value);

}
