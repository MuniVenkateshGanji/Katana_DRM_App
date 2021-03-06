package com.jntucep.c19_delhi.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by ishita on 11/3/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskItem {
    String assignee_id;
    //    Object comments;
    String creator_id;
    String description;
    String due_date;
    String assignee_ref;

    public TaskItem() {
    }

    public TaskItem(String assignee_id, /*Object comments, */String creator_id, String description, String due_date, String assignee_ref) {
        this.assignee_id = assignee_id;
//        this.comments = comments;
        this.description = description;
        this.creator_id = creator_id;
        this.due_date = due_date;
        this.assignee_ref = assignee_ref;
    }

    public String getAssignee_id() {
        return assignee_id;
    }

    public String getDescription() {
        return description;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getAssignee_ref() {
        return assignee_ref;
    }
}
