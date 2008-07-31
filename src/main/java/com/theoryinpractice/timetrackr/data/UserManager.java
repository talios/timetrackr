/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 4/02/2006
 * Time: 19:02:40
 */
package com.theoryinpractice.timetrackr.data;

import com.theoryinpractice.timetrackr.vo.User;

import java.util.List;
import java.util.ArrayList;

public interface UserManager {
    ArrayList<User> findAllUsers();

    User findByUserName(String username);

    void createUser(User user);

    void archiveWorkItemsForUser(User user);

    Long calculateTimeForUser(User user, Boolean includeArchived);

    void deleteUser(User user);
}