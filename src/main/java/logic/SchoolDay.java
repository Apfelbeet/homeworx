package logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

/**
 *
 */
public class SchoolDay {
    Lesson[] lessons;

    public SchoolDay(int length){
        lessons = new Lesson[length];
    }

    public Lesson getLesson(int index){
        return lessons[index];
    }

    public void setLessons(Lesson[] lessons) {
        this.lessons = lessons;
    }

    public void setLesson(int index, Lesson l){
        this.lessons[index] = l;
    }

    public int getLessonLength(int index){
        return this.lessons[index].getLength();
    }

    public void setLessonLength(int index, int length){
        this.lessons[index].setLength(length);
    }

    public int getLessonindex(Lesson l){
        throw new NotImplementedException();
    }


}
