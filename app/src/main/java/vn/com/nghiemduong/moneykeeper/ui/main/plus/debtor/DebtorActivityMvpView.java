package vn.com.nghiemduong.moneykeeper.ui.main.plus.debtor;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Record;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public interface DebtorActivityMvpView {
    void resultGetAllContact(ArrayList<Record> listContact);
}
