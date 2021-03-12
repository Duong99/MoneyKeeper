package vn.com.nghiemduong.moneykeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.record.RecordDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.data.model.db.Record;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Adapter hạng mục cha chứa hạng mục con trong chọn hạng mục vay nợ và người vay nợ
 * <p>
 * - @created_by nxduong on 5/3/2021
 **/
public class CategoryContainDebtorAdapter extends
        RecyclerView.Adapter<CategoryContainDebtorAdapter.ViewHolder>
        implements DebtorAdapter.IOnClickContact {

    private Context mContext;
    private ArrayList<Category> mParentCategories;
    private IOnClickCategoryDebt mOnClickCategoryDebt;
    private Category mCategory;
    private RecordDatabase mRecordDatabase;

    public CategoryContainDebtorAdapter(Context context, ArrayList<Category> parentCategories,
                                        IOnClickCategoryDebt onClickCategoryDebt, Category category) {
        this.mContext = context;
        this.mParentCategories = parentCategories;
        this.mOnClickCategoryDebt = onClickCategoryDebt;
        this.mCategory = category;
        this.mRecordDatabase = new RecordDatabase(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_parent_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category parentCategory = mParentCategories.get(position);

        if (parentCategory != null) {
            if (mCategory != null) {
                if (mCategory.getCategoryId() == parentCategory.getCategoryId()) {
                    holder.ivSelectedParentCategory.setVisibility(View.VISIBLE);
                }
            }
            holder.ivImageCategoryPay.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    parentCategory.getCategoryPath(), mContext));
            holder.tvTitleCategoryPay.setText(parentCategory.getCategoryName());

            // Lấy danh sách những người vay nợ
            ArrayList<Record> listContacts = new ArrayList<>();
            if (parentCategory.getCategoryId() == AppConstants.THU_NO_ID) {
                listContacts = mRecordDatabase.getAllRecordWhereType(AppConstants.CHO_VAY);
            }

            if (parentCategory.getCategoryId() == AppConstants.TRA_NO_ID) {
                listContacts = mRecordDatabase.getAllRecordWhereType(AppConstants.DI_VAY);
            }

            if (listContacts.size() == 0) {
                holder.rcvSubCategoryPay.setVisibility(View.GONE);
            } else {
                holder.rcvSubCategoryPay.setAdapter(
                        new DebtorAdapter(mContext, listContacts, this));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mParentCategories.size();
    }

    @Override
    public void onClickContact(Record record) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageCategoryPay, ivSelectedParentCategory;
        TextView tvTitleCategoryPay;
        RecyclerView rcvSubCategoryPay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageCategoryPay = itemView.findViewById(R.id.ivImageCategoryPay);
            ivSelectedParentCategory = itemView.findViewById(R.id.ivSelectedParentCategory);
            tvTitleCategoryPay = itemView.findViewById(R.id.tvTitleCategoryPay);
            rcvSubCategoryPay = itemView.findViewById(R.id.rcvSubCategoryPay);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            rcvSubCategoryPay.setLayoutManager(layoutManager);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickCategoryDebt.onClickCategoryDebt(mParentCategories.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }

                }
            });
        }
    }

    public interface IOnClickCategoryDebt {
        void onClickCategoryDebt(Category category);
    }
}
