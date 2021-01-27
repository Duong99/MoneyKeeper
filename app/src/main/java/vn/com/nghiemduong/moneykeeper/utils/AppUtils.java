package vn.com.nghiemduong.moneykeeper.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;

/**
 -

    *
 - @created_by nxduong on 21/1/2021

**/
public class AppUtils {
    public static final String TAG = "fata";

    // Loại tài khoản
    public static final int TIEN_MAT  = 1;
    public static final int TAI_KHOAN_NGAN_HANG = 2;
    public static final int THE_TIN_DUNG = 3;
    public static final int TAI_KHOAN_DAU_TU = 4;
    public static final int KHAC = 5;

    // Loại tiền
    public static final int VND  = 1;

    //Có báo cho vào báo cáo không
    public static final int CO_BAO_CAO  = 1;
    public static final int KHONG_BAO_CAO  = 0;




    public static void handlerException(Exception e){
        Log.e(TAG, e.toString());
    }

    public static String getEditText(EditText et){
        return et.getText().toString();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bytes){

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
            byte[] image  = stream.toByteArray();
            return image;
        }catch (Exception e){
            handlerException(e);
        }
        return null;
    }
}
