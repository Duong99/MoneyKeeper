package vn.com.nghiemduong.moneykeeper.data.db.record;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Record;

/**
 * - @created_by nxduong on 2/2/2021
 **/
public interface RecordDatabaseMvpPresenter {
    ArrayList<Record> getAllRecord();

    ArrayList<Record> searchRecordByDate(String fromDate, String toDate);

    ArrayList<Record> getAllRecordWhereType(int type);

    long insertRecord(Record record);

    long updateRecord(Record record, int numberMoneyPrevious);

    long deleteRecord(Record record);
}
