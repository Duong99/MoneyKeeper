package vn.com.nghiemduong.moneykeeper.data.model;

import java.io.Serializable;

/**
 * - Đối tượng chuyển khoản (chuyển tiền giữ 2 tài khoản)
 * - @created_by nxduong on 16/2/2021
 **/
public class Transfer implements Serializable {
    private int transferId;// (INTEGER) : id chuyển tiền
    private int amountOfMoney;// (INTEGER) : Số tiền chuyển
    private int fromAccountId;//(INTEGER) : id tài khoản chuyển tiền
    private int toAccountId;//(INTEGER) : id tài khoản nhận tiền
    private String explain;//(TEXT): Diễn giải
    private String date;//	(TEXT) : Thời gian (ngày / tháng / năm)
    private String time;//	(TEXT) : Thời gian ( Giờ : Phút)
    private int report;//	(INTEGER): Có tính vào báo cáo không 1 là có, 0 là không
    private byte[] image;//(BOLD): Ảnh

    /**
     * Hàm tạo có id dùng trong đọc dữ liệu từ database và sửa
     *
     * @created_by nxduong on 16/2/2021
     */
    public Transfer(int transferId, int amountOfMoney, int fromAccountId, int toAccountId,
                    String explain, String date, String time, int report, byte[] image) {
        this.transferId = transferId;
        this.amountOfMoney = amountOfMoney;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.explain = explain;
        this.date = date;
        this.time = time;
        this.report = report;
        this.image = image;
    }

    /**
     * Hàm tạo không có id dùng cho việc thêm dữ liệu vào database
     *
     * @created_by nxduong on 16/2/2021
     */
    public Transfer(int amountOfMoney, int fromAccountId, int toAccountId, String explain,
                    String date, String time, int report, byte[] image) {
        this.amountOfMoney = amountOfMoney;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.explain = explain;
        this.date = date;
        this.time = time;
        this.report = report;
        this.image = image;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
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
