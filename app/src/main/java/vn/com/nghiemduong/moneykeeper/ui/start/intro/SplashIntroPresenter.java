package vn.com.nghiemduong.moneykeeper.ui.start.intro;

import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Intro;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 22/1/2021
 **/
public class SplashIntroPresenter implements SplashIntroMvpPresenter {
    private SplashIntroMvpView mSplashIntroMvpView;
    private Context mContext;

    public SplashIntroPresenter(SplashIntroMvpView splashIntroMvpView, Context context) {
        this.mSplashIntroMvpView = splashIntroMvpView;
        this.mContext = context;
    }

    // Thêm dữ liệu vào list slide intro
    @Override
    public void getSlideListIntro() {
        ArrayList<Intro> listIntro = new ArrayList<>();
        String intro1 = mContext.getResources().getString(R.string.text_intro1_1) + "\n"
                + mContext.getResources().getString(R.string.text_intro1_2) + "\n"
                + mContext.getResources().getString(R.string.text_intro1_3);

        listIntro.add(new Intro(R.drawable.ic_splash_intro3, intro1));
        listIntro.add(new Intro(R.drawable.ic_splash_intro2,
                mContext.getResources().getString(R.string.text_intro2)));
        listIntro.add(new Intro(R.drawable.ic_splash_intro1,
                mContext.getResources().getString(R.string.text_intro3)));

        mSplashIntroMvpView.resultSlideListIntros(listIntro);
    }
}
