package vn.com.nghiemduong.moneykeeper.ui.account.register;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.db.user.UserDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.User;
import vn.com.nghiemduong.moneykeeper.utils.AppConstants;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;
import vn.com.nghiemduong.moneykeeper.utils.NetworkUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * - @created_by nxduong on 22/1/2021
 **/
public class RegisterPresenter implements RegisterMvpPresenter {
    private FirebaseAuth mAuth;
    private RegisterMvpView mRegisterMvpView;
    private Activity mActivity;

    public RegisterPresenter(RegisterMvpView mRegisterMvpView, Activity activity) {
        this.mRegisterMvpView = mRegisterMvpView;
        this.mActivity = activity;
    }

    @Override
    public void registerAccount(final User user) {
        final UserDatabase userDatabase = new UserDatabase(mActivity);
        if (userDatabase.getUser(user.getEmail()) != null) { // Email này đã đăng ký
            mRegisterMvpView.registerAccountFail(mActivity.getResources().getString(R.string.register_email_exits));
        } else {
            // Initialize Firebase Auth
            if (NetworkUtils.isNetworkConnected(mActivity)) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                        .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    userDatabase.insertUser(user);
                                    mRegisterMvpView.registerAccountSuccess(
                                            mActivity.getResources().getString(R.string.register_success));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    mRegisterMvpView.registerAccountFail(mActivity.getResources()
                                            .getString(R.string.register_fail));
                                }
                            }
                        });
            } else {
                long insert = userDatabase.insertUser(user);
                if (insert == DBUtils.checkDBFail) {
                    mRegisterMvpView.registerAccountFail(mActivity.getResources()
                            .getString(R.string.register_fail));
                } else {
                    mRegisterMvpView.registerAccountSuccess(
                            mActivity.getResources().getString(R.string.register_success));
                }
            }
        }
    }
}
