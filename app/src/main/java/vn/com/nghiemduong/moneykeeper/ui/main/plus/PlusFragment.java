package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.os.Bundle;

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
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.borrow.BorrowFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.collect.CollectMoneyFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.loan.LoanFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.pay.PayFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer.TransferFragment;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class PlusFragment extends BaseFragment implements PlusMvpView {
    private View mView;
    private Spinner spinnerCategories;
    private CustomSpinnerCategoriesArrayAdapter mSpinnerAdapter;
    private PlusPresenter mPlusPresenter;

    public PlusFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_plus, container, false);
        init();
        onClickSpinnerCategories();
        return mView;
    }

    private void onClickSpinnerCategories() {
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        beginTransactionCategoriesLayout(new PayFragment());
                        break;
                    case 1:
                        beginTransactionCategoriesLayout(new CollectMoneyFragment());
                        break;
                    case 2:
                        beginTransactionCategoriesLayout(new LoanFragment());
                        break;
                    case 3:
                        beginTransactionCategoriesLayout(new BorrowFragment());
                        break;
                    case 4:
                        beginTransactionCategoriesLayout(new TransferFragment());
                        break;
                    case 5:
                        showToast(getString(R.string.evolving_functions));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {
        spinnerCategories = mView.findViewById(R.id.spinnerCategories);
        mPlusPresenter = new PlusPresenter(this, getContext());
        mPlusPresenter.addCategories();
    }

    @Override
    public void resultListCategories(ArrayList<Category> listCategory) {
        mSpinnerAdapter = new CustomSpinnerCategoriesArrayAdapter(getContext(), listCategory);
        spinnerCategories.setAdapter(mSpinnerAdapter);
    }

    private void beginTransactionCategoriesLayout(Fragment fg) {
        FragmentManager fmManager = getFragmentManager();
        assert fmManager != null;
        FragmentTransaction ft = fmManager.beginTransaction();
        ft.replace(R.id.framelayoutCategory, fg);
        ft.commit();
    }
}