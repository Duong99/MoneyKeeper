package vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.AccountAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;

/**
 * -
 * Màn hình cho phép chọn tài khoản
 * <p>
 * - @created_by nxduong on 28/1/2021
 **/

public class ChooseAccountActivity extends BaseActivity implements
        AccountAdapter.IOnClickAccount {

    public final static int REQUEST_CODE_CHOOSE_ACCOUNT = 119;

    private RecyclerView rcvChooseAccount;
    private AccountAdapter mAccountAdapter;
    private AccountMoneyDatabase mAccountMoneyDatabase;
    private Toolbar tbChooseAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);

        init();

        tbChooseAccount.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        rcvChooseAccount = findViewById(R.id.rcvChooseAccount);
        tbChooseAccount = findViewById(R.id.tbChooseAccount);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvChooseAccount.setLayoutManager(layoutManager);

        mAccountMoneyDatabase = new AccountMoneyDatabase(this);

        mAccountAdapter = new AccountAdapter(this, mAccountMoneyDatabase.getAllAccount(), this);
        rcvChooseAccount.setAdapter(mAccountAdapter);
    }

    @Override
    public void onClickAccount(Account account) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_ACCOUNT", account);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickOptionAccount(Account account, int position) {

    }
}