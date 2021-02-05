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
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 27/1/2021
 **/
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Account> mListAccount;
    private IOnClickAccount mOnClickAccount;

    public AccountAdapter(Context mContext, ArrayList<Account> mListAccount,
                          IOnClickAccount onClickAccount) {
        this.mContext = mContext;
        this.mListAccount = mListAccount;
        this.mOnClickAccount = onClickAccount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account account = mListAccount.get(position);
        if (account != null) {
            if (position == mListAccount.size() - 1) {
                holder.viewLine.setVisibility(View.GONE);
            }
            holder.ivPictureAccountType.setImageBitmap(AppUtils.convertPathFileImageAssetsToBitmap(
                    account.getAccountTypePath(), mContext));
            holder.tvAccountName.setText(account.getAccountName());
            holder.tvTotalMoneyAccount.setText(String.valueOf(account.getMoneyCurrent()));
        }
    }

    // Thêm tài khoảnrong danh sách ở màn hình tài khoản
    public void addAccount(Account account) {
        mListAccount.add(account);
        notifyDataSetChanged();
    }

    // Xóa tài khoản trong danh sách ở màn hình tài khoản
    public void deleteAccount(Account account) {
        mListAccount.remove(account);
        notifyDataSetChanged();
    }

    // Sửa tài khoản trong danh sách ở màn hình tài khoản
    public void updateAccount(Account account, int position) {
        mListAccount.remove(position);
        mListAccount.add(position, account);
        notifyDataSetChanged();
    }

    // Lấy danh sách tài khoản
    public ArrayList<Account> getAllAccount() {
        return mListAccount;
    }

    @Override
    public int getItemCount() {
        return mListAccount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPictureAccountType, ivOptionAccount;
        private TextView tvAccountName, tvTotalMoneyAccount;
        private View viewLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPictureAccountType = itemView.findViewById(R.id.ivPictureAccountType);
            ivOptionAccount = itemView.findViewById(R.id.ivOptionAccount);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            tvTotalMoneyAccount = itemView.findViewById(R.id.tvTotalMoneyAccount);
            viewLine = itemView.findViewById(R.id.viewLine);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickAccount.onClickAccount(mListAccount.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });

            ivOptionAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickAccount.onClickOptionAccount(mListAccount.get(position), position);
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickAccount {
        void onClickAccount(Account account);

        void onClickOptionAccount(Account account, int position);
    }
}
