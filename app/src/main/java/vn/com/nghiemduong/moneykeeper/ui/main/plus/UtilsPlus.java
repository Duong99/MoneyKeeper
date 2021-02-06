package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Lớp dùng chung cho màn hình thêm chi tiền, ...
 * - @created_by nxduong on 28/1/2021
 **/
public class UtilsPlus {

    /**
     * @param data, ivImageAccount, tvTitleAccount, context
     *              Hàm có nhiệm vụ đọc giữ liệu khi người dùng chọn tài khoản
     *              vài trả dữ liệu lên View
     * @return account
     * - @created_by nxduong on 28/1/2021
     **/
    public static Account onFinishChooseAccount(Intent data, ImageView ivImageAccount,
                                                TextView tvTitleAccount, Context context) {
        Account account = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");

        assert account != null;
        ivImageAccount.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                account.getAccountTypePath(), context));
        tvTitleAccount.setText(account.getAccountName());
        return account;
    }

    /**
     * @param data, ivImageCategory, tvTitleCategory, context
     *              <p>
     *              Hàm có nhiệm vụ đọc giữ liệu khi người dùng chọn hạng mục
     *              vài trả dữ liệu lên View
     * @return category
     * - @created_by nxduong on 28/1/2021
     **/
    public static Category onFinishChooseCategory(Intent data, ImageView ivImageCategory,
                                                  TextView tvTitleCategory, Context context) {
        Category category =
                (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                        .getSerializable("BUNDLE_CATEGORY");

        assert category != null;
//        tvTitleCategory.setText(category.getTitle());
//        ivImageCategory.setImageBitmap(
//                AppUtils.convertPathFileImageAssetsToBitmap(category.getImage(), context));
        return category;
    }

    /**
     * Hàm lấy thời gian hiện tại
     *
     * @return currentDateAndTime
     * @created_by nxduong on 29/1/2021
     */
    public static String getTimeCurrent() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        return currentTime;
    }

    /**
     * -  Hàm lấy trả về thời gian được trả 29/12021
     * <p>
     * - @created_by nxduong on
     **/
    public static void selectTimeByDialog(final TextView tvTime, Context context) {
        final String[] timeSelected = tvTime.getText().toString().split(":");
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour;
                String minute;
                if (selectedHour < 10) {
                    hour = "0" + selectedHour;
                } else {
                    hour = String.valueOf(selectedHour);
                }

                if (selectedMinute < 10) {
                    minute = "0" + selectedHour;
                } else {
                    minute = String.valueOf(selectedMinute);
                }
                tvTime.setText(hour + ":" + minute);
            }

        }, Integer.parseInt(timeSelected[0]), Integer.parseInt(timeSelected[1]), true);  //Yes 24 hour time
        mTimePicker.show();
    }

    // Hàm lấy ngày hiện tại
    public static String getDateCurrent() {
        SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        return currentDate;
    }
}
