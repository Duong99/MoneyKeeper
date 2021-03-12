package vn.com.nghiemduong.moneykeeper.ui.main.plus.debtor;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.DebtorAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.db.Record;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 31/1/2021
 **/

public class DebtorActivity extends BaseActivity implements DebtorActivityMvpView,
        DebtorAdapter.IOnClickContact, View.OnClickListener,
        AppPermission.IOnRequestPermissionContactResult {

    public final static int REQUEST_CODE_CHOOSE_LENDER = 543;
    public final static int REQUEST_CODE_CHOOSE_BORROWER = 544;
    public final static String KEY_CONTACT_ACTIVITY_TYPE = "KEY_CONTACT_ACTIVITY_TYPE";

    private TextView tvTitleBarContact;
    private DebtorAdapter mDebtorAdapter;
    private DebtorActivityPresenter mContactActivityPresenter;
    private RecyclerView rcvContact;
    private EditText etSearchDebtor;
    private int mKeyContactType = -1; // kiểm tra xem acivity nào gọi đến


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtor);

        init();

        etSearchDebtor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            Timer timer = new Timer();
            final long DELAY = 300; // milliseconds

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mDebtorAdapter.getFilter().filter(AppUtils.getEditText(etSearchDebtor));
//                    timer.cancel();
//                    timer = new Timer();
//                    timer.schedule(
//                            new TimerTask() {
//                                @Override
//                                public void run() {
//                                    mDebtorAdapter.getFilter().filter(AppUtils.getEditText(etSearchDebtor));
//                                    // TODO: do what you need here (refresh list)
//                                    // you will probably need to use runOnUiThread(Runnable action)
//                                    // for some specific actions (e.g. manipulating views)
//                                }
//                            },
//                            DELAY
//                    );
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
            }
        });
    }

    // Khởi tạo / ánh xạ view
    private void init() {
        rcvContact = findViewById(R.id.rcvContact);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvContact.setLayoutManager(layoutManager);


        tvTitleBarContact = findViewById(R.id.tvTitleBarContact);
        etSearchDebtor = findViewById(R.id.etSearchDebtor);

        ImageView ivBackContact = findViewById(R.id.ivBackContact);
        ivBackContact.setOnClickListener(this);

        getKeyIntent();
        mContactActivityPresenter = new DebtorActivityPresenter(this, this);

        new AppPermission().requestContactPermission(this, this, this);
    }

    // Hàm để set lấy giá trị key và set các view cho phù hợp với activity gọi đến
    private void getKeyIntent() {
        mKeyContactType = getIntent().getIntExtra(KEY_CONTACT_ACTIVITY_TYPE, -1);
        switch (mKeyContactType) {
            case REQUEST_CODE_CHOOSE_LENDER:
                tvTitleBarContact.setText(getResources().getString(R.string.lender));
                break;

            case REQUEST_CODE_CHOOSE_BORROWER:
                tvTitleBarContact.setText(getResources().getString(R.string.borrower));
                break;
        }
    }

    @Override
    public void resultGetAllContact(ArrayList<Record> listContact) {
        mDebtorAdapter = new DebtorAdapter(this, listContact, this);
        rcvContact.setAdapter(mDebtorAdapter);
    }

    @Override
    public void onClickContact(Record contact) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BUNDLE_CONTACT", contact);
        intent.putExtra("BUNDLE", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackContact:
                try {
                    onBackPressed();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppPermission.PERMISSIONS_REQUEST_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionContactGranted();
            } else {
                onPermissionContactNotGranted();
                finish();
            }
        }
    }

    @Override
    public void onPermissionContactGranted() {
        mContactActivityPresenter.getAllContactFromDevice();
    }

    @Override
    public void onPermissionContactNotGranted() {

    }
}