package vn.com.nghiemduong.moneykeeper.ui.main.plus.contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.doodle.android.chips.ChipsView;
import com.doodle.android.chips.model.Contact;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ContactAdapter;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;

/**
 * - @created_by nxduong on 31/1/2021
 **/

public class ContactActivity extends BaseActivity implements ContactActivityMvpView,
        ContactAdapter.IOnClickContact, View.OnClickListener {

    public final static int REQUEST_CODE_CHOOSE_CONTACT_WITH_WHOM = 543;
    public final static int REQUEST_CODE_CHOOSE_CONTACT_LENDER = 571;
    public final static String KEY_CONTACT_ACTIVITY_TYPE = "KEY_CONTACT_ACTIVITY_TYPE";

    private TextView tvTitleBarContact;
    private ContactAdapter mContactAdapter;
    private ContactActivityPresenter mContactActivityPresenter;
    private RecyclerView rcvContact;
    private EditText etSearchContact;
    private ImageView ivBackContact, ivDoneSelectedContact;
    private int mKeyContactType = -1; // kiểm tra xem acivity nào gọi đến
    private ChipsView chipViewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        init();

        chipViewContact.setChipsListener(new ChipsView.ChipsListener() {
            @Override
            public void onChipAdded(ChipsView.Chip chip) {

            }

            @Override
            public void onChipDeleted(ChipsView.Chip chip) {

            }

            @Override
            public void onTextChanged(CharSequence text) {
                //mContactAdapter.getFilter().filter(text.toString());
            }

            @Override
            public boolean onInputNotRecognized(String text) {
                return false;
            }
        });
    }

    // Khởi tạo / ánh xạ view
    private void init() {
        rcvContact = findViewById(R.id.rcvContact);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvContact.setLayoutManager(layoutManager);

        chipViewContact = findViewById(R.id.chipViewContact);
        tvTitleBarContact = findViewById(R.id.tvTitleBarContact);

        ivBackContact = findViewById(R.id.ivBackContact);
        ivBackContact.setOnClickListener(this);

        ivDoneSelectedContact = findViewById(R.id.ivDoneSelectedContact);
        ivDoneSelectedContact.setOnClickListener(this);

        getKeyIntent();
        mContactActivityPresenter = new ContactActivityPresenter(this, this);
        if (AppPermission.requestReadContactPermission(this, this)) {
            mContactActivityPresenter.getAllContactFromDevice();
        }
    }

    // Hàm để set lấy giá trị key và set các view cho phù hợp với activity gọi đến
    private void getKeyIntent() {
        mKeyContactType = getIntent().getIntExtra(KEY_CONTACT_ACTIVITY_TYPE, -1);
        switch (mKeyContactType) {
            case REQUEST_CODE_CHOOSE_CONTACT_WITH_WHOM:
                tvTitleBarContact.setText(getResources().getString(R.string.with_whom));
                break;

            case REQUEST_CODE_CHOOSE_CONTACT_LENDER:
                tvTitleBarContact.setText(getResources().getString(R.string.lender));
                break;
        }
    }

    @Override
    public void resultGetAllContact(ArrayList<Contact> listContact) {
        mContactAdapter = new ContactAdapter(this, listContact, this);
        rcvContact.setAdapter(mContactAdapter);
    }

    @Override
    public void onClickContact(Contact contact) {
        chipViewContact.addChip(contact.getDisplayName(), contact.getAvatarUri(), contact);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppPermission.PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mContactActivityPresenter.getAllContactFromDevice();
            } else {
                showToast("------");
            }
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackContact:
                onBackPressed();
                break;

            case R.id.ivDoneSelectedContact:

                break;
        }
    }
}