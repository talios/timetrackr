/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 20/02/2006
 * Time: 21:43:30
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.TimeTrackrSession;
import com.theoryinpractice.timetrackr.data.ActivityManager;
import com.theoryinpractice.timetrackr.forms.AddWorkItemForm;
import com.theoryinpractice.timetrackr.vo.Activity;
import com.theoryinpractice.timetrackr.vo.WorkItem;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.behavior.StringHeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

import java.util.List;

public class ActivityDetailsPanel extends Panel {

    @SpringBean
    ActivityManager activityManager;

    public ActivityDetailsPanel(final String id, final Activity activity) {
        super(id);
        setOutputMarkupId(true);

        TimeTrackrSession session = (TimeTrackrSession) getSession();

        if (!activity.getActivityId().equals(session.getCurrentActivityId())) {
            add(new SimpleAttributeModifier("class", "displayhide"));
        }

        Label label;
        add(label = new Label("description", activity.getDescription()));
        label.setEscapeModelStrings(false);


        add(new Link("deleteActivity") {
            public void onClick() {
                activityManager.deleteActivity(activity);
                setResponsePage(Index.class);
            }
        });

        List<WorkItem> workItems = activityManager.findWorkItemsForActivity(activity, false);

        add(new WorkItemsListView("workItems", workItems));

        add(new AddWorkItemForm("newWorkItem", activity));
    }

    private class WorkItemsListView extends ListView<WorkItem> {
        public WorkItemsListView(String component, List list) {
            super(component, list);
        }

        protected void populateItem(final ListItem<WorkItem> listItem) {
            final WorkItem workItem = listItem.getModelObject();

            final Label timeLabel;
            listItem.add(timeLabel = new Label("time", new Model<String>() {
                public String getObject() {
                    return workItem.getFormattedTimeFor();
                }
            }));

            Label label;
            listItem.add(label = new Label("comment", workItem.getComment()));
            label.setEscapeModelStrings(false);

            if (workItem.getActive()) {
                listItem.add(new AttributeModifier("class", true, new Model("workitemactive")));
                timeLabel.setOutputMarkupId(true);
                timeLabel.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)));
            }
        }
    }
}