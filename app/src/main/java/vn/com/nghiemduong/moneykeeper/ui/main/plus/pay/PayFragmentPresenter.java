package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.content.Context;
import android.content.Intent;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.data.model.db.MoneyPay;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public class PayFragmentPresenter implements PayFragmentMvpPresenter {
    private PayFragmentMvpView mPayMvpView;

    public PayFragmentPresenter(PayFragmentMvpView payMvpView) {
        this.mPayMvpView = payMvpView;
    }

    /**
     * Lấy giá trị hạng mục được chọn
     *
     * @return category hạng mục được chọn
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void doGetChooseCategory(Intent data) {
        Category category = (Category)
                Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                        .getSerializable("BUNDLE_CATEGORY");
        mPayMvpView.resultChooseCategory(category);
    }

    /**
     * Hàm lấy giá trị tài khoản được chọn
     *
     * @return data
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void doGetChooseAccount(Intent data) {
        Account account = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");
        mPayMvpView.resultChooseAccount(account);
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
