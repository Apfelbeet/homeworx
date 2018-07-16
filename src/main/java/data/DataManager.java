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
    private static final File SAVE_FILE = new File(/*ClassLoader.getSystemClassLoader().getResource("save.json").getFile()  */ System.getProperty("user.dir") + "\\out\\production\\classes\\save.json" /*ClassLoader.getSystemClassLoader().getResource("save.json").getFile()*/);
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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
        return 0;
    }

    private static ArrayList<Subject> readSubjects() {
        ArrayList<Subject> list = new ArrayList<>();
        try {
            JSONArray array = getJsonData().getJSONArray("subjects");
            for (int i = 0; i < array.length(); i++) {
                list.add(new Subject(Integer.parseInt(array.getJSONObject(i).getString("id")), array.getJSONObject(i).getString("name"), array.getJSONObject(i).getString("shortName")));
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
            for (int i = 0; i < array.length(); i++) {
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
                if (jsonArray.getJSONObject(i).getInt("id") == subject.getId()) {
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
            for (int i = 0; i < array.length(); i++) {
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
            for (int i = 0; i < array.length(); i++) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(DATE_FORMAT.parse(array.getJSONObject(i).getString("date")));
                reminder.add(new Reminder(array.getJSONObject(i).getInt("id"), array.getJSONObject(i).getString("description"), calendar, array.getJSONObject(i).getInt("priority")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reminder;
    }

    private static SchoolDay[] readSchoolDays(ArrayList<Subject> subjects) {
        SchoolDay[] days = new SchoolDay[7];
        for (int i = 0; i < days.length; i++) days[i] = new SchoolDay(11);
        try {
            JSONArray array = getJsonData().getJSONArray("lessons");
            for (int i = 0; i < array.length(); i++) {
                JSONObject tempObject = array.getJSONObject(i);
                days[Day.valueOf(tempObject.getString("day")).ordinal()].getLessons()[tempObject.getInt("hour")] = new Lesson(tempObject.getInt("id"), tempObject.getInt("length"), getSubjectFromId(tempObject.getInt("subject"), subjects));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return days;
    }

    private static Subject getSubjectFromId(int id, ArrayList<Subject> subjects) {
        for (Subject subject : subjects) {
            if (subject.getId() == id) return subject;
        }
        return null;
    }

    public static void saveId() {
        try {
            jsonObject = getJsonData().put("id", IdManager.getId());
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void saveJSONSubjectInSubjects(JSONObject subject, int index) {
        try {
            if (index > -1)
                jsonObject = getJsonData().put("subjects", getJsonData().getJSONArray("subjects").put(index, subject));
            else
                jsonObject = getJsonData().put("subjects", getJsonData().getJSONArray("subjects").put(subject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveSubjects(Subject subject) {
        try {
            int i = indexOfSubjectWithName(subject);
            JSONObject subjectJSON;
            if (i > -1) {
                subjectJSON = getJsonData().getJSONArray("subjects").getJSONObject(i);
            } else {
                subjectJSON = new JSONObject()
                        .put("grades", new JSONArray())
                        .put("homework", new JSONArray());
            }
            subjectJSON
                    .put("name", subject.getName())
                    .put("shortName", subject.getShortName())
                    .put("id", subject.getId());

            saveJSONSubjectInSubjects(subjectJSON, i);
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveGrade(Subject subject, Grade grade) {
        try {
            JSONObject subjectJSON = getSubject(subject);
            JSONObject gradeJSON = new JSONObject().
                    put("id", grade.getId()).
                    put("value", grade.getValue()).
                    put("gradeType", grade.getGradeType().toString());
            JSONArray gradesJSON = subjectJSON.getJSONArray("grades");
            int gradeIndex = indexOfJSONArrayWithId(gradesJSON, grade.getId());
            if (gradeIndex > -1)
                gradesJSON.put(gradeIndex, gradeJSON);
            else
                gradesJSON.put(gradeJSON);
            subjectJSON.put("grades", gradesJSON);
            saveJSONSubjectInSubjects(subjectJSON, indexOfSubjectWithName(subject));
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveHomework(Subject subject, Homework homework) {
        try {
            JSONObject subjectJSON = getSubject(subject);
            JSONObject homeworkObjectJSON = new JSONObject()
                    .put("id", homework.getId())
                    .put("description", homework.getDescription())
                    .put("date", DATE_FORMAT.format(homework.getDeadline().getTime()))
                    .put("priority", homework.getPriority());
            JSONArray homeworkArrayJSON = subjectJSON.getJSONArray("homework");
            int homeworkIndex = indexOfJSONArrayWithId(homeworkArrayJSON, homework.getId());
            if (homeworkIndex > -1)
                homeworkArrayJSON.put(homeworkIndex, homeworkObjectJSON);
            else
                homeworkArrayJSON.put(homeworkObjectJSON);
            subjectJSON.put("homework", homeworkArrayJSON);
            saveJSONSubjectInSubjects(subjectJSON, indexOfSubjectWithName(subject));
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveReminder(Reminder reminder) {
        try {
            JSONArray reminders = getJsonData().getJSONArray("reminders");
            int i = indexOfJSONArrayWithId(reminders, reminder.getId());
            JSONObject reminderJSON = new JSONObject()
                    .put("id", reminder.getId())
                    .put("description", reminder.getDescription())
                    .put("date", DATE_FORMAT.format(reminder.getDeadline().getTime()))
                    .put("priority", reminder.getPriority());
            if (i > -1)
                reminders.put(i, reminderJSON);
            else
                reminders.put(reminderJSON);
            jsonObject = getJsonData().put("reminders", reminders);
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveLesson(Lesson lesson, Day day, SchoolDay schoolDay) {
        try {
            JSONArray lessons = getJsonData().getJSONArray("lessons");
            int i = indexOfJSONArrayWithId(lessons, lesson.getId());
            JSONObject lessonJSON = new JSONObject()
                    .put("id", lesson.getId())
                    .put("length", lesson.getLength())
                    .put("subject", lesson.getSubject().getId())
                    .put("day", day.name())
                    .put("hour", schoolDay.getLessonIndex(lesson));
            if (i > -1)
                lessons.put(i, lessonJSON);
            else
                lessons.put(lessonJSON);
            jsonObject = getJsonData().put("lessons", lessons);
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static int indexOfJSONArrayWithId(JSONArray array, int id) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).getInt("id") == id)
                    return i;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static int indexOfSubjectWithName(Subject subject) {
        try {
            JSONArray array = getJsonData().getJSONArray("subjects");
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).getInt("id") == subject.getId()) {
                    return i;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;

    }

    private static JSONArray remove(JSONArray array, int blackIndex) {
        try {
            JSONArray newList = new JSONArray();
            for (int i = 0; i < array.length(); i++)
                if (i != blackIndex) {
                    newList.put(array.getJSONObject(i));
                }
            array = newList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void deleteSubject(Subject subject) {
        try {
            JSONArray subjects = getJsonData().getJSONArray("subjects");
            jsonObject = getJsonData().put("subjects", remove(subjects, indexOfSubjectWithName(subject)));
            deleteLessons(subject);
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void deleteGrade(Subject subject, Grade grade) {
        try {
            JSONObject subjectJSON = getSubject(subject);
            JSONArray grades = subjectJSON.getJSONArray("grades");
            saveJSONSubjectInSubjects(subjectJSON.put("grades", remove(grades, indexOfJSONArrayWithId(grades, grade.getId()))), indexOfSubjectWithName(subject));
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteHomework(Subject subject, Homework homework) {
        try {
            JSONObject subjectJSON = getSubject(subject);
            JSONArray homeworkArray = subjectJSON.getJSONArray("homework");
            saveJSONSubjectInSubjects(subjectJSON.put("homework", remove(homeworkArray, indexOfJSONArrayWithId(homeworkArray, homework.getId()))), indexOfSubjectWithName(subject));
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteReminder(Reminder reminder) {
        try {
            JSONArray reminders = getJsonData().getJSONArray("reminders");
            jsonObject = getJsonData().put("reminders", remove(reminders, indexOfJSONArrayWithId(reminders, reminder.getId())));
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLesson(Lesson lesson) {
        try {
            JSONArray lessons = getJsonData().getJSONArray("lessons");
            jsonObject = getJsonData().put("lessons", remove(lessons, indexOfJSONArrayWithId(lessons, lesson.getId())));
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLessons(Subject subject) {
        try {
            JSONArray lessons = getJsonData().getJSONArray("lessons");
            //jsonObject = getJsonData().put("lessons", remove(lessons, indexOfJSONArrayWithId(lessons, lesson.getId())));
            for(int i = 0; i < lessons.length(); i++) {
                if(lessons.getJSONObject(i).getInt("subject") == subject.getId()) {
                    lessons = remove(lessons, i);
                }
            }
            jsonObject = getJsonData().put("lessons", lessons);
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void write() {
        try {
            System.out.println(jsonObject);
            BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE));
            if(jsonObject != null) {
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();
            }
            jsonObject = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reset() {
        try {
            jsonObject = new JSONObject("{\"subjects\": [],\"lessons\": [],\"reminders\": [],\"id\": 0}");
            write();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
