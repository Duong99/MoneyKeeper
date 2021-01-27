package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.overview;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragment;

/**
 * -
 * Màn hình tài khoản, chứa các fragment
 * <p>
 * - @created_by nxduong on 25/1/2021
 **/
public class AccountOverviewFragment extends BaseFragment {
    private View mView;

    public AccountOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_overivew_account, container, false);
        beginTransactionCategoriesLayout(new AccountFragment());
        return mView;
    }

    private void beginTransactionCategoriesLayout(Fragment fg) {
        FragmentManager fmManager = getFragmentManager();
        assert fmManager != null;
        FragmentTransaction ft = fmManager.beginTransaction();
        ft.replace(R.id.flAccountOverview, fg);
        ft.commit();
    }
}