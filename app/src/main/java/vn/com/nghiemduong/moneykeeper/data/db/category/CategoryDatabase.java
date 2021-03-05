package vn.com.nghiemduong.moneykeeper.data.db.category;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.db.BaseSqLite;
import vn.com.nghiemduong.moneykeeper.data.model.db.Category;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * Class có tác dụng thêm, sửa, xóa, lấy dữ liệu trong bảng Danh mục (category)
 * - @created_by nxduong on 6/2/2021
 **/
public class CategoryDatabase extends BaseSqLite implements CategoryDatabaseMvpPresenter {

    private final static String NAME_TABLE_CATEGORY = "tb_Category";
    public final static String CATEGORY_ID = "categoryId";
    private final static String CATEGORY_NAME = "categoryName";
    private final static String CATEGORY_PATH = "categoryPath";
    private final static String CATEGORY_EXPLAIN = "explain";
    private final static String CATEGORY_TYPE = "type";
    private final static String CATEGORY_PARENT_ID = "categoryParentId";
    private final static String CATEGORY_LEVEL = "level";
    private SQLiteDatabase db;

    public CategoryDatabase(@Nullable Context context) {
        super(context);
    }

    /**
     * Hàm lấy danh sác hạng mục cha trong database
     *
     * @return parentCategories danh sách hạng mục cha
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public ArrayList<Category> getAllParentCategory(int type, int level) {
        db = this.getReadableDatabase();
        ArrayList<Category> parentCategories = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_CATEGORY
                + " WHERE " + CATEGORY_TYPE + " = " + type
                + " AND " + CATEGORY_LEVEL + " = " + level;

        try {
            Category category;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    category = new Category(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            cursor.getInt(6));
                    parentCategories.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return parentCategories;
    }

    /**
     * Hàm lấy danh sác hạng mục con trong database
     *
     * @return subCategories danh sách hạng mục con
     * @created_by nxduong on 5/3/2021
     */
    @Override
    public ArrayList<Category> getAllSubCategory(int idParentCategory) {
        db = this.getReadableDatabase();
        ArrayList<Category> subCategories = new ArrayList<>();
        String query = "SELECT * FROM " + NAME_TABLE_CATEGORY
                + " WHERE " + CATEGORY_PARENT_ID + " = " + idParentCategory;

        try {
            Category category;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    category = new Category(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            cursor.getInt(6));
                    subCategories.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return subCategories;
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
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    category = new Category(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            cursor.getInt(6));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            AppUtils.handlerException(e);
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
        values.put(CATEGORY_TYPE, category.getType());
        values.put(CATEGORY_PARENT_ID, category.getCategoryParentId());
        values.put(CATEGORY_LEVEL, category.getLevel());
        long insert = DBUtils.checkDBFail;
        try {
            insert = db.insert(NAME_TABLE_CATEGORY, null, values);
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
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
        values.put(CATEGORY_TYPE, category.getType());
        values.put(CATEGORY_PARENT_ID, category.getCategoryParentId());
        values.put(CATEGORY_LEVEL, category.getLevel());
        long update = DBUtils.checkDBFail;
        try {
            update = db.update(NAME_TABLE_CATEGORY, values, CATEGORY_ID + " = ? ",
                    new String[]{String.valueOf(category.getCategoryId())});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
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
        long delete = DBUtils.checkDBFail;
        try {
            delete = db.delete(NAME_TABLE_CATEGORY, CATEGORY_ID + " = ?",
                    new String[]{String.valueOf(categoryId)});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }

        db.close();
        return delete;
    }
}
