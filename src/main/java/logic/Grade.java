package logic;

/**
 * Saves all information of a grade
 *
 * @version 1.1
 */


public class Grade {
    private int id;
    private int value;
    private int wage;
    private boolean exam;

    public Grade(int id, int value, int wage, boolean exam) {
        this.id = id;
        this.value = value;
        this.wage = wage;
        this.exam = exam;
    }

    public Grade(int value, int wage, boolean exam) {
        this(IdManager.generateId(), value, wage, exam);
    }

    public Grade(int value) {
        this(value, 1, false);
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public int getWage() {
        return wage;
    }

    public boolean isExam() {
        return exam;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExam(boolean exam) {
        this.exam = exam;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }
}
