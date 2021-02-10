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
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.MoneyCollect.MoneyCollectDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.dialog.attention.AttentionDialog;
import vn.com.nghiemduong.moneykeeper.ui.dialog.date.CustomDateTimeDialog;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.choose.ChooseCategoriesActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Màn chi thu tiền
 * - @created_by nxduong on 26/1/2021
 **/
public class CollectMoneyFragment extends BaseFragment implements View.OnClickListener,
        CustomDateTimeDialog.IOnClickSaveDateTime, AttentionDialog.IOnClickAttentionDialog,
        CollectMoneyFragmentMvpView {
    private View mView;
    private RelativeLayout rlChooseCategoryCollect, rlChooseAccountCollect,
            rlSelectFolder, rlSelectCamera, rlContentImage;
    private ImageView ivImageCategoriesCollect, ivImageAccountCollect, ivRemoveImageSelected,
            ivImageSelected, ivDetail;
    private TextView tvTitleSelectCategoryCollect, tvCalendarCollect, tvTimeCollect,
            tvTitleAccountCollect, tvDetail;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swNotIncludeReport;
    private LinearLayout llSelectImage, llSave, llDelete, llContentDetail, llLayoutDetail;
    private Bitmap imageCollect;
    private EditText etInputMoney, etExplain;
    private MoneyCollectDatabase mMoneyCollectDatabase;
    private Category mCategory;
    private SubCategory mSubCategory;
    private Account mAccount;
    private MoneyCollect mMoneyCollect;
    private CollectMoneyFragmentPresenter mCollectMoneyFragmentPresenter;

    public CollectMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_collect_money, container, false);

        init();
        getDataMoneyCollectFromBundle();
        mCollectMoneyFragmentPresenter.doGetAccountFirstFromDB(getContext());
        return mView;
    }

    /**
     * Hàm lấy dữ liệu đối tượng MoneyColelct khi người dùng click vào để chỉnh sửa hoặc xóa
     * và set các view tương ứng
     *
     * @created_by nxduong on 6/2/2021
     */

    private void getDataMoneyCollectFromBundle() {
        if (this.getArguments() != null) {
            mCollectMoneyFragmentPresenter.doGetMoneyCollectFromBundle(this, getContext());
        }
    }

    /**
     * Khởi tạo ánh xạ view
     *
     * @created_by nxduong on 26/1/2021
     */

    private void init() {
        rlChooseCategoryCollect = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategoryCollect.setOnClickListener(this);

        llSave = mView.findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        llDelete = mView.findViewById(R.id.llDelete);
        llDelete.setOnClickListener(this);

        llLayoutDetail = mView.findViewById(R.id.llLayoutDetail);
        llLayoutDetail.setOnClickListener(this);

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
        ivDetail = mView.findViewById(R.id.ivDetail);

        tvTitleAccountCollect = mView.findViewById(R.id.tvTitleAccount);
        tvDetail = mView.findViewById(R.id.tvDetail);
        llSelectImage = mView.findViewById(R.id.llSelectImage);
        llContentDetail = mView.findViewById(R.id.llContentDetail);

        etInputMoney = mView.findViewById(R.id.etInputMoney);
        etInputMoney.setTextColor(getResources().getColor(R.color.green));

        etExplain = mView.findViewById(R.id.etExplain);
        swNotIncludeReport = mView.findViewById(R.id.swNotIncludeReport);

        mMoneyCollectDatabase = new MoneyCollectDatabase(getContext());
        mCollectMoneyFragmentPresenter = new CollectMoneyFragmentPresenter(this);
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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("BUNDLE_ACCOUNT", mAccount);
                    intent.putExtra("BUNDLE", bundle);
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

                    int accountId = mAccount.getAccountId();
                    int amountOfMoney = Integer.parseInt(AppUtils.getEditText(etInputMoney));

                    int subCategoryId;
                    int categoryId;

                    String explain = AppUtils.getEditText(etExplain);
                    String date = tvCalendarCollect.getText().toString();
                    String time = tvTimeCollect.getText().toString();

                    if (mMoneyCollect == null) { // Thêm thu tiền
                        MoneyCollect moneyCollect;
                        if (mSubCategory != null) { // Đối tượng có hạng mục con
                            subCategoryId = mSubCategory.getSubCategoryId();
                            categoryId = mSubCategory.getCategoryId();

                            moneyCollect = new MoneyCollect(accountId, amountOfMoney,
                                    categoryId, subCategoryId, explain, date, time, report, image);
                        } else { // Đối tượng không có hạng mục con
                            categoryId = mCategory.getCategoryId();
                            moneyCollect = new MoneyCollect(accountId, amountOfMoney,
                                    categoryId, explain, date, time, report, image);
                        }

                        long insert = mMoneyCollectDatabase.insertMoneyCollect(moneyCollect);
                        if (insert == DBUtils.checkDBFail) {
                            showToast(getResources().getString(R.string.insert_collect_fail));
                        } else {
                            showToast(getResources().getString(R.string.insert_collect_success));
                            etInputMoney.setText(getString(R.string._0));
                            etExplain.setText(null);
                            mMoneyCollect = null;
                        }
                    } else { // Sửa thu tiền
                        int moneyPrevious = mMoneyCollect.getAmountOfMoney();
                        MoneyCollect moneyCollect;
                        if (mSubCategory != null) { // Đối tượng có hạng mục con
                            subCategoryId = mSubCategory.getSubCategoryId();
                            categoryId = mSubCategory.getCategoryId();

                            moneyCollect = new MoneyCollect(mMoneyCollect.getCollectId(),
                                    accountId, amountOfMoney, categoryId, subCategoryId,
                                    explain, date, time, report, image);
                        } else { // Đối tượng không có hạng mục con

                            categoryId = mCategory.getCategoryId();
                            moneyCollect = new MoneyCollect(mMoneyCollect.getCollectId(),
                                    accountId, amountOfMoney, categoryId, -1,
                                    explain, date, time, report, image);
                        }

                        long update = mMoneyCollectDatabase.updateMoneyCollect(moneyCollect, moneyPrevious);
                        if (update == DBUtils.checkDBFail) {
                            showToast(getResources().getString(R.string.update_collect_fail));
                        } else {
                            showToast(getResources().getString(R.string.update_collect_success));
                            etInputMoney.setText(getString(R.string._0));
                            etExplain.setText(null);
                            onBackPressed();
                        }
                    }
                }
                break;

            case R.id.llLayoutDetail:
                if (llContentDetail.getVisibility() == View.GONE) {
                    llContentDetail.setVisibility(View.VISIBLE);
                    tvDetail.setText(getString(R.string.hidden_detail));
                    ivDetail.setBackgroundResource(R.drawable.ic_arrow_up);
                } else {
                    llContentDetail.setVisibility(View.GONE);
                    tvDetail.setText(getString(R.string.more_detail));
                    ivDetail.setBackgroundResource(R.drawable.ic_arrow_down);
                }
                break;

            case R.id.llDelete:
                new AttentionDialog(getContext(),
                        this, AttentionDialog.ATTENTION_DELETE_DATA).show();
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
                        mCollectMoneyFragmentPresenter
                                .onActivityResultChooseCategoryCollect(data, getContext());
                        break;

                    case ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT:
                        mCollectMoneyFragmentPresenter
                                .onActivityResultAccountCollect(data);
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

    @Override
    public void onClickYesDelete() {
        try {
            if (mMoneyCollect != null) {
                long delete = mMoneyCollectDatabase.deleteMoneyCollect(mMoneyCollect);
                if (delete == DBUtils.checkDBFail) {
                    showToast(getString(R.string.delete_collect_fail));
                } else {
                    showToast(getString(R.string.delete_collect_success));
                }
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    @Override
    public void resultGetMoneyCollectFromBundle(MoneyCollect moneyCollect, Account account,
                                                Category category, SubCategory subCategory) {
        this.mAccount = account;
        this.mMoneyCollect = moneyCollect;
        this.mSubCategory = subCategory;
        this.mCategory = category;
        if (mMoneyCollect != null) {
            etInputMoney.setText(String.valueOf(mMoneyCollect.getAmountOfMoney()));
            etExplain.setText(mMoneyCollect.getExplain());
            tvCalendarCollect.setText(mMoneyCollect.getDate());
            tvTimeCollect.setText(mMoneyCollect.getTime());
            ivImageSelected.setImageBitmap(AppUtils.convertByteArrayToBitmap(mMoneyCollect.getImage()));
            llDelete.setVisibility(View.VISIBLE); // Hiện nút xóa lên

            if (mSubCategory != null) {
                ivImageCategoriesCollect.setImageBitmap(
                        AppUtils.convertPathFileImageAssetsToBitmap(mSubCategory.getSubCategoryPath(),
                                Objects.requireNonNull(getContext())));
                tvTitleSelectCategoryCollect.setText(mSubCategory.getSubCategoryName());
            } else {
                if (mCategory != null) {
                    ivImageCategoriesCollect.setImageBitmap(
                            AppUtils.convertPathFileImageAssetsToBitmap(mCategory.getCategoryPath(),
                                    Objects.requireNonNull(getContext())));
                    tvTitleSelectCategoryCollect.setText(mCategory.getCategoryName());
                }
            }

            if (mAccount != null) {
                tvTitleAccountCollect.setText(mAccount.getAccountName());
                ivImageAccountCollect.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                        mAccount.getAccountTypePath(), Objects.requireNonNull(getContext())));
            }
        }
    }

    @Override
    public void resultOnActivityResultChooseCategoryCollect(Category category, SubCategory subCategory) {
        this.mCategory = category;
        this.mSubCategory = subCategory;
        if (mSubCategory != null) {
            tvTitleSelectCategoryCollect.setText(mSubCategory.getSubCategoryName());
            ivImageCategoriesCollect.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mSubCategory.getSubCategoryPath(), getContext()));
        } else {
            if (mCategory != null) {
                tvTitleSelectCategoryCollect.setText(mCategory.getCategoryName());
                ivImageCategoriesCollect.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                        mCategory.getCategoryPath(), getContext()));
            }
        }
    }

    @Override
    public void resultOnFinishChooseAccountCollect(Account account) {
        this.mAccount = account;
        if (mAccount != null) {
            ivImageAccountCollect.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    mAccount.getAccountTypePath(), Objects.requireNonNull(getContext())));
            tvTitleAccountCollect.setText(mAccount.getAccountName());
        }
    }

    @Override
    public void resultGetAccountFirstFromDB(Account account) {
        if (mAccount == null) {
            this.mAccount = account;
            if (mAccount != null) {
                ivImageAccountCollect.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                        mAccount.getAccountTypePath(), Objects.requireNonNull(getContext())));
                tvTitleAccountCollect.setText(mAccount.getAccountName());
            }
        }
    }
}