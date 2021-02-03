package vn.com.nghiemduong.moneykeeper.ui.main.overview.overviewmain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragmentMvpView;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragmentPresenter;

/**
 * -  Màn hình tổng quan chính
 * <p>
 * - @created_by nxduong on 2/2/2021
 **/

public class OverviewMainFragment extends BaseFragment implements View.OnClickListener,
        AccountFragmentMvpView {

    private View mView;
    private ImageView ivVisibilityTotalMoney;
    private TextView tvTotalMoney;
    private RelativeLayout rlTotalMoneyBackground;
    private AccountFragmentPresenter mAccountFragmentPresenter;
    private MainActivity mMainActivity;
    private AccountFragment mAccountFragment;

    private int mTotalMoney = 0;

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
        mAccountFragmentPresenter = new AccountFragmentPresenter(this);
        mAccountFragmentPresenter.doSumOfMoneyOfAllAccount(mMainActivity.getAllAccount());
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


    @Override
    public void onTotalMoneyOfAllAccount(int totalMoney) {
        mTotalMoney = totalMoney;
        tvTotalMoney.setText(String.valueOf(mTotalMoney));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }
}