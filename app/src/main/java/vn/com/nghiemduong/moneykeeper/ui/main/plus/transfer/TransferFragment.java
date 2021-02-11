package vn.com.nghiemduong.moneykeeper.ui.main.plus.transfer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.dialog.date.CustomDateTimeDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Màn hình chuyển tiền giữa các tài khoản
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class TransferFragment extends BaseFragment implements View.OnClickListener,
        TransferFragmentMvpView, CustomDateTimeDialog.IOnClickSaveDateTime {

    private static final int REQUEST_CODE_CHOOSE_FROM_ACCOUNT = 21;
    private static final int REQUEST_CODE_CHOOSE_TO_ACCOUNT = 22;

    private View mView;
    private EditText etMoney, etExplain;

    private LinearLayout llLayoutDetail, llContentDetail, llSelectImage;

    private RelativeLayout rlChooseFromAccount, rlChooseToAccount, rlSelectFolder,
            rlSelectCamera, rlContentImage;

    private ImageView ivImageFromAccount, ivImageToAccount, ivDetail,
            ivImageSelected, ivRemoveImageSelected;

    private TextView tvTitleFromAccount, tvTitleToAccount, tvCalendar, tvTime, tvDetail;

    private Account mFromAccount, mToAccount; // Tài khoản chuyển tiền và tài khoản chuyển tiền đến

    private TransferFragmentPresenter mTransferFragmentPresenter;
    private Bitmap imageTransfer;

    public TransferFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_transfer, container, false);

        init();
        mTransferFragmentPresenter.doGetFromAccountFirstFromDB();
        return mView;
    }

    /**
     * Khởi tạo / ánh xạ view
     *
     * @created_by nxduong on 11/2/2021
     */
    private void init() {
        etExplain = mView.findViewById(R.id.etExplain);
        etMoney = mView.findViewById(R.id.etInputMoney);
        etMoney.setTextColor(getResources().getColor(R.color.blue));

        ivDetail = mView.findViewById(R.id.ivDetail);
        tvDetail = mView.findViewById(R.id.tvDetail);
        llContentDetail = mView.findViewById(R.id.llContentDetail);

        ivImageSelected = mView.findViewById(R.id.ivImageSelected);
        llSelectImage = mView.findViewById(R.id.llSelectImage);
        rlContentImage = mView.findViewById(R.id.rlContentImage);

        ivImageFromAccount = mView.findViewById(R.id.ivImageAccount);
        tvTitleFromAccount = mView.findViewById(R.id.tvTitleAccount);

        ivImageToAccount = mView.findViewById(R.id.ivImageToAccount);
        tvTitleToAccount = mView.findViewById(R.id.tvTitleToAccount);

        rlChooseFromAccount = mView.findViewById(R.id.rlChooseAccount);
        rlChooseFromAccount.setOnClickListener(this);

        rlSelectFolder = mView.findViewById(R.id.rlSelectFolder);
        rlSelectFolder.setOnClickListener(this);

        rlChooseToAccount = mView.findViewById(R.id.rlChooseToAccount);
        rlChooseToAccount.setOnClickListener(this);

        rlSelectCamera = mView.findViewById(R.id.rlSelectCamera);
        rlSelectCamera.setOnClickListener(this);

        ivRemoveImageSelected = mView.findViewById(R.id.ivRemoveImageSelected);
        ivRemoveImageSelected.setOnClickListener(this);

        tvCalendar = mView.findViewById(R.id.tvCalendar);
        tvCalendar.setOnClickListener(this);
        tvCalendar.setText(UtilsPlus.getDateCurrent());

        tvTime = mView.findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);
        tvTime.setText(UtilsPlus.getTimeCurrent());

        llLayoutDetail = mView.findViewById(R.id.llLayoutDetail);
        llLayoutDetail.setOnClickListener(this);

        mTransferFragmentPresenter = new TransferFragmentPresenter(this, getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseAccount: // Chọn chuyển tiền từ tài khoản đến
                try {
                    Intent intent = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BUNDLE_ACCOUNT", mFromAccount);
                    intent.putExtra("BUNDLE", bundle);
                    startActivityForResult(intent, REQUEST_CODE_CHOOSE_FROM_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseToAccount: // Chọn chuyển tiền đến tài khoản
                try {
                    Intent intent = new Intent(getContext(), ChooseAccountActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BUNDLE_ACCOUNT", mToAccount);
                    intent.putExtra("BUNDLE", bundle);
                    startActivityForResult(intent, REQUEST_CODE_CHOOSE_TO_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvCalendar: // Chọn ngày tháng năm
                try {
                    new CustomDateTimeDialog(Objects.requireNonNull(getContext()),
                            CustomDateTimeDialog.KEY_CALENDAR, tvCalendar.getText().toString(),
                            tvTime.getText().toString(), this).show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvTime: // Thời thời gian
                try {
                    new CustomDateTimeDialog(Objects.requireNonNull(getContext()),
                            CustomDateTimeDialog.KEY_WATCH, tvCalendar.getText().toString(),
                            tvTime.getText().toString(), this).show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.llLayoutDetail: // Chọn vào xem chi tiết
                if (llContentDetail.getVisibility() == View.GONE) {
                    llContentDetail.setVisibility(View.VISIBLE);
                    ivDetail.setBackground(getResources().getDrawable(R.drawable.ic_arrow_down));
                    tvDetail.setText(getString(R.string.hidden_detail));
                } else {
                    llContentDetail.setVisibility(View.GONE);
                    ivDetail.setBackground(getResources().getDrawable(R.drawable.ic_arrow_up));
                    tvDetail.setText(getString(R.string.more_detail));
                }
                break;

            case R.id.rlSelectFolder: // Chọn ảnh từ thu mục
                try {
                    if (AppPermission.requestCameraPermission(getContext(), getActivity())) {
                        Intent intentFolder = new Intent(Intent.ACTION_PICK);
                        intentFolder.setType("image/*");
                        startActivityForResult(intentFolder, AppUtils.REQUEST_CODE_IMAGE_FROM_FOLDER);
                    }
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

                break;

            case R.id.rlSelectCamera: // Chọn camera chụp ảnh
                try {
                    if (AppPermission.requestCameraPermission(getContext(), getActivity())) {
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intentCamera, AppUtils.REQUEST_CODE_IMAGE_FROM_CAMERA);
                    }
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.ivRemoveImageSelected: // Xóa ảnh được chọn
                try {
                    llSelectImage.setVisibility(View.VISIBLE);
                    rlContentImage.setVisibility(View.GONE);
                    imageTransfer = null;
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case REQUEST_CODE_CHOOSE_FROM_ACCOUNT:
                        mTransferFragmentPresenter.doGetOnActivityFromAccount(data);
                        break;

                    case REQUEST_CODE_CHOOSE_TO_ACCOUNT:
                        mTransferFragmentPresenter.doGetOnActivityToAccount(data);
                        break;

                    case AppUtils.REQUEST_CODE_IMAGE_FROM_FOLDER:
                        try {
                            Uri uri = data.getData();
                            imageTransfer = MediaStore.Images.Media.getBitmap(
                                    Objects.requireNonNull(getContext())
                                            .getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setValueImageFolderOrCamera();
                        break;

                    case AppUtils.REQUEST_CODE_IMAGE_FROM_CAMERA:
                        imageTransfer = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                        setValueImageFolderOrCamera();
                        break;

                }
            }
        }
    }

    @Override
    public void resultGetFromAccountFirstFromDB(Account fromAccount) {
        if (mFromAccount == null) {
            this.mFromAccount = fromAccount;
            setValueFromAccount();
        }
    }

    @Override
    public void resultGetOnActivityFromAccount(Account fromAccount) {
        this.mFromAccount = fromAccount;
        setValueFromAccount();
    }

    @Override
    public void resultGetOnActivityToAccount(Account toAccount) {
        this.mToAccount = toAccount;
        setValueToAccount();
    }

    /**
     * Hàm set giá trị các view của từ tài khoản
     *
     * @created_by nxduong on 11/2/2021
     */
    private void setValueFromAccount() {
        if (mFromAccount != null) {
            ivImageFromAccount.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mFromAccount.getAccountTypePath(), Objects.requireNonNull(getContext())));
            tvTitleFromAccount.setText(mFromAccount.getAccountName());
        }
    }

    /**
     * Hàm set giá trị các view của tài khoản chuyển tiền đến
     *
     * @created_by nxduong on 11/2/2021
     */
    private void setValueToAccount() {
        if (mToAccount != null) {
            ivImageToAccount.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mToAccount.getAccountTypePath(), Objects.requireNonNull(getContext())));
            tvTitleToAccount.setText(mToAccount.getAccountName());
        }
    }

    /**
     * Hàm set giá trị khi chọn ảnh
     *
     * @created_by nxduong on 11/2/2021
     */
    private void setValueImageFolderOrCamera() {
        ivImageSelected.setImageBitmap(imageTransfer);
        llSelectImage.setVisibility(View.GONE);
        rlContentImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void saveDateTime(String date, String time) {
        tvCalendar.setText(date);
        tvTime.setText(time);
    }
}