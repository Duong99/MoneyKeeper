package vn.com.nghiemduong.moneykeeper.ui.main.plus.debtor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Record;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public class DebtorActivityPresenter implements DebtorActivityMvpPresenter {
    private DebtorActivityMvpView mContactActivityMvpView;
    private Context mContext;

    public DebtorActivityPresenter(DebtorActivityMvpView contactActivityMvpView, Context context) {
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
        String contactName = null;
        String contactId = null;

        ArrayList<Record> listContacts = new ArrayList<>();
        Record record;

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor != null) {
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

                    record = new Record();
                    record.setDebtor(contactName);
                    listContacts.add(record);
                    //phoneCursor.close();
                }
            }
        }
        cursor.close();
        mContactActivityMvpView.resultGetAllContact(listContacts);
    }
}
