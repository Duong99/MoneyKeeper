package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.type;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.AccountType;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public class AccountTypePresenter implements AccountTypeMvpPresenter {
    private AccountTypeMvpView mAccountTypeMvpView;
    private Context mContext;

    public AccountTypePresenter(AccountTypeMvpView accountTypeMvpView, Context context) {
        this.mAccountTypeMvpView = accountTypeMvpView;
        this.mContext = context;
    }

    /**
     * Hàm thêm các loại tài khoản vào danh sách
     *
     * @created_by nxduong on 26/1/2021
     */

    @Override
    public void addAccountType() {
        ArrayList<AccountType> listAccountType = new ArrayList<>();
        listAccountType.add(new AccountType("assets/ImageCategory/THU/THU_tra_no.png",
                AppUtils.getNameAccountType(AppConstants.TIEN_MAT, mContext)));
        listAccountType.add(new AccountType("assets/ImageCategory/THU/THU_nha_cua.png",
                AppUtils.getNameAccountType(AppConstants.TAI_KHOAN_NGAN_HANG, mContext)));
        listAccountType.add(new AccountType("assets/ImageCategory/THU/THU_the.png",
                AppUtils.getNameAccountType(AppConstants.THE_TIN_DUNG, mContext)));
        listAccountType.add(new AccountType("assets/ImageCategory/THU/THU_dau_tu.png",
                AppUtils.getNameAccountType(AppConstants.TAI_KHOAN_DAU_TU, mContext)));
        listAccountType.add(new AccountType("assets/ImageCategory/THU/THU_cho_vay.png",
                AppUtils.getNameAccountType(AppConstants.KHAC, mContext)));

        mAccountTypeMvpView.resultListAccountType(listAccountType);
    }
}
