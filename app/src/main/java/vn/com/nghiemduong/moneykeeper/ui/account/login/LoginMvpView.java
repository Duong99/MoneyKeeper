package vn.com.nghiemduong.moneykeeper.ui.account.login;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 21/1/2021
 **/
public interface LoginMvpView {
    void loginGoogleSuccess();

    void loginGoogleFail();

    void loginFacebookSuccess();

    void loginFacebookFail();

    void loginFirebaseSuccess();

    void loginFirebaseFail();
}
