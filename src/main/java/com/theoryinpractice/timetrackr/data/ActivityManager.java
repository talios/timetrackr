/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 4/02/2006
 * Time: 19:07:40
 */
package com.theoryinpractice.timetrackr.data;

import com.theoryinpractice.timetrackr.vo.Activity;
import com.theoryinpractice.timetrackr.vo.User;
import com.theoryinpractice.timetrackr.vo.WorkItem;

import java.util.List;

public interface ActivityManager {
    void saveActivity(Activity activity);

    void updateActivity(Activity activity);

    void stopUserWorking(User user);

    void startTimeOn(Activity activity, String comment);

    List<WorkItem> findWorkItemsForActivity(Activity activity, Boolean includeArchived);

    Long calculateTimeFor(Activity activity, Boolean includeArchived);

    void deleteActivity(Activity activity);
}
