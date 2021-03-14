package vn.com.nghiemduong.moneykeeper.data.db.plan;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.Plan;

/**
 * -
 * - @created_by nxduong on 14/3/2021
 **/
public interface PlanDatabaseMvpPresenter {

    long insertPlan(Plan plan);

    long updatePlan(Plan plan);

    long deletePlan(Plan plan);
}
