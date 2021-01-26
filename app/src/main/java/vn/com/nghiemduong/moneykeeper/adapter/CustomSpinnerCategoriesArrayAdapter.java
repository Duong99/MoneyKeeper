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
import vn.com.nghiemduong.moneykeeper.data.model.Category;

/**
 -
    Custom lại spinner trong layout fragment_plus
    *
 - @created_by nxduong on

**/
public class CustomSpinnerCategoriesArrayAdapter extends ArrayAdapter<Category> {

    private Context mContext;
    private ArrayList<Category> mListCategories;

    public CustomSpinnerCategoriesArrayAdapter(@NonNull Context context,
                                                ArrayList<Category> mListCategories) {
        super(context, R.layout.item_custom_spinner_categories, mListCategories);
        this.mContext = context;
        this.mListCategories = mListCategories;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_custom_spinner_categories, parent,
                    false);
        }


        ImageView ivPictureCategories = convertView.findViewById(R.id.ivPictureCategories);
        ImageView ivCheckCategories = convertView.findViewById(R.id.ivCheckCategories);
        TextView tvTitleCategories = convertView.findViewById(R.id.tvTitleCategories);

        ivPictureCategories.setBackgroundResource(mListCategories.get(position).getPicture());
        tvTitleCategories.setText(mListCategories.get(position).getTitle());
        return convertView;
    }

}
