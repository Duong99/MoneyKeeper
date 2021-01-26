package vn.com.nghiemduong.moneykeeper.data.db.user;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class UserMoneyDatabasePresenter implements UserMoneyDatabaseMvpPresenter{
    private UserMoneyDatabaseMvpView mUserMoneyDatabaseMvpView;

    public UserMoneyDatabasePresenter(UserMoneyDatabaseMvpView mUserMoneyDatabaseMvpView) {
        this.mUserMoneyDatabaseMvpView = mUserMoneyDatabaseMvpView;
    }
}
