package vn.com.nghiemduong.moneykeeper.data.model;

/**
 * -
 * Lớp tài khoản ví người dùng
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class Account {
    private int accountId;
    private int moneyCurrent;
    private String accountName;
    private String accountType;
    private byte[] imageType;
    private String moneyType;
    private String explain;
    private String report;

    public Account(int accountId, int moneyCurrent, String accountName, String accountType,
                   byte[] imageType, String moneyType, String explain, String report) {
        this.accountId = accountId;
        this.moneyCurrent = moneyCurrent;
        this.accountName = accountName;
        this.accountType = accountType;
        this.imageType = imageType;
        this.moneyType = moneyType;
        this.explain = explain;
        this.report = report;
    }

    public Account(int moneyCurrent, String accountName, String accountType,
                   byte[] imageType, String moneyType, String explain, String report) {
        this.moneyCurrent = moneyCurrent;
        this.accountName = accountName;
        this.accountType = accountType;
        this.imageType = imageType;
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

    public int getMoneyCurrent() {
        return moneyCurrent;
    }

    public void setMoneyCurrent(int moneyCurrent) {
        this.moneyCurrent = moneyCurrent;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public byte[] getImageType() {
        return imageType;
    }

    public void setImageType(byte[] imageType) {
        this.imageType = imageType;
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

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
