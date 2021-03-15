package vn.com.nghiemduong.moneykeeper.ui.account.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.BuildConfig;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.user.UserDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.User;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.NetworkUtils;

/**
 * - @created_by nxduong on 21/1/2021
 **/
public class LoginPresenter implements LoginMvpPresenter {
    public final static int RC_SIGN_IN = 12345;
    public static final String EMAIL = "email";
    private LoginMvpView mLoginMvpView;
    private Context mContext;
    private Activity mActivity;
    private FirebaseAuth mAuth;

    public LoginPresenter(LoginMvpView loginMvpView, Context context, Activity activity) {
        this.mLoginMvpView = loginMvpView;
        this.mContext = context;
        this.mActivity = activity;
    }

    @Override
    public void loginGoogle() {

    }

    @Override
    public void handleSignInGoogleResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // Lấy thông tin đăng nhập tài khoản google
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
            }
            // Signed in successfully, show authenticated UI.
            // updateUI(account);
            mLoginMvpView.loginSuccess();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(AppConstants.TAG, "signInResult:failed code=" + e.getStatusCode());
            mLoginMvpView.loginFail(mActivity.getString(R.string.login_fail));
        }
    }

    @Override
    public void loginFacebook(CallbackManager callbackManager) {
        if (NetworkUtils.isNetworkConnected(mContext)) { // Có internet
            FacebookSdk.sdkInitialize(mActivity.getApplicationContext());
            if (BuildConfig.DEBUG) {
                FacebookSdk.setIsDebugEnabled(true);
                FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
            }
            LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("email", "public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            handleFacebookAccessToken(loginResult.getAccessToken());
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException exception) {
                            mLoginMvpView.loginFail(mActivity.getString(R.string.login_fail));
                        }
                    });
        } else { // Không có internet

        }

    }

    /**
     * Hàm đăng nhập
     *
     * @param user đối tượng người dùng để đăng nhập
     * @created_by nxduong on 15/3/2021
     */
    @Override
    public void loginFirebase(User user) {
        if (NetworkUtils.isNetworkConnected(mContext)) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                mLoginMvpView.loginSuccess();
                            } else {
                                // If sign in fails, display a message to the user.
                                mLoginMvpView.loginFail(mActivity.getString(R.string.login_fail));
                            }
                        }
                    });
        } else {
            UserDatabase userDatabase = new UserDatabase(mContext);
            User checkUser = userDatabase.getUser(user.getEmail());
            if (checkUser == null) {
                mLoginMvpView.loginFail(mActivity.getString(R.string.login_fail));
            } else {
                if (checkUser.getPassword().equals(user.getPassword())) {
                    mLoginMvpView.loginSuccess();
                } else {
                    mLoginMvpView.loginFail(mActivity.getString(R.string.login_fail));
                }
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user == null) {

                        }
                        mLoginMvpView.loginFail(mActivity.getString(R.string.login_fail));
                    }
                });
    }
}
