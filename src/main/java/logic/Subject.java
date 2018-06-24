package logic;

import java.util.ArrayList;

import static logic.GradeType.Schulaufgabe;

public class Subject {

    private ArrayList<Homework> homework;
    private ArrayList<Grade> grades;
    private String name;
    private String shortName;

    public Subject(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
        grades = new ArrayList<>();
        homework = new ArrayList<>();
    }

    public ArrayList<Homework> getHomework() {
        return homework;
    }

    public void setHomework(ArrayList<Homework> homework) {
        this.homework = homework;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void addNewGrade(Grade grade) {
        grades.add(grade);
    }

    public float calculateAverage() {
        float oralTotal = 0, writtenTotal = 0, oralCount = 0, writtenCount = 0;

        for(Grade g: grades){
            switch (g.getGradeType()){
                case Schulaufgabe:
                    writtenTotal += g.getValue();
                    writtenCount += 1;
                    break;
                default:
                    oralTotal += g.getValue();
                    oralCount += 1;
            }

        }
        float tempWritten = 0, tempOral = 0;
        int divide = 0;
        if(writtenCount > 0) {
            tempWritten = (writtenTotal/writtenCount)*2;
            divide += 2;
        }
        if(oralCount > 0) {
            tempOral = oralTotal/oralCount;
            divide += 1;
        }

        return divide > 0 ? (tempOral+tempWritten)/divide : 0;
    }
}
