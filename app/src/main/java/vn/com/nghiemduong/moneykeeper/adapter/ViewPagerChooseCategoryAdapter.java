package vn.com.nghiemduong.moneykeeper.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.collect.CategoryCollectFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.loan.CategoryLoanFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.pay.CategoryPayFragment;

/**
 * - @created_by nxduong on 27/1/2021
 **/
public class ViewPagerChooseCategoryAdapter extends FragmentStatePagerAdapter {
    private String[] titleChooseCategory = {"CHI TIỀN", "THU TIỀN", "VAY NỢ"};

    public ViewPagerChooseCategoryAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CategoryPayFragment();
            case 1:
                return new CategoryCollectFragment();
            case 2:
                return new CategoryLoanFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return titleChooseCategory.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleChooseCategory[position];
    }
}
