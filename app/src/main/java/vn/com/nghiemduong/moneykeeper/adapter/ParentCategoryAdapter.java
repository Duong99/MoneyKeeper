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
 * - @created_by nxduong on 8/2/2021
 **/
public class ParentCategoryAdapter extends RecyclerView.Adapter<ParentCategoryAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Category> mListCategoriesParent;
    private IOnClickCategoryParentView mOnClickCategoryParent;
    private int mCategoryParentId;

    public ParentCategoryAdapter(Context context, ArrayList<Category> listCategoriesParent,
                                 IOnClickCategoryParentView onClickCategoryParent, int categoryParentId) {
        this.mContext = context;
        this.mListCategoriesParent = listCategoriesParent;
        this.mOnClickCategoryParent = onClickCategoryParent;
        this.mCategoryParentId = categoryParentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_category_parent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mListCategoriesParent.get(position);
        if (category != null) {
            if (mCategoryParentId == category.getCategoryId()) {
                holder.ivTickCategoryParent.setVisibility(View.VISIBLE);
            }
            holder.tvTitleCategoryParent.setText(category.getCategoryName());
            holder.ivPictureCategoryParent.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(category.getCategoryPath(), mContext));
        }
    }

    @Override
    public int getItemCount() {
        return mListCategoriesParent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPictureCategoryParent, ivTickCategoryParent;
        private TextView tvTitleCategoryParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPictureCategoryParent = itemView.findViewById(R.id.ivPictureCategoryParent);
            ivTickCategoryParent = itemView.findViewById(R.id.ivTickCategoryParent);
            tvTitleCategoryParent = itemView.findViewById(R.id.tvTitleCategoryParent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mOnClickCategoryParent.onClickCategoryParent(
                                mListCategoriesParent.get(position));
                    }
                }
            });
        }
    }

    public interface IOnClickCategoryParentView {
        void onClickCategoryParent(Category category);
    }
}
