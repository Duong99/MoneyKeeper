package vn.com.nghiemduong.moneykeeper.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * - class kiểm tra quyền trong ứng dụng
 * - @created_by nxduong on 31/1/2021
 **/

public class AppPermission extends AppCompatActivity {
    public final static int PERMISSIONS_REQUEST_READ_CONTACTS = 123;
    public final static int PERMISSIONS_REQUEST_SEND_SMS = 128;
    public final static int PERMISSIONS_REQUEST_CALL_PHONE = 129;
    public final static int PERMISSIONS_REQUEST_CAMERA = 1291;

    /**
     * Hàm kiểm tra quyền đọc danh bạ trong điện thoại
     *
     * @param context, activity
     * @return checkPermission
     * @created_by nxduong on 21/1/2021
     */
    public static boolean requestReadContactPermission(Context context, Activity activity) {
        boolean checkPermission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.READ_CONTACTS)) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                checkPermission = true;
            }
        } else {
            checkPermission = true;
        }

        return checkPermission;
    }

    /**
     * Kiểm tra quyền ghi contact
     *
     * @created_by nxduong on 21/1/2021
     */

    public static boolean requestWriteContactPermission(Context context, Activity activity, int request) {
        boolean checkPermission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.WRITE_CONTACTS)) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.WRITE_CONTACTS},
                            request);
                }
            } else {
                checkPermission = true;
            }
        } else {
            checkPermission = true;
        }

        return checkPermission;
    }

    /**
     * Kiểm tra quyền gửi tin nhắn
     *
     * @param
     * @return
     * @created_by nxduong on 21/1/2021
     * @see
     */

    public static boolean requestSendSMSPermission(Context context, Activity activity) {
        boolean checkPermission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.SEND_SMS)) {

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.SEND_SMS},
                            PERMISSIONS_REQUEST_SEND_SMS);
                }
            } else {
                checkPermission = true;
            }
        } else {
            checkPermission = true;
        }

        return checkPermission;
    }

    /**
     * Kiểm tra quyền call phone
     *
     * @created_by nxduong on
     */

    public static boolean requestCallPhonePermission(Context context, Activity activity) {
        boolean checkPermission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.CALL_PHONE)) {

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CALL_PHONE},
                            PERMISSIONS_REQUEST_CALL_PHONE);
                }
            } else {
                checkPermission = true;
            }
        } else {
            checkPermission = true;
        }

        return checkPermission;
    }

    /**
     * Kiểm tra quyền camera
     *
     * @param context activity
     * @created_by nxduong on 1/2/2021
     */

    public static boolean requestCameraPermission(Context context, Activity activity) {
        boolean checkPermission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.CAMERA)) {

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSIONS_REQUEST_CAMERA);
                }
            } else {
                checkPermission = true;
            }
        } else {
            checkPermission = true;
        }

        return checkPermission;
    }
}