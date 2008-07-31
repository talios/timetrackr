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
import java.util.*;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.markup.html.link.Link;

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
        Map root = new HashMap();
        root.put("reportForDate", reportForDate);
        root.put("user", user);
        root.put("formattedTimeFor", TimeFormat.format(userManager.calculateTimeForUser(user, Boolean.FALSE)));

        List activities = new ArrayList();
        for (Activity activity : user.getActivities()) {
            Long timeFor = activityManager.calculateTimeFor(activity, Boolean.FALSE);
            List<WorkItem> work = activityManager.findWorkItemsForActivity(activity, Boolean.FALSE);

            ActivityReport stuff = new ActivityReport(activity, timeFor, work);
            activities.add(stuff);
        }

        root.put("activities", activities);

        /*
        try {
            freemarker.template.Configuration freemarker = new freemarker.template.Configuration();
            freemarker.setClassForTemplateLoading(getClass(), "");
            freemarker.setObjectWrapper(new DefaultObjectWrapper());

            Template temp = freemarker.getTemplate("todaysTimeEmail.ftl");

            StringWriter sw = new StringWriter();
            temp.process(root, sw, new DefaultObjectWrapper());
            sw.flush();

            Properties props = new Properties();
            props.put("mail.smtp.host", configuration.getSmtpHost());
            props.put("mail.smtp.auth", "false");

            Session mailSession = Session.getInstance(props);
            MimeMessage message = new MimeMessage(mailSession);

            InternetAddress[] addressTo = new InternetAddress[]{new InternetAddress(user.getEmailAddress(), user.getPersonalName())};
            message.setRecipients(Message.RecipientType.TO, addressTo);
            InternetAddress from = new InternetAddress(configuration.getAdminEmailAddress(), configuration.getAdminPersonalName());
            message.setSender(from);
            message.setFrom(from);
            message.setSubject("Time Report for " + reportForDate);

            Multipart mixedMessage = new MimeMultipart("mixed");

            BodyPart bodyPartText = new MimeBodyPart();
            bodyPartText.setContent(sw.toString(), "text/html");

            mixedMessage.addBodyPart(bodyPartText);
            message.setContent(mixedMessage);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect();
            transport.send(message);

            Index resultPage = new Index(null);
            resultPage.getFeedbackMessages().info(resultPage, "Email Report Sent");
            setResponsePage(resultPage);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        */
    }

}