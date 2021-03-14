package vn.com.nghiemduong.moneykeeper.data.model.db;

import com.google.gson.internal.$Gson$Preconditions;

import java.io.Serializable;

/**
 * tb_Records: Bảng ghi chép gồm cái type chi tiền, thu tiền, trả nợ, thu nợ,
 * đi vay, cho vay, chuyển khoản
 *
 * <p>
 * private @created_by nxduong on 3/2/2021
 **/
public class Record implements Serializable {
    private int recordId;// (INTEGER) : id ghi chép
    private int amount;// (INTEGER): Số tiền
    private int categoryId;// 	(INTEGER) : id của category
    private String debtor;// (TEXT) : Người vay nợ
    private String description;// (TEXT): Diễn giải
    private String date;// (TEXT) : Thời gian (ngày / tháng / năm)
    private String time;// (TEXT) : Thời gian ( Giờ : Phút)
    private int accountId;// 	(INTEGER) : id tài khoản
    private int toAccountId;// (INTEGER) : tài khoản chuyển tiền đến
    private String dateDuration;// (TEXT) : Ngày thu nợ và ngày trả nợ
    private int report;//  	(INTEGER): Có tính vào báo cáo không 1 là có, 0 là không
    private byte[] image;// 	(BOLD): Ảnh
    private int type;// 	(INTEGER) kiểu chi tiền, thu tiền..

    public Record() {
    }

    public Record(int recordId, int amount, int categoryId, String debtor, String description, String date,
                  String time, int accountId, int toAccountId, String dateDuration, int report, byte[] image, int type) {
        this.recordId = recordId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.debtor = debtor;
        this.description = description;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.toAccountId = toAccountId;
        this.dateDuration = dateDuration;
        this.report = report;
        this.image = image;
        this.type = type;
    }

    /**
     * Hàm tạo sử dụng cho chi tiền, thu tiền để thêm
     *
     * @created_by nxduong on 11/3/2021
     */

    public Record(int amount, int categoryId, String description, String date, String time,
                  int accountId, int report, byte[] image, int type) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.description = description;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.report = report;
        this.image = image;
        this.type = type;
    }

    /**
     * Hàm tạo sử dụng cho chi tiền, thu tiền để sửa
     *
     * @created_by nxduong on 11/3/2021
     */

    public Record(int recordId, int amount, int categoryId, String description, String date,
                  String time, int accountId, int report, byte[] image, int type) {
        this.recordId = recordId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.description = description;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.report = report;
        this.image = image;
        this.type = type;
    }

    /**
     * Hàm tạo cho thu nợ và trả nợ để thêm
     *
     * @created_by nxduong on 11/3/2021
     */
    public Record(int amount, int categoryId, String debtor, String description, String date, String time,
                  int accountId, byte[] image, int type) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.debtor = debtor;
        this.description = description;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.image = image;
        this.type = type;
    }

    /**
     * Hàm tạo cho thu nợ và trả nợ để sửa
     *
     * @created_by nxduong on 11/3/2021
     */

    public Record(int recordId, int amount, int categoryId, String debtor, String description,
                  String date, String time, int accountId, byte[] image, int type) {
        this.recordId = recordId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.debtor = debtor;
        this.description = description;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.image = image;
        this.type = type;
    }

    /**
     * Hàm tạo cho vay và đi vay để thêm
     *
     * @created_by nxduong on 11/3/2021
     */

    public Record(int amount, int categoryId, String debtor, String description, String date, String time,
                  int accountId, String dateDuration, byte[] image, int type) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.debtor = debtor;
        this.description = description;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.dateDuration = dateDuration;
        this.image = image;
        this.type = type;
    }

    /**
     * Hàm tạo cho vay và đi vay để sửa
     *
     * @created_by nxduong on 11/3/2021
     */
    public Record(int recordId, int amount, int categoryId, String debtor, String description,
                  String date, String time, int accountId, String dateDuration, byte[] image, int type) {
        this.recordId = recordId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.debtor = debtor;
        this.description = description;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.dateDuration = dateDuration;
        this.image = image;
        this.type = type;
    }

    /**
     * Hàm tạo cho chuyển khoản để thêm
     *
     * @created_by nxduong on 11/3/2021
     */

    public Record(int amount, int accountId, int toAccountId, String description, String date, String time,
                  int report, byte[] image, int type) {
        this.amount = amount;
        this.description = description;
        this.accountId = accountId;
        this.toAccountId = toAccountId;
        this.date = date;
        this.time = time;
        this.report = report;
        this.image = image;
        this.type = type;
    }

    /**
     * Hàm tạo cho chuyển khoản để sửa
     *
     * @created_by nxduong on 11/3/2021
     */

    public Record(int recordId, int amount, int accountId, int toAccountId, String description,
                  String date, String time, int report, byte[] image, int type) {
        this.recordId = recordId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.time = time;
        this.accountId = accountId;
        this.toAccountId = toAccountId;
        this.report = report;
        this.image = image;
        this.type = type;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getDateDuration() {
        return dateDuration;
    }

    public void setDateDuration(String dateDuration) {
        this.dateDuration = dateDuration;
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
}
