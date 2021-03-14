package vn.com.nghiemduong.moneykeeper.data.model.db;

import java.io.Serializable;

/**
 * -
 * Lớp tài khoản ví người dùng
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class Account implements Serializable {
    private int accountId;    //(INTEGER): id tài khoản
    private String accountName;    //(TEXT): Tên tài khoản
    private int currentAmount;    //(INTEGER): Tiền hiện tại trong tài khoản
    private String accountTypePath; //(TEXT) : Đường dẫn ảnh của loại tài khoản trong assets
    private String accountTypeName; //(TEXT) : Tên loại tài khoản
    private String moneyType;    //(TEXT) : Loại tiền tệ mặc định VND
    private String description;     // (TEXT) : Diễn giải
    private int report;       /// (INTEGER): Có tính vào báo cáo không 1 là có, 0 là không

    public Account(int accountId, String accountName, int currentAmount,
                   String accountTypePath, String accountTypeName, String moneyType,
                   String description, int report) {
        this.accountId = accountId;
        this.accountName = accountName;

        this.currentAmount = currentAmount;
        this.accountTypePath = accountTypePath;
        this.accountTypeName = accountTypeName;
        this.moneyType = moneyType;
        this.description = description;
        this.report = report;
    }

    public Account(String accountName, int currentAmount, String accountTypePath,
                   String accountTypeName, String moneyType, String description, int report) {
        this.accountName = accountName;
        this.currentAmount = currentAmount;
        this.accountTypePath = accountTypePath;
        this.accountTypeName = accountTypeName;
        this.moneyType = moneyType;
        this.description = description;
        this.report = report;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
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

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }
}
