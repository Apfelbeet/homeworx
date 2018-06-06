package logic;

import java.util.ArrayList;

/**
    Management of all subjects and reminder. Access to the logic part of the application

    @version 1.1
 */

public class Schedule {
    private ArrayList<Subject> subjects;
    private ArrayList<Reminder> reminders;

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setReminders(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
}
