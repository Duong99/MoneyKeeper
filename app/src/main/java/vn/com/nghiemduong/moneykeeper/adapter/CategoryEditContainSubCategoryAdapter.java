package vn.com.nghiemduong.moneykeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * - @created_by nxduong on 5/3/2021
 **/
public class CategoryEditContainSubCategoryAdapter
        extends RecyclerView.Adapter<CategoryEditContainSubCategoryAdapter.ViewHolder>
        implements SubCategoryEditAdapter.IOnClickSubCategoryForEdit {

    private Context mContext;
    private ArrayList<Category> mCategories;
    private IOnClickCategoryForEdit mOnClickCategoryForEdit;

    public CategoryEditContainSubCategoryAdapter(Context context, ArrayList<Category> categories,
                                                 IOnClickCategoryForEdit onClickCategoryForEdit) {
        this.mContext = context;
        this.mCategories = categories;
        this.mOnClickCategoryForEdit = onClickCategoryForEdit;
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
        Category category = mCategories.get(position);
        if (category != null) {
            holder.ivImageCategoryEdit.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(
                            category.getCategoryPath(), mContext));
            holder.tvTitleCategoryEdit.setText(category.getCategoryName());

            ArrayList<Category> subCategories = new CategoryDatabase(mContext)
                    .getAllSubCategory(category.getCategoryId());
            if (subCategories.size() != 0) {
                holder.rcvSubCategoryEdit.setAdapter(
                        new SubCategoryEditAdapter(mContext, subCategories, this));
            } else {
                holder.ivExpandCategoryEdit.setVisibility(View.INVISIBLE);
                holder.rcvSubCategoryEdit.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    @Override
    public void onClickSubCategoryForEdit(Category subCategory) {
        mOnClickCategoryForEdit.onClickCategoryEdit(subCategory);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivExpandCategoryEdit, ivImageCategoryEdit;
        private TextView tvTitleCategoryEdit;
        private RecyclerView rcvSubCategoryEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivExpandCategoryEdit = itemView.findViewById(R.id.ivExpandCategoryEdit);
            ivImageCategoryEdit = itemView.findViewById(R.id.ivImageCategoryEdit);
            tvTitleCategoryEdit = itemView.findViewById(R.id.tvTitleCategoryEdit);
            rcvSubCategoryEdit = itemView.findViewById(R.id.rcvSubCategoryEdit);
            rcvSubCategoryEdit.setLayoutManager(new LinearLayoutManager(mContext));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickCategoryForEdit.onClickCategoryEdit(mCategories.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });

            ivExpandCategoryEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RotateAnimation animation;
                    if (rcvSubCategoryEdit.getVisibility() == View.GONE) {
                        rcvSubCategoryEdit.setVisibility(View.VISIBLE);
                        animation = new RotateAnimation(180, 360,
                                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    } else {
                        rcvSubCategoryEdit.setVisibility(View.GONE);
                        animation = new RotateAnimation(0, 180,
                                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    }

                    animation.setDuration(300);
                    animation.setFillAfter(true);
                    ivExpandCategoryEdit.startAnimation(animation);

                }
            });
        }
    }

    public interface IOnClickCategoryForEdit {
        void onClickCategoryEdit(Category category);
    }
}
