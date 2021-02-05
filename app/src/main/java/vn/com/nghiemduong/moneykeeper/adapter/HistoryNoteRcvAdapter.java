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
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 4/2/2021
 **/
public class HistoryNoteRcvAdapter extends RecyclerView.Adapter<HistoryNoteRcvAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MoneyCollect> mListMoneyCollect;
    private ArrayList<MoneyPay> mListMoneyPay;

    public HistoryNoteRcvAdapter(Context mContext, ArrayList<MoneyCollect> listMoneyCollect,
                                 ArrayList<MoneyPay> listMoneyPay) {
        this.mContext = mContext;
        this.mListMoneyCollect = listMoneyCollect;
        this.mListMoneyPay = listMoneyPay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_history_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoneyCollect moneyCollect = mListMoneyCollect.get(position);
        if (moneyCollect != null) {
            holder.ivCategoryHistoryNote.setImageBitmap(
                    AppUtils.convertPathFileImageAssetsToBitmap(moneyCollect.getCategoryPath(), mContext));
            holder.tvAccountNameHistoryRecent.setText(moneyCollect.getAccountName());
            holder.tvNumberMoneyHistoryNote.setText(String.valueOf(moneyCollect.getAmountOfMoney()));
            holder.tvDateHistoryNote.setText(moneyCollect.getDate());
            holder.tvCategoryNameHistoryNote.setText(moneyCollect.getCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        return mListMoneyCollect.size(); //+ mListMoneyPay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategoryNameHistoryNote, tvNumberMoneyHistoryNote,
                tvDateHistoryNote, tvAccountNameHistoryRecent;
        private ImageView ivCategoryHistoryNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryNameHistoryNote = itemView.findViewById(R.id.tvCategoryNameHistoryNote);
            tvNumberMoneyHistoryNote = itemView.findViewById(R.id.tvNumberMoneyHistoryNote);
            tvDateHistoryNote = itemView.findViewById(R.id.tvDateHistoryNote);
            tvAccountNameHistoryRecent = itemView.findViewById(R.id.tvAccountNameHistoryRecent);
            ivCategoryHistoryNote = itemView.findViewById(R.id.ivCategoryHistoryNote);
        }
    }
}
