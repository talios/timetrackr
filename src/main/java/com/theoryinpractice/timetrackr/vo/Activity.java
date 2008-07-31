/*
 * Created by IntelliJ IDEA.
 * User: amrk
 * Date: 3/02/2006
 * Time: 21:38:22
 */
package com.theoryinpractice.timetrackr.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activity")
public class Activity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;
    private Integer userId;
    private String name;
    private String description;


    @OneToMany(targetEntity = WorkItem.class, mappedBy = "activityId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("start desc")
    private Set<WorkItem> workItems = new HashSet<WorkItem>();

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(Set<WorkItem> workItems) {
        this.workItems = workItems;
    }

}