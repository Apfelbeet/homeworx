package data;

import logic.Reminder;
import logic.SchoolDay;
import logic.Subject;

import java.util.ArrayList;

public class Data {
    private ArrayList<Subject> subjects;
    private SchoolDay[] schoolDays;
    private ArrayList<Reminder> reminder;
    private int currentId;

    public Data(ArrayList<Subject> subjects, SchoolDay[] schoolDays, ArrayList<Reminder> reminder, int currentId) {
        this.subjects = subjects;
        this.schoolDays = schoolDays;
        this.reminder = reminder;
        this.currentId = currentId;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public SchoolDay[] getSchoolDays() {
        return schoolDays;
    }

    public ArrayList<Reminder> getReminder() {
        return reminder;
    }

    public int getCurrentId() {
        return currentId;
    }
}
