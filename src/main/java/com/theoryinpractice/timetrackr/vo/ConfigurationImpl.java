/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 7/02/2006
 * Time: 18:42:40
 */
package com.theoryinpractice.timetrackr.vo;

public class ConfigurationImpl implements Configuration {
    private String smtpHost;
    private String smtpUser;
    private String smtpPassword;
    private String adminPersonalName;
    private String adminEmailAddress;

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public void setSmtpUser(String smtpUser) {
        this.smtpUser = smtpUser;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public String getAdminPersonalName() {
        return adminPersonalName;
    }

    public void setAdminPersonalName(String adminPersonalName) {
        this.adminPersonalName = adminPersonalName;
    }

    public String getAdminEmailAddress() {
        return adminEmailAddress;
    }

    public void setAdminEmailAddress(String adminEmailAddress) {
        this.adminEmailAddress = adminEmailAddress;
    }
}