/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 4/02/2006
 * Time: 18:07:23
 */
package com.theoryinpractice.timetrackr.vo;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer userId;
    private String username;
    private String password;
    private String personalName;
    private String emailAddress;

    @OneToMany(targetEntity = Activity.class, fetch = FetchType.EAGER, mappedBy = "userId", cascade = CascadeType.REMOVE)
    @OrderBy("name")
    private Set<Activity> activities;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

}