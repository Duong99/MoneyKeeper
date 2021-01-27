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
    private int accountTypeId;
    private byte[] image;
    private String title;

    public AccountType(int accountTypeId, byte[] image, String title) {
        this.accountTypeId = accountTypeId;
        this.image = image;
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }
}
