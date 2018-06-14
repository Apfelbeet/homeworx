package data;

import logic.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {
    private static final File SAVE_FILE = new File(ClassLoader.getSystemClassLoader().getResource("save.json").getFile());

    //private static final BlockingQueue<> queue = new LinkedBlockingDeque();
    private static JSONObject jsonObject = null;

    private static JSONObject getJsonData() {
        if (jsonObject == null) {
            String json = "";
            try {
                Scanner scanner = new Scanner(SAVE_FILE);
                while (scanner.hasNextLine()) {
                    json += scanner.nextLine();
                }
                jsonObject = new JSONObject(json);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static Data readAll() {
        ArrayList<Subject> subjects = readSubjects();
        subjects.forEach((subject) -> {
            readGrades(subject).forEach(subject::addNewGrade);
            readHomework(subject).forEach(homework -> subject.getHomework().add(homework));
        });
        ArrayList<Reminder> reminders = readReminders();
        ArrayList<Lesson> lessons = readLessons();
        int id = readId();
        return new Data(subjects, lessons, reminders, id);
    }

    private static int readId() {
        try {
            return getJsonData().getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static ArrayList<Subject> readSubjects() {
        return null;
    }

    private static ArrayList<Grade> readGrades(Subject subject) {
        return null;
    }

    private static ArrayList<Homework> readHomework(Subject subject) {
        return null;
    }

    private static ArrayList<Reminder> readReminders() {
        return null;
    }

    private static ArrayList<Lesson> readLessons() {
        return null;
    }


    public static void saveId() {
        try {
            getJsonData().put("id", IdManager.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveSubjects(Subject subject) {

    }

    public static void saveGrade(Subject subject, Grade grade) {

    }

    public static void saveHomework(Subject subject, Homework homework) {

    }

    public static void saveReminder(Reminder reminder) {

    }

    public static void saveLesson(Lesson lesson) {

    }

    public static void deleteSubject(Subject subject) {
        getJsonData().remove(subject.getName());
        write();
    }

    public static void deleteGrade(Subject subject, Grade grade) {

    }

    public static void deleteHomework(Subject subject, Homework homework) {

    }

    public static void deleteReminder(Reminder reminder) {

    }

    public static void deleteLesson(Lesson lesson) {

    }

    private static void write() {

    }


}
