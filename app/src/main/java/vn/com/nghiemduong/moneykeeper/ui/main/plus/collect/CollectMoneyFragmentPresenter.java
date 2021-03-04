package vn.com.nghiemduong.moneykeeper.ui.main.plus.collect;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;

/**
 * - @created_by nxduong on 1/2/2021
 **/
public class CollectMoneyFragmentPresenter implements CollectMoneyFragmentMvpPresenter {
    private CollectMoneyFragmentMvpView mCollectMoneyFragmentMvpView;

    public CollectMoneyFragmentPresenter(CollectMoneyFragmentMvpView collectMoneyFragmentMvpView) {
        this.mCollectMoneyFragmentMvpView = collectMoneyFragmentMvpView;
    }


    /**
     * Hàm lấy giá trị tài khoản đầu tiên trong database
     *
     * @created_by nxduong on 10/2/2021
     */

    @Override
    public void doGetAccountFirstFromDB(Context context) {
        mCollectMoneyFragmentMvpView.resultGetAccountFirstFromDB(
                new AccountMoneyDatabase(context).getAccountFirstly());
    }
}
