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
import java.util.Collections;

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
    private ArrayList<String> mListTimes;
    private IOnClickHistoryNoteMvpView mIOnClickHistoryNoteMvpView;

    public HistoryNoteRcvAdapter(Context mContext, ArrayList<MoneyCollect> listMoneyCollect,
                                 ArrayList<MoneyPay> listMoneyPay,
                                 IOnClickHistoryNoteMvpView onClickHistoryNoteMvpView) {
        this.mContext = mContext;
        this.mListMoneyCollect = listMoneyCollect;
        this.mListMoneyPay = listMoneyPay;
        this.mIOnClickHistoryNoteMvpView = onClickHistoryNoteMvpView;
        this.mListTimes = new ArrayList<>();
        sortListTimes();
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

        MoneyPay moneyPay;
        if (mListTimes.get(position) != null) {
            for (int i = 0; i < mListMoneyPay.size(); i++) {
                moneyPay = mListMoneyPay.get(i);
                if (moneyPay != null) {
/*                    if (mListTimes.get(position).equals(moneyPay.getDate()
                            + moneyPay.getTime())) {
                        holder.ivCategoryHistoryNote.setImageBitmap(
                                AppUtils.convertPathFileImageAssetsToBitmap(moneyPay.getCategoryPath(),
                                        mContext));
                        holder.tvAccountNameHistoryRecent.setText(moneyPay.getAccountName());
                        holder.tvNumberMoneyHistoryNote.setText(String.valueOf(moneyPay.getAmountOfMoney()));
                        holder.tvNumberMoneyHistoryNote.setTextColor(mContext.getResources()
                                .getColor(R.color.red));
                        holder.tvDateHistoryNote.setText(moneyPay.getDate());
                        holder.tvCategoryNameHistoryNote.setText(moneyPay.getCategoryName());
                        break;
                    }*/
                }
            }

            MoneyCollect moneyCollect;
            for (int j = 0; j < mListMoneyCollect.size(); j++) {
                moneyCollect = mListMoneyCollect.get(j);
                /*if (moneyCollect != null) {
                    if (mListTimes.get(position).equals(moneyCollect.getDate()
                            + moneyCollect.getTime())) {
                        holder.ivCategoryHistoryNote.setImageBitmap(
                                AppUtils.convertPathFileImageAssetsToBitmap(moneyCollect.getCategoryPath(),
                                        mContext));
                        holder.tvAccountNameHistoryRecent.setText(moneyCollect.getAccountName());
                        holder.tvNumberMoneyHistoryNote.setText(String.valueOf(moneyCollect.getAmountOfMoney()));
                        holder.tvNumberMoneyHistoryNote.setTextColor(mContext.getResources()
                                .getColor(R.color.green));
                        holder.tvDateHistoryNote.setText(moneyCollect.getDate());
                        holder.tvCategoryNameHistoryNote.setText(moneyCollect.getCategoryName());
                        break;
                    }
                }*/
            }
        }

    }

    @Override
    public int getItemCount() {
        return mListTimes.size();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    boolean checkBreak = false;
                    if (position != RecyclerView.NO_POSITION) {
                        for (int i = 0; i < mListMoneyPay.size(); i++) {
                            if (mListTimes.get(position).equals(mListMoneyPay.get(i).getDate()
                                    + mListMoneyPay.get(i).getTime())) {
                                mIOnClickHistoryNoteMvpView.onClickHistoryNote(null, mListMoneyPay.get(i));
                                checkBreak = true;
                                break;
                            }
                        }

                        for (int j = 0; j < mListMoneyCollect.size(); j++) {
                            if (checkBreak) {
                                break;
                            } else {
                                if (mListTimes.get(position).equals(mListMoneyCollect.get(j).getDate()
                                        + mListMoneyCollect.get(j).getTime())) {
                                    mIOnClickHistoryNoteMvpView.onClickHistoryNote(mListMoneyCollect.get(j), null);
                                    break;
                                }
                            }

                        }
                    }
                }
            });
        }
    }

    public interface IOnClickHistoryNoteMvpView {
        void onClickHistoryNote(MoneyCollect moneyCollect, MoneyPay moneyPay);
    }

    /**
     * -
     * Hàm này có chức năng lấy thời gian từ 2 list mListMoneyCollect, mListMoneyPay
     * và sắp xếp chúng
     * <p>
     * - @created_by nxduong on 2/5/2021
     **/
    private void sortListTimes() {
        for (int i = 0; i < mListMoneyPay.size(); i++) {
            mListTimes.add(new String(mListMoneyPay.get(i).getDate()
                    + mListMoneyPay.get(i).getTime()));
        }

        for (int i = 0; i < mListMoneyCollect.size(); i++) {
            mListTimes.add(new String(mListMoneyCollect.get(i).getDate()
                    + mListMoneyCollect.get(i).getTime()));
        }

        Collections.sort(mListTimes, Collections.<String>reverseOrder());
    }
}
