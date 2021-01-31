package vn.com.nghiemduong.moneykeeper.data.model;

/**
 * -
 * Đối tượng contact lưu thông tin người liên lạc
 * <p>
 * - @created_by nxduong on 31/1/2021
 **/
public class Contact {
    private int contactId;
    private String contactName;
    private String contactPhone;

    public Contact(int contactId, String contactName, String contactPhone) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public Contact(String contactName, String contactPhone) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
