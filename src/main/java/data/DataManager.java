package data;

import logic.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class DataManager {
    private static final File SAVE_FILE = new File(ClassLoader.getSystemClassLoader().getResource("save.json").getFile());
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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
                scanner.close();
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
        SchoolDay[] schoolDays = readSchoolDays(subjects);
        int id = readId();
        return new Data(subjects, schoolDays, reminders, id);
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
                grades.add(new Grade(array.getJSONObject(i).getInt("id"), array.getJSONObject(i).getInt("value"), GradeType.valueOf(array.getJSONObject(i).getString("gradeType"))));
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
        ArrayList<Homework> homework = new ArrayList<>();
        try {
            JSONArray array = getSubject(subject).getJSONArray("homework");
            for(int i = 0; i < array.length(); i++) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(DATE_FORMAT.parse(array.getJSONObject(i).getString("date")));
                homework.add(new Homework(array.getJSONObject(i).getInt("id"), array.getJSONObject(i).getString("description"), calendar, array.getJSONObject(i).getInt("priority")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return homework;
    }

    private static ArrayList<Reminder> readReminders() {
        ArrayList<Reminder> reminder = new ArrayList<>();
        try {
            JSONArray array = getJsonData().getJSONArray("reminders");
            for(int i = 0; i < array.length(); i++) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(DATE_FORMAT.parse(array.getJSONObject(i).getString("date")));
                reminder.add(new Reminder(array.getJSONObject(i).getInt("id"), array.getJSONObject(i).getString("description"), calendar, array.getJSONObject(i).getInt("priority")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SchoolDay[] readSchoolDays(ArrayList<Subject> subjects) {
        SchoolDay[] days = new SchoolDay[7];
        for(int i = 0; i < days.length; i++) days[i] = new SchoolDay(11);
        try {
            JSONArray array = getJsonData().getJSONArray("lessons");
            for (int i = 0; i < array.length(); i++) {
                JSONObject tempObject = array.getJSONObject(i);
                days[Day.valueOf(tempObject.getString("day")).ordinal()].getLessons()[tempObject.getInt("hour")] = new Lesson(tempObject.getInt("id"), tempObject.getInt("length"), getSubjectFromId(tempObject.getString("subject"), subjects));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Subject getSubjectFromId(String name, ArrayList<Subject> subjects) {
        for(Subject subject: subjects) {
            if(subject.getName().equals(name)) return subject;
        }
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
        try {
            JSONObject jsonSubject = new JSONObject();
            jsonSubject.put("name", subject.getName());
            jsonSubject.put("shortName", subject.getShortName());
            //FIXME
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE));
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
            jsonObject = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
