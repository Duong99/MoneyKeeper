package vn.com.nghiemduong.moneykeeper.ui.main.plus.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.doodle.android.chips.model.Contact;

import java.util.ArrayList;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public class ContactActivityPresenter implements ContactActivityMvpPresenter {
    private ContactActivityMvpView mContactActivityMvpView;
    private Context mContext;

    public ContactActivityPresenter(ContactActivityMvpView contactActivityMvpView, Context context) {
        this.mContactActivityMvpView = contactActivityMvpView;
        this.mContext = context;
    }

    /**
     * Hàm đọc contact từ trên thiết bị điện thoại
     *
     * @created_by nxduong on 31/1/2021
     */

    @Override
    public void getAllContactFromDevice() {
        ContentResolver contentResolver = mContext.getContentResolver();
        String contactId;
        String contactName = null;
        String phoneNumber = null;

        ArrayList<Contact> listContacts = new ArrayList<>();

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

/*                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }*/

                    listContacts.add(new Contact(contactName, contactName, contactName, contactId, null));
                    //phoneCursor.close();
                }
            }
        }
        cursor.close();
        mContactActivityMvpView.resultGetAllContact(listContacts);
    }
}
