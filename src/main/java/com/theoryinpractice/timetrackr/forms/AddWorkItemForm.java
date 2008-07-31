/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 20/02/2006
 * Time: 21:59:00
 */
package com.theoryinpractice.timetrackr.forms;

import com.theoryinpractice.timetrackr.TimeTrackrSession;
import com.theoryinpractice.timetrackr.data.ActivityManager;
import com.theoryinpractice.timetrackr.pages.Index;
import com.theoryinpractice.timetrackr.vo.Activity;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class AddWorkItemForm extends Form {

    @SpringBean
    ActivityManager activityManager;

    private Activity activity;
    private String comment;

    public AddWorkItemForm(String string, Activity activity) {
        super(string);
        this.activity = activity;
        comment = "";

        final TextArea comment;
        add(comment = new TextArea("comment", new PropertyModel(this, "comment")));
        comment.add(new AttributeModifier("onKeyPress", true, new Model<String>() {
            public String getObject() {
                return "return submitWork(document.getElementById('" + AddWorkItemForm.this.getMarkupId() + "'), event);";
            }
        }));

        add(new SubmitLink("submitLink"));
    }

    protected void onSubmit() {
        TimeTrackrSession session = (TimeTrackrSession) getSession();

        activityManager.stopUserWorking(session.getUser());
        activityManager.startTimeOn(activity, comment);
        session.setCurrentActivityId(activity.getActivityId());
        session.refreshUser();
        setResponsePage(Index.class);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}