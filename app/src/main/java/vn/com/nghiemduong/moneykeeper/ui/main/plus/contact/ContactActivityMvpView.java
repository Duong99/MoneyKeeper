package vn.com.nghiemduong.moneykeeper.ui.main.plus.contact;

import com.doodle.android.chips.model.Contact;

import java.util.ArrayList;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public interface ContactActivityMvpView {
    void resultGetAllContact(ArrayList<Contact> listContact);
}
