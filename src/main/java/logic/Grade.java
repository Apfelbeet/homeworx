/**
 * Class Grade creats single grades.
 */
package logic;

public class Grade {
    private int id;
    private int value;
    private GradeType gradeType;


    public Grade(int id, int value, GradeType gradeType) {
        this.id = id;
        this.value = value;
        this.gradeType = gradeType;
    }

    public Grade(int value, GradeType gradeType) {
        this(IdManager.generateId(), value, gradeType);
    }

    public int getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public GradeType getGradeType() {
        return gradeType;
    }
}


