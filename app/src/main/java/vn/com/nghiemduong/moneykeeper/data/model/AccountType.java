package vn.com.nghiemduong.moneykeeper.data.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * - Đối tượng loại tài khoản
 * <p>
 * <p>
 * - @created_by nxduong on
 **/
public class AccountType implements Serializable {
    private String accountTypePath; //(TEXT) : Đường dẫn ảnh của loại tài khoản trong assets
    private String accountTypeName; //(TEXT) : Tên loại tài khoản

    public AccountType(String accountTypePath, String accountTypeName) {
        this.accountTypePath = accountTypePath;
        this.accountTypeName = accountTypeName;
    }

    public String getAccountTypePath() {
        return accountTypePath;
    }

    public void setAccountTypePath(String accountTypePath) {
        this.accountTypePath = accountTypePath;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }
}
