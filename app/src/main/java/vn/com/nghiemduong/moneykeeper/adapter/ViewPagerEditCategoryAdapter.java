package vn.com.nghiemduong.moneykeeper.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vn.com.nghiemduong.moneykeeper.ui.main.category.update.collect.EditCategoryCollectFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.category.update.pay.EditCategoryPayFragment;

/**
 * - @created_by nxduong on 6/2/2021
 **/
public class ViewPagerEditCategoryAdapter extends FragmentPagerAdapter {

    private String[] editCategories = {"MỤC CHI", "MỤC THU"};

    public ViewPagerEditCategoryAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EditCategoryPayFragment();
            case 1:
                return new EditCategoryCollectFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return editCategories.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return editCategories[position];
    }
}
