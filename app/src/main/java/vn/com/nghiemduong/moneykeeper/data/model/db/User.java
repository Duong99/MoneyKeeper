package vn.com.nghiemduong.moneykeeper.data.model.db;

/**
 * -  Đối tượng User cung cấp tên tài khoản, mật khẩu, email, ...
 * <p>
 * <p>
 * - @created_by nxduong on
 **/
public class User {
    private int userId;
    private String name;
    private String email;
    private String numberPhone;
    private String birthday;
    private int gender;
    private String password;

    public User(int userId, String name, String email, String numberPhone,
                String birthday, int gender, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
        this.birthday = birthday;
        this.gender = gender;
        this.password = password;
    }

    public User(String name, String email, String numberPhone, String birthday, int gender, String password) {
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
        this.birthday = birthday;
        this.gender = gender;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
