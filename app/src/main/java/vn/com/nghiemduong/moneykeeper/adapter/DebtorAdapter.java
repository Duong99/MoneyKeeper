package vn.com.nghiemduong.moneykeeper.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.db.Record;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public class DebtorAdapter extends RecyclerView.Adapter<DebtorAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Record> mListDebtors;
    private ArrayList<Record> mListDebtorsFiltered;
    private IOnClickContact mOnClickString;
    private CustomFilter mCustomFilter;

    public DebtorAdapter(Context mContext, ArrayList<Record> listDebtors,
                         IOnClickContact onClickString) {
        this.mContext = mContext;
        this.mListDebtors = listDebtors;
        this.mListDebtorsFiltered = listDebtors;
        this.mOnClickString = onClickString;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record contact = mListDebtors.get(position);
        if (contact != null) {
            holder.tvStringName.setText(contact.getDebtor());
            holder.tvDefaultStringImage.setText(contact.getDebtor().substring(0, 1));

            // Set màu cho ảnh String
            if (position % 3 == 0) {
                holder.tvDefaultStringImage.setBackgroundColor(mContext.getResources()
                        .getColor(R.color.contact_color_blue));
            } else if (position % 4 == 0) {
                holder.tvDefaultStringImage.setBackgroundColor(mContext.getResources()
                        .getColor(R.color.contact_color_violet));
            } else if (position % 5 == 0) {
                holder.tvDefaultStringImage.setBackgroundColor(mContext.getResources()
                        .getColor(R.color.contact_color_pink));
            } else {
                holder.tvDefaultStringImage.setBackgroundColor(mContext.getResources()
                        .getColor(R.color.contact_color_orange));
            }

            if (contact.getType() == AppConstants.DI_VAY) {
                holder.tvMoneyDebtor.setText(String.valueOf(contact.getAmount()));
                holder.tvMoneyDebtor.setTextColor(mContext.getResources()
                        .getColor(R.color.input_amount_red_pay));
            }

            if (contact.getType() == AppConstants.CHO_VAY) {
                holder.tvMoneyDebtor.setText(String.valueOf(contact.getAmount()));
                holder.tvMoneyDebtor.setTextColor(mContext.getResources()
                        .getColor(R.color.green));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListDebtors.size();
    }

    @Override
    public Filter getFilter() {
        if (mCustomFilter == null) {
            mCustomFilter = new CustomFilter();
        }
        return mCustomFilter;
    }

    /**
     * -  Lớp bộ lọc String, tìm kiếm String
     * - @created_by nxduong on 31/1/2021
     **/
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<Record> filters = new ArrayList<>();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                for (int i = 0; i < mListDebtorsFiltered.size(); i++) {
                    if (mListDebtorsFiltered.get(i).getDebtor().toUpperCase().contains(constraint)) {
                        Record model = new Record();
                        model.setDebtor(mListDebtorsFiltered.get(i).getDebtor());
                        filters.add(model);
                    }
                }
            } else {
                filters = mListDebtorsFiltered;
            }
            results.values = filters;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListDebtors = (ArrayList<Record>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDefaultStringImage, tvStringName, tvMoneyDebtor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDefaultStringImage = itemView.findViewById(R.id.tvDefaultContactImage);
            tvStringName = itemView.findViewById(R.id.tvContactName);
            tvMoneyDebtor = itemView.findViewById(R.id.tvMoneyDebtor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickString.onClickContact(mListDebtors.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickContact {
        void onClickContact(Record record);
    }
}
