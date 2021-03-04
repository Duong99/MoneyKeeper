package vn.com.nghiemduong.moneykeeper.data.model.db;

import java.io.Serializable;

/**
 * Đối tượng chuyển khoản (chuyển tiền giữ 2 tài khoản)
 *
 * @created_by nxduong on 16/2/2021
 **/
public class Transfer implements Serializable {
    private int transferId;     //	(INTEGER) : id chuyển tiền
    private int amount;  //  (INTEGER) : Số tiền chuyển
    private int fromAccountId;  //  (INTEGER) : id tài khoản chuyển tiền
    private int toAccountId;    //	(INTEGER) : id tài khoản nhận tiền
    private String date;        //  (TEXT) : Thời gian (ngày / tháng / năm)
    private String time;        //  (TEXT) : Thời gian ( Giờ : Phút)
    private String explain;     //  (TEXT) : Diễn giải
    private int report;         //  (INTEGER) : Có tính vào báo cáo không 1 là có, 0 là không
    private byte[] image;       //	(BOLD): Ảnh
    private int status;

    public Transfer(int transferId, int amount, int fromAccountId, int toAccountId,
                    String date, String time, String explain, int report, byte[] image, int status) {
        this.transferId = transferId;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.date = date;
        this.time = time;
        this.explain = explain;
        this.report = report;
        this.image = image;
        this.status = status;
    }

    public Transfer(int amount, int fromAccountId, int toAccountId, String date, String time,
                    String explain, int report, byte[] image, int status) {
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.date = date;
        this.time = time;
        this.explain = explain;
        this.report = report;
        this.image = image;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
