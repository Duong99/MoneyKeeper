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
import vn.com.nghiemduong.moneykeeper.data.model.AccountType;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public class AccountTypeRcvAdapter extends RecyclerView.Adapter<AccountTypeRcvAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<AccountType> mListAccountType;
    private IOnClickAccountType mOnClickAccountType;
    private AccountType mAccountTypeClicked;

    public AccountTypeRcvAdapter(Context context, ArrayList<AccountType> listAccountType,
                                 IOnClickAccountType onClickAccountType, AccountType accountTypeClicked) {
        this.mContext = context;
        this.mListAccountType = listAccountType;
        this.mOnClickAccountType = onClickAccountType;
        this.mAccountTypeClicked = accountTypeClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_account_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivImageAccountType.setImageBitmap(
                AppUtils.convertByteArrayToBitmap(mListAccountType.get(position).getImage()));
        holder.tvTitleAccountType.setText(mListAccountType.get(position).getTitle());

        // Kiểm tra xem lại tại khoản nào được chọn thì cho tích hiện lên
        if (mListAccountType.get(position).getAccountTypeId() == mAccountTypeClicked.getAccountTypeId()){
            holder.ivCheckAccountType.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mListAccountType.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageAccountType, ivCheckAccountType;
        TextView tvTitleAccountType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCheckAccountType = itemView.findViewById(R.id.ivCheckAccountType);
            ivImageAccountType = itemView.findViewById(R.id.ivImageAccountType);
            tvTitleAccountType = itemView.findViewById(R.id.tvTitleAccountType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickAccountType.onClickAccountType(mListAccountType.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickAccountType {
        void onClickAccountType(AccountType accountType);
    }
}
