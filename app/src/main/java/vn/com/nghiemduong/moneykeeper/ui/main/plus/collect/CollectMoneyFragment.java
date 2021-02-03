package vn.com.nghiemduong.moneykeeper.ui.main.plus.collect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.MoneyCollect.MoneyCollectDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.dialog.date.CustomDateTimeDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.ChooseCategoriesActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

import static android.app.Activity.RESULT_OK;

/**
 * -
 * Màn chi thu tiền
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class CollectMoneyFragment extends BaseFragment implements View.OnClickListener,
        CustomDateTimeDialog.IOnClickSaveDateTime {
    private View mView;
    private RelativeLayout rlChooseCategoryCollect, rlChooseAccountCollect,
            rlSelectFolder, rlSelectCamera, rlContentImage;
    private ImageView ivImageCategoriesCollect, ivImageAccountCollect, ivRemoveImageSelected,
            ivImageSelected;
    private TextView tvTitleSelectCategoryCollect, tvCalendarCollect, tvTimeCollect,
            tvTitleAccountCollect;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swNotIncludeReport;
    private LinearLayout llSelectImage, llSave;
    private Bitmap imageCollect;
    private EditText etInputMoney, etExplain;
    private MoneyCollectDatabase mMoneyCollectDatabase;
    private Category mCategory;
    private Account mAccount;


    public CollectMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_collect_money, container, false);

        init();
        return mView;
    }

    // Khởi tạo / Ánh xạ cho view
    private void init() {
        rlChooseCategoryCollect = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategoryCollect.setOnClickListener(this);

        llSave = mView.findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        rlChooseAccountCollect = mView.findViewById(R.id.rlChooseAccount);
        rlChooseAccountCollect.setOnClickListener(this);

        rlSelectFolder = mView.findViewById(R.id.rlSelectFolder);
        rlSelectFolder.setOnClickListener(this);

        rlSelectCamera = mView.findViewById(R.id.rlSelectCamera);
        rlSelectCamera.setOnClickListener(this);

        rlContentImage = mView.findViewById(R.id.rlContentImage);
        rlContentImage.setOnClickListener(this);

        ivRemoveImageSelected = mView.findViewById(R.id.ivRemoveImageSelected);
        ivRemoveImageSelected.setOnClickListener(this);

        tvCalendarCollect = mView.findViewById(R.id.tvCalendar);
        tvCalendarCollect.setOnClickListener(this);
        tvCalendarCollect.setText(UtilsPlus.getDateCurrent());

        tvTimeCollect = mView.findViewById(R.id.tvTime);
        tvTimeCollect.setOnClickListener(this);
        tvTimeCollect.setText(UtilsPlus.getTimeCurrent());

        ivImageCategoriesCollect = mView.findViewById(R.id.ivImageCategories);
        tvTitleSelectCategoryCollect = mView.findViewById(R.id.tvTitleSelectCategory);
        ivImageSelected = mView.findViewById(R.id.ivImageSelected);
        ivImageAccountCollect = mView.findViewById(R.id.ivImageAccount);

        tvTitleAccountCollect = mView.findViewById(R.id.tvTitleAccount);
        llSelectImage = mView.findViewById(R.id.llSelectImage);
        etInputMoney = mView.findViewById(R.id.etInputMoney);
        etExplain = mView.findViewById(R.id.etExplain);
        swNotIncludeReport = mView.findViewById(R.id.swNotIncludeReport);

        mMoneyCollectDatabase = new MoneyCollectDatabase(getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChooseCategory:
                try {
                    startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                            ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvCalendar:
                try {
                    CustomDateTimeDialog customDateDialog = new CustomDateTimeDialog(getContext(),
                            CustomDateTimeDialog.KEY_CALENDAR, tvCalendarCollect.getText().toString(),
                            tvTimeCollect.getText().toString(), this);
                    customDateDialog.show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvTime:
                try {
                    CustomDateTimeDialog customTimeDialog = new CustomDateTimeDialog(getContext(),
                            CustomDateTimeDialog.KEY_WATCH, tvCalendarCollect.getText().toString(),
                            tvTimeCollect.getText().toString(), this);
                    customTimeDialog.show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseAccount:
                try {
                    Intent intent = new Intent(getContext(), ChooseAccountActivity.class);
                    startActivityForResult(intent, ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlSelectFolder:
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

            case R.id.rlSelectCamera:
                try {
                    if (AppPermission.requestCameraPermission(getContext(), getActivity())) {
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intentCamera, AppUtils.REQUEST_CODE_IMAGE_FROM_CAMERA);
                    }
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.ivRemoveImageSelected:
                try {
                    llSelectImage.setVisibility(View.VISIBLE);
                    rlContentImage.setVisibility(View.GONE);
                    imageCollect = null;
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.llSave:
                if (AppUtils.getEditText(etInputMoney).isEmpty()) {

                } else if (mCategory == null) {

                } else if (mAccount == null) {

                } else {
                    int report = 1;

                    if (swNotIncludeReport.isChecked()) {
                        report = 0;
                    }
                    byte[] image = null;
                    if (imageCollect != null) {
                        image = AppUtils.convertBitmapToByteArray(imageCollect);
                    }
                    MoneyCollect moneyCollect = new MoneyCollect(mAccount.getAccountId(),
                            Integer.parseInt(AppUtils.getEditText(etInputMoney)),
                            mCategory.getTitle(), mCategory.getImage(), mAccount.getAccountName(),
                            AppUtils.getEditText(etExplain), tvCalendarCollect.getText().toString(),
                            tvTimeCollect.getText().toString(), report, image);
                    long insert = mMoneyCollectDatabase.insertMoneyCollect(moneyCollect);
                    if (insert == DBUtils.checkDBFail) {
                        showToast(getResources().getString(R.string.insert_collect_fail));
                    } else {
                        showToast(getResources().getString(R.string.insert_collect_success));
                        etInputMoney.setText("0");
                        etExplain.setText("");
                    }
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
                    case ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY:
                        mCategory = UtilsPlus.onFinishChooseCategory(data, ivImageCategoriesCollect,
                                tvTitleSelectCategoryCollect, getContext());
                        break;

                    case ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT:
                        mAccount = UtilsPlus.onFinishChooseAccount(data, ivImageAccountCollect,
                                tvTitleAccountCollect, getContext());
                        break;
                    case AppUtils.REQUEST_CODE_IMAGE_FROM_FOLDER:
                        Uri uri = data.getData();
                        try {
                            imageCollect = MediaStore.Images.Media.getBitmap(getContext()
                                    .getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ivImageSelected.setImageBitmap(imageCollect);
                        llSelectImage.setVisibility(View.GONE);
                        rlContentImage.setVisibility(View.VISIBLE);
                        break;

                    case AppUtils.REQUEST_CODE_IMAGE_FROM_CAMERA:
                        imageCollect = (Bitmap) data.getExtras().get("data");
                        ivImageSelected.setImageBitmap(imageCollect);
                        llSelectImage.setVisibility(View.GONE);
                        rlContentImage.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    @Override
    public void saveDateTime(String date, String time) {
        tvTimeCollect.setText(time);
        tvCalendarCollect.setText(date);
    }
}