package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.ChooseCategoriesActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;


/**
 * -  Màn hình chi tiền
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class PayFragment extends BaseFragment implements PayMvpView, View.OnClickListener {
    private View mView;
    private RelativeLayout rlChooseCategoryPay, rlChooseAccountPay;
    private ImageView ivImageCategoriesPay, ivImageAccountPay;
    private TextView tvTitleSelectCategoryPay, tvTitleAccountPay;


    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_pay, container, false);
        init();
        return mView;
    }

    private void init() {
        rlChooseCategoryPay = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategoryPay.setOnClickListener(this);

        rlChooseAccountPay = mView.findViewById(R.id.rlChooseAccount);
        rlChooseAccountPay.setOnClickListener(this);

        ivImageCategoriesPay = mView.findViewById(R.id.ivImageCategories);
        tvTitleSelectCategoryPay = mView.findViewById(R.id.tvTitleSelectCategory);
        ivImageAccountPay = mView.findViewById(R.id.ivImageAccount);
        tvTitleAccountPay = mView.findViewById(R.id.tvTitleAccount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseCategory:
                startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                        ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY);
                break;

            case R.id.rlChooseAccount:
                startActivityForResult(new Intent(getContext(), ChooseAccountActivity.class),
                        ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY) {
                    Category category =
                            (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                                    .getSerializable("BUNDLE_CATEGORY");

                    tvTitleSelectCategoryPay.setText(category.getTitle());
                    ivImageCategoriesPay.setImageBitmap(
                            AppUtils.convertByteArrayToBitmap(category.getImage()));
                }
            }

            if (requestCode == ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT) {
                Account account = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                        .getSerializable("BUNDLE_ACCOUNT");

                ivImageAccountPay.setImageBitmap(AppUtils.convertByteArrayToBitmap(account.getImageType()));
                tvTitleAccountPay.setText(account.getAccountName());
            }
        }
    }
}
