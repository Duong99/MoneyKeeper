package vn.com.nghiemduong.moneykeeper.utils;

/**
 * -
 * - @created_by nxduong on 8/3/2021
 **/
public class AppConstants {

    // Định dạng thời gian
    public static final String FORMAT_TIME_VN = "dd/MM/yyyy";
    public static final String FORMAT_TIME_US = "MM/dd/yyyy";
    public static final String FORMAT_TIME_ISO_8601 = "yyyy/MM/dd";

    // Giới tính
    public static final int NAM = 1;
    public static final int NU = 2;

    // Loại tài khoản
    public static final int TIEN_MAT = 1;
    public static final int TAI_KHOAN_NGAN_HANG = 2;
    public static final int THE_TIN_DUNG = 3;
    public static final int TAI_KHOAN_DAU_TU = 4;
    public static final int KHAC = 5;

    public static final String TAG = "fata";

    // Path
    public static final String PATH_THU = "assets/ImageCategory/THU/";
    public static final String PATH_CHI = "assets/ImageCategory/CHI/";
    public static final String PATH_UN_KNOW = PATH_CHI + "CHI_UnKnow.png";
    public static final String PATH_CHUYEN_KHOAN = PATH_CHI + "CHI_phi_chuyen_khoan.png";


    // Loại tiền
    public static final String VND = "VND";

    //Có báo cho vào báo cáo không
    public static final int CO_BAO_CAO = 1;
    public static final int KHONG_BAO_CAO = -1;

    public static final int REQUEST_CODE_IMAGE_FROM_FOLDER = 211;
    public static final int REQUEST_CODE_IMAGE_FROM_CAMERA = 212;

    // Custom toast
    public static final int TOAST_SUCCESS = 21;
    public static final int TOAST_WARRING = 22;
    public static final int TOAST_ERROR = 23;

    // Các trạng thái của kế hoạch /định kỳ
    public static final int WAIT_PAY = 2;
    public static final int PAID = 1;

    // Các kiểu /Loại hạng mục
    public static final int CHI_TIEN = 1;
    public static final int THU_TIEN = 2;
    public static final int CHO_VAY = 3;
    public static final int THU_NO = 4;
    public static final int DI_VAY = 5;
    public static final int TRA_NO = 6;
    public static final int CHUYEN_KHOAN = 7;

    public static final int VAY_NO = 3;
    public static final int CHO_VAY_ID = 4;
    public static final int DI_VAY_ID = 5;
    public static final int THU_NO_ID = 6;
    public static final int TRA_NO_ID = 7;

    // Cập độ hạng mục
    public static final int CAP_DO_1 = 1;
    public static final int CAP_DO_2 = 2;

}
