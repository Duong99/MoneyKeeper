package vn.com.nghiemduong.moneykeeper.data.model;

/**
 * -  Đối tượng User cung cấp tên tài khoản, mật khẩu, email, ...
 * <p>
 * <p>
 * - @created_by nxduong on
 **/
public class User {
    private int userId;
    private String lastName;
    private String firstName;
    private String image;
    private String email;
    private String numberPhone;
    private String birthday;
    private boolean gender;
    private String job;
    private String password;

    // Hàm tạo thông tin người dùng

    // Hàm tạo để đăng ký người dùng

    public User(String lastName, String firstName, String email, String numberPhone, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.numberPhone = numberPhone;
        this.email = email;
        this.password = password;
    }

    //Hàm tạo để đăng nhập
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
