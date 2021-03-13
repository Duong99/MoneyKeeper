package vn.com.nghiemduong.moneykeeper.ui.main.more;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.account.login.LoginActivity;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.more.general.GeneralSettingsActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class MoreFragment extends BaseFragment implements View.OnClickListener {

    private View mView;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_more, container, false);
        init();
        return mView;
    }

    /**
     * Hàm khởi tạo / ánh xạ view
     *
     * @created_by nxduong on 13/3/2021
     */
    private void init() {
        RelativeLayout rlGeneralSettings = mView.findViewById(R.id.rlGeneralSettings);
        rlGeneralSettings.setOnClickListener(this);

        RelativeLayout rlLogout = mView.findViewById(R.id.rlLogout);
        rlLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlGeneralSettings: // Chọn chức năng cài đặt chung
                Intent intentGeneralSettings = new Intent(getContext(), GeneralSettingsActivity.class);
                startActivity(intentGeneralSettings);
                break;

            case R.id.rlLogout: // Chọn đăng xuất tài khoản
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            FirebaseAuth.getInstance().signOut();
                            Intent intentLogout = new Intent(getContext(), LoginActivity.class);
                            startActivity(intentLogout);
                        } catch (Exception e) {
                            AppUtils.handlerException(e);
                        }
                    }
                });

                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
        }
    }
}