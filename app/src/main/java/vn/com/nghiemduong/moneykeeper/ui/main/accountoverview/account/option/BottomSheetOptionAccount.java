package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.option;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.com.nghiemduong.moneykeeper.R;

/**
 * -
 * Class Bottom sheet sử lý các sự kiện trong layout bottom_sheet_option_account
 * <p>
 * - @created_by nxduong on 27/1/2021
 **/
public class BottomSheetOptionAccount extends BottomSheetDialog implements View.OnClickListener {

    private BottomSheetOptionAccountMvpView mOptionAccountMvpView;
    private BottomSheetDialog mBottomSheetDialog;

    public BottomSheetOptionAccount(@NonNull Context context,
                                    BottomSheetOptionAccountMvpView optionAccountMvpView) {
        super(context);
        this.mOptionAccountMvpView = optionAccountMvpView;
        View viewBts = getLayoutInflater().inflate(R.layout.bottom_sheet_option_account, null);

        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(viewBts);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        initBottomSheet(viewBts);
    }

    private void initBottomSheet(View viewBts) {
        LinearLayout llSwapOptionAccount = viewBts.findViewById(R.id.llSwapOptionAccount);
        llSwapOptionAccount.setOnClickListener(this);

        LinearLayout llAdjustOptionAccount = viewBts.findViewById(R.id.llAdjustOptionAccount);
        llAdjustOptionAccount.setOnClickListener(this);

        LinearLayout llDeleteOptionAccount = viewBts.findViewById(R.id.llDeleteOptionAccount);
        llDeleteOptionAccount.setOnClickListener(this);

        LinearLayout llEditOptionAccount = viewBts.findViewById(R.id.llEditOptionAccount);
        llEditOptionAccount.setOnClickListener(this);

        LinearLayout llShareOptionAccount = viewBts.findViewById(R.id.llShareAccountOptionAccount);
        llShareOptionAccount.setOnClickListener(this);

        LinearLayout llLockOptionAccount = viewBts.findViewById(R.id.llLockOptionAccount);
        llLockOptionAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mBottomSheetDialog.cancel();
        switch (v.getId()) {
            case R.id.llSwapOptionAccount:
                mOptionAccountMvpView.onClickSwapOptionAccount();
                break;

            case R.id.llAdjustOptionAccount:
                mOptionAccountMvpView.onClickAdjustOptionAccount();
                break;

            case R.id.llShareAccountOptionAccount:
                mOptionAccountMvpView.onClickShareOptionAccount();
                break;

            case R.id.llEditOptionAccount:
                mOptionAccountMvpView.onClickEditOptionAccount();
                break;

            case R.id.llDeleteOptionAccount:
                mOptionAccountMvpView.onClickDeleteOptionAccount();
                break;

            case R.id.llLockOptionAccount:
                mOptionAccountMvpView.onClickLockOptionAccount();
                break;
        }

    }
}
