package vn.com.nghiemduong.moneykeeper.data.db.plan;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import vn.com.nghiemduong.moneykeeper.data.db.BaseSqLite;
import vn.com.nghiemduong.moneykeeper.data.db.account.AccountDatabase;
import vn.com.nghiemduong.moneykeeper.data.db.category.CategoryDatabase;
import vn.com.nghiemduong.moneykeeper.data.model.db.Plan;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;
import vn.com.nghiemduong.moneykeeper.utils.DBUtils;

/**
 * - Kế hoạch
 * - @created_by nxduong on 14/3/2021
 **/
public class PlanDatabase extends BaseSqLite implements PlanDatabaseMvpPresenter {

    private final static String NAME_TABLE_PLAN = "tb_Plan";
    public final static String PLAN_ID = "planId";
    private final static String AMOUNT = "amount";
    private final static String ACCOUNT_ID = AccountDatabase.ACCOUNT_ID;
    private final static String CATEGORY_ID = CategoryDatabase.CATEGORY_ID;
    private final static String TO_ACCOUNT_ID = "toAccountId";
    private final static String DESCRIPTION = "description";
    private final static String CYCLE = "cycle";
    private final static String REPORT = "report";
    private final static String TYPE = "type";
    private final static String RECORD_CHECK = "recordCheck";

    private SQLiteDatabase db;

    public PlanDatabase(@Nullable Context context) {
        super(context);
    }

    /**
     * Thêm kế hoạch
     *
     * @param plan đối tượng kế hoạch
     * @created_by nxduong on 14/3/2021
     */
    @Override
    public long insertPlan(Plan plan) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, plan.getAmount());
        values.put(ACCOUNT_ID, plan.getAccountId());
        values.put(CATEGORY_ID, plan.getCategoryId());
        values.put(TO_ACCOUNT_ID, plan.getToAccountId());
        values.put(DESCRIPTION, plan.getDescription());
        values.put(CYCLE, plan.getCycle());
        values.put(REPORT, plan.getReport());
        values.put(TYPE, plan.getType());
        values.put(RECORD_CHECK, plan.getRecordCheck());

        long insert = DBUtils.checkDBFail;
        try {
            insert = db.insert(NAME_TABLE_PLAN, null, values);
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return insert;
    }

    /**
     * sửa kế hoạch
     *
     * @param plan đối tượng kế hoạch
     * @created_by nxduong on 14/3/2021
     */

    @Override
    public long updatePlan(Plan plan) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, plan.getAmount());
        values.put(ACCOUNT_ID, plan.getAccountId());
        values.put(CATEGORY_ID, plan.getCategoryId());
        values.put(TO_ACCOUNT_ID, plan.getToAccountId());
        values.put(DESCRIPTION, plan.getDescription());
        values.put(CYCLE, plan.getCycle());
        values.put(REPORT, plan.getReport());
        values.put(TYPE, plan.getType());
        values.put(RECORD_CHECK, plan.getRecordCheck());

        long update = DBUtils.checkDBFail;
        try {
            update = db.update(NAME_TABLE_PLAN, values, PLAN_ID + " = ?",
                    new String[]{String.valueOf(plan.getPlanId())});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return update;
    }

    /**
     * xóa kế hoạch
     *
     * @param plan đối tượng kế hoạch
     * @created_by nxduong on 14/3/2021
     */
    @Override
    public long deletePlan(Plan plan) {
        db = this.getWritableDatabase();
        long delete = DBUtils.checkDBFail;
        try {
            delete = db.delete(NAME_TABLE_PLAN, PLAN_ID,
                    new String[]{String.valueOf(plan.getPlanId())});
        } catch (Exception e) {
            AppUtils.handlerException(e);
        }
        db.close();
        return delete;
    }
}
