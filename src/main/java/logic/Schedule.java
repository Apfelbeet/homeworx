package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class Schedule {

    Map<Day, SchoolDay> days;
    ArrayList<Reminder> reminders;
    ArrayList<Subject> subjects;



    public Schedule(int dayLength, boolean weekendSchool){
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

    public Date getNextLessonTime(Subject l){
        throw new NotImplementedException();
    }

    public ArrayList<Lesson> getLessonsOfDay(Day day){
        throw new NotImplementedException();
    }

    public void addReminder(Reminder r){
        reminders.add(r);
    }

    public void removeReminder(Reminder r){
        reminders.remove(r);
    }
}
