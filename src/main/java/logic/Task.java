package logic;

import java.util.Calendar;


public abstract class Task {

    private int id;
    private String description;
    private Calendar deadline;
    private int priority;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    public Task(int id, String description, Calendar deadline, int priority) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

   public Task(String description, Calendar deadline, int priority) {
        this(IdManager.generateId(), description, deadline, priority);
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
