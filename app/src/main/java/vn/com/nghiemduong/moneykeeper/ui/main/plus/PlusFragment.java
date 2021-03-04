package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CustomSpinnerCategoriesArrayAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;
import vn.com.nghiemduong.moneykeeper.data.model.db.MoneyPay;
import vn.com.nghiemduong.moneykeeper.data.model.db.Transfer;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.borrow.BorrowFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.collect.CollectMoneyFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.loan.LoanFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.pay.PayFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer.TransferFragment;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class PlusFragment extends BaseFragment implements PlusMvpView, View.OnClickListener {
    private View mView;
    private Spinner spinnerCategories;
    private CustomSpinnerCategoriesArrayAdapter mSpinnerAdapter;
    private PlusPresenter mPlusPresenter;
    private Bundle mBundle = null;
    private IOnClickSave mIOnClickSave;

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

    }

    private void onClickSpinnerCategories() {
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Chọn màn hình chi tiền
                        try {
                            beginTransactionCategoriesLayout(new PayFragment());
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                        break;
                    case 1: // Chọn màn hình thu tiền
                        try {
                            beginTransactionCategoriesLayout(new CollectMoneyFragment());
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                        break;
                    case 2: // Chọn màn hình cho vay
                        try {
                            beginTransactionCategoriesLayout(new LoanFragment());
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                        break;

                    case 3: // Chọn màn hình đi vay
                        try {
                            beginTransactionCategoriesLayout(new BorrowFragment());
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                        break;
                    case 4: // Chọn màn hình chuyển khoản
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
        ImageView ivSaveDonePlus = mView.findViewById(R.id.ivSaveDonePlus);
        ivSaveDonePlus.setOnClickListener(this);

        spinnerCategories = mView.findViewById(R.id.spinnerCategories);
        mPlusPresenter = new PlusPresenter(this, getContext(), getActivity());
        mPlusPresenter.addCategories();
    }

    @Override
    public void resultListCategories(ArrayList<HeaderCategory> listCategory) {
        mSpinnerAdapter = new CustomSpinnerCategoriesArrayAdapter(
                Objects.requireNonNull(getContext()), listCategory);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSaveDonePlus:
                try {
                    mIOnClickSave.onClickSave();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;
        }
    }

    /**
     * Lớp interface thực hiện sự kiệnlưu sửa
     *
     * @created_by nxduong on 18/2/2021
     **/

    public interface IOnClickSave {
        void onClickSave();
    }

    public void setIOnClickSave(IOnClickSave onClickSave) {
        this.mIOnClickSave = onClickSave;
    }

}