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

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Account;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.data.model.db.Record;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Lớp RecordOverviewAdapter được sử dụng trong màn hình tổng quan
 *
 * @created_by nxduong on 11/3/2021
 */
public class RecordOverviewAdapter extends RecyclerView.Adapter<RecordOverviewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Record> mListRecord;
    private CategoryDatabase mCategoryDatabase;
    private AccountDatabase mAccountDatabase;

    public RecordOverviewAdapter(Context mContext, ArrayList<Record> listRecord) {
        this.mContext = mContext;
        this.mListRecord = listRecord;
        this.mCategoryDatabase = new CategoryDatabase(mContext);
        this.mAccountDatabase = new AccountDatabase(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_record_overview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record record = mListRecord.get(position);

        if (record != null) {
            Category category = mCategoryDatabase.getCategory(record.getCategoryId());
            if (category != null) {
                holder.ivCategoryRecordOverview.setImageBitmap(
                        AppUtils.convertPathFileImageAssetsToBitmap(category.getCategoryPath(), mContext));
                holder.tvCategoryNameRecordOverview.setText(category.getCategoryName());
            }

            Account account = mAccountDatabase.getAccount(record.getAccountId());
            if (account != null) {
                holder.tvAccountNameRecordOverview.setText(account.getAccountName());
            }

            holder.tvNumberMoneyRecordOverview.setText(String.valueOf(record.getAmount()));
            holder.tvDateRecordOverview.setText(record.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return mListRecord.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCategoryRecordOverview;
        private TextView tvCategoryNameRecordOverview, tvDateRecordOverview,
                tvNumberMoneyRecordOverview, tvAccountNameRecordOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCategoryRecordOverview = itemView.findViewById(R.id.ivCategoryRecordOverview);
            tvCategoryNameRecordOverview = itemView.findViewById(R.id.tvCategoryNameRecordOverview);
            tvDateRecordOverview = itemView.findViewById(R.id.tvDateRecordOverview);
            tvNumberMoneyRecordOverview = itemView.findViewById(R.id.tvNumberMoneyRecordOverview);
            tvAccountNameRecordOverview = itemView.findViewById(R.id.tvAccountNameRecordOverview);
        }
    }
}
