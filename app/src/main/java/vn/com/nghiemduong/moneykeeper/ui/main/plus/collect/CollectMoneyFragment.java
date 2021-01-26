package vn.com.nghiemduong.moneykeeper.ui.main.plus.collect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.nghiemduong.moneykeeper.R;
/**
 -
    Màn chi thu tiền
    *
 - @created_by nxduong on 26/1/2021

**/
public class CollectMoneyFragment extends Fragment {

    public CollectMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collect_money, container, false);
    }
}