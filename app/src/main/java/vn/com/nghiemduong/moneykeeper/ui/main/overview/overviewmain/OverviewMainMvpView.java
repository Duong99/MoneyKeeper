package vn.com.nghiemduong.moneykeeper.ui.main.overview.overviewmain;

import java.util.ArrayList;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface OverviewMainMvpView {
    void resultListStage(ArrayList<String> listStage);

    void resultGetTotalAmountFromDB(int amountCollectStages, int amountSpendingStages,
                                    int totalMoneyStages, int heightChartCollect,
                                    int heightChartSpending);
}
