package vn.com.nghiemduong.moneykeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountMoneyDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.SubCategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.data.model.Transfer;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 4/2/2021
 **/
public class HistoryNoteRcvAdapter extends RecyclerView.Adapter<HistoryNoteRcvAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MoneyCollect> mListMoneyCollect;
    private ArrayList<MoneyPay> mListMoneyPay;
    private ArrayList<Transfer> mListTransfer;
    private ArrayList<String> mListTimes;
    private IOnClickHistoryNoteMvpView mIOnClickHistoryNoteMvpView;
    private AccountMoneyDatabase mAccountMoneyDatabase;
    private CategoryDatabase mCategoryDatabase;
    private SubCategoryDatabase mSubCategoryDatabase;

    public HistoryNoteRcvAdapter(Context mContext, ArrayList<MoneyCollect> listMoneyCollect,
                                 ArrayList<MoneyPay> listMoneyPay, ArrayList<Transfer> listTransfer,
                                 IOnClickHistoryNoteMvpView onClickHistoryNoteMvpView) {
        this.mContext = mContext;
        this.mListMoneyCollect = listMoneyCollect;
        this.mListMoneyPay = listMoneyPay;
        this.mListTransfer = listTransfer;
        this.mIOnClickHistoryNoteMvpView = onClickHistoryNoteMvpView;
        this.mListTimes = new ArrayList<>();
        this.mAccountMoneyDatabase = new AccountMoneyDatabase(mContext);
        this.mCategoryDatabase = new CategoryDatabase(mContext);
        this.mSubCategoryDatabase = new SubCategoryDatabase(mContext);
        sortListTimes();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_history_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MoneyPay moneyPay;
        Account account;
        Category category;
        SubCategory subCategory;
        if (mListTimes.get(position) != null) {
            for (int i = 0; i < mListMoneyPay.size(); i++) {
                moneyPay = mListMoneyPay.get(i);
                if (moneyPay != null) {
                    if (mListTimes.get(position).equals(moneyPay.getDate()
                            + moneyPay.getTime())) {

                        holder.tvNumberMoneyHistoryNote.setText(String.valueOf(moneyPay.getAmountOfMoney()));
                        holder.tvNumberMoneyHistoryNote.setTextColor(mContext.getResources()
                                .getColor(R.color.red));
                        holder.tvDateHistoryNote.setText(moneyPay.getDate());
                        if (moneyPay.getSubCategoryId() == 0) {
                            category = mCategoryDatabase.getCategory(moneyPay.getCategoryId());
                            if (category != null) {
                                holder.ivCategoryHistoryNote.setImageBitmap(
                                        AppUtils.convertPathFileImageAssetsToBitmap(
                                                category.getCategoryPath(), mContext));
                                holder.tvCategoryNameHistoryNote.setText(category.getCategoryName());
                            }
                        } else {
                            subCategory = mSubCategoryDatabase.getSubCategory(
                                    moneyPay.getSubCategoryId());
                            if (subCategory != null) {
                                holder.ivCategoryHistoryNote.setImageBitmap(
                                        AppUtils.convertPathFileImageAssetsToBitmap(
                                                subCategory.getSubCategoryPath(), mContext));
                                holder.tvCategoryNameHistoryNote.setText(subCategory.getSubCategoryName());
                            }
                        }
                        account = mAccountMoneyDatabase.getAccount(moneyPay.getAccountId());
                        if (account != null) {
                            holder.tvAccountNameHistoryRecent.setText(account.getAccountName());
                        } else {
                            holder.tvAccountNameHistoryRecent.setText(mContext.getResources()
                                    .getString(R.string.unknown));
                        }
                        break;
                    }
                }
            }

            MoneyCollect moneyCollect;
            for (int j = 0; j < mListMoneyCollect.size(); j++) {
                moneyCollect = mListMoneyCollect.get(j);
                if (moneyCollect != null) {
                    if (mListTimes.get(position).equals(moneyCollect.getDate()
                            + moneyCollect.getTime())) {

                        if (moneyCollect.getSubCategoryId() == 0) {
                            category = mCategoryDatabase.getCategory(moneyCollect.getCategoryId());
                            if (category != null) {
                                holder.ivCategoryHistoryNote.setImageBitmap(
                                        AppUtils.convertPathFileImageAssetsToBitmap(
                                                category.getCategoryPath(), mContext));
                                holder.tvCategoryNameHistoryNote.setText(category.getCategoryName());
                            }
                        } else {
                            subCategory = mSubCategoryDatabase.getSubCategory(
                                    moneyCollect.getSubCategoryId());
                            if (subCategory != null) {
                                holder.ivCategoryHistoryNote.setImageBitmap(
                                        AppUtils.convertPathFileImageAssetsToBitmap(
                                                subCategory.getSubCategoryPath(), mContext));
                                holder.tvCategoryNameHistoryNote.setText(subCategory.getSubCategoryName());
                            }
                        }

                        holder.tvNumberMoneyHistoryNote.setText(String.valueOf(
                                moneyCollect.getAmountOfMoney()));
                        holder.tvNumberMoneyHistoryNote.setTextColor(mContext.getResources()
                                .getColor(R.color.color_green_collect));
                        holder.tvDateHistoryNote.setText(moneyCollect.getDate());

                        account = mAccountMoneyDatabase.getAccount(moneyCollect.getAccountId());
                        if (account != null) {
                            holder.tvAccountNameHistoryRecent.setText(account.getAccountName());
                        } else {
                            holder.tvAccountNameHistoryRecent.setText(
                                    mContext.getResources().getString(R.string.unknown));
                        }
                        break;
                    }
                }
            }

            Transfer transfer;
            for (int k = 0; k < mListTransfer.size(); k++) {
                transfer = mListTransfer.get(k);
                if (transfer != null) {
                    if (mListTimes.get(position).equals(transfer.getDate()
                            + transfer.getTime())) {

                        holder.tvNumberMoneyHistoryNote.setText(String.valueOf(
                                transfer.getAmountOfMoney()));
                        holder.tvNumberMoneyHistoryNote.setTextColor(mContext.getResources()
                                .getColor(R.color.black));
                        holder.tvDateHistoryNote.setText(transfer.getDate());

                        holder.tvCategoryNameHistoryNote.setText(mContext.getResources()
                                .getString(R.string.transfer_to_account));

                        account = mAccountMoneyDatabase.getAccount(transfer.getFromAccountId());

                        if (account != null) {
                            holder.tvAccountNameHistoryRecent.setText(account.getAccountName());
                        } else {
                            holder.tvAccountNameHistoryRecent.setText(mContext.getResources()
                                    .getString(R.string.unknown));
                        }

                        holder.ivCategoryHistoryNote.setImageBitmap(
                                AppUtils.convertPathFileImageAssetsToBitmap(
                                        "assets/ImageCategory/CHI/CHI_phi_chuyen_khoan.png",
                                        mContext));
                        break;
                    }
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mListTimes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategoryNameHistoryNote, tvNumberMoneyHistoryNote,
                tvDateHistoryNote, tvAccountNameHistoryRecent;
        private ImageView ivCategoryHistoryNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryNameHistoryNote = itemView.findViewById(R.id.tvCategoryNameHistoryNote);
            tvNumberMoneyHistoryNote = itemView.findViewById(R.id.tvNumberMoneyHistoryNote);
            tvDateHistoryNote = itemView.findViewById(R.id.tvDateHistoryNote);
            tvAccountNameHistoryRecent = itemView.findViewById(R.id.tvAccountNameHistoryRecent);
            ivCategoryHistoryNote = itemView.findViewById(R.id.ivCategoryHistoryNote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    boolean checkBreak = false;
                    if (position != RecyclerView.NO_POSITION) {
                        for (int i = 0; i < mListMoneyPay.size(); i++) {
                            if (mListTimes.get(position).equals(mListMoneyPay.get(i).getDate()
                                    + mListMoneyPay.get(i).getTime())) {
                                mIOnClickHistoryNoteMvpView
                                        .onClickMoneyPayHistoryNote(mListMoneyPay.get(i));
                                checkBreak = true;
                                break;
                            }
                        }

                        for (int j = 0; j < mListMoneyCollect.size(); j++) {
                            if (checkBreak) {
                                break;
                            } else {
                                if (mListTimes.get(position).equals(mListMoneyCollect.get(j).getDate()
                                        + mListMoneyCollect.get(j).getTime())) {
                                    mIOnClickHistoryNoteMvpView
                                            .onClickMoneyCollectHistoryNote(mListMoneyCollect.get(j));
                                    break;
                                }
                            }
                        }

                        for (int k = 0; k < mListTransfer.size(); k++) {
                            if (checkBreak) {
                                break;
                            } else {
                                if (mListTimes.get(position).equals(mListTransfer.get(k).getDate()
                                        + mListTransfer.get(k).getTime())) {
                                    mIOnClickHistoryNoteMvpView
                                            .onClickTransferHistoryNote(mListTransfer.get(k));
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public interface IOnClickHistoryNoteMvpView {
        void onClickMoneyCollectHistoryNote(MoneyCollect moneyCollect);

        void onClickMoneyPayHistoryNote(MoneyPay moneyPay);

        void onClickTransferHistoryNote(Transfer transfer);
    }

    /**
     * -
     * Hàm này có chức năng lấy thời gian từ 2 list mListMoneyCollect, mListMoneyPay
     * và sắp xếp chúng
     * <p>
     * - @created_by nxduong on 2/5/2021
     **/
    private void sortListTimes() {
        for (int i = 0; i < mListMoneyPay.size(); i++) {
            mListTimes.add(mListMoneyPay.get(i).getDate()
                    + mListMoneyPay.get(i).getTime());
        }

        for (int i = 0; i < mListMoneyCollect.size(); i++) {
            mListTimes.add(mListMoneyCollect.get(i).getDate()
                    + mListMoneyCollect.get(i).getTime());
        }

        for (int i = 0; i < mListTransfer.size(); i++) {
            mListTimes.add(mListTransfer.get(i).getDate()
                    + mListTransfer.get(i).getTime());
        }

        Collections.sort(mListTimes, Collections.<String>reverseOrder());
    }
}
