package vn.com.nghiemduong.moneykeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 7/2/2021
 **/
public class IconCategoryAdapter extends RecyclerView.Adapter<IconCategoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Category> mListCategory; // Chỉ lưu trường categoryPath
    private IOnClickIconCategory mIOnClickIconCategory;

    public IconCategoryAdapter(Context context, ArrayList<Category> listCategory,
                               IOnClickIconCategory onClickIconCategory) {
        this.mContext = context;
        this.mListCategory = listCategory;
        this.mIOnClickIconCategory = onClickIconCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_icon_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category != null) {
            holder.ivIconCategory.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(category.getCategoryPath(), mContext));
        }
    }

    @Override
    public int getItemCount() {
        return mListCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivIconCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIconCategory = itemView.findViewById(R.id.ivIconCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mIOnClickIconCategory.onClickIcon(mListCategory.get(position));
                    }
                }
            });
        }
    }

    public interface IOnClickIconCategory {
        void onClickIcon(Category category);
    }
}
