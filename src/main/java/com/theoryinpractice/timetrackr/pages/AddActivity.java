/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 5/02/2006
 * Time: 13:38:30
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.TimeTrackrSecurePage;
import com.theoryinpractice.timetrackr.forms.AddActivyForm;
import com.theoryinpractice.timetrackr.vo.Activity;
import com.theoryinpractice.timetrackr.vo.User;
import org.apache.wicket.PageParameters;

public class AddActivity extends TimeTrackrSecurePage {

    public AddActivity(final PageParameters parameters) {
        User user = getQuickStartSession().refreshUser();

        setVersioned(false);

        Activity activity = new Activity();
        activity.setUserId(user.getUserId());
        add(new AddActivyForm("addActivity", activity));
    }

}