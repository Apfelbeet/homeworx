package data;

import logic.*;
import org.json.JSONArray;
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
        ArrayList<Subject> list = new ArrayList<>();
        try {

            JSONArray array = getJsonData().getJSONArray("subjects");
            for (int i = 0; i < array.length(); i++) {
                list.add(new Subject(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getString("shortName")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static ArrayList<Grade> readGrades(Subject subject) {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            JSONArray array = getSubject(subject).getJSONArray("grades");
            for (int i = 0; i < array.length(); i ++) {
                grades.add(new Grade(array.getJSONObject(i).getInt("value"), GradeType.valueOf(array.getJSONObject(i).getString("gradeType"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return grades;
    }

    private static JSONObject getSubject(Subject subject) {
        try {
            JSONArray jsonArray = getJsonData().getJSONArray("subjects");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("name").equals(subject.getName())) {
                    return jsonArray.getJSONObject(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
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
