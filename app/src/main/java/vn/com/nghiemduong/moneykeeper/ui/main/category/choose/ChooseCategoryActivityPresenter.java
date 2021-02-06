package vn.com.nghiemduong.moneykeeper.ui.main.category.choose;

/**
 * - @created_by nxduong on 28/1/2021
 **/
public class ChooseCategoryActivityPresenter implements ChooseCategoryActivityMvpPresenter {
    private ChooseCategoryActivityMvpView mChooseCategoryActivityMvpView;

    public ChooseCategoryActivityPresenter(ChooseCategoryActivityMvpView chooseCategoryActivityMvpView) {
        this.mChooseCategoryActivityMvpView = mChooseCategoryActivityMvpView;
    }
}
