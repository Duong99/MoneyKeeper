package vn.com.nghiemduong.moneykeeper.ui.main.overview.overviewmain;

import android.content.Context;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface OverviewMainMvpPresenter {
    void doInsertListSpinnerStage(Context context);

    void doGetTotalAmountFromDB(Context context, String fromDate, String toDate);
}
