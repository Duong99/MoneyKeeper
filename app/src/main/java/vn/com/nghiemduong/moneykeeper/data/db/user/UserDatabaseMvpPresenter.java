package vn.com.nghiemduong.moneykeeper.data.db.user;

import java.util.ArrayList;

import vn.com.nghiemduong.moneykeeper.data.model.db.User;

/**
 * - @created_by nxduong on 26/1/2021
 **/
public interface UserDatabaseMvpPresenter {

    ArrayList<User> getAllUsers();

    User getUser(String email);

    long insertUser(User user);

    long updateUser(User user);

    long deleteUser(User user);
}
