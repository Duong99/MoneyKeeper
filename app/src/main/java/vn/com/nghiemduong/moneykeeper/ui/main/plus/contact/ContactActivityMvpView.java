package vn.com.nghiemduong.moneykeeper.ui.main.plus.contact;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Contact;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public interface ContactActivityMvpView {
    void resultGetAllContact(ArrayList<Contact> listContact);
}
