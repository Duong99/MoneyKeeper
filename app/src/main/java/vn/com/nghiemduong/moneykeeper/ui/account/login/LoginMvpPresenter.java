package vn.com.nghiemduong.moneykeeper.ui.account.login;

import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import vn.com.nghiemduong.moneykeeper.data.model.User;

/**
 * - @created_by nxduong on 20/1/2021
 **/
public interface LoginMvpPresenter {
    void loginGoogle();

    void handleSignInGoogleResult(Task<GoogleSignInAccount> completedTask);

    void loginFacebook(ImageView ivLoginFacebook);

    void loginFirebase(User user);
}
