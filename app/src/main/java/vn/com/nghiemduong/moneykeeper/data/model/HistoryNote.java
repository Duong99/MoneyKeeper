package vn.com.nghiemduong.moneykeeper.data.model;

/**
 * -
 * Lớp chứa 2 đối tượng MoneyCollect và MoneyPay để lấy giá giá trị
 * thời gian và sắp xếp chúng
 * <p>
 * - @created_by nxduong on 5/2/2021
 **/
public class HistoryNote {
    private MoneyCollect moneyCollect;
    private MoneyPay moneyPay;
    private long time;

    public HistoryNote(MoneyCollect moneyCollect, MoneyPay moneyPay) {
        this.moneyCollect = moneyCollect;
        this.moneyPay = moneyPay;


    }

    public MoneyCollect getMoneyCollect() {
        return moneyCollect;
    }

    public void setMoneyCollect(MoneyCollect moneyCollect) {
        this.moneyCollect = moneyCollect;
    }

    public MoneyPay getMoneyPay() {
        return moneyPay;
    }

    public void setMoneyPay(MoneyPay moneyPay) {
        this.moneyPay = moneyPay;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
