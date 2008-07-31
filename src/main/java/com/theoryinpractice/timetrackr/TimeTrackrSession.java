/*
 * $Id: TimeTrackrSession.java,v 1.4 2005/03/24 21:10:55 jonathanlocke Exp $
 * $Revision: 1.4 $ $Date: 2005/03/24 21:10:55 $
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.theoryinpractice.timetrackr;

import com.theoryinpractice.timetrackr.data.UserManager;
import com.theoryinpractice.timetrackr.vo.User;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.Request;
import org.apache.wicket.Application;
import org.apache.wicket.Response;

/**
 * Subclass of WebSession for TimeTrackrApplication to allow easy and typesafe
 * access to session properties.
 *
 * @author Jonathan Locke
 */
public final class TimeTrackrSession extends WebSession {
    //  TODO Add any session properties here

    @SpringBean
    private UserManager userManager;

    private Integer currentActivityId;
    private User user;

    public static TimeTrackrSession get() {
        return (TimeTrackrSession) WebSession.get();
    }

    /**
     * Constructor
     *
     */
    protected TimeTrackrSession(Request request) {
        super(request);
        user = null;
        InjectorHolder.getInjector().inject(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCurrentActivityId() {
        return currentActivityId;
    }

    public void setCurrentActivityId(Integer currentActivityId) {
        this.currentActivityId = currentActivityId;
    }

    public User refreshUser() {
        User user = userManager.findByUserName(getUser().getUsername());
        setUser(user);
        return user;
    }

}
