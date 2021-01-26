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
import vn.com.nghiemduong.moneykeeper.data.model.Report;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Report> mListReports;

    public ReportAdapter(Context mContext, ArrayList<Report> listReports) {
        this.mContext = mContext;
        this.mListReports = listReports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        parent = (ViewGroup) inflater.inflate(R.layout.item_rcv_report, null);
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivReport.setBackgroundResource(mListReports.get(position).getImage());
        holder.tvReport.setText(mListReports.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mListReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivReport;
        private TextView tvReport;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivReport = itemView.findViewById(R.id.ivReport);
            tvReport = itemView.findViewById(R.id.tvReport);
        }
    }
}
