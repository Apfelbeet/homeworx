package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
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
            if(d == Day.Saturday)
                break;

            days.put(d, new SchoolDay(dayLength));
        }
    }

    public void addLesson(int lessonHour, Lesson lesson, Day day){
        throw new NotImplementedException();
    }

    public Day getDayFromLesson(Lesson l){
        throw new NotImplementedException();
    }

    public GregorianCalendar getNextLessonTime(Subject s){
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int currentDay = Day.values()[dayOfWeek - 2].ordinal();
        for(int i = currentDay; i < 14; i++){
            for(Lesson lesson : days.get(Day.values()[currentDay]).getLessons()){
                if(lesson.getSubject() == s){
                    GregorianCalendar returnType = new GregorianCalendar();
                    returnType.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK + i + 2));
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
}
