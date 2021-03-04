package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.AccountAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.dialog.attention.AttentionDeleteDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.option.BottomSheetOptionAccount;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.option.BottomSheetOptionAccountMvpView;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.add.AddAccountActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

import static android.app.Activity.RESULT_OK;

/**
 * -  Màn hình hiển thị danh sách tài khoản tài khoản
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/

public class AccountFragment extends BaseFragment implements View.OnClickListener,
        AccountAdapter.IOnClickAccount, BottomSheetOptionAccountMvpView,
        AccountFragmentMvpView, AttentionDeleteDialog.IOnClickAttentionDialog {
    private SwipeRefreshLayout srlAccount;
    private View mView;
    private RecyclerView rcvListAccount;
    private TextView tvTotalMoney, tvTotalMoneyUsing;
    private AccountAdapter mAccountAdapter;
    private AccountMoneyDatabase mAccountDatabase;
    private Account mAccount;
    private int mPosition = -1; // vị trí click tài khoản
    private AccountFragmentPresenter mAccountFragmentPresenter;
    private MainActivity mMainActivity;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        init();

        srlAccount.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlAccount.setRefreshing(false);
            }
        });
        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == AddAccountActivity.REQUEST_CODE_ACCOUNT_ADD) {
                    mAccountAdapter = new AccountAdapter(getContext(), mMainActivity.getAllAccount(), this);
                    rcvListAccount.setAdapter(mAccountAdapter);
                    mAccountFragmentPresenter.doSumOfMoneyOfAllAccount(mAccountAdapter.getAllAccount());
                }

                if (requestCode == AddAccountActivity.REQUEST_CODE_ACCOUNT_EDIT) {
                    mAccountAdapter = new AccountAdapter(getContext(), mMainActivity.getAllAccount(), this);
                    rcvListAccount.setAdapter(mAccountAdapter);
                    mAccountFragmentPresenter.doSumOfMoneyOfAllAccount(mAccountAdapter.getAllAccount());
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Khởi tạo ánh xạ view trong màn hình tài khoản
     *
     * @created_by nxduong on 26/1/2021
     */

    private void init() {
        rcvListAccount = mView.findViewById(R.id.rcvListAccount);
        tvTotalMoney = mView.findViewById(R.id.tvTotalMoney);
        tvTotalMoneyUsing = mView.findViewById(R.id.tvTotalMoneyUsing);
        srlAccount = mView.findViewById(R.id.srlAccount);
        srlAccount.setColorSchemeColors(Objects.requireNonNull(getContext())
                .getResources().getColor(R.color.blue));

        ImageView ivAddAccount = mView.findViewById(R.id.ivAddAccount);
        ivAddAccount.setOnClickListener(this);

        mAccountFragmentPresenter = new AccountFragmentPresenter(this);

        mAccountDatabase = new AccountMoneyDatabase(getContext());
        mAccountAdapter = new AccountAdapter(getContext(), mMainActivity.getAllAccount(), this);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvListAccount.setLayoutManager(layoutManager);
        rcvListAccount.setAdapter(mAccountAdapter);

        mAccountFragmentPresenter.doSumOfMoneyOfAllAccount(mMainActivity.getAllAccount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddAccount:
                Intent intent = new Intent(getContext(), AddAccountActivity.class);
                intent.putExtra(AddAccountActivity.KEY_ADD_EDIT_ACCOUNT, AddAccountActivity.ADD_ACCOUNT);
                startActivityForResult(intent, AddAccountActivity.REQUEST_CODE_ACCOUNT_ADD);
                break;
        }
    }

    @Override
    public void onClickAccount(Account account) {

    }

    @Override
    public void onClickOptionAccount(Account account, int position) {
        try {
            mAccount = account;
            mPosition = position;
            new BottomSheetOptionAccount(Objects.requireNonNull(getContext()),
                    this);
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

    }

    @Override
    public void onClickSwapOptionAccount() {

    }

    @Override
    public void onClickAdjustOptionAccount() {

    }

    @Override
    public void onClickShareOptionAccount() {

    }

    @Override
    public void onClickEditOptionAccount() {
        Intent intent = new Intent(getContext(), AddAccountActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_ACCOUNT", mAccount);
        intent.putExtra("BUNDLE", bundle);
        intent.putExtra(AddAccountActivity.KEY_ADD_EDIT_ACCOUNT, AddAccountActivity.EDIT_ACCOUNT);
        startActivityForResult(intent, AddAccountActivity.REQUEST_CODE_ACCOUNT_EDIT);
    }

    @Override
    public void onClickDeleteOptionAccount() {
        try {
            new AttentionDeleteDialog(Objects.requireNonNull(getContext()), this,
                    AttentionDeleteDialog.ATTENTION_DELETE_ACCOUNT).show();
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    @Override
    public void onClickLockOptionAccount() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTotalMoneyOfAllAccount(int totalMoney) {
        tvTotalMoney.setText(AppUtils.formatNumber(String.valueOf(totalMoney)));
        tvTotalMoneyUsing.setText("( " + AppUtils.formatNumber(String.valueOf(totalMoney)) + " đ )");
    }

    @Override
    public void onClickYesDelete() {
        try {
            if (mAccount != null) {
                long delete = mAccountDatabase.deleteAccount(mAccount.getAccountId());
                if (delete == DBUtils.checkDBFail) {
                    showCustomToast(getString(R.string.delete_account_fail), AppUtils.TOAST_ERROR);
                } else {
                    showCustomToast(getString(R.string.delete_account_success), AppUtils.TOAST_SUCCESS);
                    mAccountAdapter = new AccountAdapter(getContext(), mAccountDatabase.getAllAccount(), this);
                    rcvListAccount.setAdapter(mAccountAdapter);
                    mAccountFragmentPresenter.doSumOfMoneyOfAllAccount(mAccountAdapter.getAllAccount());
                    mAccount = null;
                }
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

}