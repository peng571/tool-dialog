package org.pengyr.tool.dialog.picker;

/**
 * Created by Peng on 2018/4/14.
 */

public class SimplePickerItem extends ObjectPickerItem<String> {

    /**
     * @param name     pick item name
     * @param callback callback
     */
    public SimplePickerItem(String name, PickerCallback<String> callback) {
        super(name, (v) -> name, callback);
    }

}
