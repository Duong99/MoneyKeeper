package vn.com.nghiemduong.moneykeeper.ui.main;

/**
 * - @created_by nxduong on 25/1/2021
 **/
public class MainPresenter implements MainMvpPresenter{
    private MainMvpView mMainMvpView;

    public MainPresenter(MainMvpView mMainMvpView) {
        this.mMainMvpView = mMainMvpView;
    }
}
