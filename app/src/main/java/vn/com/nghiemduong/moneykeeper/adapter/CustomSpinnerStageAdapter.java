package vn.com.nghiemduong.moneykeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.HeaderCategory;

/**
 * -
 * - @created_by nxduong on 18/2/2021
 **/
public class CustomSpinnerStageAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> mListStage;
    private String s;

    public CustomSpinnerStageAdapter(@NonNull Context context,
                                     ArrayList<String> listStage, String s) {
        super(context, R.layout.item_spinner_stage, listStage);
        this.mContext = context;
        this.s = s;
        this.mListStage = listStage;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_spinner_stage, parent,
                    false);
        }

        TextView tvTitleStage = convertView.findViewById(R.id.tvTitleStage);
        ImageView ivStage = convertView.findViewById(R.id.ivStage);

        tvTitleStage.setText(mListStage.get(position));
        if (mListStage.get(position).equals(s)) {
            ivStage.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
