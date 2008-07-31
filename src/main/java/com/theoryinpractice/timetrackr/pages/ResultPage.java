/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 6/02/2006
 * Time: 00:39:24
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.TimeTrackrPage;
import com.theoryinpractice.timetrackr.data.UserManager;
import com.theoryinpractice.timetrackr.vo.User;
import org.apache.wicket.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.time.Duration;

public class ResultPage extends TimeTrackrPage {
    private User user;

    @SpringBean
    UserManager userManager;

    public ResultPage(final PageParameters parameters) {

        user = userManager.findByUserName("talios");

        add(new FeedbackPanel("feedback"));

        Label totalTime;
        add(totalTime = new Label("totalTime", new AbstractReadOnlyModel<String>() {
            public String getObject() {
                Long totalTime = userManager.calculateTimeForUser(user, Boolean.FALSE);
                return TimeFormat.format(totalTime);
            }
        }));
        totalTime.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(60)));
        totalTime.setOutputMarkupId(true);


        add(new PopupCloseLink("close"));
    }

}