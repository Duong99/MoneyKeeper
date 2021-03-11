package vn.com.nghiemduong.moneykeeper.ui.main.overview.overviewmain;

import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;

import vn.com.nghiemduong.moneykeeper.data.model.db.Record;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public class OverviewMainPresenter implements OverviewMainMvpPresenter {
    private OverviewMainMvpView mOverviewMainMvpView;

    public OverviewMainPresenter(OverviewMainMvpView overviewMainMvpView) {
        this.mOverviewMainMvpView = overviewMainMvpView;
    }

    /**
     * @created_by nxduong on 18/2/2021
     */
    @Override
    public void doInsertListSpinnerStage(Context context) {
        ArrayList<String> listStage = new ArrayList<>();

        listStage.add(context.getResources().getString(R.string.today));
        listStage.add(context.getResources().getString(R.string.this_week));
        listStage.add(context.getResources().getString(R.string.this_month));
        listStage.add(context.getResources().getString(R.string.this_quarter));
        listStage.add(context.getResources().getString(R.string.this_year));

        mOverviewMainMvpView.resultListStage(listStage);
    }

    /**
     * Hàm tính tổng tiền thu và chi cho từ giai đoạn
     * Tính chiều cao của tháp phần trăm tổng thu và chi
     *
     * @created_by nxduong on 18/2/2021
     */

    @Override
    public void doGetTotalAmountFromDB(Context context, String fromDate, String toDate) {

        ArrayList<Record> moneyCollects = new ArrayList<>();
        ArrayList<Record> moneyPays = new ArrayList<>();
        int totalAmountCollectStages = 0;
        int totalAmountPayStages = 0;
        for (int i = 0; i < moneyCollects.size(); i++) {
            totalAmountCollectStages += moneyCollects.get(i).getAmount();
        }

        for (int i = 0; i < moneyPays.size(); i++) {
            totalAmountPayStages += moneyPays.get(i).getAmount();
        }
        double maxHeight = 400;
        double heightChartCollect = 5;
        double heightChartSpending = 5;
        double totalAmount = totalAmountCollectStages + totalAmountPayStages;

        if (totalAmount != 0) {
            heightChartCollect = maxHeight
                    * (Double.parseDouble(String.valueOf(totalAmountCollectStages))
                    / Double.parseDouble(String.valueOf(totalAmount)));
            if (heightChartCollect < 5) {
                heightChartCollect = 5;
            }
            heightChartSpending = maxHeight - heightChartCollect;
            if (heightChartSpending < 5) {
                heightChartSpending = 5;
            }
        }

        mOverviewMainMvpView.resultGetTotalAmountFromDB(
                totalAmountCollectStages, totalAmountPayStages,
                totalAmountCollectStages - totalAmountPayStages,
                (int) Math.round(heightChartCollect),
                (int) Math.round(heightChartSpending));
    }
}
