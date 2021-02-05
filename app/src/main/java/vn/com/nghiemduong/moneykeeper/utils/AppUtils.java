package vn.com.nghiemduong.moneykeeper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;

import com.doodle.android.chips.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import vn.com.nghiemduong.moneykeeper.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Class chứa các hàm dùng chung cho project
 * - @created_by nxduong on 21/1/2021
 **/
public class AppUtils {
    public static final String MY_SHARED_PREFERENCES_UID = "MY_SHARED_PREFERENCES_UID";
    public static final String KEY_UID = "UID";
    public static final String TAG = "fata";

    // Loại tài khoản
    public static final int TIEN_MAT = 1;
    public static final int TAI_KHOAN_NGAN_HANG = 2;
    public static final int THE_TIN_DUNG = 3;
    public static final int TAI_KHOAN_DAU_TU = 4;
    public static final int KHAC = 5;

    // Loại tiền
    public static final String VND = "VND";

    //Có báo cho vào báo cáo không
    public static final int CO_BAO_CAO = 1;
    public static final int KHONG_BAO_CAO = 0;

    public static final int REQUEST_CODE_IMAGE_FROM_FOLDER = 120;
    public static final int REQUEST_CODE_IMAGE_FROM_CAMERA = 121;


    // hàm sử lý khi ứng dụng Exception
    public static void handlerException(Exception e) {
        Log.e(TAG, e.toString());
    }

    // Hàm lấy giá trị trong EditText
    public static String getEditText(EditText et) {
        return et.getText().toString();
    }

    // Hàm convert từ  byte[] sang bitmap
    public static Bitmap convertByteArrayToBitmap(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    /**
     * Hàm convert file ảnh trong assets thành bitmap
     *
     * @param path
     * @return bitmap
     * @created_by nxduong on 2/2/2021
     */

    public static Bitmap convertPathFileImageAssetsToBitmap(String path, Context context) {
        Bitmap bitmap = null;
        try {
            InputStream is;
            is = context.getAssets().open(path);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // hàm convert từ bitmap sang byte[]
    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
            byte[] image = stream.toByteArray();
            return image;
        } catch (Exception e) {
            handlerException(e);
        }
        return null;
    }

    // Hàm trả về tên loại tài khoản
    public static String getNameAccountType(int accountTypeId, Context context) {
        switch (accountTypeId) {
            case TIEN_MAT:
                return context.getResources().getString(R.string.cash);

            case TAI_KHOAN_NGAN_HANG:
                return context.getResources().getString(R.string.account_bank);

            case THE_TIN_DUNG:
                return context.getResources().getString(R.string.credit);

            case TAI_KHOAN_DAU_TU:
                return context.getResources().getString(R.string.account_invest);

            case KHAC:
                return context.getResources().getString(R.string.account_more);

        }
        return "";
    }

    /**
     * Hàm đọc file ảnh từ trong assets rồi trả về kiểu byte[]
     * <p>
     * - @created_by nxduong on 28/1/2021
     **/
    public static byte[] getByteImageFromAssets(String path, Context context) {
        InputStream is = null;
        try {
            is = context.getAssets().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return AppUtils.convertBitmapToByteArray(bitmap);
    }


    /**
     * @return uid: id người dùng
     * @created_by nxduong on 2/2/2021
     */
    public String getUIDAuthenticationFirebase(Activity activity) {
        SharedPreferences sharedPreferences =
                activity.getSharedPreferences(MY_SHARED_PREFERENCES_UID, MODE_PRIVATE);
        String uid = sharedPreferences.getString(KEY_UID, "");
        return uid;
    }
}
