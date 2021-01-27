package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.type;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.AccountTypeRcvAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.AccountType;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -    Màn hình hiển thị loại tài khoản cho phép chọn loại tài khoản
 * <p>
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/

public class AccountTypeActivity extends BaseActivity implements AccountTypeMvpView,
        AccountTypeRcvAdapter.IOnClickAccountType {

    public final static int REQUEST_CODE_ACCOUNT_TYPE = 112;
    private RecyclerView rcvAccountType;
    private Toolbar toolbarAccountType;
    private AccountTypePresenter mAccountTypePresenter;
    private AccountTypeRcvAdapter mAccountTypeAdapter;
    private AccountType mAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);

        init();

        toolbarAccountType.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    // Khởi tạo /ánh xạ
    private void init() {
        rcvAccountType = findViewById(R.id.rcvAccountType);
        toolbarAccountType = findViewById(R.id.toolbarAccountType);

        mAccountType = (AccountType) Objects.requireNonNull(getIntent().getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT_TYPE"); // Lấy dữ liệu từ Acivity gửi

        mAccountTypePresenter = new AccountTypePresenter(this, this);
        mAccountTypePresenter.addAccountType();

    }

    @Override
    public void resultListAccountType(ArrayList<AccountType> listAccountType) {
        mAccountTypeAdapter = new AccountTypeRcvAdapter(this, listAccountType,
                this, mAccountType);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        rcvAccountType.setLayoutManager(layoutManager);
        rcvAccountType.setAdapter(mAccountTypeAdapter);
    }

    @Override
    public void onClickAccountType(AccountType accountType) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_ACCOUNT_TYPE", accountType);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}