package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class PlusPresenter implements PlusMvpPresenter {
    private PlusMvpView mPlusMvpView;
    private Context mContext;
    private Activity mActivity;

    public PlusPresenter(PlusMvpView mPlusMvpView, Context context, Activity activity) {
        this.mPlusMvpView = mPlusMvpView;
        this.mContext = context;
        this.mActivity = activity;
    }

    @Override
    public void addCategories() {
        ArrayList<HeaderCategory> listCategories = new ArrayList<>();
        listCategories.add(new HeaderCategory(R.drawable.ic_minus_red,
                mContext.getResources().getString(R.string.spend_money)));
        listCategories.add(new HeaderCategory(R.drawable.ic_plus_green,
                mContext.getResources().getString(R.string.collect_money)));
        listCategories.add(new HeaderCategory(R.drawable.ic_cho_vay,
                mContext.getResources().getString(R.string.loan)));
        listCategories.add(new HeaderCategory(R.drawable.ic_vay,
                mContext.getResources().getString(R.string.borrow)));
        listCategories.add(new HeaderCategory(R.drawable.ic_phi_chuyen_khoan,
                mContext.getResources().getString(R.string.transfer)));
        mPlusMvpView.resultListCategories(listCategories);
    }

    /**
     * @param position vị trí của từng màn hình mục
     * @created_by nxduong on 7/3/2021
     */

    @Override
    public void initView(int position) {
        switch (position) {
            case AppConstants.CHI_TIEN:
                mPlusMvpView.initViewPay();
                break;
            case AppConstants.THU_TIEN:
                mPlusMvpView.initViewCollect();
                break;
            case AppConstants.CHO_VAY:
                mPlusMvpView.initViewLoan();
                break;
            case AppConstants.DI_VAY:
                mPlusMvpView.initViewBorrow();
                break;
            case AppConstants.CHUYEN_KHOAN:
                mPlusMvpView.initViewTransfer();
                break;
        }
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
        mPlusMvpView.resultChooseCategory(category);

        if (category != null) {
            setScreenFunctionWithCategory(category);
        }

    }

    /**
     * Hàm lấy giá trị tài khoản được chọn
     *
     * @return data
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void doGetChooseAccount(Intent data, int request_code) {
        Account account = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");
        mPlusMvpView.resultChooseAccount(account, request_code);
    }

    /**
     * Hàm lấy giá trị tài khoản đầu tiên trong database
     *
     * @created_by nxduong on 10/2/2021
     */

    @Override
    public void doGetAccountFirstFromDB(Context context) {
        mPlusMvpView.resultGetAccountFirstFromDB(
                new AccountMoneyDatabase(context).getAccountFirstly());
    }

    /**
     * Hàm kiểm tra hạng mục ứng với màn hình chức năng đó
     *
     * @param category hạng mục
     * @created_by nxduong on 9/3/2021
     */
    @Override
    public void setScreenFunctionWithCategory(Category category) {
        if (category.getType() == AppConstants.CHI_TIEN) {
            mPlusMvpView.initViewPay();
        } else if (category.getType() == AppConstants.THU_TIEN) {
            mPlusMvpView.initViewCollect();
        } else if (category.getType() == AppConstants.VAY_NO) {
            if (category.getCategoryId() == AppConstants.CHO_VAY_ID) {
                mPlusMvpView.initViewLoan();
            }

            if (category.getCategoryId() == AppConstants.DI_VAY_ID) {
                mPlusMvpView.initViewBorrow();
            }

            if (category.getCategoryId() == AppConstants.THU_NO_ID) {
                mPlusMvpView.initDebtCollect();
            }

            if (category.getCategoryId() == AppConstants.TRA_NO_ID) {
                mPlusMvpView.initViewDebtPay();
            }
        }
    }
}
