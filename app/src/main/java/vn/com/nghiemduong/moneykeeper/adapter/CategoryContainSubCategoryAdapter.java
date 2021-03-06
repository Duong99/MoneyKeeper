package vn.com.nghiemduong.moneykeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - Adapter hạng mục cha chứa hạng mục con trong chọn hạng mục chi tiền và thu tiền
 * - @created_by nxduong on 5/3/2021
 **/
public class CategoryContainSubCategoryAdapter
        extends RecyclerView.Adapter<CategoryContainSubCategoryAdapter.ViewHolder>
        implements SubCategoryPayAdapter.IOnClickSubCategoryPay {

    private Context mContext;
    private ArrayList<Category> mParentCategories;
    private IOnClickCategory mOnClickCategory;

    public CategoryContainSubCategoryAdapter(Context context, ArrayList<Category> parentCategories,
                                             IOnClickCategory onClickCategory) {
        this.mContext = context;
        this.mParentCategories = parentCategories;
        this.mOnClickCategory = onClickCategory;
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

            ArrayList<Category> subCategories = new CategoryDatabase(mContext)
                    .getAllSubCategory(parentCategory.getCategoryId());

            if (subCategories.size() != 0) {
                holder.rcvSubCategoryPay.setAdapter(new SubCategoryPayAdapter(
                        mContext, subCategories, this));
            } else {
                holder.rcvSubCategoryPay.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mParentCategories.size();
    }

    @Override
    public void onClickSubCategoryPay(Category subCategory) {
        mOnClickCategory.onClickCategory(subCategory);
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
            rcvSubCategoryPay.setLayoutManager(new GridLayoutManager(mContext, 4));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickCategory.onClickCategory(mParentCategories.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickCategory {
        void onClickCategory(Category category);
    }
}
