package vn.com.nghiemduong.moneykeeper.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;

import static android.content.Context.MODE_PRIVATE;

/**
 * Class chứa các hàm dùng chung cho project
 * - @created_by nxduong on 21/1/2021
 **/
public class AppUtils {

    // hàm sử lý khi ứng dụng Exception
    public static void handlerException(Exception e) {
        Log.e(AppConstants.TAG, e.toString());
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
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }

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
        if (bitmap != null) {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                return stream.toByteArray();
            } catch (Exception e) {
                handlerException(e);
            }
        }
        return null;
    }

    // Hàm trả về tên loại tài khoản
    public static String getNameAccountType(int accountTypeId, Context context) {
        switch (accountTypeId) {
            case AppConstants.TIEN_MAT:
                return context.getResources().getString(R.string.cash);

            case AppConstants.TAI_KHOAN_NGAN_HANG:
                return context.getResources().getString(R.string.account_bank);

            case AppConstants.THE_TIN_DUNG:
                return context.getResources().getString(R.string.credit);

            case AppConstants.TAI_KHOAN_DAU_TU:
                return context.getResources().getString(R.string.account_invest);

            case AppConstants.KHAC:
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
                activity.getSharedPreferences("MY_SHARED_PREFERENCES", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }

    /**
     * @return format time: định dạng thời gian
     * @created_by nxduong on 12/3/2021
     */
    public static String getFormatTime(Activity activity) {
        SharedPreferences sharedPreferences =
                activity.getSharedPreferences("MY_SHARED_PREFERENCES", MODE_PRIVATE);
        return sharedPreferences.getString("FORMAT_TIME", AppConstants.FORMAT_TIME_ISO_8601);
    }

    public static String getFormatTimeDefault() {
        return AppConstants.FORMAT_TIME_US;
    }

    public static SimpleDateFormat getSimpleDateFormatDefault() {
        return new SimpleDateFormat(getFormatTimeDefault(),
                Locale.getDefault());
    }

    /**
     * Yêu cầu vào thời gian đã được format theo kiểu việt nam dd/MM/yyyy
     * Hàm format thời gian
     *
     * @created_by nxduong on 12/3/2021
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String date, Activity activity) {
        SimpleDateFormat sdfDefault = getSimpleDateFormatDefault();

        SimpleDateFormat sdfFormat = new SimpleDateFormat(getFormatTime(activity),
                Locale.getDefault());
        try {
            Date dateFormatDefault = sdfDefault.parse(date);
            if (dateFormatDefault != null) {
                return sdfFormat.format(dateFormatDefault);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateDefault(String date, Activity activity) {
        SimpleDateFormat sdfDefault = getSimpleDateFormatDefault();

        SimpleDateFormat sdfFormat = new SimpleDateFormat(getFormatTime(activity),
                Locale.getDefault());
        try {
            Date dateFormatDefault = sdfFormat.parse(date);
            if (dateFormatDefault != null) {
                return sdfDefault.format(dateFormatDefault);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Hàm so sánh ngày tháng với ngày tháng hiện tại
     * kết quả  > 0 compareDate sinh ra sau currentDate
     * < 0 compareDate sinh ra trước currentDate
     * = 0 bằng nhau
     *
     * @created_by nxduong on 12/3/2021
     */
    @SuppressLint("SimpleDateFormat")
    public static int compareDateWithCurrentDate(String compareDate, Activity activity) {
        SimpleDateFormat sdfFormatDefault = getSimpleDateFormatDefault();
        SimpleDateFormat sdfFormat = new SimpleDateFormat(getFormatTime(activity), Locale.getDefault());


        //SimpleDateFormat sdfFormat = new SimpleDateFormat(getFormatTime(activity), Locale.getDefault());
        try {
            Date date = sdfFormatDefault.parse(compareDate);

            String sDateCurrent = sdfFormatDefault.format(Objects.requireNonNull(
                    sdfFormat.parse(UtilsPlus.getDateCurrent(activity))));
            Date currentDate = sdfFormatDefault.parse(sDateCurrent);

            if (currentDate != null && date != null) {
                return date.compareTo(currentDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
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
    public static String UpDownDate(int i, Activity activity) {
        // Định dạng thời gian
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar c1 = Calendar.getInstance();

        Date date = new Date(sdf.format(new Date().getTime()));
        c1.setTime(date);
        switch (i) {
            case 1: // Thống kê tuần này
                c1.roll(Calendar.DATE, -7);
                return dateFormat.format(c1.getTime());

            case 2: // Thống kê tháng này
                c1.roll(Calendar.DATE, -(Integer.parseInt(UtilsPlus.getDateCurrent(activity).substring(0, 2)) - 1));
                return dateFormat.format(c1.getTime());

            case 3: // Thống kê quý này
                if (Calendar.MONTH < 4) {
                    c1.roll(Calendar.MONTH, -(Calendar.MONTH - 1));
                    c1.roll(Calendar.DATE,
                            -(Integer.parseInt(UtilsPlus.getDateCurrent(activity).substring(0, 2)) - 1));
                } else {
                    c1.roll(Calendar.MONTH, -3);
                }

                return dateFormat.format(c1.getTime());

            case 4: // Thống kê năm nay
                c1.roll(Calendar.MONTH, -(Calendar.MONTH - 1));
                c1.roll(Calendar.DATE,
                        -(Integer.parseInt(UtilsPlus.getDateCurrent(activity).substring(0, 2)) - 1));
                return dateFormat.format(c1.getTime());
        }

        return "";
    }

}
