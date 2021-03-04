package vn.com.nghiemduong.moneykeeper.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * -
 * - @created_by nxduong on 2/3/2021
 **/

public class BaseSqLite extends SQLiteOpenHelper {
    public BaseSqLite(@Nullable Context context) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
