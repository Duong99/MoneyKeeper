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
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 7/2/2021
 **/
public class CategoryEditAdapter extends RecyclerView.Adapter<CategoryEditAdapter.ViewHolder> {

    private RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private Context mContext;
    private ArrayList<Category> mListCategory;

    public CategoryEditAdapter(Context context, ArrayList<Category> listCategory) {
        this.mContext = context;
        this.mListCategory = listCategory;
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
        Category category = mListCategory.get(position);
        if (category != null) {
            holder.ivImageCategoryEdit.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    category.getCategoryPath(), mContext));
            holder.tvTitleCategoryEdit.setText(category.getCategoryName());

            ArrayList<SubCategory> mListSubCategories = category.getSubCategories();
            if (mListSubCategories == null || mListSubCategories.size() == 0) {
                holder.rcvSubCategoryEdit.setVisibility(View.GONE);
                holder.ivExpandCategoryEdit.setVisibility(View.INVISIBLE);
            } else {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);

                SubCategoryEditAdapter subCategoryEditAdapter =
                        new SubCategoryEditAdapter(mContext, mListSubCategories);

                holder.rcvSubCategoryEdit.setLayoutManager(layoutManager);
                holder.rcvSubCategoryEdit.setAdapter(subCategoryEditAdapter);
                holder.rcvSubCategoryEdit.setRecycledViewPool(recycledViewPool);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListCategory.size();
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

            ivExpandCategoryEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rcvSubCategoryEdit.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
