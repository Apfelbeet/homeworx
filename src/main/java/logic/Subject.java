package logic;

import java.util.ArrayList;

public class Subject {

    private ArrayList<Homework> homework;
    private ArrayList<Grade> grades;
    private String name;
    private String shortName;

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

    public float calculateAverage()
    {
        int oralTotal = 0, writtenTotal = 0, oralCount = 0, writtenCount = 0;

        for(Grade g: grades){
            switch (g.gradeType){
                case Schulaufgabe:
                    writtenTotal += g.value;
                    writtenCount += 1;
                    break;
                default:
                    oralTotal += g.value;
                    oralCount += 1;
            }

        }
        return ((writtenTotal/writtenCount)*2 + oralTotal/oralCount)/3;
    }
}
