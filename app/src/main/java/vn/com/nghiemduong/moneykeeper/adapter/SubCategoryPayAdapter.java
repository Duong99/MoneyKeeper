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
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * - @created_by nxduong on 27/1/2021
 **/
public class SubCategoryPayAdapter extends RecyclerView.Adapter<SubCategoryPayAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SubCategory> mListSubCategories;
    private IOnClickSubCategoryPay mIOnClickSubCategoryPay;

    public SubCategoryPayAdapter(Context mContext, ArrayList<SubCategory> mListSubCategories,
                                 IOnClickSubCategoryPay onClickSubCategoryPay) {
        this.mContext = mContext;
        this.mListSubCategories = mListSubCategories;
        this.mIOnClickSubCategoryPay = onClickSubCategoryPay;
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
        holder.ivImageItemSubCategoryPay.setImageBitmap(
                AppUtils.convertByteArrayToBitmap(mListSubCategories.get(position).getPicture()));
        holder.tvTitleItemSubCategoryPay.setText(mListSubCategories.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mListSubCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageItemSubCategoryPay;
        TextView tvTitleItemSubCategoryPay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImageItemSubCategoryPay = itemView.findViewById(R.id.ivImageItemSubCategoryPay);
            tvTitleItemSubCategoryPay = itemView.findViewById(R.id.tvTitleItemSubCategoryPay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mIOnClickSubCategoryPay.onClickSubCategoryPay(
                                    mListSubCategories.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickSubCategoryPay {
        void onClickSubCategoryPay(SubCategory subCategory);
    }

}
