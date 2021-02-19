package vn.com.nghiemduong.moneykeeper.ui.main.overview.overviewmain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.CustomSpinnerStageAdapter;
import vn.com.nghiemduong.moneykeeper.adapter.HistoryNoteRcvAdapter;
import vn.com.nghiemduong.moneykeeper.data.db.moneyCollect.MoneyCollectDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.moneyPay.MoneyPayDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.transfer.TransferDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyCollect;
import vn.com.nghiemduong.moneykeeper.data.model.MoneyPay;
import vn.com.nghiemduong.moneykeeper.data.model.Transfer;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragmentMvpView;
import vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.account.AccountFragmentPresenter;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.PlusFragment;
import vn.com.nghiemduong.moneykeeper.ui.main.plus.UtilsPlus;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -  Màn hình tổng quan chính
 * <p>
 * - @created_by nxduong on 2/2/2021
 **/

public class OverviewMainFragment extends BaseFragment implements View.OnClickListener,
        AccountFragmentMvpView, HistoryNoteRcvAdapter.IOnClickHistoryNoteMvpView,
        OverviewMainMvpView {

    private View mView;
    private TextView tvTotalMoney, tvTotalMoneyHidden, tvAmountStages, tvTotalAmountCollectStages,
            tvTotalAmountSpendingStages;
    private ImageView ivVisibilityTotalMoney, ivChartCollect, ivChartSpending;
    private Spinner spnStage;
    private TransferDatabase mTransferDatabase;
    private MainActivity mMainActivity;
    private PieChart pieChart;
    private MoneyPayDatabase mMoneyPayDatabase;
    private MoneyCollectDatabase mMoneyCollectDatabase;
    private ArrayList<MoneyPay> mListMoneyPays;
    private ArrayList<Transfer> mListTransfers;
    private ArrayList<MoneyCollect> mListMoneyCollect;
    private RecyclerView rcvRecentNotes;
    private HistoryNoteRcvAdapter mHistoryNoteAdapter;
    private OverviewMainPresenter mOverviewMainPresenter;

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

        spnStage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    switch (position) {
                        case 0: // Thống kê hôm nay
                            mOverviewMainPresenter.doGetTotalAmountFromDB(
                                    getContext(), UtilsPlus.getDateCurrent(), "");
                            break;

                        default:
                            mOverviewMainPresenter.doGetTotalAmountFromDB(
                                    getContext(), AppUtils.UpDownDate(position),
                                    UtilsPlus.getDateCurrent());
                            break;
                    }
                } catch (Exception e) {
                    AppUtils.handlerException(e);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return mView;
    }

    /**
     * Khởi tạo / ánh xạ view
     *
     * @created_by nxduong on 2/2/2021
     */

    private void init() {
        ivVisibilityTotalMoney = mView.findViewById(R.id.ivVisibilityTotalMoney);
        ivVisibilityTotalMoney.setOnClickListener(this);

        RelativeLayout rlTotalMoneyBackground = mView.findViewById(R.id.rlTotalMoneyBackground);
        rlTotalMoneyBackground.setOnClickListener(this);

        tvAmountStages = mView.findViewById(R.id.tvAmountStages);
        tvTotalAmountCollectStages = mView.findViewById(R.id.tvTotalAmountCollectStages);
        tvTotalAmountSpendingStages = mView.findViewById(R.id.tvTotalAmountSpendingStages);

        ivChartCollect = mView.findViewById(R.id.ivChartCollect);
        ivChartSpending = mView.findViewById(R.id.ivChartSpending);

        tvTotalMoney = mView.findViewById(R.id.tvTotalMoney);
        tvTotalMoneyHidden = mView.findViewById(R.id.tvTotalMoneyHidden);

        spnStage = mView.findViewById(R.id.spnStage);
        rcvRecentNotes = mView.findViewById(R.id.rcvRecentNotes);
        rcvRecentNotes.setNestedScrollingEnabled(false);
        AccountFragmentPresenter mAccountFragmentPresenter = new AccountFragmentPresenter(this);
        mAccountFragmentPresenter.doSumOfMoneyOfAllAccount(mMainActivity.getAllAccount());

        mOverviewMainPresenter = new OverviewMainPresenter(this);
        mOverviewMainPresenter.doInsertListSpinnerStage(Objects.requireNonNull(getContext()));
        mOverviewMainPresenter.doGetTotalAmountFromDB(getContext(), UtilsPlus.getDateCurrent(), "");

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
        mTransferDatabase = new TransferDatabase(getContext());
        //mListMoneyPays = new ArrayList<>();
        mListMoneyPays = mMoneyPayDatabase.getAllMoneyPay();
        mListMoneyCollect = mMoneyCollectDatabase.getAllMoneyCollect();
        mListTransfers = mTransferDatabase.getAllTransfer();

        mHistoryNoteAdapter = new HistoryNoteRcvAdapter(getContext(), mListMoneyCollect,
                mListMoneyPays, mListTransfers, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvRecentNotes.setLayoutManager(layoutManager);
        rcvRecentNotes.setAdapter(mHistoryNoteAdapter);

        //addDataSet(pieChart);
        //mChart.setOnChartValueSelectedListener(getContext());

    }

    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();


//        for (int i = 0; i < mListMoneyPays.size(); i++) {
//            Drawable d = new BitmapDrawable(getResources(),
//                    AppUtils.convertPathFileImageAssetsToBitmap(mListMoneyPays.get(i).getCategoryPath(),
//                            getContext()));
//            yEntrys.add(new PieEntry(mListMoneyPays.get(i).getAmountOfMoney(),
//                    /*                    mListMoneyPays.get(i).getCategoryName(),*/ d));
//        }

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
                if (tvTotalMoneyHidden.getVisibility() == View.GONE) {
                    ivVisibilityTotalMoney.setBackground(
                            getResources().getDrawable(R.drawable.ic_visibility_off));
                    tvTotalMoneyHidden.setVisibility(View.VISIBLE);
                    tvTotalMoney.setVisibility(View.GONE);
                } else {
                    ivVisibilityTotalMoney.setBackground(
                            getResources().getDrawable(R.drawable.ic_visibility));
                    tvTotalMoneyHidden.setVisibility(View.GONE);
                    tvTotalMoney.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.rlTotalMoneyBackground:

                break;
        }
    }


    @Override
    public void onTotalMoneyOfAllAccount(int totalMoney) {
        mTotalMoney = totalMoney;
        tvTotalMoney.setText(AppUtils.formatNumber(String.valueOf(mTotalMoney)));

        if (totalMoney < 0) {
            tvTotalMoney.setTextColor(getResources().getColor(R.color.red));
            tvTotalMoneyHidden.setTextColor(getResources().getColor(R.color.red));

        } else {
            tvTotalMoney.setTextColor(getResources().getColor(R.color.blue));
            tvTotalMoneyHidden.setTextColor(getResources().getColor(R.color.blue));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    /**
     * Hàm chuyển màn hình giữa các fragment và truyền dữ liệu với Bundle
     *
     * @param fg fragment cần chuyển tới
     *           <p>
     *           - @created_by nxduong on 5/2/2021
     **/

    public void beginTransactionCategoriesLayout(Fragment fg, Bundle bundle) {
        fg.setArguments(bundle);
        FragmentManager fmManager = getFragmentManager();
        if (fmManager != null) {
            FragmentTransaction ft = fmManager.beginTransaction();
            ft.replace(R.id.flOverView, fg);
            ft.commit();
        }
    }

    /**
     * Sự kiện khi click vào lịch sử ghi thu tiền
     *
     * @created_by nxduong on 17/2/2021
     */

    @Override
    public void onClickMoneyCollectHistoryNote(MoneyCollect moneyCollect) {
        try {
            Bundle bundle = new Bundle();
            if (moneyCollect != null) {
                bundle.putSerializable("BUNDLE_MONEY_COLLECT", moneyCollect);
                beginTransactionCategoriesLayout(new PlusFragment(), bundle);
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }


    /**
     * Sự kiện khi click vào lịch sử ghi chi tiền
     *
     * @created_by nxduong on 17/2/2021
     */

    @Override
    public void onClickMoneyPayHistoryNote(MoneyPay moneyPay) {
        try {
            Bundle bundle = new Bundle();
            if (moneyPay != null) {
                bundle.putSerializable("BUNDLE_MONEY_PAY", moneyPay);
                beginTransactionCategoriesLayout(new PlusFragment(), bundle);
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    /**
     * Sự kiện khi click vào lịch sử ghi chuyển tiền
     *
     * @created_by nxduong on 17/2/2021
     */
    @Override
    public void onClickTransferHistoryNote(Transfer transfer) {
        try {
            Bundle bundle = new Bundle();
            if (transfer != null) {
                bundle.putSerializable("BUNDLE_TRANSFER", transfer);
                beginTransactionCategoriesLayout(new PlusFragment(), bundle);
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
    }

    // setAdapter cho spinner khoảng thời gian
    @Override
    public void resultListStage(ArrayList<String> listStage) {
        CustomSpinnerStageAdapter spinnerStageAdapter =
                new CustomSpinnerStageAdapter(Objects.requireNonNull(getContext()),
                        listStage, listStage.get(2));
        spnStage.setAdapter(spinnerStageAdapter);
        spnStage.setSelection(2);
    }

    /**
     * Hàm trả về các giá trị tổng tiền thu, chi
     * vào độ cao đổ tháp thu, chi
     *
     * @created_by nxduong on 18/2/2021
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void resultGetTotalAmountFromDB(int amountCollectStages, int amountSpendingStages,
                                           int totalMoneyStages, int heightChartCollect,
                                           int heightChartSpending) {
        tvAmountStages.setText(
                AppUtils.formatNumber(String.valueOf(totalMoneyStages)) + " " + getString(R.string.dong));
        tvTotalAmountCollectStages.setText(
                AppUtils.formatNumber(String.valueOf(amountCollectStages)) + " " + getString(R.string.dong));
        tvTotalAmountSpendingStages.setText(
                AppUtils.formatNumber(String.valueOf(amountSpendingStages)) + " " + getString(R.string.dong));

        ivChartCollect.requestLayout();
        ivChartSpending.requestLayout();
        ivChartCollect.getLayoutParams().height = heightChartCollect;
        ivChartSpending.getLayoutParams().height = heightChartSpending;
    }
}