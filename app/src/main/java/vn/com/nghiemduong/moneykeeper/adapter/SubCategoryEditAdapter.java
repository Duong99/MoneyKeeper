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
 * - @created_by nxduong on 7/2/2021
 **/
public class SubCategoryEditAdapter extends RecyclerView.Adapter<SubCategoryEditAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SubCategory> mListSubCategory;
    private IOnClickSubCategoryEditView mIOnClickSubCategoryEditView;

    public SubCategoryEditAdapter(Context context, ArrayList<SubCategory> listSubCategory,
                                  IOnClickSubCategoryEditView onClickSubCategoryEditView) {
        this.mContext = context;
        this.mListSubCategory = listSubCategory;
        this.mIOnClickSubCategoryEditView = onClickSubCategoryEditView;
    }

    // Thêm hạng mục con
    public void addSubCategory(SubCategory subCategory) {
        if (subCategory != null) {
            mListSubCategory.add(subCategory);
            notifyDataSetChanged();
        }
    }

    // Xóa hạng mục con
    public void deleteSubCategory(SubCategory subCategory) {
        if (subCategory != null) {
            mListSubCategory.remove(subCategory);
            notifyDataSetChanged();
        }
    }

    // Xóa tất cả danh sách hạng mục con
    public void deleteAllSubCategory() {
        mListSubCategory.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_category_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategory subCategory = mListSubCategory.get(position);
        if (subCategory != null) {
            holder.ivImageCategoryEdit.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    subCategory.getSubCategoryPath(), mContext));
            holder.tvTitleCategoryEdit.setText(subCategory.getSubCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        return mListSubCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImageCategoryEdit, ivExpandCategoryEdit;
        TextView tvTitleCategoryEdit;
        RecyclerView rcvSubCategoryEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageCategoryEdit = itemView.findViewById(R.id.ivImageCategoryEdit);
            ivExpandCategoryEdit = itemView.findViewById(R.id.ivExpandCategoryEdit);
            tvTitleCategoryEdit = itemView.findViewById(R.id.tvTitleCategoryEdit);
            rcvSubCategoryEdit = itemView.findViewById(R.id.rcvSubCategoryEdit);

            rcvSubCategoryEdit.setVisibility(View.GONE);
            ivExpandCategoryEdit.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mIOnClickSubCategoryEditView.onClickSubCategory(mListSubCategory.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickSubCategoryEditView {
        void onClickSubCategory(SubCategory subCategory);
    }
}
