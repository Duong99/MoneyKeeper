package vn.com.nghiemduong.moneykeeper.ui.main.plus.debtor;

import com.doodle.android.chips.model.Contact;

import java.util.ArrayList;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public interface DebtorActivityMvpView {
    void resultGetAllContact(ArrayList<Contact> listContact);
}