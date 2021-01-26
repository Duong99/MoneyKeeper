package vn.com.nghiemduong.moneykeeper.ui.main.report;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Report;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public interface ReportMvpView {
    void resultListReport(ArrayList<Report> listReports);
}
