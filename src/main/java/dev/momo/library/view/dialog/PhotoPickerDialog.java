package dev.momo.library.view.dialog;

import android.app.Activity;
import android.app.Fragment;

import dev.momo.library.core.image.PhotoPicker;
import dev.momo.library.view.R;

/**
 * Work with PhotoPicker
 * show normal option when pick up image
 * <p>
 * - choose from gallery
 * - tack a photo
 * <p>
 * Created by momo peng on 2017/3/28.
 */

public class PhotoPickerDialog {

    private final static int REQUEST_PICK_PHOTO_CODE = 1;

    public static PickerDialog get(final Activity activity) {
        return PickerDialog.newInstance(R.string.photo_title, REQUEST_PICK_PHOTO_CODE)
                .addItem(R.string.photo_camera, new PickerCallback() {
                    @Override
                    public void onPick(int index, String name) {
                        PhotoPicker.startTakePictureIntent(activity);
                    }
                })
                .addItem(R.string.photo_from_gallery, new PickerCallback() {
                    @Override
                    public void onPick(int index, String name) {
                        PhotoPicker.startPickImageIntent(activity);
                    }
                });
    }


    public static PickerDialog get(final Fragment fragment) {
        return PickerDialog.newInstance(R.string.photo_title, REQUEST_PICK_PHOTO_CODE)
                .addItem(R.string.photo_camera, new PickerCallback() {
                    @Override
                    public void onPick(int index, String name) {
                        PhotoPicker.startTakePictureIntent(fragment);
                    }
                })
                .addItem(R.string.photo_from_gallery, new PickerCallback() {
                    @Override
                    public void onPick(int index, String name) {
                        PhotoPicker.startPickImageIntent(fragment);
                    }
                });
    }

}
