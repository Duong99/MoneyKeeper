package vn.com.nghiemduong.moneykeeper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.doodle.android.chips.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;

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


    // Path
    public static final String PATH_THU = "assets/ImageCategory/THU/";
    public static final String PATH_CHI = "assets/ImageCategory/CHI/";
    public static final String PATH_UN_KNOW = "assets/ImageCategory/CHI/CHI_UnKnow.png";

    // Loại tiền
    public static final String VND = "VND";

    //Có báo cho vào báo cáo không
    public static final int CO_BAO_CAO = 1;
    public static final int KHONG_BAO_CAO = 0;

    public static final int REQUEST_CODE_IMAGE_FROM_FOLDER = 120;
    public static final int REQUEST_CODE_IMAGE_FROM_CAMERA = 121;

    // Custom toast
    public static final int TOAST_SUCCESS = 21;
    public static final int TOAST_WARRING = 22;
    public static final int TOAST_ERROR = 23;

    // Các trạng thái của kế hoạch /định kỳ
    public static final int WAIT_PAY = 2;
    public static final int PAID = 1;

    // Các kiểu /Loại hạng mục
    public static final int CHI_TIEN = 1;
    public static final int THU_TIEN = 2;
    public static final int CHO_VAY = 3;
    public static final int THU_NO = 4;
    public static final int DI_VAY = 5;
    public static final int TRA_NO = 6;

    public static final int VAY_NO = 3;
    // Cập độ hạng mục
    public static final int CAP_DO_1 = 1;
    public static final int CAP_DO_2 = 2;


    // hàm sử lý khi ứng dụng Exception
    public static void handlerException(Exception e) {
        Log.e(TAG, e.toString());
    }

    // Hàm lấy giá trị trong EditText
    public static String getEditText(EditText et) {
        return et.getText().toString();
    }

    // Hàm lấy giá trị trong EditText khi format số
    public static String getEditTextFormatNumber(EditText et) {
        return et.getText().toString().replaceAll(",", "");
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
    public static String getUIDAuthenticationFirebase(Activity activity) {
        SharedPreferences sharedPreferences =
                activity.getSharedPreferences(MY_SHARED_PREFERENCES_UID, MODE_PRIVATE);
        return sharedPreferences.getString(KEY_UID, "");
    }

    /**
     * Hàm format số trong EditText khi EditText thay đổi
     *
     * @param et EditText cần format
     * @created_by nxduong on 17/2/2021
     */

    public static void formatNumberEditText(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    et.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                et.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    et.setText(formattedString);
                    et.setSelection(et.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                et.addTextChangedListener(this);
            }
        });
    }

    public static String formatNumber(String number) {
        Long longval;
        if (number.contains(",")) {
            number = number.replaceAll(",", "");
        }
        longval = Long.parseLong(number);

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");
        String formattedString = formatter.format(longval);
        return formattedString;
    }

    /**
     * Hàm tính khoảng thời gian
     *
     * @param i khoảng thời gian cần tính
     * @created_by nxduong on 19/2/2021
     */
    public static String UpDownDate(int i) {
        // Định dạng thời gian
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar c1 = Calendar.getInstance();

        Date date = Date.valueOf(sdf.format(new java.util.Date().getTime()));
        c1.setTime(date);
        switch (i) {
            case 1: // Thống kê tuần này
                c1.roll(Calendar.DATE, -7);
                return dateFormat.format(c1.getTime());

            case 2: // Thống kê tháng này
                c1.roll(Calendar.DATE, -(Integer.parseInt(UtilsPlus.getDateCurrent().substring(0, 2)) - 1));
                return dateFormat.format(c1.getTime());

            case 3: // Thống kê quý này
                if (Calendar.MONTH < 4) {
                    c1.roll(Calendar.MONTH, -(Calendar.MONTH - 1));
                    c1.roll(Calendar.DATE,
                            -(Integer.parseInt(UtilsPlus.getDateCurrent().substring(0, 2)) - 1));
                } else {
                    c1.roll(Calendar.MONTH, -3);
                }

                return dateFormat.format(c1.getTime());

            case 4: // Thống kê năm nay
                c1.roll(Calendar.MONTH, -(Calendar.MONTH - 1));
                c1.roll(Calendar.DATE,
                        -(Integer.parseInt(UtilsPlus.getDateCurrent().substring(0, 2)) - 1));
                return dateFormat.format(c1.getTime());
        }

        return "";
    }

}
