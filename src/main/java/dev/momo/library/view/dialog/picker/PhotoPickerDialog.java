//package dev.momo.library.view.dialog.picker;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.support.annotation.Nullable;
//
//import dev.momo.library.core.image.PhotoPicker;
//import dev.momo.library.view.R;
//
///**
// * Work with PhotoPicker
// * show normal option when pick up image
// * <p>
// * - choose from gallery
// * - tack a photo
// * <p>
// * Created by momo peng on 2017/3/28.
// */
//
//public class PhotoPickerDialog {
//
//    private final static int REQUEST_PICK_PHOTO_CODE = 1;
//
//    public static PickerDialog get(final Activity activity, @Nullable String authorities) {
//        return PickerDialog.newInstance(R.string.photo_title, REQUEST_PICK_PHOTO_CODE)
//                .addItem(R.string.photo_camera, (int index, String name) ->
//                        PhotoPicker.startTakePictureIntent(activity, authorities))
//                .addItem(R.string.photo_from_gallery, (int index, String name) ->
//                        PhotoPicker.startPickImageIntent(activity));
//    }
//
//
//    public static PickerDialog get(final Fragment fragment, @Nullable String authorities) {
//        return PickerDialog.newInstance(R.string.photo_title, REQUEST_PICK_PHOTO_CODE)
//                .addItem(R.string.photo_camera, (int index, String name) ->
//                        PhotoPicker.startTakePictureIntent(fragment, authorities))
//                .addItem(R.string.photo_from_gallery, (int index, String name) ->
//                        PhotoPicker.startPickImageIntent(fragment));
//    }
//
//}
