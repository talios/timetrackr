/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 7/02/2006
 * Time: 23:31:50
 */
package com.theoryinpractice.timetrackr.pages;

import com.theoryinpractice.timetrackr.TimeTrackrSession;
import com.theoryinpractice.timetrackr.data.ActivityManager;
import com.theoryinpractice.timetrackr.data.UserManager;
import com.theoryinpractice.timetrackr.vo.Activity;
import com.theoryinpractice.timetrackr.vo.Configuration;
import com.theoryinpractice.timetrackr.vo.User;
import com.theoryinpractice.timetrackr.vo.WorkItem;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;
import java.util.*;

import com.google.gxp.base.GxpContext;
import com.theoryinpractice.timetrackr.gxp.WorkItemSummary;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.markup.html.link.Link;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;

public class EmailReportLink extends Link {

    @SpringBean
    ActivityManager activityManager;

    @SpringBean
    UserManager userManager;

    @SpringBean
    Configuration configuration;

    public EmailReportLink(String s) {
        super(s);
    }

    public void onClick() {
        // Refresh the user
        TimeTrackrSession session = (TimeTrackrSession) getSession();
        User user = session.refreshUser();

        String reportForDate = SimpleDateFormat.getDateTimeInstance().format(new Date());

        List activities = new ArrayList();
        for (Activity activity : user.getActivities()) {
            Long timeFor = activityManager.calculateTimeFor(activity, Boolean.FALSE);
            List<WorkItem> work = activityManager.findWorkItemsForActivity(activity, Boolean.FALSE);

            ActivityReport stuff = new ActivityReport(activity, timeFor, work);
            activities.add(stuff);
        }

        try {

            StringBuilder sb = new StringBuilder();
            WorkItemSummary.write(
                    sb, new GxpContext(Locale.getDefault()),
                    reportForDate,
                    TimeFormat.format(userManager.calculateTimeForUser(user, Boolean.FALSE)),
                    activities
            );

            boolean useAuth = configuration.getSmtpUser() != null && !"".equals(configuration.getSmtpUser());

            Properties props = new Properties();
            props.put("mail.smtp.host", configuration.getSmtpHost());
            props.put("mail.smtp.auth", Boolean.toString(useAuth));

            Session mailSession;
            if (useAuth) {
                mailSession = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(configuration.getSmtpUser(), configuration.getSmtpPassword());
                    }
                });
            } else {
                mailSession = Session.getInstance(props);
            }

            MimeMessage message = new MimeMessage(mailSession);

            InternetAddress[] addressTo = new InternetAddress[]{new InternetAddress(user.getEmailAddress(), user.getPersonalName())};
            message.setRecipients(Message.RecipientType.TO, addressTo);
            InternetAddress from = new InternetAddress(configuration.getAdminEmailAddress(), configuration.getAdminPersonalName());
            message.setSender(from);
            message.setFrom(from);
            message.setSubject("Time Report for " + reportForDate);

            Multipart mixedMessage = new MimeMultipart("mixed");

            BodyPart bodyPartText = new MimeBodyPart();
            bodyPartText.setContent(sb.toString(), "text/html");

            mixedMessage.addBodyPart(bodyPartText);
            message.setContent(mixedMessage);

            Transport transport = mailSession.getTransport("smtp");

            if (useAuth) {
                transport.connect(configuration.getSmtpUser(), configuration.getSmtpPassword());
            } else {
                transport.connect();
            }

            System.out.println(MessageFormat.format("Sending email from {0} to {1} via {2} with username {3} and password {4}",
                    configuration.getAdminEmailAddress(), user.getEmailAddress(), configuration.getSmtpHost(), configuration.getSmtpUser(), configuration.getSmtpPassword()
            ));

            transport.send(message);

            Index resultPage = new Index(null);
            resultPage.info("Email Report Sent");
            setResponsePage(resultPage);


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

}