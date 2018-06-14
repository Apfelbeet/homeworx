package logic;

public class Lesson {
    private int length;
    private Subject subject;
    private int id;

    public Lesson(int length, Subject subject){
        this(IdManager.generateId(), length, subject);
    }

    public Lesson(int id, int length, Subject subject) {
        this.id = id;
        this.length = length;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
