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
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * - @created_by nxduong on 5/3/2021
 **/
public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Category> mSubCategories;
    private IOnClickSubCategoryPay mOnClickSubCategoryPay;
    private Category mCategory;

    public SubCategoryAdapter(Context context, ArrayList<Category> subCategories,
                              IOnClickSubCategoryPay onClickSubCategoryPay, Category category) {
        this.mContext = context;
        this.mSubCategories = subCategories;
        this.mOnClickSubCategoryPay = onClickSubCategoryPay;
        this.mCategory = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_sub_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category subCategory = mSubCategories.get(position);
        if (subCategory != null) {
            if (mCategory != null) {
                if (mCategory.getCategoryId() == subCategory.getCategoryId()) {
                    holder.ivSelectedSubCategory.setVisibility(View.VISIBLE);
                }
            }
            holder.ivImageSubCategory.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    subCategory.getCategoryPath(), mContext));
            holder.tvTitleSubCategory.setText(subCategory.getCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        return mSubCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImageSubCategory, ivSelectedSubCategory;
        private TextView tvTitleSubCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageSubCategory = itemView.findViewById(R.id.ivImageSubCategory);
            ivSelectedSubCategory = itemView.findViewById(R.id.ivSelectedSubCategory);
            tvTitleSubCategory = itemView.findViewById(R.id.tvTitleSubCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickSubCategoryPay.onClickSubCategoryPay(mSubCategories.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickSubCategoryPay {
        void onClickSubCategoryPay(Category subCategory);
    }
}
