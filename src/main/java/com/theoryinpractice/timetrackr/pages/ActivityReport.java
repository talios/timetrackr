/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 23/02/2006
 * Time: 07:16:44
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.vo.Activity;

import java.util.List;

public class ActivityReport {
    private Activity activity;
    private Long timeFor;
    private List workItems;

    public ActivityReport(Activity activity, Long timeFor, List workItems) {
        this.activity = activity;
        this.timeFor = timeFor;
        this.workItems = workItems;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Long getTimeFor() {
        return timeFor;
    }

    public void setTimeFor(Long timeFor) {
        this.timeFor = timeFor;
    }

    public List getWorkItems() {
        return workItems;
    }

    public void setWorkItems(List workItems) {
        this.workItems = workItems;
    }

    public String getFormattedTimeFor() {
        return TimeFormat.format(getTimeFor());
    }
}