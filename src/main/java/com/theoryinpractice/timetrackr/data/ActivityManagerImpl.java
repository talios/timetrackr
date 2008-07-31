/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 4/02/2006
 * Time: 19:08:59
 */
package com.theoryinpractice.timetrackr.data;

import com.theoryinpractice.timetrackr.vo.Activity;
import com.theoryinpractice.timetrackr.vo.User;
import com.theoryinpractice.timetrackr.vo.WorkItem;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Transactional
public class ActivityManagerImpl implements ActivityManager {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveActivity(Activity activity) {
        entityManager.persist(activity);
    }

    public void updateActivity(Activity activity) {
        entityManager.persist(activity);
    }

    public void stopUserWorking(User user) {
        for (Activity activity : user.getActivities()) {
            stopTimeOn(activity);
        }

        /*
        // Create iCal file
        Calendar calendar = null;
        try {
            calendar = new Calendar();
            calendar.getProperties().add(new ProdId("-//Mark Derricutt//iCal4j 1.0//EN"));
            calendar.getProperties().add(Version.VERSION_2_0);
            calendar.getProperties().add(CalScale.GREGORIAN);
            calendar.getProperties().add(new XProperty("X-WR-CALNAME", user.getUsername() + " Time Records"));

            for (Activity activity : user.getActivities()) {
                List<WorkItem> workItems = findWorkItemsForActivity(activity, true);

                for (WorkItem each : workItems) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
                    cal.set(java.util.Calendar.DAY_OF_MONTH, 25);

                    VEvent event = new VEvent(
                            new net.fortuna.ical4j.model.DateTime(each.getStart()),
                            new net.fortuna.ical4j.model.DateTime(each.getStop()),
                            activity.getName() + " - " + each.getComment());

                    // initialise as an all-day event..
                    event.getProperties().add(new Comment(each.getComment()));
                    event.getProperties().add(new Uid(each.getWorkItemId() + "-event"));
                    calendar.getComponents().add(event);
                }

            }

            FileOutputStream fout = new FileOutputStream("/tmp/" + user.getUsername() + ".ics");

            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ValidationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
          */

    }

    private void stopTimeOn(Activity activity) {
        for (WorkItem workItem : activity.getWorkItems()) {
            if (workItem.getActive()) {
                workItem.setActive(Boolean.FALSE);
                workItem.setStop(new Date());
                entityManager.merge(workItem);
            }
        }
    }

    public void startTimeOn(Activity activity, String comment) {
        stopTimeOn(activity);

        // Add new work item
        WorkItem workItem = new WorkItem();
        workItem.setActivityId(activity.getActivityId());
        workItem.setStart(new Date());
        workItem.setActive(Boolean.TRUE);
        workItem.setArchived(Boolean.FALSE);
        workItem.setComment(comment);

        entityManager.persist(workItem);
    }

    public List<WorkItem> findWorkItemsForActivity(Activity activity, Boolean includeArchived) {

        DetachedCriteria crit = DetachedCriteria.forClass(WorkItem.class);
        crit.add(Expression.eq("activityId", activity.getActivityId()));
        if (!includeArchived) {
            crit.add(Expression.ne("archived", Boolean.TRUE));
        }

        return entityManager.createQuery("from WorkItem w where w.activityId = :activityId and w.archived = :archived")
                .setParameter("activityId", activity.getActivityId())
                .setParameter("archived", Boolean.FALSE)
                .getResultList();
    }

    public Long calculateTimeFor(Activity activity, Boolean includeArchived) {

        List<WorkItem> workItems = findWorkItemsForActivity(activity, includeArchived);
        long time = 0;
        for (WorkItem each : workItems) {
            time += each.getTimeFor();
        }
        return time;
    }

    public void deleteActivity(Activity activity) {
        entityManager.remove(activity);
    }
}