
import data.DataManager;
import logic.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class testMain {

    public static void main(String[] args) {
        Schedule s = new Schedule(5, false);
        Subject subject1 = new Subject("Deutsch", "D");
        Subject subject2 = new Subject("Mathe", "M");
        Lesson lesson1 = new Lesson(1, subject1);
        Lesson lesson2 = new Lesson(1, subject2);
        Lesson lesson3 = new Lesson(1, subject1);
        Lesson lesson4 = new Lesson(1, subject2);
        Grade grade1 = new Grade(1, GradeType.Ex);
        Grade grade2 = new Grade(1, GradeType.Ex);
        Grade grade3 = new Grade(2, GradeType.Schulaufgabe);
        subject1.addNewGrade(grade1);
        subject1.addNewGrade(grade2);
        subject1.addNewGrade(grade3);
        subject2.addNewGrade(grade1);
        subject2.addNewGrade(grade2);
        subject2.addNewGrade(grade3);
        s.getDays().get(Day.Monday).setLesson(0, lesson1);
        s.getDays().get(Day.Monday).setLesson(1, lesson2);
        s.getDays().get(Day.Friday).setLesson(0, lesson3);
        s.getDays().get(Day.Tuesday).setLesson(1, lesson4);

        DataManager.saveSubjects(subject1);
        DataManager.saveSubjects(subject2);
        DataManager.saveSubjects(new Subject("Deutsch", "Deu"));
        DataManager.saveGrade(subject1, grade1);
        DataManager.saveGrade(subject1, grade2);
        DataManager.saveGrade(subject1, new Grade(4, 4, GradeType.Kurztest));
        DataManager.saveHomework(subject1, new Homework("Schlafen", new GregorianCalendar(), 2));
        DataManager.saveReminder(new Reminder("Bennets Logo ist schlecht :P", new GregorianCalendar(), 10));
        DataManager.saveLesson(lesson1, s.getDayFromLesson(lesson1), s.getDays().get(s.getDayFromLesson(lesson1)));

        Schedule s2 = new Schedule();
        System.out.println(subject1.calculateAverage());

    }
}
