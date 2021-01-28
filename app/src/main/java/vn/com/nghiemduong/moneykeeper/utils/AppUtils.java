package vn.com.nghiemduong.moneykeeper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import vn.com.nghiemduong.moneykeeper.R;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 21/1/2021
 **/
public class AppUtils {
    public static final String TAG = "fata";

    // Loại tài khoản
    public static final int TIEN_MAT = 1;
    public static final int TAI_KHOAN_NGAN_HANG = 2;
    public static final int THE_TIN_DUNG = 3;
    public static final int TAI_KHOAN_DAU_TU = 4;
    public static final int KHAC = 5;

    // Loại tiền
    public static final int VND = 1;

    //Có báo cho vào báo cáo không
    public static final int CO_BAO_CAO = 1;
    public static final int KHONG_BAO_CAO = 0;


    public static void handlerException(Exception e) {
        Log.e(TAG, e.toString());
    }

    public static String getEditText(EditText et) {
        return et.getText().toString();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

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
}
