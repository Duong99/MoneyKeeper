package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;

/**

 * - @created_by nxduong on 25/1/2021
 **/
public class PlusPresenter implements PlusMvpPresenter{
    private PlusMvpView mPlusMvpView;
    private Context mContext;

    public PlusPresenter(PlusMvpView mPlusMvpView, Context context) {
        this.mPlusMvpView = mPlusMvpView;
        this.mContext = context;
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
        listCategories.add(new HeaderCategory(R.drawable.ic_cho_vay,
                mContext.getResources().getString(R.string.transfer)));
        listCategories.add(new HeaderCategory(R.drawable.ic_cho_vay,
                mContext.getResources().getString(R.string.balance_adjustment)));
        mPlusMvpView.resultListCategories(listCategories);
    }
}
