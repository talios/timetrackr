/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 23/02/2006
 * Time: 07:16:44
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.vo.Activity;
import com.theoryinpractice.timetrackr.vo.WorkItem;

import java.util.List;

public class ActivityReport {
    private Activity activity;
    private Long timeFor;
    private List<WorkItem> workItems;

    public ActivityReport(Activity activity, Long timeFor, List<WorkItem> workItems) {
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

    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }

    public String getFormattedTimeFor() {
        return TimeFormat.format(getTimeFor());
    }
}