package vn.com.nghiemduong.moneykeeper.ui.main.report;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.TypeAdapter;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.adapter.ReportAdapter;
import vn.com.nghiemduong.moneykeeper.data.model.Report;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseFragment;

/**
 * Màn hình danh sách báo cáo
 * <p>
 * - @created_by nxduong on 25/1/2021
 **/

public class ReportFragment extends BaseFragment implements ReportMvpView {
    private View mView;
    private RecyclerView rcvReport;
    private ReportAdapter mReportAdapter;
    private ReportPresenter mReportPresenter;
    private SwipeRefreshLayout srlReport;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_report, container, false);

        init();
        onRefreshListener();

        return mView;
    }

    private void onRefreshListener() {
        srlReport.setColorSchemeColors(Objects.requireNonNull(getContext()).getResources()
                .getColor(R.color.blue));
        srlReport.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlReport.setRefreshing(false);
            }
        });
    }

    // Khởi tạo / ánh xạ cho các view
    private void init() {
        rcvReport = mView.findViewById(R.id.rcvReport);
        srlReport = mView.findViewById(R.id.srlReport);

        mReportPresenter = new ReportPresenter(this, getContext());
        mReportPresenter.addListReport();
    }

    @Override
    public void resultListReport(ArrayList<Report> listReports) {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        rcvReport.setHasFixedSize(true);

        rcvReport.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        mReportAdapter = new ReportAdapter(getContext(), listReports);
        rcvReport.setAdapter(mReportAdapter);

    }
}