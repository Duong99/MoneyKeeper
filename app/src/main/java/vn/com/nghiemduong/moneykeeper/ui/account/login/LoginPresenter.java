package vn.com.nghiemduong.moneykeeper.ui.account.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.CallbackManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.User;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.MainActivity;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * <p>
 * <p>
 * - @created_by nxduong on 21/1/2021
 **/
public class LoginPresenter implements LoginMvpPresenter {
    public final static int RC_SIGN_IN = 12345;
    public static final String EMAIL = "email";
    private LoginMvpView mLoginMvpView;
    private GoogleSignInClient mGoogleSignInClient;
    private Context mContext;
    private Activity mActivity;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    public LoginPresenter(LoginMvpView loginMvpView, Context context, Activity activity) {
        this.mLoginMvpView = loginMvpView;
        this.mContext = context;
        this.mActivity = activity;
    }

    @Override
    public void loginGoogle() {

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);
        //updateUI(account);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
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
            mLoginMvpView.loginGoogleSuccess();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(AppUtils.TAG, "signInResult:failed code=" + e.getStatusCode());
            mLoginMvpView.loginGoogleFail();
        }
    }

    @Override
    public void loginFacebook(ImageView ivLoginFacebook) {
        callbackManager = CallbackManager.Factory.create();

    }

    @Override
    public void loginFirebase(User user) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mLoginMvpView.loginFirebaseSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            mLoginMvpView.loginFirebaseFail();
                        }
                    }
                });
    }

}
