package vn.com.nghiemduong.moneykeeper.data.model.db;

import java.io.Serializable;

/**
 * - Đối tượng kế hoạch
 * - @created_by nxduong on 14/3/2021
 **/
public class Plan implements Serializable {

    private int planId;     //	INTEGER,	: id kế hoạch
    private int amount;     //	INTEGER,	: số tiền
    private int accountId;  //		INTEGER,	: id tài khoản
    private int categoryId;     //		INTEGER,	: id hạng mục
    private int toAccountId;    //		INTEGER,	: id tài khoản chuyển tiền đến
    private String description;//	TEXT,	: diễn giải
    private int cycle;      //	INTEGER,	: tần suất ghi chép kế hoạch
    private int report;     //	INTEGER,	: báo cáo
    private int type;       //	INTEGER,	: loại ghi chép
    private int recordCheck;//	INTEGER,	: kiểm tra đã đến ngày ghi chép  đã ghi chép chưa

    public Plan(int planId, int amount, int accountId, int categoryId, int toAccountId,
                String description, int cycle, int report, int type, int recordCheck) {
        this.planId = planId;
        this.amount = amount;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.toAccountId = toAccountId;
        this.description = description;
        this.cycle = cycle;
        this.report = report;
        this.type = type;
        this.recordCheck = recordCheck;
    }

    public Plan(int amount, int accountId, int categoryId, int toAccountId, String description,
                int cycle, int report, int type, int recordCheck) {
        this.amount = amount;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.toAccountId = toAccountId;
        this.description = description;
        this.cycle = cycle;
        this.report = report;
        this.type = type;
        this.recordCheck = recordCheck;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRecordCheck() {
        return recordCheck;
    }

    public void setRecordCheck(int recordCheck) {
        this.recordCheck = recordCheck;
    }
}
