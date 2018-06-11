package logic;

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

    public Lesson[] getLessons(){
        return lessons;
    }

    public int getLessonIndex(Lesson l){
        for(int i = 0; i < lessons.length; i++) {
            if(lessons[i] == l) return i;
        }
        return -1;
    }


}
