/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 5/02/2006
 * Time: 10:47:59
 */
package com.theoryinpractice.timetrackr.components;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;

public class RoundButton extends WebComponent {

    String caption;

    public RoundButton(final String id, String caption) {
        super(id);
        this.caption = caption;
    }


    protected void onComponentTagBody(MarkupStream markupStream, ComponentTag componentTag) {

        String message = "<div class=\"blgrey\"><div class=\"brgrey\"><div class=\"tlgrey\"><div class=\"trgreythin\">"
                + caption
                + "</div></div></div></div><div class=\"clear\">&nbsp;</div>";

        replaceComponentTagBody(markupStream, componentTag, message);
    }


}