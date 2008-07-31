/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 5/02/2006
 * Time: 13:39:46
 */
package com.theoryinpractice.timetrackr.forms;

import com.theoryinpractice.timetrackr.data.ActivityManager;
import com.theoryinpractice.timetrackr.pages.Index;
import com.theoryinpractice.timetrackr.vo.Activity;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class AddActivyForm extends Form {

    @SpringBean
    ActivityManager activityManager;

    private Activity activity;

    public AddActivyForm(String string, Activity activity) {
        super( string);
        this.activity = activity;

        add(new TextField("name", new PropertyModel(activity, "name")));
        add(new TextArea("description", new PropertyModel(activity, "description")));
    }

    protected void onSubmit() {
        activityManager.saveActivity(activity);

        Index resultPage = new Index(null);
        resultPage.info("Added '" + activity.getName() + "'");
        setResponsePage(resultPage);
    }
}