package logic;

import java.util.Date;

public abstract class Task {

    private int id;
    private String description;
    private Date deadline;
    private int priority;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;

    public Task(int id, String description, Date deadline, int priority) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    public Task(String description, Date deadline, int priority) {
        this(IdManager.generateId(), description, deadline, priority);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
