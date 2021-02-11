package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CustomSpinnerCategoriesArrayAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.collect.CollectMoneyFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.pay.PayFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer.TransferFragment;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class PlusFragment extends BaseFragment implements PlusMvpView {
    private View mView;
    private Spinner spinnerCategories;
    private CustomSpinnerCategoriesArrayAdapter mSpinnerAdapter;
    private PlusPresenter mPlusPresenter;
    private Bundle mBundle = null;

    public PlusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_plus, container, false);
        init();
        onClickSpinnerCategories();
        getDataBundle();
        return mView;
    }

    /**
     * Hàm lấy dữ liệu 2 đối tượng Thu tiền (moneyCollect) và Chi tiền (MoneyPay)
     *
     * @created_by nxduong on 5/2/2021
     */
    private void getDataBundle() {
        if (this.getArguments() != null) {
            mBundle = new Bundle();
            MoneyCollect moneyCollects =
                    (MoneyCollect) this.getArguments().getSerializable("BUNDLE_MONEY_COLLECT");
            if (moneyCollects != null) {
                mBundle.putSerializable("BUNDLE_MONEY_COLLECT", moneyCollects);
                spinnerCategories.setSelection(1);
            } else {
                MoneyPay moneyPay =
                        (MoneyPay) this.getArguments().getSerializable("BUNDLE_MONEY_PAY");
                if (moneyPay != null) {
                    mBundle.putSerializable("BUNDLE_MONEY_PAY", moneyPay);
                    spinnerCategories.setSelection(0);
                }
            }
        }
    }

    private void onClickSpinnerCategories() {
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        try {
                            beginTransactionCategoriesLayout(new PayFragment());
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                        break;
                    case 1:
                        try {
                            beginTransactionCategoriesLayout(new CollectMoneyFragment());
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                        break;
                    case 2:
                    case 3:
                    case 5:
                        showToast(getString(R.string.evolving_functions));
                        break;
                    case 4:
                        try {
                            beginTransactionCategoriesLayout(new TransferFragment());
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Khởi tạo / ánh xạ cho các view
    private void init() {
        spinnerCategories = mView.findViewById(R.id.spinnerCategories);
        mPlusPresenter = new PlusPresenter(this, getContext(), getActivity());
        mPlusPresenter.addCategories();
    }

    @Override
    public void resultListCategories(ArrayList<HeaderCategory> listCategory) {
        mSpinnerAdapter = new CustomSpinnerCategoriesArrayAdapter(getContext(), listCategory);
        spinnerCategories.setAdapter(mSpinnerAdapter);
    }

    /**
     * Hàm chuyển màn hình giữa các fragment
     * - @created_by nxduong on 25/1/2021
     **/
    private void beginTransactionCategoriesLayout(Fragment fg) {
        if (mBundle != null) {
            fg.setArguments(mBundle);
        }
        FragmentManager fmManager = getFragmentManager();
        assert fmManager != null;
        FragmentTransaction ft = fmManager.beginTransaction();
        ft.replace(R.id.framelayoutCategory, fg);
        ft.commit();
    }

}