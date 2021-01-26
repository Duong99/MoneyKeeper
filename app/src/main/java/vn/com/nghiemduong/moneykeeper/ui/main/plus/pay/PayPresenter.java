package vn.com.nghiemduong.moneykeeper.ui.main.plus.pay;

import android.content.Context;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public class PayPresenter implements PayMvpPresenter {
    private PayMvpView mPayMvpView;
    private Context mContext;

    public PayPresenter(Context context, PayMvpView payMvpView) {
        this.mContext = context;
        this.mPayMvpView = payMvpView;
    }
}
