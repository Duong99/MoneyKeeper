package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;

import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoryActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;

/**
 * -  Màn hình chi tiền
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class PayFragment extends BaseFragment implements View.OnClickListener, PayFragmentMvpView {

    private View mView;
    private EditText etInputAmount;
    private ImageView ivImageCategory, ivImageAccount;
    private TextView tvTitleCategory, tvTitleAccount;
    private Account mAccount;
    private Category mCategory;

    private PayFragmentPresenter mPayFragmentPresenter;

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

    /**
     * Hàm khởi tạo / Ánh xạ View
     *
     * @created_by nxduong on 5/3/2021
     */
    private void init() {
        etInputAmount = mView.findViewById(R.id.etInputAmount);
        etInputAmount.setTextColor(getResources().getColor(R.color.input_amount_red_pay));
        AppUtils.formatNumberEditText(etInputAmount);

        // View hạng mục, thể loại
        RelativeLayout rlChooseCategory = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategory.setOnClickListener(this);
        ivImageCategory = mView.findViewById(R.id.ivImageCategory);
        tvTitleCategory = mView.findViewById(R.id.tvTitleCategory);

        // View tài khoản
        RelativeLayout rlChooseAccount = mView.findViewById(R.id.rlChooseAccount);
        rlChooseAccount.setOnClickListener(this);
        ivImageAccount = mView.findViewById(R.id.ivImageAccount);
        tvTitleAccount = mView.findViewById(R.id.tvTitleAccount);
        tvTitleAccount.setTextColor(getResources().getColor(R.color.text_unvalue));


        mPayFragmentPresenter = new PayFragmentPresenter(this);
        mPayFragmentPresenter.doGetAccountFirstFromDB(getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseCategory: // Chọn hạng mục
                try {
                    Intent intentChooseCategory = new Intent(getContext(), ChooseCategoryActivity.class);
                    startActivityForResult(intentChooseCategory,
                            ChooseCategoryActivity.REQUEST_CODE_CHOOSE_CATEGORY);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

                break;

            case R.id.rlChooseAccount: // Chọn tài khoản
                try {
                    Intent intentChooseAccount = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundleChooseAccount = new Bundle();
                    bundleChooseAccount.putSerializable("BUNDLE_ACCOUNT", mAccount);
                    intentChooseAccount.putExtra("BUNDLE", bundleChooseAccount);
                    startActivityForResult(intentChooseAccount, ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == ChooseCategoryActivity.REQUEST_CODE_CHOOSE_CATEGORY) {
                    mPayFragmentPresenter.doGetChooseCategory(data);
                }

                if (requestCode == ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT) {
                    mPayFragmentPresenter.doGetChooseAccount(data);
                }
            }
        }
    }

    /**
     * Hàm trả về kết quả khi chọn hạng mục
     *
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void resultChooseCategory(Category category) {
        this.mCategory = category;
        if (mCategory != null) {
            ivImageCategory.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mCategory.getCategoryPath(), Objects.requireNonNull(getContext())));
            tvTitleCategory.setText(mCategory.getCategoryName());
            tvTitleCategory.setTextColor(getResources().getColor(R.color.text_valuable));
        }
    }

    /**
     * Kết quả trả về tài khoản được chọn
     *
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void resultChooseAccount(Account account) {
        this.mAccount = account;
        if (mAccount != null) {
            ivImageAccount.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    account.getAccountTypePath(), Objects.requireNonNull(getContext())));
            tvTitleAccount.setText(mAccount.getAccountName());
            tvTitleAccount.setTextColor(getResources().getColor(R.color.text_valuable));
        }
    }

    /**
     * Hàm trả về kết quả tài khoản đầu tiên trong database
     *
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void resultGetAccountFirstFromDB(Account account) {
        this.mAccount = account;
        if (mAccount != null) {
            ivImageAccount.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    account.getAccountTypePath(), Objects.requireNonNull(getContext())));
            tvTitleAccount.setText(mAccount.getAccountName());
            tvTitleAccount.setTextColor(getResources().getColor(R.color.text_valuable));
        }
    }
}
