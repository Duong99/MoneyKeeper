package vn.com.nghiemduong.moneykeeper.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.overview.AccountOverviewFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.more.MoreFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.overview.OverviewFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.PlusFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.report.ReportFragment;

/**

 - @created_by nxduong on 25/1/2021

**/
public class BottomNavigationAdapter extends FragmentStatePagerAdapter {
    private OverviewFragment mOverviewFragment;
    private AccountOverviewFragment mAccountFragment;
    private PlusFragment mPlusFragment;
    private ReportFragment mReportFragment;
    private MoreFragment mMoreFragment;

    public BottomNavigationAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mOverviewFragment = new OverviewFragment();
        mAccountFragment = new AccountOverviewFragment();
        mPlusFragment = new PlusFragment();
        mReportFragment = new ReportFragment();
        mMoreFragment = new MoreFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mOverviewFragment;
            case 1:
                return mAccountFragment;
            case 2:
                return mPlusFragment;
            case 3:
                return mReportFragment;
            case 4:
                return mMoreFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
