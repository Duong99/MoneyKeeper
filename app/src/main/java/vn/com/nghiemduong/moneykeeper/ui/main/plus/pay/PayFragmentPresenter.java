package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.SubCategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public class PayFragmentPresenter implements PayFragmentMvpPresenter {
    private PayFragmentMvpView mPayMvpView;

    public PayFragmentPresenter(PayFragmentMvpView payMvpView) {
        this.mPayMvpView = payMvpView;
    }

    @Override
    public void doGetMoneyPayFromBundle(Fragment fm, Context context) {
        if (fm.getArguments() != null) {
            MoneyPay moneyPay = (MoneyPay) fm.getArguments().getSerializable("BUNDLE_MONEY_PAY");
            Account account = null;
            Category category = null;
            SubCategory subCategory = null;
            if (moneyPay != null) {
                account = new AccountMoneyDatabase(context)
                        .getAccount(moneyPay.getAccountId());
                category = new CategoryDatabase(context).getCategory(moneyPay.getCategoryId());
                if (moneyPay.getSubCategoryId() != 0) {
                    subCategory = new SubCategoryDatabase(context)
                            .getSubCategory(moneyPay.getSubCategoryId());
                }
            }
            mPayMvpView.resultGetMoneyPayFromBundle(moneyPay, account,
                    category, subCategory);
        }
    }

    /**
     * Hàm lấy dữ liệu hạng mục được chọn
     *
     * @param data dữ liệu hạng mục được trả về khi chọn tài khoản
     * @created_by nxduong on 10/2/2021
     */

    @Override
    public void onActivityResultChooseCategoryPay(Intent data, Context context) {
        Category category =
                (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                        .getSerializable("BUNDLE_PARENT_CATEGORY");

        SubCategory subCategory =
                (SubCategory) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                        .getSerializable("BUNDLE_SUB_CATEGORY");

        mPayMvpView.resultOnActivityResultChooseCategoryPay(category, subCategory);
    }

    /**
     * Hàm lấy dữ liệu tài khoản được chọn
     *
     * @param data dữ liệu tài khoản được trả về khi chọn tài khoản
     * @created_by nxduong on 10/2/2021
     */

    @Override
    public void onActivityResultAccountPay(Intent data) {
        Account account = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");
        mPayMvpView.resultOnFinishChooseAccountPay(account);
    }

    /**
     * Hàm lấy giá trị tài khoản đầu tiên trong database
     *
     * @created_by nxduong on 10/2/2021
     */

    @Override
    public void doGetAccountFirstFromDB(Context context) {
        mPayMvpView.resultGetAccountFirstFromDB(
                new AccountMoneyDatabase(context).getAccountFirstly());
    }
}
