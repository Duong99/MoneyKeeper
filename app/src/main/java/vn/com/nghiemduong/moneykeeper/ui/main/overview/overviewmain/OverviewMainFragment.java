package vn.com.nghiemduong.moneykeeper.ui.main.overview.overviewmain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Arrays;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.HistoryNoteRcvAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.MoneyCollect.MoneyCollectDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.MoneyPay.MoneyPayDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.Account;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragmentMvpView;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragmentPresenter;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  Màn hình tổng quan chính
 * <p>
 * - @created_by nxduong on 2/2/2021
 **/

public class OverviewMainFragment extends BaseFragment implements View.OnClickListener,
        AccountFragmentMvpView {

    private View mView;
    private ImageView ivVisibilityTotalMoney;
    private TextView tvTotalMoney;
    private RelativeLayout rlTotalMoneyBackground;
    private AccountFragmentPresenter mAccountFragmentPresenter;
    private MainActivity mMainActivity;
    private PieChart pieChart;
    private MoneyPayDatabase mMoneyPayDatabase;
    private MoneyCollectDatabase mMoneyCollectDatabase;
    private ArrayList<MoneyPay> mListMoneyPays;
    private ArrayList<MoneyCollect> mListMoneyCollect;
    private RecyclerView rcvRecentNotes;
    private HistoryNoteRcvAdapter mHistoryNoteAdapter;

    private int mTotalMoney = 0;

    public OverviewMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_overview_main, container, false);
        init();
        return mView;
    }

    private void init() {
        ivVisibilityTotalMoney = mView.findViewById(R.id.ivVisibilityTotalMoney);
        ivVisibilityTotalMoney.setOnClickListener(this);

        rlTotalMoneyBackground = mView.findViewById(R.id.rlTotalMoneyBackground);
        rlTotalMoneyBackground.setOnClickListener(this);

        tvTotalMoney = mView.findViewById(R.id.tvTotalMoney);
        rcvRecentNotes = mView.findViewById(R.id.rcvRecentNotes);
        mAccountFragmentPresenter = new AccountFragmentPresenter(this);
        mAccountFragmentPresenter.doSumOfMoneyOfAllAccount(mMainActivity.getAllAccount());

        pieChart = mView.findViewById(R.id.pieChart);

        pieChart.setRotationEnabled(true);
        pieChart.setDescription(new Description());
//        mChart.setHoleRadius(35f);
//        mChart.setTransparentCircleAlpha(0);
//        //mChart.setCenterText("PieChart");
//        //mChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        mMoneyCollectDatabase = new MoneyCollectDatabase(getContext());
        mMoneyPayDatabase = new MoneyPayDatabase(getContext());
        //mListMoneyPays = new ArrayList<>();
        mListMoneyPays = mMoneyPayDatabase.getAllMoneyPay();
        mListMoneyCollect = mMoneyCollectDatabase.getAllMoneyCollect();

        mHistoryNoteAdapter = new HistoryNoteRcvAdapter(getContext(), mListMoneyCollect, mListMoneyPays);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvRecentNotes.setLayoutManager(layoutManager);
        rcvRecentNotes.setAdapter(mHistoryNoteAdapter);
        //rcvRecentNotes.setNestedScrollingEnabled(false);
        //addDataSet(pieChart);
        //mChart.setOnChartValueSelectedListener(getContext());

    }

    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();


        for (int i = 0; i < mListMoneyPays.size(); i++) {
            Drawable d = new BitmapDrawable(getResources(),
                    AppUtils.convertPathFileImageAssetsToBitmap(mListMoneyPays.get(i).getCategoryPath(),
                            getContext()));
            yEntrys.add(new PieEntry(mListMoneyPays.get(i).getAmountOfMoney(),
                    /*                    mListMoneyPays.get(i).getCategoryName(),*/ d));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "lable");
        //pieDataSet.setSliceSpace(9);
        pieDataSet.setValueTextSize(16);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);

        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setDrawIcons(true);

        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueLineColor(R.color.black);


        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);

        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);

        legend.setWordWrapEnabled(true);

        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(5f);
        legend.setYOffset(0f);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivVisibilityTotalMoney:
                break;

            case R.id.rlTotalMoneyBackground:

                break;
        }
    }


    @Override
    public void onTotalMoneyOfAllAccount(int totalMoney) {
        mTotalMoney = totalMoney;
        tvTotalMoney.setText(String.valueOf(mTotalMoney));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }
}