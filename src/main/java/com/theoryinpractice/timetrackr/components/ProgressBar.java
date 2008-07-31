/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 15/11/2005
 * Time: 20:55:53
 */
package com.theoryinpractice.timetrackr.components;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;

public class ProgressBar extends WebComponent {
    private Float progress = Float.valueOf(0.0f);

    public ProgressBar(String id, Float progress) {
        super(id);
        this.progress = progress;
    }

    protected void onComponentTagBody(MarkupStream markupStream, ComponentTag componentTag) {

        String colour = progress < 100 ? "red" : "green";

        String progressBar = "<div style=\"width: 100%; background-color: silver; display: block;\">\n" +
                "<div style=\"width: " + progress.toString() + "%; background-color: " + colour + "; display: block;\">&nbsp;</div>\n" +
                "</div>";

        replaceComponentTagBody(markupStream, componentTag, progressBar);
    }

}