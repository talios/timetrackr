/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 11/06/2006
 * Time: 22:40:59
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.TimeTrackrPage;
import com.theoryinpractice.timetrackr.data.UserManager;
import com.theoryinpractice.timetrackr.vo.User;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class Signup extends TimeTrackrPage {

    @SpringBean
    private UserManager userManager;


    public Signup() {
        add(new SignupForm("signupForm"));
    }

    public class SignupForm extends Form {
        private String username;
        private String password;
        private String emailAddress;
        private String personalName;

        public SignupForm(String id) {
            super(id);

            username = "";
            password = "";
            emailAddress = "";
            personalName = "";

            add(new TextField( "username", new PropertyModel(this, "username")));
            add(new TextField("emailAddress", new PropertyModel(this, "emailAddress")));
            add(new TextField("personalName", new PropertyModel(this, "personalName")));
            add(new PasswordTextField("newpassword", new PropertyModel(this, "password")));
        }

        protected void onSubmit() {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmailAddress(emailAddress);
            user.setPersonalName(personalName);

            userManager.createUser(user);

            setResponsePage(Signin.class);
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

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getPersonalName() {
            return personalName;
        }

        public void setPersonalName(String personalName) {
            this.personalName = personalName;
        }
    }

}