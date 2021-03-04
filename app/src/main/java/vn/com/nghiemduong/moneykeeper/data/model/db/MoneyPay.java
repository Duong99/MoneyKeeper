package vn.com.nghiemduong.moneykeeper.data.model.db;

import com.google.gson.internal.$Gson$Preconditions;

import java.io.Serializable;

/**
 * private
 * Đối tượng thu MoneyCollect khi người dùng thêm chi tiền
 * <p>
 * private @created_by nxduong on 3/2/2021
 **/
public class MoneyPay implements Serializable {
    private int payId;          //(INTEGER) : id thu tiền
    private int amount;  //(INTEGER) : Số tiền thu
    private int categoryId;     //(INTEGER) : id của category cha
    private String debtor;      //  (TEXT) : id người vay nợ
    private String explain;     //(TEXT) : Diễn giải
    private String date;        //(TEXT) : Thời gian (ngày / tháng / năm)
    private String time;        //(TEXT) : Thời gian ( Giờ : Phút)
    private int accountId;      //(INTEGER) : id tài khoản
    private int report;         //(INTEGER) : Có tính vào báo cáo không 1 là có, 0 là không
    private byte[] image;       //(BOLD) : Ảnh
    private int type;           // Các kiểu như chi tiền , thu tiền, cho vay, ....
    private int status;         // Trạng thái trong kế hoạch định kỳ chờ/đã

    public MoneyPay(int payId, int amount, int categoryId, String debtor, String explain,
                    String date, String time, int accountId, int report, byte[] image,
                    int type, int status) {
        this.payId = payId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.debtor = debtor;
        this.explain = explain;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.report = report;
        this.image = image;
        this.type = type;
        this.status = status;
    }

    public MoneyPay(int amount, int categoryId, String debtor, String explain, String date,
                    String time, int accountId, int report, byte[] image, int type, int status) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.debtor = debtor;
        this.explain = explain;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.report = report;
        this.image = image;
        this.type = type;
        this.status = status;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
