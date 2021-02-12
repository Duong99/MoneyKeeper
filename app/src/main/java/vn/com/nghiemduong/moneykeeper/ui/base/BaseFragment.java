package vn.com.nghiemduong.moneykeeper.ui.base;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.ui.custom.CustomToast;


/**
 * - @created_by nxduong on 25/1/2021
 **/
public class BaseFragment extends Fragment implements MvpView {

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCustomToast(String message, int type) {
        CustomToast.makeToast(getContext(), message, type).show();
    }

    @Override
    public void onBackPressed() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }
}
