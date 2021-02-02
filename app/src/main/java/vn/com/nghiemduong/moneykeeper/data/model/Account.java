package vn.com.nghiemduong.moneykeeper.data.model;

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
    private int moneyCurrent;    //(INTEGER): Tiền hiện tại trong tài khoản
    private String accountTypePath; //(TEXT) : Đường dẫn ảnh của loại tài khoản trong assets
    private String accountTypeName; //(TEXT) : Tên loại tài khoản
    private String moneyType;    //(TEXT) : Loại tiền tệ mặc định VND
    private String explain;     // (TEXT) : Diễn giải
    private int report;       /// (INTEGER): Có tính vào báo cáo không 1 là có, 0 là không

    public Account(int accountId, String accountName, int moneyCurrent,
                   String accountTypePath, String accountTypeName, String moneyType,
                   String explain, int report) {
        this.accountId = accountId;
        this.accountName = accountName;

        this.moneyCurrent = moneyCurrent;
        this.accountTypePath = accountTypePath;
        this.accountTypeName = accountTypeName;
        this.moneyType = moneyType;
        this.explain = explain;
        this.report = report;
    }

    public Account(String accountName, int moneyCurrent, String accountTypePath,
                   String accountTypeName, String moneyType, String explain, int report) {
        this.accountName = accountName;
        this.moneyCurrent = moneyCurrent;
        this.accountTypePath = accountTypePath;
        this.accountTypeName = accountTypeName;
        this.moneyType = moneyType;
        this.explain = explain;
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

    public int getMoneyCurrent() {
        return moneyCurrent;
    }

    public void setMoneyCurrent(int moneyCurrent) {
        this.moneyCurrent = moneyCurrent;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }
}
