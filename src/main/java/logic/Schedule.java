package logic;

import data.Data;
import data.DataManager;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;



public class Schedule {
    Map<Day, SchoolDay> days;
    ArrayList<Reminder> reminders;
    ArrayList<Subject> subjects;

    /**
     * Konstruktor (Einrichtungskonstruktor), der den Stundenplan das erste Mal zur Einrichtung erzeugt
     * @param dayLength: Attribut, dessen Wert beschreibt, wie lang der Tag ist
     * @param weekendSchool: Booleansches Attribut, das definiert, ob auch Unterrichtsstunden am Wochenende sind und der Stundenplan somit erweitert werden muss
     * Days ist eine Hashmap, also wird jedem Day (Schlüssel) ein Schoolday (Datensatz) zugeordnet.
     * Dann wird in einer For-Schleife jeder Tag durchgegangen und dann in die Hashmap eingefügt und einem Schultag (mit Eingabeparameter dayLength) zugewiesen.
     * Wenn der aktuelle Tag ein Wochenendstag ist und weekendSchool false ist, dann wird die For-Schleife abgebrochen, damit die Wochenendstage nicht in den Stundenplan mit
     * eingefügt werden.
     */
    public Schedule(int dayLength, boolean weekendSchool){
        days = new HashMap<Day, SchoolDay>();
        for(Day d : Day.values()){
            if(d == Day.Saturday && !weekendSchool)
                break;

            days.put(d, new SchoolDay(dayLength));
        }
    }

    /**
     * Konstruktor (Ladekonstruktor), der für jedes Starten des Programms nach der Einrichtung die persistenten Daten lädt.
     * @param data: Ein Datenpaket, das alle wichtigen Information enthält, die zum Aufsetzen des Stundenplans bei jeder neuen Anwendung benötigt und gespeichert werden müssen.
     * Alle gespeicherten SchoolDays werden durchgegangen und für jeden Tag werden die gespeicherten SchoolDays aufgerufen und jedem Tag entsprechend in der Hashmap zugefügt.
     * Im Folgenden Schritt wird die aktuelle Id aufgerufen, damit beim Neustart des Programms bereits vergebene Ids nicht erneut vergeben werden.
     * Die bereits gespeicherten Daten von subject und reminders werden abgerufen / zugewiesen, damit beim Neustart des Programms diese nicht erneut vergeben werden müssen.
     *
     */
    public Schedule(Data data) {
        days = new HashMap<Day, SchoolDay>();
        for(int i = 0; i < data.getSchoolDays().length; i++) {
            days.put(Day.values()[i], data.getSchoolDays()[i]);
        }
        IdManager.setId(data.getCurrentId());
        subjects = data.getSubjects();
        reminders = data.getReminder();

    }
    /**
     * Konstruktor (Nutzerkonstruktor), der vom Nutzer nach jedem Neustarten aufgerufen wird.
     * Dabei ruft er den Konstruktor den Ladekonstruktor auf und gibt ihm alle Informationen des DataManager weiter.
     */
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

    public int getHomeworkAmount(){
        int g = 0;
        for (int i = 0; i < subjects.size(); i++){
            g += subjects.get(i).getHomework().size();
        }
        return g;
    }

    public int getHomeworkAmountForDay(Day day) {
        int g = 0;
        for (int i = 0; i < days.get(day).getLessons().length; i++){
                g += days.get(day).getLessons()[i].getSubject().getHomework().size();
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

    public void removeLesson(Lesson lesson) {
    days.forEach((key, value) -> {
        for(int i = 0 ; i < value.getLessons().length; i++) {
            if(value.getLessons()[i] != null && lesson.getId() == value.getLessons()[i].getId())
                value.getLessons()[i] = null;
        }
    });
    }

    public void removeSubject(Subject subject) {
        days.forEach((key, value) -> {
            for(int i = 0 ; i < value.getLessons().length; i++) {
                if(value.getLessons()[i] != null && subject.equals(value.getLessons()[i].getSubject()))
                    value.getLessons()[i] = null;
            }
        });

        subjects.remove(subject);
    }
}
