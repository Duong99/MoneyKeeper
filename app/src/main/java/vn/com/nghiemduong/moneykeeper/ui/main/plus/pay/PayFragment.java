package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.chooseaccount.ChooseAccountActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.ChooseCategoriesActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.contact.ContactActivity;
import vn.com.nghiemduong.moneykeeper.ui.dialog.date.CustomDateTimeDialog;
import vn.com.nghiemduong.moneykeeper.utils.AppPermission;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

import static android.app.Activity.RESULT_OK;

/**
 * -  Màn hình chi tiền
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class PayFragment extends BaseFragment implements PayMvpView, View.OnClickListener,
        CustomDateTimeDialog.IOnClickSaveDateTime {

    private View mView;
    private RelativeLayout rlChooseCategoryPay, rlChooseAccountPay, rlSelectCategoryFee,
            rlRepaymentDate, rlLender, rlWhoContact, rlSelectFolder, rlSelectCamera,
            rlContentImage;
    private ImageView ivImageCategoriesPay, ivImageAccountPay, ivImageCategoryFee,
            ivImageSelectedPay, ivRemoveImageSelected;

    private TextView tvTitleSelectCategoryPay, tvTitleAccountPay, tvTitleCategoryFee,
            tvTitlePerson, tvTimePay, tvCalenderPay;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swtFee, swtLoanToPayAmount;
    private LinearLayout llContentFee, llContentLoanToPayAmount, llSelectImage;
    private Bitmap imagePay;


    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_pay, container, false);
        init();

        swtFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                extendOrCloseFee(isChecked);
            }
        });

        swtLoanToPayAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                extendOrCloseLoanToPayAmount(isChecked);
            }
        });
        return mView;
    }

    private void extendOrCloseFee(boolean isChecked) {
        if (isChecked) {
            llContentFee.setVisibility(View.VISIBLE);
        } else {
            llContentFee.setVisibility(View.GONE);
        }
    }

    private void extendOrCloseLoanToPayAmount(boolean isChecked) {
        if (isChecked) {
            llContentLoanToPayAmount.setVisibility(View.VISIBLE);
        } else {
            llContentLoanToPayAmount.setVisibility(View.GONE);
        }
    }

    // khởi tạo / ánh xạ
    private void init() {
        rlChooseCategoryPay = mView.findViewById(R.id.rlChooseCategory);
        rlChooseCategoryPay.setOnClickListener(this);

        rlLender = mView.findViewById(R.id.rlLender);
        rlLender.setOnClickListener(this);

        swtFee = mView.findViewById(R.id.swtFee);
        swtLoanToPayAmount = mView.findViewById(R.id.swtLoanToPayAmount);

        rlChooseAccountPay = mView.findViewById(R.id.rlChooseAccount);
        rlChooseAccountPay.setOnClickListener(this);

        rlSelectCategoryFee = mView.findViewById(R.id.rlSelectCategoryFee);
        rlSelectCategoryFee.setOnClickListener(this);

        rlRepaymentDate = mView.findViewById(R.id.rlRepaymentDate);
        rlRepaymentDate.setOnClickListener(this);

        rlWhoContact = mView.findViewById(R.id.rlWhoContact);
        rlWhoContact.setOnClickListener(this);

        rlSelectFolder = mView.findViewById(R.id.rlSelectFolder);
        rlSelectFolder.setOnClickListener(this);

        rlSelectCamera = mView.findViewById(R.id.rlSelectCamera);
        rlSelectCamera.setOnClickListener(this);

        tvTitlePerson = mView.findViewById(R.id.tvTitlePerson);
        tvTitlePerson.setText(getResources().getString(R.string.pay_for_someone));

        tvTimePay = mView.findViewById(R.id.tvTime);
        tvTimePay.setOnClickListener(this);
        tvTimePay.setText(UtilsPlus.getTimeCurrent());

        tvCalenderPay = mView.findViewById(R.id.tvCalendar);
        tvCalenderPay.setOnClickListener(this);
        tvCalenderPay.setText(UtilsPlus.getDateCurrent());

        ivImageCategoriesPay = mView.findViewById(R.id.ivImageCategories);
        ivImageSelectedPay = mView.findViewById(R.id.ivImageSelected);
        ivImageAccountPay = mView.findViewById(R.id.ivImageAccount);
        ivImageCategoryFee = mView.findViewById(R.id.ivImageCategoryFee);

        ivRemoveImageSelected = mView.findViewById(R.id.ivRemoveImageSelected);
        ivRemoveImageSelected.setOnClickListener(this);

        llContentFee = mView.findViewById(R.id.llContentFee);
        llContentLoanToPayAmount = mView.findViewById(R.id.llContentLoanToPayAmount);

        rlContentImage = mView.findViewById(R.id.rlContentImage);
        llSelectImage = mView.findViewById(R.id.llSelectImage);

        tvTitleSelectCategoryPay = mView.findViewById(R.id.tvTitleSelectCategory);
        tvTitleAccountPay = mView.findViewById(R.id.tvTitleAccount);
        tvTitleCategoryFee = mView.findViewById(R.id.tvTitleCategoryFee);
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

            case R.id.rlSelectCategoryFee:
                try {
                    startActivityForResult(new Intent(getContext(), ChooseCategoriesActivity.class),
                            ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY_FEE);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlChooseAccount:
                try {
                    startActivityForResult(new Intent(getContext(), ChooseAccountActivity.class),
                            ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvTime:
                try {
                    CustomDateTimeDialog customCalendarView = new CustomDateTimeDialog(getContext(),
                            CustomDateTimeDialog.KEY_WATCH, tvCalenderPay.getText().toString(),
                            tvTimePay.getText().toString(), this);
                    customCalendarView.show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.tvCalendar:
                try {
                    CustomDateTimeDialog customCalendarView = new CustomDateTimeDialog(getContext(),
                            CustomDateTimeDialog.KEY_CALENDAR, tvCalenderPay.getText().toString(),
                            tvTimePay.getText().toString(), this);
                    customCalendarView.show();
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlWhoContact:
                try {
                    Intent intent = new Intent(getContext(), ContactActivity.class);
                    intent.putExtra(ContactActivity.KEY_CONTACT_ACTIVITY_TYPE,
                            ContactActivity.REQUEST_CODE_CHOOSE_CONTACT_WITH_WHOM);
                    startActivityForResult(intent,
                            ContactActivity.REQUEST_CODE_CHOOSE_CONTACT_WITH_WHOM);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlLender:
                try {
                    Intent intent = new Intent(getContext(), ContactActivity.class);
                    intent.putExtra(ContactActivity.KEY_CONTACT_ACTIVITY_TYPE,
                            ContactActivity.REQUEST_CODE_CHOOSE_CONTACT_LENDER);
                    startActivityForResult(intent,
                            ContactActivity.REQUEST_CODE_CHOOSE_CONTACT_LENDER);
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }
                break;

            case R.id.rlRepaymentDate:

                break;

            case R.id.rlSelectFolder:
                if (AppPermission.requestCameraPermission(getContext(), getActivity())) {
                    Intent intentFolder = new Intent(Intent.ACTION_PICK);
                    intentFolder.setType("image/*");
                    startActivityForResult(intentFolder, AppUtils.REQUEST_CODE_IMAGE_FROM_FOLDER);
                }
                break;

            case R.id.rlSelectCamera:
                if (AppPermission.requestCameraPermission(getContext(), getActivity())) {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCamera, AppUtils.REQUEST_CODE_IMAGE_FROM_CAMERA);
                }
                break;

            case R.id.ivRemoveImageSelected:
                llSelectImage.setVisibility(View.VISIBLE);
                rlContentImage.setVisibility(View.GONE);
                imagePay = null;
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY_FEE:
                        UtilsPlus.onFinishChooseCategory(data,
                                ivImageCategoryFee, tvTitleCategoryFee);
                        break;

                    case ChooseCategoriesActivity.REQUEST_CODE_CHOOSE_CATEGORY:
                        UtilsPlus.onFinishChooseCategory(data,
                                ivImageCategoriesPay, tvTitleSelectCategoryPay);
                        break;

                    case ChooseAccountActivity.REQUEST_CODE_CHOOSE_ACCOUNT:
                        UtilsPlus.onFinishChooseAccount(data,
                                ivImageAccountPay, tvTitleAccountPay, getContext());
                        break;

                    case AppUtils.REQUEST_CODE_IMAGE_FROM_FOLDER:
                        Uri uri = data.getData();
                        try {
                            imagePay = MediaStore.Images.Media.getBitmap(getContext()
                                    .getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ivImageSelectedPay.setImageBitmap(imagePay);
                        llSelectImage.setVisibility(View.GONE);
                        rlContentImage.setVisibility(View.VISIBLE);
                        break;

                    case AppUtils.REQUEST_CODE_IMAGE_FROM_CAMERA:
                        imagePay = (Bitmap) data.getExtras().get("data");
                        ivImageSelectedPay.setImageBitmap(imagePay);
                        llSelectImage.setVisibility(View.GONE);
                        rlContentImage.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    @Override
    public void saveDateTime(String date, String time) {
        tvCalenderPay.setText(date);
        tvTimePay.setText(time);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
