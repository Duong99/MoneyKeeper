package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.AccountAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabaseMvpView;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.option.BottomSheetOptionAccount;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.option.BottomSheetOptionAccountMvpView;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.add.AddAccountActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;

/**
 * -  Màn hình chứa tài khoản
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/

public class AccountFragment extends BaseFragment implements View.OnClickListener,
        AccountMoneyDatabaseMvpView, AccountAdapter.IOnClickAccount, BottomSheetOptionAccountMvpView {
    private View mView;
    private RecyclerView rcvListAccount;
    private ImageView ivAddAccount;
    private AccountAdapter mAccountAdapter;
    private AccountMoneyDatabase mAccountDatabase;
    private Account mAccount;
    private int mPosition = -1; // vị trí click tài khoản

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        init();
        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == AddAccountActivity.REQUEST_CODE_ACCOUNT_ADD) {
                    mAccount = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                            .getSerializable("BUNDLE_ACCOUNT");
                    mAccountAdapter.addAccount(mAccount);
                }

                if (requestCode == AddAccountActivity.REQUEST_CODE_ACCOUNT_EDIT) {
                    mAccount = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                            .getSerializable("BUNDLE_ACCOUNT");
                    mAccountAdapter.updateAccount(mAccount, mPosition);
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        rcvListAccount = mView.findViewById(R.id.rcvListAccount);

        ivAddAccount = mView.findViewById(R.id.ivAddAccount);
        ivAddAccount.setOnClickListener(this);

        mAccountDatabase = new AccountMoneyDatabase(getContext(), this);
        mAccountDatabase.getAllAccount();
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
    public void getAllAccountResult(ArrayList<Account> listAccount) {
        mAccountAdapter = new AccountAdapter(getContext(), listAccount, this);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvListAccount.setLayoutManager(layoutManager);
        rcvListAccount.setAdapter(mAccountAdapter);
    }

    @Override
    public void insertAccountSuccess() {

    }

    @Override
    public void insertAccountFail() {

    }

    @Override
    public void updateAccountSuccess() {

    }

    @Override
    public void updateAccountFail() {

    }

    @Override
    public void deleteAccountSuccess() {
        mAccountAdapter.deleteAccount(mAccount);
        showToast(getString(R.string.delete_account_success));
    }

    @Override
    public void deleteAccountFail() {
        showToast(getString(R.string.delete_account_fail));
    }

    @Override
    public void onClickAccount(Account account) {

    }

    @Override
    public void onClickOptionAccount(Account account, int position) {
        mAccount = account;
        mPosition = position;
        new BottomSheetOptionAccount(Objects.requireNonNull(getContext()), this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.note));
        builder.setMessage(getString(R.string.you_want_delete_account));

        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mAccount != null) {
                    mAccountDatabase.deleteAccount(mAccount.getAccountId());
                }
            }
        });

        builder.show();

    }

    @Override
    public void onClickLockOptionAccount() {

    }
}