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


    public Schedule(int dayLength){

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
}
