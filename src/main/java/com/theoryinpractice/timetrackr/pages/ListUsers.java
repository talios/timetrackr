/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 13/06/2006
 * Time: 21:50:07
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.TimeTrackrPage;
import com.theoryinpractice.timetrackr.data.ActivityManager;
import com.theoryinpractice.timetrackr.data.UserManager;
import com.theoryinpractice.timetrackr.vo.User;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;

public class ListUsers extends TimeTrackrPage {

    @SpringBean
    private UserManager userManager;

    @SpringBean
    private ActivityManager activityManager;

    public ListUsers() {
        add(new Link("logoutButton") {
          public void onClick() {
              activityManager.stopUserWorking(getQuickStartSession().getUser());
              getQuickStartSession().setUser(null);
              setResponsePage(Signin.class);
          }

        });
        Model<ArrayList<User>> model = new Model<ArrayList<User>>() {
            public ArrayList<User> getObject() {
                return userManager.findAllUsers();
            }
        };
        add(new ListView( "users", model) {
            protected void populateItem(ListItem listItem) {
                User user = (User) listItem.getModelObject();
                listItem.add(new Label("name", user.getUsername()));
                listItem.add(new Label("email", user.getEmailAddress()));
            }
        });

    }
}