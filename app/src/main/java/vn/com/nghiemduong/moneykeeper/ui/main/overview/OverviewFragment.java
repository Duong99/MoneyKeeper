package vn.com.nghiemduong.moneykeeper.ui.main.overview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.overview.overviewmain.OverviewMainFragment;

/**
 * Màn hình tổng quan
 *
 * @created_by nxduong on 25/1/2021
 **/
public class OverviewFragment extends BaseFragment implements OverviewFragmentMvpView {
    private View mView;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_overview, container, false);
        beginTransactionCategoriesLayout(new OverviewMainFragment());
        return mView;
    }

    /**
     * Hàm chuyển màn hình giữa các fragment
     * - @created_by nxduong on 2/2/2021
     **/
    private void beginTransactionCategoriesLayout(Fragment fg) {
        FragmentManager fmManager = getFragmentManager();
        assert fmManager != null;
        FragmentTransaction ft = fmManager.beginTransaction();
        ft.replace(R.id.flOverView, fg);
        ft.commit();
    }
}