
import logic.*;

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
        Grade grade2 = new Grade(4, GradeType.Ex);
        subject1.addNewGrade(grade1);
        subject1.addNewGrade(grade2);
        s.getDays().get(Day.Monday).setLesson(0, lesson1);
        s.getDays().get(Day.Monday).setLesson(1, lesson2);
        s.getDays().get(Day.Tuesday).setLesson(0, lesson3);
        s.getDays().get(Day.Tuesday).setLesson(1, lesson4);

        for (Lesson l : s.getLessonsOfDay(Day.Monday)) {
            if(l != null) System.out.println(l.getSubject().getName());
        }
        System.out.println(s.getNextLessonTime(subject1));
        System.out.println(s.getDayFromLesson(lesson1));
        System.out.println(subject1.calculateAverage());

    }
}
