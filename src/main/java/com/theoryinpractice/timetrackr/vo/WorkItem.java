/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 3/02/2006
 * Time: 21:39:00
 */
package com.theoryinpractice.timetrackr.vo;

import com.theoryinpractice.timetrackr.pages.TimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "workitem")
public class WorkItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workItemId;
    private Integer activityId;
    private Date start;
    private Date stop;
    private String comment;
    private Boolean active;
    private Boolean archived;

    public Integer getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Integer workItemId) {
        this.workItemId = workItemId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Long getTimeFor() {
        long time = 0;
        long startTime = getStart() == null ? new Date().getTime() : getStart().getTime();
        long stopTime = getStop() == null ? new Date().getTime() : getStop().getTime();

        time += stopTime - startTime;
        return time;
    }

    public String getFormattedTimeFor() {
        return TimeFormat.format(getTimeFor());
    }

}