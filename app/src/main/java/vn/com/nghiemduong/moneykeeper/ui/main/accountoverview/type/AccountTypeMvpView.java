package vn.com.nghiemduong.moneykeeper.ui.main.accountoverview.type;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.AccountType;

/**
 * <p>
 * - @created_by nxduong on 26/1/2021
 **/
public interface AccountTypeMvpView {
    void resultListAccountType(ArrayList<AccountType> listAccountType);
}
