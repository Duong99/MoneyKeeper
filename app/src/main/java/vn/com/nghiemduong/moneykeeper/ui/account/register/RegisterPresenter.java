package vn.com.nghiemduong.moneykeeper.ui.account.register;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.com.nghiemduong.moneykeeper.data.model.User;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * -
 * <p>
 * <p>
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
    public void registerAccount(User user) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mRegisterMvpView.registerAccountSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                           mRegisterMvpView.registerAccountFail();
                        }
                    }
                });
    }
}
