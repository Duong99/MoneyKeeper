package vn.com.nghiemduong.moneykeeper.ui.main.plus.collect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.SubCategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;

/**
 * - @created_by nxduong on 1/2/2021
 **/
public class CollectMoneyFragmentPresenter implements CollectMoneyFragmentMvpPresenter {
    private CollectMoneyFragmentMvpView mCollectMoneyFragmentMvpView;

    public CollectMoneyFragmentPresenter(CollectMoneyFragmentMvpView collectMoneyFragmentMvpView) {
        this.mCollectMoneyFragmentMvpView = collectMoneyFragmentMvpView;
    }

    /**
     * Hàm lấy các đối tựợng MoneyCollect, Account, Category, SubCategory bắn sang View
     *
     * @param fm ,context
     * @created_by nxduong on 10/2/2021
     */

    @Override
    public void doGetMoneyCollectFromBundle(Fragment fm, Context context) {
        if (fm.getArguments() != null) {
            MoneyCollect moneyCollect = (MoneyCollect) fm.getArguments()
                    .getSerializable("BUNDLE_MONEY_COLLECT");

            Account account = null;
            Category category = null;
            SubCategory subCategory = null;
            if (moneyCollect != null) {
                account = new AccountMoneyDatabase(context)
                        .getAccount(moneyCollect.getAccountId());
                category = new CategoryDatabase(context).getCategory(moneyCollect.getCategoryId());
                if (moneyCollect.getSubCategoryId() != 0) {
                    subCategory = new SubCategoryDatabase(context)
                            .getSubCategory(moneyCollect.getSubCategoryId());
                }
            }
            mCollectMoneyFragmentMvpView.resultGetMoneyCollectFromBundle(moneyCollect, account,
                    category, subCategory);
        }
    }

    @Override
    public void onActivityResultChooseCategoryCollect(Intent data, Context context) {
        Category category =
                (Category) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                        .getSerializable("BUNDLE_PARENT_CATEGORY");

        SubCategory subCategory =
                (SubCategory) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                        .getSerializable("BUNDLE_SUB_CATEGORY");

        mCollectMoneyFragmentMvpView
                .resultOnActivityResultChooseCategoryCollect(category, subCategory);
    }

    @Override
    public void onActivityResultAccountCollect(Intent data) {
        Account account = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");
        mCollectMoneyFragmentMvpView.resultOnFinishChooseAccountCollect(account);
    }
}
