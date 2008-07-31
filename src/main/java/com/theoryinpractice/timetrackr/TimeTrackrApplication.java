package com.theoryinpractice.timetrackr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.theoryinpractice.timetrackr.pages.Index;
import com.theoryinpractice.timetrackr.pages.ListUsers;
import com.theoryinpractice.timetrackr.pages.Signin;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.*;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Runs the TimeTrackrApplication when invoked from command line.
 */
public class TimeTrackrApplication extends WebApplication implements IUnauthorizedComponentInstantiationListener {

    private TimeTrackrAuthorizationStrategy timeTrackrAuthorizationStrategy;

    /**
     * Logging
     */
    private final Logger logger = LoggerFactory.getLogger(TimeTrackrApplication.class);

    /**
     * Constructor
     */
    public TimeTrackrApplication() {
    }

    protected void init() {

        addComponentInstantiationListener(new SpringComponentInjector(this));

        mountBookmarkablePage("/activities", Index.class);
        mountBookmarkablePage("/signin", Signin.class);
        mountBookmarkablePage("/admin/users", ListUsers.class);

        getDebugSettings().setAjaxDebugModeEnabled(false);
        getMarkupSettings().setStripWicketTags(true);

        // Set the authentication strategy
        if (timeTrackrAuthorizationStrategy != null) {
            getSecuritySettings().setAuthorizationStrategy(timeTrackrAuthorizationStrategy);
            getSecuritySettings().setUnauthorizedComponentInstantiationListener(this);
        }
    }

    public Class getHomePage() {
        return Index.class;
    }


    public Session newSession(Request request, Response response) {
        return new TimeTrackrSession(request);
    }

    /**
     * When an unauthorized page is encountered, redirect to the signin page.
     *
     * @param component
     */
    public void onUnauthorizedInstantiation(final Component component) {
        if (component instanceof Page && !(component instanceof Signin)) {
            throw new RestartResponseAtInterceptPageException(Signin.class);
        } else {
            throw new UnauthorizedInstantiationException(component.getClass());
        }
    }


    public TimeTrackrAuthorizationStrategy getTimeTrackrAuthorizationStrategy() {
        return timeTrackrAuthorizationStrategy;
    }

    public void setTimeTrackrAuthorizationStrategy(TimeTrackrAuthorizationStrategy timeTrackrAuthorizationStrategy) {
        this.timeTrackrAuthorizationStrategy = timeTrackrAuthorizationStrategy;
    }

}