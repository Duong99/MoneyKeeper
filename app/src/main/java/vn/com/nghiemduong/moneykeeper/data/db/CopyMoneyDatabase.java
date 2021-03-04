package vn.com.nghiemduong.moneykeeper.data.db;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.com.nghiemduong.moneykeeper.BuildConfig;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * -
 * Lớp dùng để copy database vào máy android
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/

public class CopyMoneyDatabase extends BaseSqLite {

    private Context context;
    private String DB_PATH = "data/data/" + BuildConfig.APPLICATION_ID + "/databases/";


    private SQLiteDatabase db;

    public CopyMoneyDatabase(Context context) {
        super(context);
        this.context = context;

        boolean dbExist = checkDatabase();

        if (!dbExist) {
            System.out.println("Database doesn't exist!");
            createDatabase();
        }
    }

    // Kiểm tra database có chưa
    private boolean checkDatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DBUtils.DB_NAME;
            File dbFile = new File(myPath);
            checkdb = dbFile.exists();
        } catch (SQLException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void createDatabase() {
        this.getReadableDatabase();

        try {
            copyDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Copy database
    private void copyDatabase() throws IOException {
        db = this.getWritableDatabase();
        InputStream myInput = context.getAssets().open("databases/" + DBUtils.DB_NAME);
        File file = new File((DB_PATH));
        file.mkdirs();

        OutputStream myOutput = new FileOutputStream(DB_PATH + DBUtils.DB_NAME);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
