package vn.com.nghiemduong.moneykeeper.ui.main.report;

import android.content.Context;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Report;

/**
 * Màn hình báo cáo
 * - @created_by nxduong on 25/1/2021
 **/
public class ReportPresenter implements ReportMvpPresenter {
    private ReportMvpView mReportMvpView;
    private Context mContext;

    public ReportPresenter(ReportMvpView mReportMvpView, Context context) {
        this.mReportMvpView = mReportMvpView;
        this.mContext = context;
    }

    /**
     * Hàm thêm đối tượng báo cáo vào trong danh sách báo cáo
     *
     * @return listReport danh sách báo cáo
     * @created_by nxduong on 25/1/2021
     */

    @Override
    public void addListReport() {
        ArrayList<Report> listReport = new ArrayList<>();
        listReport.add(new Report(R.drawable.ic_tai_chinh_v1,
                mContext.getResources().getString(R.string.current_finance)));
        listReport.add(new Report(R.drawable.ic_tinh_hinh_thu_chi_v2,
                mContext.getResources().getString(R.string.revenue_and_expenditure_situation)));
        listReport.add(new Report(R.drawable.ic_phan_tich_chi_tieu_v1,
                mContext.getResources().getString(R.string.spend_analysis)));
        listReport.add(new Report(R.drawable.ic_phan_tich_thu_v1,
                mContext.getResources().getString(R.string.collection_analysis)));
        listReport.add(new Report(R.drawable.ic_theo_doi_v2,
                mContext.getResources().getString(R.string.follow_loan)));
        listReport.add(new Report(R.drawable.ic_doi_tuong_v1,
                mContext.getResources().getString(R.string.object_collect_spend)));
        listReport.add(new Report(R.drawable.ic_chuyen_di_v2,
                mContext.getResources().getString(R.string.journey_event)));
        listReport.add(new Report(R.drawable.ic_phan_tich_v1,
                mContext.getResources().getString(R.string.finance_analysis)));
        mReportMvpView.resultListReport(listReport);
    }
}
