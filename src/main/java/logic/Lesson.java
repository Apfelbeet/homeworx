package logic;

public class Lesson {
    private int length;
    private Subject subject;

    public Lesson(int length, Subject subject){
        this.length = length;
        this.subject = subject;
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
