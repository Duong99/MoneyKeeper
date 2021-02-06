package vn.com.nghiemduong.moneykeeper.data.db.category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * -
 * Class có tác dụng thêm sửa xóa lấy dữ liệu hạng mục con
 * <p>
 * - @created_by nxduong on 6/2/2021
 **/

public class SubCategoryDatabase extends SQLiteOpenHelper implements SubCategoryDatabaseMvpPresenter {

    private final static String NAME_TABLE_SUBCATEGORY = "tb_SubCategory";
    public final static String SUB_ID = "subCategoryId";
    private final static String CATEGORY_ID = CategoryDatabase.CATEGORY_ID;
    private final static String SUB_NAME = "subCategoryName";
    private final static String SUB_PATH = "subCategoryPath";
    private final static String SUB_EXPLAIN = "explain";
    private SQLiteDatabase db;

    public SubCategoryDatabase(@Nullable Context context) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * Hàm lấy tất cả dữ liệu từ trong bảng Hạng Mục con (SubCategory)
     *
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public ArrayList<SubCategory> getAllSubCategory(int categoryId) {
        db = this.getReadableDatabase();
        ArrayList<SubCategory> listSubCategories = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_SUBCATEGORY
                + " WHERE " + CATEGORY_ID + " = " + categoryId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                SubCategory account = new SubCategory(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));

                listSubCategories.add(account);
            } while (cursor.moveToNext());
        }
        db.close();
        return listSubCategories;
    }

    /**
     * Lấy 1 trường dữ liệu trong bảng SubCategory
     *
     * @param subCategoryId id của SubCategory cần lấy
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public SubCategory getSubCategory(int subCategoryId) {
        db = this.getReadableDatabase();
        SubCategory account = null;
        String query = "SELECT * FROM " + NAME_TABLE_SUBCATEGORY +
                " WHERE " + SUB_ID + " = " + subCategoryId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                account = new SubCategory(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
            } while (cursor.moveToNext());
        }
        db.close();
        return account;
    }

    /**
     * Thêm dữ dữ liệu subCategory vào trong bảng  SubCategory
     *
     * @param subCategory đối tượng thêm
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public long insertSubCategory(SubCategory subCategory) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, subCategory.getCategoryId());
        values.put(SUB_NAME, subCategory.getSubCategoryName());
        values.put(SUB_PATH, subCategory.getSubCategoryPath());
        values.put(SUB_EXPLAIN, subCategory.getExplain());
        long insert = db.insert(NAME_TABLE_SUBCATEGORY, null, values);

        db.close();
        return insert;
    }


    /**
     * Cập nhật dữ dữ liệu subCategory vào trong bảng  SubCategory
     *
     * @param subCategory đối tượng cập nhật
     * @created_by nxduong on 6/2/2021
     */


    @Override
    public long updateSubCategory(SubCategory subCategory) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, subCategory.getCategoryId());
        values.put(SUB_NAME, subCategory.getSubCategoryName());
        values.put(SUB_PATH, subCategory.getSubCategoryPath());
        values.put(SUB_EXPLAIN, subCategory.getExplain());
        long update = db.update(NAME_TABLE_SUBCATEGORY, values, SUB_ID + " = ? ",
                new String[]{String.valueOf(subCategory.getCategoryId())});

        db.close();
        return update;
    }

    /**
     * Xóa dữ dữ liệu subCategory vào trong bảng  SubCategory
     *
     * @param subCategoryId đối tượng xóa
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public long deleteSubCategory(int subCategoryId) {
        db = this.getWritableDatabase();
        long delete = db.delete(NAME_TABLE_SUBCATEGORY, SUB_ID + " = ?",
                new String[]{String.valueOf(subCategoryId)});
        db.close();
        return delete;
    }
}
