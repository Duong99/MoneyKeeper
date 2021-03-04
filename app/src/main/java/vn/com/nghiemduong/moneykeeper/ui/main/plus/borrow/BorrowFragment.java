package vn.com.nghiemduong.moneykeeper.ui.main.plus.borrow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.nghiemduong.moneykeeper.R;

/**
 * -  Màn hình đi vay
 * <p>
 * - @created_by nxduong on 27/2/2021
 **/
public class BorrowFragment extends Fragment {

    public BorrowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_borrow, container, false);
    }
}