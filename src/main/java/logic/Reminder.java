package logic;

import java.util.Date;

public class Reminder extends Task{
    public Reminder(int id, String description, Date deadline, int priority) {
        super(id, description, deadline, priority);
    }

    public Reminder(String description, Date deadline, int priority) {
        super(description, deadline, priority);
    }
}
