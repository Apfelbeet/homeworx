package logic;

import java.util.ArrayList;

import static logic.GradeType.Schulaufgabe;

public class Subject {

    private ArrayList<Homework> homework;
    private ArrayList<Grade> grades;
    private String name;
    private String shortName;

    /**
     *
     * @param name: Name des Subjekts wird durch den Benutzer zugewiesen.
     * @param shortName: Kürzel des Subjekts wird durch den Benutzer zugewiesen.
     */
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

    /**
     * Zuerst werden die Attribute oralTotal(Summe der Notenwerte der mündlichen Noten), writtenTotal(Summe der Notenwerte der schriftlichen Noten), oralCount(Anzahl der mündlichen Noten),
     * writternCount(Anzahl der schriftlichen Noten) werden deklariert sowie initialisiert.
     * Mithilfe einer Forschleife werden alle Elemente der ArrayList grades durchlaufen.
     * Ist ihr Notentyp eine Schulaufgabe wird der Notenwert zu writtenTotal addiert und writtenCount wird um 1 erhöht.
     * Für alle anderen Fälle wird der Notenwert zu oralTotal addiert und oralCount um 1 erhöht.
     *
     * Nun werden die Attribute tempWritten(Durchschnitt der schriftlichen Noten), tempOral(Durchschnitt der mündlichen Noten) und divide(Der Nenner, der sich aus der Gewichtung der schriftlichen
     * und mündlichen Notenypen zusammensetzt) werden deklariert sowie initialisiert.
     * Dann wird überprüft ob die Anzahl der schriftlichen Noten nicht 0 oder kleiner ist, damit mathematische Fehler verhindert werden.
     * Wenn dies der Fall ist, kann der schriftliche Durchschnitt durch Division der Summe der Notenwerte und Anzahl der Noten berechnet werden.
     * Dann wird überprüft ob die Anzahl der mündlichen Noten nicht 0 oder kleiner ist, damit mathematische Fehler verhindert werden.
     * Wenn dies der Fall ist, kann der mündlichen Durchschnitt durch Division der Summe der Notenwerte und Anzahl der Noten berechnet werden.
     * Der Nenner wird entsprechend der Gewichtung der Noten erhöht.
     *
     * Im letzten Schritt wird erneut überprüft ob der Nenner gleich 0 ist. Wenn nicht wird der Durchschnitt der mündlichen und schriftlichen Noten durch den Nenner geteilt.
     * @return Der berechnete Notenschnitt des Fachs wird als Wert mit Nachkommastellen zurückgegeben.
     */
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
