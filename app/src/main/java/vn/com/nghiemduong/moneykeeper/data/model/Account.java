package vn.com.nghiemduong.moneykeeper.data.model;

import java.io.Serializable;

/**
 * -
 * Lớp tài khoản ví người dùng
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public class Account implements Serializable {
    private int accountId;
    private int moneyCurrent;
    private String accountName;
    private int accountType;
    private byte[] imageType;
    private int moneyType;
    private String explain;
    private int report;

    public Account(int accountId, int moneyCurrent, String accountName, int accountType,
                   byte[] imageType, int moneyType, String explain, int report) {
        this.accountId = accountId;
        this.moneyCurrent = moneyCurrent;
        this.accountName = accountName;
        this.accountType = accountType;
        this.imageType = imageType;
        this.moneyType = moneyType;
        this.explain = explain;
        this.report = report;
    }

    public Account(int moneyCurrent, String accountName, int accountType,
                   byte[] imageType, int moneyType, String explain, int report) {
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

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public byte[] getImageType() {
        return imageType;
    }

    public void setImageType(byte[] imageType) {
        this.imageType = imageType;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
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
