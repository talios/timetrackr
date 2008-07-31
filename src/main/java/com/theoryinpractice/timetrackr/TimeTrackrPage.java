package com.theoryinpractice.timetrackr;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.PageParameters;

/**
 * Base class for all pages in the QuickStart application. Any page which
 * subclasses this page can get session properties from TimeTrackrSession via
 * getQuickStartSession().
 */
public abstract class TimeTrackrPage extends WebPage {

    public TimeTrackrPage() {
        init();
    }

    public TimeTrackrPage(PageParameters pageParameters) {
        super(pageParameters);
        init();
    }

    private void init() {
        add(HeaderContributor.forCss(TimeTrackrPage.class, "main.css"));
    }

    /**
     * Get downcast session object for easy access by subclasses
     *
     * @return The session
     */
    public TimeTrackrSession getQuickStartSession() {
        return (TimeTrackrSession) getSession();
    }

    public TimeTrackrApplication getQuickStartApplication() {
        return (TimeTrackrApplication) getApplication();
    }

}