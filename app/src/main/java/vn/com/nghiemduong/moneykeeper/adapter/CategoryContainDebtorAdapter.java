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
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Adapter hạng mục cha chứa hạng mục con trong chọn hạng mục vay nợ và người vay nợ
 * <p>
 * - @created_by nxduong on 5/3/2021
 **/
public class CategoryContainDebtorAdapter extends RecyclerView.Adapter<CategoryContainDebtorAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Category> mParentCategories;

    public CategoryContainDebtorAdapter(Context context, ArrayList<Category> parentCategories) {
        this.mContext = context;
        this.mParentCategories = parentCategories;
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
            holder.ivImageCategoryPay.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    parentCategory.getCategoryPath(), mContext));
            holder.tvTitleCategoryPay.setText(parentCategory.getCategoryName());

            // Lấy danh sách những người vay nợ
        }
    }

    @Override
    public int getItemCount() {
        return mParentCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageCategoryPay;
        TextView tvTitleCategoryPay;
        RecyclerView rcvSubCategoryPay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageCategoryPay = itemView.findViewById(R.id.ivImageCategoryPay);
            tvTitleCategoryPay = itemView.findViewById(R.id.tvTitleCategoryPay);
            rcvSubCategoryPay = itemView.findViewById(R.id.rcvSubCategoryPay);
        }
    }
}
