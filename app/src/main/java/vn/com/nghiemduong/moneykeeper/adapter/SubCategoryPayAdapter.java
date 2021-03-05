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
public class SubCategoryPayAdapter extends RecyclerView.Adapter<SubCategoryPayAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Category> mSubCategories;
    private IOnClickSubCategoryPay mOnClickSubCategoryPay;

    public SubCategoryPayAdapter(Context context, ArrayList<Category> subCategories,
                                 IOnClickSubCategoryPay onClickSubCategoryPay) {
        this.mContext = context;
        this.mSubCategories = subCategories;
        this.mOnClickSubCategoryPay = onClickSubCategoryPay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_sub_category_pay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category subCategory = mSubCategories.get(position);
        if (subCategory != null) {
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

        private ImageView ivImageSubCategory;
        private TextView tvTitleSubCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageSubCategory = itemView.findViewById(R.id.ivImageSubCategory);
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
