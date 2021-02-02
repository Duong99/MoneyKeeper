package vn.com.nghiemduong.moneykeeper.ui.main.plus.collect;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.dialog.date.CustomDateTimeDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.PlusFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.ChooseCategoriesActivity;

import static android.app.Activity.RESULT_OK;

/**
 * -
 * Màn chi thu tiền
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class CollectMoneyFragment extends BaseFragment implements View.OnClickListener,
        CustomDateTimeDialog.IOnClickSaveDateTime {
    private View mView;
    private RelativeLayout rlChooseCategoryCollect, rlChooseAccountCollect;
    private ImageView ivImageCategoriesCollect, ivImageAccountCollect;
    private TextView tvTitleSelectCategoryCollect, tvCalendarCollect, tvTimeCollect,
            tvTitleAccountCollect;


    public CollectMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_collect_money, container, false);

        init();
        return mView;
    }

    // Khởi tạo / Ánh xạ cho view
    private void init() {
        rlChooseCategoryCollect = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategoryCollect.setOnClickListener(this);

        rlChooseAccountCollect = mView.findViewById(R.id.rlChooseAccount);
        rlChooseAccountCollect.setOnClickListener(this);

        tvCalendarCollect = mView.findViewById(R.id.tvCalendar);
        tvCalendarCollect.setOnClickListener(this);
        tvCalendarCollect.setText(UtilsPlus.getDateCurrent());

        tvTimeCollect = mView.findViewById(R.id.tvTime);
        tvTimeCollect.setOnClickListener(this);
        tvTimeCollect.setText(UtilsPlus.getTimeCurrent());

        ivImageCategoriesCollect = mView.findViewById(R.id.ivImageCategories);
        tvTitleSelectCategoryCollect = mView.findViewById(R.id.tvTitleSelectCategory);
        tvTitleAccountCollect = mView.findViewById(R.id.tvTitleAccount);
        ivImageAccountCollect = mView.findViewById(R.id.ivImageAccount);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseCategory:
                startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                        ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY);
                break;

            case R.id.tvCalendar:
                CustomDateTimeDialog customDateDialog = new CustomDateTimeDialog(getContext(),
                        CustomDateTimeDialog.KEY_CALENDAR, tvCalendarCollect.getText().toString(),
                        tvTimeCollect.getText().toString(), this);
                customDateDialog.show();
                break;

            case R.id.tvTime:
                CustomDateTimeDialog customTimeDialog = new CustomDateTimeDialog(getContext(),
                        CustomDateTimeDialog.KEY_WATCH, tvCalendarCollect.getText().toString(),
                        tvTimeCollect.getText().toString(), this);
                customTimeDialog.show();
                break;

            case R.id.rlChooseAccount:
                Intent intent = new Intent(getContext(), ChooseAccountActivity.class);
                startActivityForResult(intent, ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY:
                        UtilsPlus.onFinishChooseCategory(data, ivImageCategoriesCollect,
                                tvTitleSelectCategoryCollect);
                        break;

                    case ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT:
                        UtilsPlus.onFinishChooseAccount(data, ivImageAccountCollect,
                                tvTitleAccountCollect, getContext());
                        break;
                }
            }
        }
    }

    @Override
    public void saveDateTime(String date, String time) {
        tvTimeCollect.setText(time);
        tvCalendarCollect.setText(date);
    }
}