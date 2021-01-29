package vn.com.nghiemduong.moneykeeper.ui.main.plus.collect;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.ChooseCategoriesActivity;

/**
 * -
 * Màn chi thu tiền
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class CollectMoneyFragment extends BaseFragment implements View.OnClickListener {
    private View mView;
    private RelativeLayout rlChooseCategoryCollect;


    public CollectMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_collect_money, container, false);

        init();
        return mView;
    }

    private void init() {
        rlChooseCategoryCollect = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategoryCollect.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseCategory:
                startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                        ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY);
                break;
        }
    }
}