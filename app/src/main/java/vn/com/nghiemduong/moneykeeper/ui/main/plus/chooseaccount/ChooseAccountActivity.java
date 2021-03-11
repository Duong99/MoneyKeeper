package vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.AccountChooseAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;

/**
 * Màn hình cho phép chọn tài khoản
 * <p>
 * - @created_by nxduong on 28/1/2021
 **/

public class ChooseAccountActivity extends BaseActivity implements
        AccountChooseAdapter.IOnClickAccount {

    public final static int REQUEST_CODE_CHOOSE_ACCOUNT = 112;
    public final static int REQUEST_CODE_FROM_ACCOUNT = 113;
    public final static int REQUEST_CODE_TO_ACCOUNT = 114;

    private RecyclerView rcvChooseAccount;
    private AccountChooseAdapter mAccountAdapter;
    private AccountDatabase mAccountMoneyDatabase;
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

    // Khởi tạo / Ánh xạ view
    private void init() {
        rcvChooseAccount = findViewById(R.id.rcvChooseAccount);
        tbChooseAccount = findViewById(R.id.tbChooseAccount);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvChooseAccount.setLayoutManager(layoutManager);

        mAccountMoneyDatabase = new AccountDatabase(this);

        Account account = (Account) Objects.requireNonNull(getIntent()
                .getBundleExtra("BUNDLE")).getSerializable("BUNDLE_ACCOUNT");

        mAccountAdapter = new AccountChooseAdapter(this, mAccountMoneyDatabase.getAllAccount(),
                this, account);
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
}