package vn.com.nghiemduong.moneykeeper.ui.main.overview.overviewmain;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;

/**
 * -  Màn hình tổng quan chính
 * <p>
 * - @created_by nxduong on 2/2/2021
 **/

public class OverviewMainFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private ImageView ivVisibilityTotalMoney;
    private TextView tvTotalMoney;
    private RelativeLayout rlTotalMoneyBackground;

    public OverviewMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_overview_main, container, false);
        init();
        return mView;
    }

    private void init() {
        ivVisibilityTotalMoney = mView.findViewById(R.id.ivVisibilityTotalMoney);
        ivVisibilityTotalMoney.setOnClickListener(this);

        rlTotalMoneyBackground = mView.findViewById(R.id.rlTotalMoneyBackground);
        rlTotalMoneyBackground.setOnClickListener(this);

        tvTotalMoney = mView.findViewById(R.id.tvTotalMoney);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivVisibilityTotalMoney:

                break;

            case R.id.rlTotalMoneyBackground:

                break;
        }
    }
}