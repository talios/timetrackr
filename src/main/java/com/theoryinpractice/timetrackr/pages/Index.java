package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.TimeTrackrSecurePage;
import com.theoryinpractice.timetrackr.data.ActivityManager;
import com.theoryinpractice.timetrackr.data.UserManager;
import com.theoryinpractice.timetrackr.vo.Activity;
import com.theoryinpractice.timetrackr.vo.User;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic bookmarkable index page.
 * <p/>
 * NOTE: You can get session properties from TimeTrackrSession via getQuickStartSession()
 */
public class Index extends TimeTrackrSecurePage {

    @SpringBean
    ActivityManager activityManager;

    @SpringBean
    UserManager userManager;

    public Index(final PageParameters parameters) {
        super();

        final User user = getQuickStartSession().refreshUser();
        FeedbackPanel feedbackPanel;

        setVersioned(false);

        // Add nifty box support
        add(HeaderContributor.forCss("/nifty/NiftyLayout.css"));
        add(HeaderContributor.forJavaScript("/nifty/niftycube.js"));
        add(HeaderContributor.forJavaScript("/nifty/NiftyLayout.js"));

        add(feedbackPanel = new FeedbackPanel("feedback"));
        feedbackPanel.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)));

        add(new Link("stopWorkButton") {
            public void onClick() {
                activityManager.stopUserWorking(user);
            }
        });

        add(new Link("logoutButton") {
            public void onClick() {
                activityManager.stopUserWorking(getQuickStartSession().getUser());
                getQuickStartSession().setUser(null);
                setResponsePage(Signin.class);
            }
        });

        add(new Link("archiveWorkButton") {
            public void onClick() {
                userManager.archiveWorkItemsForUser(user);
            }
        });

        add(new Link("deleteAccountButton") {
            public void onClick() {
                userManager.deleteUser(user);
                getQuickStartSession().setUser(null);
                setResponsePage(Signin.class);
            }
        });

        add(new EmailReportLink("emailReportButton"));
        add(new PageLink("addActivityButton", AddActivity.class));

        List activityList = new ArrayList();
        activityList.addAll(user.getActivities());

        add(new ActivityListView("activities", activityList, getQuickStartSession().getCurrentActivityId()));

        Label totalTime;
        add(totalTime = new Label("totalTime", new AbstractReadOnlyModel() {
            public Object getObject() {
                Long time = userManager.calculateTimeForUser(user, Boolean.FALSE);
                return TimeFormat.format(time);
            }
        }));

        totalTime.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(60)));
        totalTime.setOutputMarkupId(true);

    }


    private class ActivityListView extends ListView {
        Integer currentActivityId;

        public ActivityListView(String component, List list, Integer currentActivityId) {
            super(component, list);
            this.currentActivityId = currentActivityId;
        }

        public void populateItem(final ListItem listItem) {
            Activity activity = (Activity) listItem.getModelObject();

            WebMarkupContainer header;
            listItem.add(header = new WebMarkupContainer("activityHeader"));

            Label labelTime;
            header.add(new Label("name", activity.getName()));
            header.add(labelTime = new Label("activityTime", new TimeFormatModel(activity)));

            if (activity.getActivityId().equals(currentActivityId)) {
                labelTime.setOutputMarkupId(true);
                labelTime.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)));
            }

            // Details
            final ActivityDetailsPanel activityDetailsPanel;
            listItem.add(activityDetailsPanel = new ActivityDetailsPanel("activityDetails", activity));

            header.add(new AttributeModifier("onclick", true, new Model<String>() {
                public String getObject() {
                    return "toggleVisibleDetails('" + activityDetailsPanel.getMarkupId() + "');";
                }
            }));

        }

        private class TimeFormatModel extends AbstractReadOnlyModel<String> {
            private final Activity activity;

            public TimeFormatModel(Activity activity) {
                this.activity = activity;
            }

            public String getObject() {
                Long time = activityManager.calculateTimeFor(activity, Boolean.FALSE);
                return TimeFormat.format(time);
            }
        }
    }


}
