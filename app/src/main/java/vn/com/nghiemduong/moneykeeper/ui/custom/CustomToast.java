package vn.com.nghiemduong.moneykeeper.ui.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.datatype.Duration;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Custom lại toast
 * - @created_by nxduong on 11/2/2021
 **/
public class CustomToast extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public CustomToast(Context context) {
        super(context);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static Toast makeToast(Context context, String message, int type) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast,
                null, false);
        LinearLayout llBackgroundCustomToast = view.findViewById(R.id.llBackgroundCustomToast);
        ImageView ivImageCustomToast = view.findViewById(R.id.ivImageCustomToast);
        TextView tvMessageCustomToast = view.findViewById(R.id.tvMessageCustomToast);
        tvMessageCustomToast.setText(message);

        switch (type) {
            case AppUtils.TOAST_SUCCESS:
                llBackgroundCustomToast.setBackgroundResource(R.drawable.bg_custom_toast_success);
                ivImageCustomToast.setBackground(context.getResources()
                        .getDrawable(R.drawable.ic_success_sync_40dp));
                llBackgroundCustomToast.setBackgroundColor(context.getResources()
                        .getColor(R.color.bg_toast_success));
                break;

            case AppUtils.TOAST_WARRING:
                llBackgroundCustomToast.setBackgroundResource(R.drawable.bg_custom_toast_warring);
                ivImageCustomToast.setBackground(context.getResources()
                        .getDrawable(R.drawable.v2_ic_info_white));
                llBackgroundCustomToast.setBackgroundColor(context.getResources()
                        .getColor(R.color.bg_toast_warring));
                break;

            case AppUtils.TOAST_ERROR:
                llBackgroundCustomToast.setBackgroundColor(context.getResources()
                        .getColor(R.color.bg_toast_error));
                break;
        }

        toast.setDuration(LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.setView(view);
        return toast;
    }
}
