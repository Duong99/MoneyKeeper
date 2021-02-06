package vn.com.nghiemduong.moneykeeper.data.model;

import com.google.gson.internal.$Gson$Preconditions;

import java.io.Serializable;

/**
 * private
 * Đối tượng thu MoneyCollect khi người dùng thêm chi tiền
 * <p>
 * private @created_by nxduong on 3/2/2021
 **/
public class MoneyPay implements Serializable {
    private int payId;          // 	(INTEGER) : id chi tiền
    private int accountId;      //	(INTEGER) : id tài khoản
    private int amountOfMoney;  // (INTEGER): Số tiền thu
    private int categoryId;     //(INTEGER) : id của category cha
    private int subCategoryId;  //(INTEGER) : id của category con
    private String explain;     //	(TEXT): Diễn giải
    private String date;        //	(TEXT) : Thời gian (ngày / tháng / năm)
    private String time;        //	(TEXT) : Thời gian ( Giờ : Phút)
    private int report;         //(INTEGER): Có tính vào báo cáo không 1 là có, 0 là không
    private byte[] image;       //(BOLD): Ảnh

    public MoneyPay(int payId, int accountId, int amountOfMoney, int categoryId, int subCategoryId,
                    String explain, String date, String time, int report, byte[] image) {
        this.payId = payId;
        this.accountId = accountId;
        this.amountOfMoney = amountOfMoney;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.explain = explain;
        this.date = date;
        this.time = time;
        this.report = report;
        this.image = image;
    }

    public MoneyPay(int accountId, int amountOfMoney, int categoryId, int subCategoryId,
                    String explain, String date, String time, int report, byte[] image) {
        this.accountId = accountId;
        this.amountOfMoney = amountOfMoney;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.explain = explain;
        this.date = date;
        this.time = time;
        this.report = report;
        this.image = image;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
