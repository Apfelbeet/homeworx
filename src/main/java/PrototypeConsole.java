import data.Data;
import data.DataManager;
import logic.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PrototypeConsole {
    private static Scanner scanner;
    private static Schedule schedule;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        System.out.println("PrototypeConsole started");
        scanner = new Scanner(System.in);
        schedule = new Schedule();
        while (command(scanner.nextLine())) {
        }
        System.out.println("PrototypeConsole terminated");
    }

    private static boolean command(String line) {
        String[] splitString = line.split(" ");
        if (splitString[0].equals("exit")) {
            return false;
        } else if (splitString.length == 2 && splitString[0].equals("reset") && splitString[1].equals("schedule")) {
            schedule = new Schedule();
            System.out.println("schedule reset");
        } else if (splitString.length == 2 && splitString[0].equals("reset") && splitString[1].equals("save")) {
            DataManager.reset();
            schedule = new Schedule();
            System.out.println("save reset");
        } else if (splitString.length > 0 && splitString[0].equals("add")) {
            if (splitString.length == 4 && splitString[1].equals("subject")) {
                Subject subject = new Subject(splitString[2], splitString[3]);
                schedule.getSubjects().add(subject);
                DataManager.saveSubjects(subject);
                System.out.println("subject added");
            } else if (splitString.length == 5 && splitString[1].equals("reminder")) {
                try {
                    GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTime(DATE_FORMAT.parse(splitString[3]));
                    Reminder reminder = new Reminder(splitString[2], calendar, Integer.parseInt(splitString[4]));
                    schedule.addReminder(reminder);
                    DataManager.saveReminder(reminder);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if(splitString.length == 6 && splitString[1].equals("homework")) {
                try {
                    GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTime(DATE_FORMAT.parse(splitString[4]));
                    Homework homework = new Homework(splitString[3], calendar, Integer.parseInt(splitString[5]));
                    searchSubject(splitString[2]).getHomework().add(homework);
                    DataManager.saveHomework(searchSubject(splitString[2]), homework);
                    System.out.println("homework added");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if(splitString.length == 6 && splitString[1].equals("lesson")) {
                Lesson lesson = new Lesson(Integer.parseInt(splitString[4]), searchSubject(splitString[5]));
                SchoolDay day = schedule.getDays().get(Day.valueOf(splitString[2]));
                day.setLesson(Integer.parseInt(splitString[3]), lesson);
                DataManager.saveLesson(lesson, Day.valueOf(splitString[2]), day);
                System.out.println("lesson added");
            } else if(splitString.length == 5 && splitString[1].equals("grade")) {
                Grade grade = new Grade(Integer.parseInt(splitString[3]), GradeType.valueOf(splitString[4]));
                searchSubject(splitString[2]).getGrades().add(grade);
                DataManager.saveGrade(searchSubject(splitString[2]), grade);
                System.out.println("grade added");
            }
        }else if(splitString.length > 0 && splitString[0].equals("get")) {
            if(splitString.length == 3 && splitString[1].equals("homework")) {
                Subject subject = searchSubject(splitString[2]);
                System.out.println("\t- " + subject.getName() + ":");
                for(Homework homework : subject.getHomework()) {
                    System.out.printf("\t\t%d: %s bis %s (%d)\n", homework.getId(), homework.getDescription(), DATE_FORMAT.format(homework.getDeadline().getTime()), homework.getPriority());
                }
            }else if(splitString.length == 2 && splitString[1].equals("reminders")) {
                for(Reminder reminder: schedule.getReminders()) {
                    System.out.printf("\t\t%d: %s bis %s (%d)\n", reminder.getId(), reminder.getDescription(), DATE_FORMAT.format(reminder.getDeadline().getTime()), reminder.getPriority());
                }
            }else if(splitString.length == 4 && splitString[1].equals("homework") && splitString[2].equals("day")) {
                SchoolDay day = schedule.getDays().get(Day.valueOf(splitString[3]));
                System.out.printf("\t- %s:\n", splitString[3]);
                for(Lesson lesson : day.getLessons()) {
                    if(lesson != null) {
                        Subject subject = lesson.getSubject();
                        System.out.println("\t- " + subject.getName() + ":");
                        for(Homework homework : subject.getHomework()) {
                            System.out.printf("\t\t%d: %s bis %s (%d)\n", homework.getId(), homework.getDescription(), DATE_FORMAT.format(homework.getDeadline().getTime()), homework.getPriority());
                        }
                    }
                }
            }
        }else if(splitString.length > 0 && splitString[0].equals("remove")){
            if(splitString.length == 3 && splitString[1].equals("subject")) {
                Subject subject = searchSubject(splitString[2]);
                schedule.getSubjects().remove(subject);
                schedule.getDays().forEach((day, schoolDay) -> {
                    for(int i = 0; i < schoolDay.getLessons().length; i++) {
                        if(schoolDay.getLessons()[i] != null && schoolDay.getLessons()[i].getSubject().getName().equals(subject.getName())) {
                            schoolDay.getLessons()[i] = null;
                        }
                    }
                });
                DataManager.deleteSubject(subject);
            }else if(splitString.length == 3 && splitString[1].equals("reminder")) {
                Reminder reminder = schedule.getReminders().stream().filter(r -> r.getId() == Integer.parseInt(splitString[2])).collect(Collectors.toList()).get(0);
                schedule.getReminders().remove(reminder);
                DataManager.deleteReminder(reminder);

            }else if(splitString.length == 4 && splitString[1].equals("grade")) {
                Subject subject = searchSubject(splitString[2]);
                Grade grade = subject.getGrades().stream().filter((g -> g.getId() == Integer.parseInt(splitString[3]))).collect(Collectors.toList()).get(0);
                subject.getGrades().remove(grade);
                DataManager.deleteGrade(subject, grade);
            }else if(splitString.length == 4 && splitString[1].equals("homework")) {
                Subject subject = searchSubject(splitString[2]);
                Homework homework = subject.getHomework().stream().filter((h -> h.getId() == Integer.parseInt(splitString[3]))).collect(Collectors.toList()).get(0);
                subject.getHomework().remove(homework);
                DataManager.deleteHomework(subject, homework);
            }else if(splitString.length == 3 && splitString[1].equals("lesson")) {
                schedule.getDays().forEach((day, schoolDay) -> {
                    for(int i = 0; i < schoolDay.getLessons().length; i++) {
                        if(schoolDay.getLessons()[i] != null && schoolDay.getLessons()[i].getId() == Integer.parseInt(splitString[2])) {
                            DataManager.deleteLesson(schoolDay.getLessons()[i]);
                            schoolDay.getLessons()[i] = null;
                        }
                    }
                });
            }
        }
        return true;
    }

    private static Subject searchSubject(String name) {
        for (Subject subject : schedule.getSubjects()) {
            if(name.equals(subject.getName())) {
                return subject;
            }
        }
        return null;
    }
}
