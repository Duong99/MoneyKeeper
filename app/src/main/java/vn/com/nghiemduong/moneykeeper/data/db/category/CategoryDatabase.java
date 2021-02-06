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
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * Class có tác dụng thêm, sửa, xóa, lấy dữ liệu trong bảng Danh mục (category)
 * - @created_by nxduong on 6/2/2021
 **/
public class CategoryDatabase extends SQLiteOpenHelper implements CategoryDatabaseMvpPresenter {


    private final static String NAME_TABLE_CATEGORY = "tb_Category";
    public final static String CATEGORY_ID = "categoryId";
    //private final static String SUB_ = "categoryId";
    private final static String CATEGORY_NAME = "categoryName";
    private final static String CATEGORY_PATH = "categoryPath";
    private final static String CATEGORY_EXPLAIN = "explain";
    private final static String CATEGORY_TYPE = "type";
    private SQLiteDatabase db;

    public CategoryDatabase(@Nullable Context context) {
        super(context, DBUtils.DB_NAME, null, DBUtils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Hàm lấy tất cả dữ liệu từ trong bảng Hạng Mục (Category)
     *
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public ArrayList<Category> getAllCategory(int type, Context context) {
        db = this.getReadableDatabase();
        ArrayList<Category> listCategories = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_CATEGORY +
                " WHERE " + CATEGORY_TYPE + " = " + type;

        SubCategoryDatabase subCategoryDatabase = new SubCategoryDatabase(context);
        ArrayList<SubCategory> subCategories;
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    subCategories = subCategoryDatabase.getAllSubCategory(cursor.getInt(0));
                    Category category = new Category(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            subCategories);

                    listCategories.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return listCategories;
    }

    /**
     * Lấy 1 trường dữ liệu trong bảng Category
     *
     * @param categoryId id của Category cần lấy
     * @created_by nxduong on 6/2/2021
     */
    @Override
    public Category getCategory(int categoryId) {
        db = this.getReadableDatabase();
        Category category = null;
        String query = "SELECT * FROM " + NAME_TABLE_CATEGORY
                + " WHERE " + CATEGORY_ID + " = " + categoryId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                category = new Category(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4));

            } while (cursor.moveToNext());
        }
        db.close();
        return category;
    }

    /**
     * Thêm dữ dữ liệu category vào trong bảng  Category
     *
     * @param category đối tượng thêm
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public long insertCategory(Category category) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getCategoryName());
        values.put(CATEGORY_PATH, category.getCategoryPath());
        values.put(CATEGORY_EXPLAIN, category.getExplain());
        values.put(CATEGORY_TYPE, category.getCategoryId());
        long insert = db.insert(NAME_TABLE_CATEGORY, null, values);

        db.close();
        return insert;
    }

    /**
     * chỉnh sửa dữ dữ liệu category vào trong bảng  Category
     *
     * @param category đối tượng thêm
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public long updateCategory(Category category) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getCategoryName());
        values.put(CATEGORY_PATH, category.getCategoryPath());
        values.put(CATEGORY_EXPLAIN, category.getExplain());
        values.put(CATEGORY_TYPE, category.getCategoryId());
        long update = db.update(NAME_TABLE_CATEGORY, values, CATEGORY_ID + " = ? ",
                new String[]{String.valueOf(category.getCategoryId())});

        db.close();
        return update;
    }

    /**
     * xóa dữ dữ liệu category vào trong bảng  Category
     *
     * @param categoryId id cần xóa trong bảng Category
     * @created_by nxduong on 6/2/2021
     */

    @Override
    public long deleteCategory(int categoryId) {
        db = this.getWritableDatabase();
        long delete = db.delete(NAME_TABLE_CATEGORY, CATEGORY_ID + " = ?",
                new String[]{String.valueOf(categoryId)});
        db.close();
        return delete;
    }
}
