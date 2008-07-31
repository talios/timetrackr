/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 4/02/2006
 * Time: 19:03:45
 */
package com.theoryinpractice.timetrackr.data;

import com.theoryinpractice.timetrackr.vo.Activity;
import com.theoryinpractice.timetrackr.vo.User;
import com.theoryinpractice.timetrackr.vo.WorkItem;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.ArrayList;

@Transactional
public class UserManagerImpl implements UserManager {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private ActivityManager activityManager;

    public void setActivityManager(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    public ArrayList<User> findAllUsers() {
        return new ArrayList(entityManager.createQuery("from User").getResultList());
    }

    public User findByUserName(String username) {

        User user = (User) entityManager.createQuery("from User u where u.username = :username")
                .setParameter("username", username)
                .getSingleResult();

        return user;
    }

    public void createUser(User user) {
        entityManager.persist(user);
    }

    public void archiveWorkItemsForUser(User user) {
        for (Activity activity : user.getActivities()) {
            List<WorkItem> workItems = activityManager.findWorkItemsForActivity(activity, Boolean.FALSE);
            for (WorkItem workItem : workItems) {
                workItem.setArchived(Boolean.TRUE);
                entityManager.merge(workItem);
            }
        }

    }

    public Long calculateTimeForUser(User user, Boolean includeArchived) {

        Long totalTime = 0L;
        for (Activity activity : user.getActivities()) {
            totalTime += activityManager.calculateTimeFor(activity, includeArchived);
        }

        return totalTime;
    }

    public void deleteUser(User user) {
        entityManager.remove(user);
    }
}