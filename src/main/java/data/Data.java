package data;

import logic.Lesson;
import logic.Reminder;
import logic.SchoolDay;
import logic.Subject;

import java.util.ArrayList;

public class Data {
    private ArrayList<Subject> subjects;
    private ArrayList<Lesson> lessons;
    private ArrayList<Reminder> reminder;
    private int currentId;

    public Data(ArrayList<Subject> subjects, ArrayList<Lesson> lesson, ArrayList<Reminder> reminder, int currentId) {
        this.subjects = subjects;
        this.lessons = lesson;
        this.reminder = reminder;
        this.currentId = currentId;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public ArrayList<Reminder> getReminder() {
        return reminder;
    }

    public int getCurrentId() {
        return currentId;
    }
}
