/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 4/02/2006
 * Time: 21:43:43
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.TimeTrackrPage;
import com.theoryinpractice.timetrackr.TimeTrackrSession;
import com.theoryinpractice.timetrackr.data.UserManager;
import com.theoryinpractice.timetrackr.vo.User;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;

import javax.servlet.http.Cookie;

public class Signin extends TimeTrackrPage {

    @SpringBean
    private UserManager userManager;

    public Signin() {
        add(new LoginForm("login"));
        add(new BookmarkablePageLink("signUpButton", Signup.class));
    }

    public class LoginForm extends Form {
        private String username;
        private String password;

        public LoginForm(String id) {
            super(id);

            username = "";
            password = "";

            add(new TextField("username", new PropertyModel(this, "username")));
            add(new PasswordTextField("password", new PropertyModel(this, "password")));
        }

        protected void onSubmit() {
            User user = userManager.findByUserName(username);

            if (user.getPassword().equals(password)) {
                TimeTrackrSession.get().setUser(user);
                setResponsePage(Index.class);

                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge((int) Duration.ONE_WEEK.getMilliseconds());


                getWebRequestCycle().getWebResponse().getHttpServletResponse().addCookie(cookie);
            } else {
                setResponsePage(Signin.class);
            }
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}