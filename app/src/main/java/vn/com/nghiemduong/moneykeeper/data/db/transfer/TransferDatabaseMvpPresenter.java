package vn.com.nghiemduong.moneykeeper.data.db.transfer;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Transfer;

/**
 * -
 * - @created_by nxduong on 16/2/2021
 **/
public interface TransferDatabaseMvpPresenter {
    ArrayList<Transfer> getAllTransfer();

    long insertTransfer(Transfer transfer);

    long updateTransfer(Transfer transfer, int numberMoneyPrevious);

    long deleteTransfer(Transfer transfer);
}
