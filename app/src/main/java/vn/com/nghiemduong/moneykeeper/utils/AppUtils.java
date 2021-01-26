package vn.com.nghiemduong.moneykeeper.utils;

import android.util.Log;
import android.widget.EditText;

/**
 -

    *
 - @created_by nxduong on 21/1/2021

**/
public class AppUtils {
    public static final String TAG = "TAG_LOG";

    public static void handlerException(Exception e){
        Log.e(TAG, e.toString());
    }

    public static String getEditText(EditText et){
        return et.getText().toString();
    }
}
