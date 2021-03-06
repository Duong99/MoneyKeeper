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
public class SubCategoryEditAdapter extends RecyclerView.Adapter<SubCategoryEditAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Category> mSubCategories;
    private IOnClickSubCategoryForEdit mOnClickSubCategoryForEdit;

    public SubCategoryEditAdapter(Context context, ArrayList<Category> subCategories,
                                  IOnClickSubCategoryForEdit onClickSubCategoryForEdit) {
        this.mContext = context;
        this.mSubCategories = subCategories;
        this.mOnClickSubCategoryForEdit = onClickSubCategoryForEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_sub_category_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category subCategory = mSubCategories.get(position);

        if (subCategory != null) {
            holder.ivImageSubCategoryEdit.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(
                            subCategory.getCategoryPath(), mContext));
            holder.tvTitleSubCategoryEdit.setText(subCategory.getCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        return mSubCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImageSubCategoryEdit;
        private TextView tvTitleSubCategoryEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageSubCategoryEdit = itemView.findViewById(R.id.ivImageSubCategoryEdit);
            tvTitleSubCategoryEdit = itemView.findViewById(R.id.tvTitleSubCategoryEdit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickSubCategoryForEdit.onClickSubCategoryForEdit(
                                    mSubCategories.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickSubCategoryForEdit {
        void onClickSubCategoryForEdit(Category subCategory);
    }
}
