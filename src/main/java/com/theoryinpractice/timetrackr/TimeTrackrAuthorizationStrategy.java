/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 11/02/2006
 * Time: 16:29:08
 */
package com.theoryinpractice.timetrackr;

import com.theoryinpractice.timetrackr.data.UserManager;
import com.theoryinpractice.timetrackr.vo.User;

import javax.servlet.http.Cookie;

import org.apache.wicket.Session;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.authorization.strategies.page.AbstractPageAuthorizationStrategy;

class TimeTrackrAuthorizationStrategy extends AbstractPageAuthorizationStrategy {
    private UserManager userManager;

    public TimeTrackrAuthorizationStrategy() {
//        super(TimeTrackrSecurePage.class, Signin.class);
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected boolean isPageAuthorized(final Class pageClass) {
        if (instanceOf(pageClass, TimeTrackrSecurePage.class)) {
            return isAuthorized();
        }

        // Allow construction by default
        return true;
    }

    protected boolean isAuthorized() {
        // check whether the user is logged on
        TimeTrackrSession timeTrackrSession = (TimeTrackrSession) Session.get();

        if (timeTrackrSession.getUser() == null) {
            ServletWebRequest swr = (ServletWebRequest) RequestCycle.get().getRequest();
            Cookie[] cookies = swr.getCookies();
            if (cookies != null) {
                for (Cookie cooky : cookies) {
                    if ("username".equals(cooky.getName())) {
                        User user = userManager.findByUserName(cooky.getValue());
                        timeTrackrSession.setUser(user);
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

}