package vn.com.nghiemduong.moneykeeper.data.model;

/**
 * Đối tượng thu MoneyCollect khi người dùng thêm thu tiền
 *
 * @created_by nxduong on 3/2/2021
 **/
public class MoneyCollect {
    private int collectId;    //(INTEGER) : id thu tiền
    private int accountId;    //(INTEGER) : id tài khoản
    private int amountOfMoney; //(INTEGER): Số tiền thu
    private String categoryName;  //(TEXT) : Tên hạng mục thu
    private String categoryPath;  //(TEXT) : Đường dẫn ảnh của tên hạng mục trong assets
    private String accountName;    //(TEXT)  : Tên tài khoản
    private String explain;//(TEXT): Diễn giải
    private String date;    //(TEXT) : Thời gian (ngày / tháng / năm)
    private String time;        //(TEXT) : Thời gian ( Giờ : Phút)
    private int report;//(INTEGER): Có tính vào báo cáo không 1 là có, 0 là không
    private byte[] image;    //(BOLD): Ảnh

    public MoneyCollect(int collectId, int accountId, int amountOfMoney, String categoryName,
                        String categoryPath, String accountName, String explain, String date,
                        String time, int report, byte[] image) {
        this.collectId = collectId;
        this.accountId = accountId;
        this.amountOfMoney = amountOfMoney;
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.accountName = accountName;
        this.explain = explain;
        this.date = date;
        this.time = time;
        this.report = report;
        this.image = image;
    }

    public MoneyCollect(int accountId, int amountOfMoney, String categoryName, String categoryPath,
                        String accountName, String explain, String date, String time, int report,
                        byte[] image) {
        this.accountId = accountId;
        this.amountOfMoney = amountOfMoney;
        this.categoryName = categoryName;
        this.categoryPath = categoryPath;
        this.accountName = accountName;
        this.explain = explain;
        this.date = date;
        this.time = time;
        this.report = report;
        this.image = image;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
