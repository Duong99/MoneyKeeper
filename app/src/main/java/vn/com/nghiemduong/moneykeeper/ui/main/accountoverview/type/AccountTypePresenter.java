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
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class AccountTypePresenter implements AccountTypeMvpPresenter {
    private AccountTypeMvpView mAccountTypeMvpView;
    private Context mContext;

    public AccountTypePresenter(AccountTypeMvpView accountTypeMvpView, Context context) {
        this.mAccountTypeMvpView = accountTypeMvpView;
        this.mContext = context;
    }

    @Override
    public void addAccountType() {
        ArrayList<AccountType> listAccountType = new ArrayList<>();

        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = mContext.getAssets().open("assets/ImageCategory/THU/THU_tra_no.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);

        listAccountType.add(new AccountType(AppUtils.TIEN_MAT,
                AppUtils.convertBitmapToByteArray(bitmap),
                mContext.getResources().getString(R.string.cash)));

        try {
            is = mContext.getAssets().open("assets/ImageCategory/THU/THU_nha_cua.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);

        listAccountType.add(new AccountType(AppUtils.TAI_KHOAN_NGAN_HANG,
                AppUtils.convertBitmapToByteArray(bitmap),
                mContext.getResources().getString(R.string.account_bank)));

        try {
            is = mContext.getAssets().open("assets/ImageCategory/THU/THU_the.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);
        listAccountType.add(new AccountType(AppUtils.THE_TIN_DUNG,
                AppUtils.convertBitmapToByteArray(bitmap),
                mContext.getResources().getString(R.string.credit)));

        try {
            is = mContext.getAssets().open("assets/ImageCategory/THU/THU_dau_tu.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);
        listAccountType.add(new AccountType(AppUtils.TAI_KHOAN_DAU_TU,
                AppUtils.convertBitmapToByteArray(bitmap),
                mContext.getResources().getString(R.string.account_invest)));

        try {
            is = mContext.getAssets().open("assets/ImageCategory/THU/THU_cho_vay.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);
        listAccountType.add(new AccountType(AppUtils.KHAC,
                AppUtils.convertBitmapToByteArray(bitmap),
                mContext.getResources().getString(R.string.account_more)));

        mAccountTypeMvpView.resultListAccountType(listAccountType);
    }
}
