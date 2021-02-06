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
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * Lớp adapter dùng chung cho cả đối tượng hạng mục(Category) có hoặc không có hạng mục con
 * nếu không có hạng mục con thì truyền giá trị ở hàm tạo là 0
 * - @created_by nxduong on 27/1/2021
 **/

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
        implements SubCategoryAdapter.IOnClickSubCategoryPay {
    private RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();

    private Context mContext;
    private ArrayList<Category> mListCategories;
    private IOnClickCategory mIOnClickCategory;


    public CategoryAdapter(Context mContext, ArrayList<Category> mListCategories,
                           IOnClickCategory onClickCategory) {
        this.mContext = mContext;
        this.mListCategories = mListCategories;
        this.mIOnClickCategory = onClickCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_category_pay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mListCategories.get(position);
        if (category != null) {
            holder.ivImageCategoryPay.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    category.getCategoryPath(), mContext));
            holder.tvTitleCategoryPay.setText(category.getCategoryName());

            ArrayList<SubCategory> mListSubCategories = category.getSubCategories();
            if (mListSubCategories == null || mListSubCategories.size() == 0) {
                holder.rcvSubCategoryPay.setVisibility(View.GONE);
            } else {
                GridLayoutManager linearLayoutManager =
                        new GridLayoutManager(holder.rcvSubCategoryPay.getContext(), 4);
                linearLayoutManager.setInitialPrefetchItemCount(
                        mListSubCategories.size());

                SubCategoryAdapter subCategoryPayAdapter =
                        new SubCategoryAdapter(mContext, mListSubCategories,
                                CategoryAdapter.this);

                holder.rcvSubCategoryPay.setLayoutManager(linearLayoutManager);
                holder.rcvSubCategoryPay.setAdapter(subCategoryPayAdapter);
                holder.rcvSubCategoryPay.setRecycledViewPool(recycledViewPool);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListCategories.size();
    }

    @Override
    public void onClickSubCategoryPay(SubCategory subCategory) {
        mIOnClickCategory.onClickSubCategoryPay(subCategory);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mIOnClickCategory.onClickCategoryPay(mListCategories.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickCategory {
        void onClickCategoryPay(Category category);

        void onClickSubCategoryPay(SubCategory subCategory);
    }
}
