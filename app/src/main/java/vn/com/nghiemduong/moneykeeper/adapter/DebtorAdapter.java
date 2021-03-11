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
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public class DebtorAdapter extends RecyclerView.Adapter<DebtorAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<String> mListDebtors;
    private ArrayList<String> mListDebtorsFiltered;
    private IOnClickContact mOnClickString;
    private CustomFilter mCustomFilter;

    public DebtorAdapter(Context mContext, ArrayList<String> listDebtors,
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
        String contact = mListDebtors.get(position);
        if (contact != null) {
            holder.tvStringName.setText(contact);
            holder.tvDefaultStringImage.setText(contact.substring(0, 1));

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
            ArrayList<String> filters = new ArrayList<>();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                for (int i = 0; i < mListDebtorsFiltered.size(); i++) {
                    if (mListDebtorsFiltered.get(i).toUpperCase().contains(constraint)) {
                        String model = new String(mListDebtorsFiltered.get(i));
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
            mListDebtors = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDefaultStringImage, tvStringName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDefaultStringImage = itemView.findViewById(R.id.tvDefaultContactImage);
            tvStringName = itemView.findViewById(R.id.tvContactName);

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
        void onClickContact(String String);
    }
}
