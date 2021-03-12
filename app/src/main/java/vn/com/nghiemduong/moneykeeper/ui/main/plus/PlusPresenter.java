package vn.com.nghiemduong.moneykeeper.ui.main.plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.record.RecordDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.data.model.db.Record;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class PlusPresenter implements PlusMvpPresenter {
    private PlusMvpView mPlusMvpView;
    private Context mContext;
    private Activity mActivity;
    private RecordDatabase mRecordDatabase;

    public PlusPresenter(PlusMvpView mPlusMvpView, Context context, Activity activity) {
        this.mPlusMvpView = mPlusMvpView;
        this.mContext = context;
        this.mActivity = activity;
        mRecordDatabase = new RecordDatabase(mContext);
    }

    @Override
    public void addCategories() {
        ArrayList<HeaderCategory> listCategories = new ArrayList<>();
        listCategories.add(new HeaderCategory(R.drawable.ic_minus_red,
                mContext.getResources().getString(R.string.spend_money)));
        listCategories.add(new HeaderCategory(R.drawable.ic_plus_green,
                mContext.getResources().getString(R.string.collect_money)));
        listCategories.add(new HeaderCategory(R.drawable.ic_cho_vay,
                mContext.getResources().getString(R.string.loan)));
        listCategories.add(new HeaderCategory(R.drawable.ic_vay,
                mContext.getResources().getString(R.string.borrow)));
        listCategories.add(new HeaderCategory(R.drawable.ic_phi_chuyen_khoan,
                mContext.getResources().getString(R.string.transfer)));
        mPlusMvpView.resultListCategories(listCategories);
    }

    /**
     * @param position vị trí của từng màn hình mục
     * @created_by nxduong on 7/3/2021
     */

    @Override
    public void initView(int position) {
        switch (position) {
            case AppConstants.CHI_TIEN:
                mPlusMvpView.initViewPay();
                break;
            case AppConstants.THU_TIEN:
                mPlusMvpView.initViewCollect();
                break;
            case AppConstants.CHO_VAY:
                mPlusMvpView.initViewLoan();
                break;
            case AppConstants.DI_VAY:
                mPlusMvpView.initViewBorrow();
                break;
            case AppConstants.CHUYEN_KHOAN:
                mPlusMvpView.initViewTransfer();
                break;
        }
    }

    @Override
    public void doGetBundleRecord(Fragment fg) {
        Record record = null;
        if (fg != null) {
            if (fg.getArguments() != null) {
                record = (Record) fg.getArguments().getSerializable("BUNDLE_RECORD");
            }
        }
        mPlusMvpView.resultGetBundleRecord(record);
    }

    /**
     * Lấy giá trị hạng mục được chọn
     *
     * @return category hạng mục được chọn
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void doGetChooseCategory(Intent data) {
        Category category = (Category)
                Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                        .getSerializable("BUNDLE_CATEGORY");
        mPlusMvpView.resultChooseCategory(category);

        if (category != null) {
            setScreenFunctionWithCategory(category);
        }

    }

    /**
     * Hàm lấy giá trị tài khoản được chọn
     *
     * @return data
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public void doGetChooseAccount(Intent data, int request_code) {
        Account account = (Account) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_ACCOUNT");
        mPlusMvpView.resultChooseAccount(account, request_code);
    }

    /**
     * Hàm lấy giá trị tài khoản đầu tiên trong database
     *
     * @created_by nxduong on 10/2/2021
     */

    @Override
    public void doGetAccountFirstFromDB(Context context) {
        mPlusMvpView.resultGetAccountFirstFromDB(
                new AccountDatabase(context).getAccountFirstly());
    }

    /**
     * Hàm lấy dữ liệu người vay nợ
     *
     * @created_by nxduong on 11/3/2021
     */
    @Override
    public void doGetDebtor(Intent data) {
        Record debtor = (Record) Objects.requireNonNull(data.getBundleExtra("BUNDLE"))
                .getSerializable("BUNDLE_CONTACT");
        mPlusMvpView.resultChooseDebtor(debtor);
    }

    /**
     * Hàm kiểm tra hạng mục ứng với màn hình chức năng đó
     *
     * @param category hạng mục
     * @created_by nxduong on 9/3/2021
     */
    @Override
    public void setScreenFunctionWithCategory(Category category) {
        if (category.getType() == AppConstants.CHI_TIEN) {
            mPlusMvpView.initViewPay();
        } else if (category.getType() == AppConstants.THU_TIEN) {
            mPlusMvpView.initViewCollect();
        } else if (category.getType() == AppConstants.VAY_NO) {
            if (category.getCategoryId() == AppConstants.CHO_VAY_ID) {
                mPlusMvpView.initViewLoan();
            }

            if (category.getCategoryId() == AppConstants.DI_VAY_ID) {
                mPlusMvpView.initViewBorrow();
            }

            if (category.getCategoryId() == AppConstants.THU_NO_ID) {
                mPlusMvpView.initDebtCollect();
            }

            if (category.getCategoryId() == AppConstants.TRA_NO_ID) {
                mPlusMvpView.initViewDebtPay();
            }
        }
    }

    @Override
    public void saveRecord(Record mRecord, int amount, Category mCategory, String debtor,
                           String explain, String date, String time, Account mAccount,
                           Account mToAccount, String dateDuration,
                           int report, byte[] image, int recordConstant) {
        Record record;

        if (recordConstant == AppConstants.CHUYEN_KHOAN) { // Chuyển khoản
            if (mAccount == null) { // Kiểm tra tài khoản chuyển tiền đi có null ko
                mPlusMvpView.showCustomToastChooseFromAccountWarring(
                        mContext.getString(R.string.please_choose_from_account));
            } else if (mToAccount == null) {// Kiểm tra tài khoản chuyển tiền đến đi có null ko
                mPlusMvpView.showCustomToastChooseToAccountWarring(
                        mContext.getString(R.string.please_choose_to_account));
            } else {
                if (mRecord == null) {// Thêm chuyển khoản
                    record = new Record(amount, mAccount.getAccountId(), mToAccount.getAccountId(),
                            explain, date, time, report, image, recordConstant);
                    long insert = mRecordDatabase.insertRecord(record);
                    if (insert == DBUtils.checkDBFail) {
                        mPlusMvpView.saveRecordFail(
                                mContext.getString(R.string.insert_transfer_fail));
                    } else {
                        mPlusMvpView.saveRecordFail(
                                mContext.getString(R.string.insert_transfer_success));
                    }
                } else { //Sửa chuyển khoản
                    record = new Record(mRecord.getRecordId(), amount,
                            mAccount.getAccountId(), mToAccount.getAccountId(),
                            explain, date, time, report, image, recordConstant);
                    long update = mRecordDatabase.updateRecord(record, mRecord.getAmount());
                    if (update == DBUtils.checkDBFail) {
                        mPlusMvpView.saveRecordFail(
                                mContext.getString(R.string.update_transfer_fail));
                    } else {
                        mPlusMvpView.saveRecordFail(
                                mContext.getString(R.string.update_transfer_success));
                    }
                }

            }
        } else { // Chi tiền, thu tiền, cho vay, đi vay, trả nợ, thu nợ
            if (mCategory == null) { // Kiểm tra hạng mục
                mPlusMvpView.showCustomToastChooseCategoryWarring(
                        mContext.getString(R.string.please_choose_category));

            } else if (mAccount == null) { // Kiểm tra tài khoản có null ko
                mPlusMvpView.showCustomToastChooseAccountWarring(
                        mContext.getString(R.string.please_choose_account));
            } else {
                if (recordConstant == AppConstants.THU_NO || recordConstant == AppConstants.TRA_NO) {
                    if (debtor.equals("")) {
                        mPlusMvpView.showCustomToastChooseDebtorWarring(
                                mContext.getString(R.string.please_choose_debtor));
                    } else {
                        if (mRecord == null) {// Thêm thu nợ, trả nợ
                            record = new Record(amount, mCategory.getCategoryId(), debtor, explain, date, time,
                                    mAccount.getAccountId(), image, recordConstant);
                            long insert = mRecordDatabase.insertRecord(record);
                            if (insert == DBUtils.checkDBFail) {
                                if (recordConstant == AppConstants.THU_NO) {
                                    mPlusMvpView.saveRecordFail(
                                            mContext.getString(R.string.insert_debt_collect_fail));
                                } else { // trả nợ
                                    mPlusMvpView.saveRecordFail(
                                            mContext.getString(R.string.insert_debt_pay_fail));
                                }
                            } else {
                                if (recordConstant == AppConstants.THU_NO) {
                                    mPlusMvpView.saveRecordSuccess(
                                            mContext.getString(R.string.insert_debt_collect_success));
                                } else { // thu tiền
                                    mPlusMvpView.saveRecordSuccess(
                                            mContext.getString(R.string.insert_debt_pay_success));
                                }
                            }
                        } else {// Sửa thu nợ, trả nợ
                            record = new Record(mRecord.getRecordId(), amount, mCategory.getCategoryId(),
                                    debtor, explain, date, time, mAccount.getAccountId(), image, recordConstant);
                            long update = mRecordDatabase.updateRecord(record, mRecord.getRecordId());
                            if (update == DBUtils.checkDBFail) {
                                if (recordConstant == AppConstants.THU_NO) {
                                    mPlusMvpView.saveRecordFail(
                                            mContext.getString(R.string.update_debt_collect_fail));
                                } else { // trả nợ
                                    mPlusMvpView.saveRecordFail(
                                            mContext.getString(R.string.update_debt_pay_fail));
                                }
                            } else {
                                if (recordConstant == AppConstants.THU_NO) {
                                    mPlusMvpView.saveRecordSuccess(
                                            mContext.getString(R.string.update_debt_collect_success));
                                } else { // thu tiền
                                    mPlusMvpView.saveRecordSuccess(
                                            mContext.getString(R.string.update_debt_pay_success));
                                }
                            }
                        }
                    }
                } else {
                    if (recordConstant == AppConstants.DI_VAY || recordConstant == AppConstants.CHO_VAY) {
                        if (debtor.equals("")) {
                            mPlusMvpView.showCustomToastChooseDebtorWarring(
                                    mContext.getString(R.string.please_choose_debtor));
                        } else {
                            if (mRecord == null) { // thêm đối tượng đi vay, cho vay
                                record = new Record(amount, mCategory.getCategoryId(), debtor, explain, date, time,
                                        mAccount.getAccountId(), dateDuration, image, recordConstant);
                                long insert = mRecordDatabase.insertRecord(record);
                                if (insert == DBUtils.checkDBFail) {
                                    if (recordConstant == AppConstants.DI_VAY) {
                                        mPlusMvpView.saveRecordFail(
                                                mContext.getString(R.string.insert_borrow_fail));
                                    } else { // cho vay
                                        mPlusMvpView.saveRecordFail(
                                                mContext.getString(R.string.insert_loan_fail));
                                    }
                                } else {
                                    if (recordConstant == AppConstants.DI_VAY) {
                                        mPlusMvpView.saveRecordSuccess(
                                                mContext.getString(R.string.insert_borrow_success));
                                    } else { // cho vay
                                        mPlusMvpView.saveRecordSuccess(
                                                mContext.getString(R.string.insert_loan_success));
                                    }
                                }
                            } else { // sửa đối tượng đi vay, cho vay
                                record = new Record(mRecord.getRecordId(), amount, mCategory.getCategoryId(),
                                        debtor, explain, date, time,
                                        mAccount.getAccountId(), dateDuration, image, recordConstant);
                                long update = mRecordDatabase.updateRecord(record, mRecord.getAmount());
                                if (update == DBUtils.checkDBFail) {
                                    if (recordConstant == AppConstants.DI_VAY) {
                                        mPlusMvpView.saveRecordFail(
                                                mContext.getString(R.string.update_borrow_fail));
                                    } else { // cho vay
                                        mPlusMvpView.saveRecordFail(
                                                mContext.getString(R.string.update_loan_fail));
                                    }
                                } else {
                                    if (recordConstant == AppConstants.DI_VAY) {
                                        mPlusMvpView.saveRecordSuccess(
                                                mContext.getString(R.string.update_borrow_success));
                                    } else { // cho vay
                                        mPlusMvpView.saveRecordSuccess(
                                                mContext.getString(R.string.update_loan_success));
                                    }
                                }
                            }
                        }
                    } else { // Đối tượng chi tiền và thu tiền
                        if (mRecord == null) { // thêm Đối tượng chi tiền và thu tiền
                            record = new Record(amount, mCategory.getCategoryId(), explain, date, time,
                                    mAccount.getAccountId(),
                                    report, image, recordConstant);
                            long insert = mRecordDatabase.insertRecord(record);
                            if (insert == DBUtils.checkDBFail) {
                                if (recordConstant == AppConstants.CHI_TIEN) {
                                    mPlusMvpView.saveRecordFail(
                                            mContext.getString(R.string.insert_pay_fail));
                                } else { // thu tiền
                                    mPlusMvpView.saveRecordFail(
                                            mContext.getString(R.string.insert_collect_fail));
                                }
                            } else {
                                if (recordConstant == AppConstants.CHI_TIEN) {
                                    mPlusMvpView.saveRecordSuccess(
                                            mContext.getString(R.string.insert_pay_success));
                                } else { // thu tiền
                                    mPlusMvpView.saveRecordSuccess(
                                            mContext.getString(R.string.insert_collect_success));
                                }
                            }
                        } else { // sửa Đối tượng chi tiền và thu tiền
                            record = new Record(mRecord.getRecordId(), amount, mCategory.getCategoryId(),
                                    explain, date, time, mAccount.getAccountId(),
                                    report, image, recordConstant);
                            long update = mRecordDatabase.updateRecord(record, mRecord.getAmount());
                            if (update == DBUtils.checkDBFail) {
                                if (recordConstant == AppConstants.CHI_TIEN) {
                                    mPlusMvpView.saveRecordFail(
                                            mContext.getString(R.string.update_pay_fail));
                                } else { // thu tiền
                                    mPlusMvpView.saveRecordFail(
                                            mContext.getString(R.string.update_collect_fail));
                                }
                            } else {
                                if (recordConstant == AppConstants.CHI_TIEN) {
                                    mPlusMvpView.saveRecordSuccess(
                                            mContext.getString(R.string.update_pay_success));
                                } else { // thu tiền
                                    mPlusMvpView.saveRecordSuccess(
                                            mContext.getString(R.string.update_collect_success));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
