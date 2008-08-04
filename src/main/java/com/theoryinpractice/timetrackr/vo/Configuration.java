/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 23/02/2006
 * Time: 07:27:05
 */
package com.theoryinpractice.timetrackr.vo;

public interface Configuration {
    String getSmtpHost();

    void setSmtpHost(String smtpHost);

    String getSmtpUser();

    void setSmtpUser(String smtpuser);

    String getSmtpPassword();

    void setSmtpPassword(String smtpPassword);

    String getAdminPersonalName();

    void setAdminPersonalName(String adminPersonalName);

    String getAdminEmailAddress();

    void setAdminEmailAddress(String adminEmailAddress);
}