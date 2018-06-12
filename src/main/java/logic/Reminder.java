package logic;

import java.util.Calendar;
import java.util.Date;

public class Reminder extends Task {
    public Reminder(int id, String description, Calendar deadline, int priority) {
        super(id, description, deadline, priority);
    }

    public Reminder(String description, Calendar deadline, int priority) {
        super(description, deadline, priority);
    }

}
