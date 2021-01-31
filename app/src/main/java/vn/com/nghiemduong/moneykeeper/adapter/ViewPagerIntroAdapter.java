package vn.com.nghiemduong.moneykeeper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Intro;

/**
 * - @created_by nxduong on 22/1/2021
 **/
public class ViewPagerIntroAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Intro> mListIntro;

    public ViewPagerIntroAdapter(Context mContext, ArrayList<Intro> mListIntro) {
        this.mContext = mContext;
        this.mListIntro = mListIntro;
    }

    @Override
    public int getCount() {
        return mListIntro.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.view_pager_slide_intro, null);

        ImageView ivPictureSlideIntro = view.findViewById(R.id.ivPictureSlideIntro);
        TextView tvTitleSlideIntro = view.findViewById(R.id.tvTitleSlideIntro);

        ivPictureSlideIntro.setBackgroundResource(mListIntro.get(position).getPicture());
        tvTitleSlideIntro.setText(mListIntro.get(position).getTitle());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
