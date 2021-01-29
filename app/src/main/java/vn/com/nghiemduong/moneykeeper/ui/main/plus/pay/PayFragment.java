package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.ChooseCategoriesActivity;
import vn.com.nghiemduong.moneykeeper.ui.view.date.CustomCalendarTimeView;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;


/**
 * -  Màn hình chi tiền
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class PayFragment extends BaseFragment implements PayMvpView, View.OnClickListener {
    private View mView;
    private RelativeLayout rlChooseCategoryPay, rlChooseAccountPay, rlSelectCategoryFee,
            rlRepaymentDate, rlLender;
    private ImageView ivImageCategoriesPay, ivImageAccountPay, ivImageCategoryFee;
    private TextView tvTitleSelectCategoryPay, tvTitleAccountPay, tvTitleCategoryFee;

    private TextView tvTimePay, tvCalenderPay;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swtFee, swtLoanToPayAmount;
    private LinearLayout llContentFee, llContentLoanToPayAmount;


    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_pay, container, false);
        init();

        swtFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llContentFee.setVisibility(View.VISIBLE);
                } else {
                    llContentFee.setVisibility(View.GONE);
                }
            }
        });

        swtLoanToPayAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llContentLoanToPayAmount.setVisibility(View.VISIBLE);
                } else {
                    llContentLoanToPayAmount.setVisibility(View.GONE);
                }
            }
        });
        return mView;
    }

    private void init() {
        rlChooseCategoryPay = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategoryPay.setOnClickListener(this);

        rlLender = mView.findViewById(R.id.rlLender);
        rlLender.setOnClickListener(this);

        swtFee = mView.findViewById(R.id.swtFee);
        swtLoanToPayAmount = mView.findViewById(R.id.swtLoanToPayAmount);

        rlChooseAccountPay = mView.findViewById(R.id.rlChooseAccount);
        rlChooseAccountPay.setOnClickListener(this);

        rlSelectCategoryFee = mView.findViewById(R.id.rlSelectCategoryFee);
        rlSelectCategoryFee.setOnClickListener(this);

        rlRepaymentDate = mView.findViewById(R.id.rlRepaymentDate);
        rlRepaymentDate.setOnClickListener(this);

        tvTimePay = mView.findViewById(R.id.tvTime);
        tvTimePay.setOnClickListener(this);
        tvTimePay.setText(UtilsPlus.getTimeCurrent());

        tvCalenderPay = mView.findViewById(R.id.tvCalendar);
        tvCalenderPay.setOnClickListener(this);
        tvCalenderPay.setText(UtilsPlus.getDateCurrent());

        ivImageCategoriesPay = mView.findViewById(R.id.ivImageCategories);
        llContentFee = mView.findViewById(R.id.llContentFee);
        llContentLoanToPayAmount = mView.findViewById(R.id.llContentLoanToPayAmount);
        tvTitleSelectCategoryPay = mView.findViewById(R.id.tvTitleSelectCategory);
        ivImageAccountPay = mView.findViewById(R.id.ivImageAccount);
        tvTitleAccountPay = mView.findViewById(R.id.tvTitleAccount);
        ivImageCategoryFee = mView.findViewById(R.id.ivImageCategoryFee);
        tvTitleCategoryFee = mView.findViewById(R.id.tvTitleCategoryFee);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseCategory:
                try {
                    startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                            ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
            case R.id.rlSelectCategoryFee:
                try {
                    startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                            ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY_FEE);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseAccount:
                try {
                    startActivityForResult(new Intent(getContext(), ChooseAccountActivity.class),
                            ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvTime:
                try {
                    UtilsPlus.selectTimeByDialog(tvTimePay, getContext());
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvCalendar:
                CustomCalendarTimeView customCalendarView = new CustomCalendarTimeView(getContext());
                customCalendarView.show();
//                try {
//                    UtilsPlus.selectCalendarByDialog(tvCalenderPay, getContext());
//                } catch (Exception e) {
//                    AppUtils.handlerException(e);
//                }
                break;

            case R.id.rlLender:

                break;

            case R.id.rlRepaymentDate:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY_FEE) {
                    UtilsPlus.onFinishChooseCategory(data,
                            ivImageCategoryFee, tvTitleCategoryFee);
                }

                if (requestCode == ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY) {
                    UtilsPlus.onFinishChooseCategory(data,
                            ivImageCategoriesPay, tvTitleSelectCategoryPay);
                }

                if (requestCode == ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT) {
                    UtilsPlus.onFinishChooseAccount(data,
                            ivImageAccountPay, tvTitleAccountPay);
                }
            }
        }
    }
}
