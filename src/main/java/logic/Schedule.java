package logic;

import data.Data;
import data.DataManager;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
/*ouefhfefuef

 *
 */


public class Schedule {
    Map<Day, SchoolDay> days;
    ArrayList<Reminder> reminders;
    ArrayList<Subject> subjects;



    public Schedule(int dayLength, boolean weekendSchool){
        days = new HashMap<Day, SchoolDay>();
        for(Day d : Day.values()){
            /*if(d == Day.Saturday)
                break;*/

            days.put(d, new SchoolDay(dayLength));
        }
    }

    public Schedule(Data data) {
        days = new HashMap<Day, SchoolDay>();
        for(int i = 0; i < data.getSchoolDays().length; i++) {
            days.put(Day.values()[i], data.getSchoolDays()[i]);
        }
        IdManager.setId(data.getCurrentId());
        subjects = data.getSubjects();
        reminders = data.getReminder();

    }

    public Schedule() {
        this(DataManager.readAll());
    }

    public void addLesson(int lessonHour, Lesson lesson, Day day){
        days.get(day).getLessons()[lessonHour] = lesson;
    }

    public Day getDayFromLesson(Lesson l){
        AtomicReference<Day> day = new AtomicReference<>();
        day.set(null);
        days.forEach((key, value) -> {
            if(value.getLessonIndex(l) != -1)
                day.set(key);
        });
        return day.get();
    }

    public GregorianCalendar getNextLessonTime(Subject s){
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int currentDay = Day.values()[dayOfWeek - 2].ordinal();
        for(int i = currentDay; i < 14; i++){
            for(Lesson lesson : days.get(Day.values()[i % 7]).getLessons()){
                if(lesson != null && lesson.getSubject() == s){
                    GregorianCalendar returnType = new GregorianCalendar();
                    returnType.add(Calendar.DAY_OF_MONTH, i -1);
                    //returnType.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK) + i + 2);
                    return returnType;
                }
            }
        }
        return null;
    }

    public Lesson[] getLessonsOfDay(Day day){
        return days.get(day).getLessons();
    }

    public void addReminder(Reminder r){
        reminders.add(r);
    }

    public void removeReminder(Reminder r){
        reminders.remove(r);
    }

    public ArrayList<Homework> getHomeworkFromSubject(Subject subject){
        return subjects.get(subjects.indexOf(subject)).getHomework();
    }


    public void addNewGrade(Subject subject, Grade grade) {
        int index = subjects.indexOf(subject);
        if(index != -1){
            subjects.get(index).addNewGrade(grade);
        }
    }

    public float calculateGradeAverage(){
        float g = 0;
        for (int i = 0; i < subjects.size(); i++) {
             g += subjects.get(i).calculateAverage();
        }
        float subjectsTotal = (float) subjects.size();
        return g/subjectsTotal;

    }

    public int getActiveHomework(){
        int g = 0;
        for (int i = 0; i < subjects.size(); i++){
            g += subjects.get(i).getHomework().size();
        }
        return g;
    }

    public Map<Day, SchoolDay> getDays() {
        return days;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
    }
}
