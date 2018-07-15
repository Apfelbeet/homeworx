/**
 * Class Grade creats single grades.
 */
package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grade {
    public static final List<Integer> VALUES = Arrays.asList(1, 2, 3, 4, 5, 6);

    private int id;
    private int value;
    private GradeType gradeType;

    /**
     * Konstruktor der Klasse zur Erzeugung von Objekten und einer ersten Wertzuweisung von deren Attributen
     * @param id: Primärschlüssel zur direkten Identifikation des Objektes
     * @param value: Wert der eingetragenen Note
     * @param gradeType: Art der Note vom Datentyp GradeType (Klasse in der die Enumeration der Notenarten erzeugt wird)
     */

    public Grade(int id, int value, GradeType gradeType) {
        this.id = id;
        this.value = value;
        this.gradeType = gradeType;
    }

    /**
     * Zweiter Konstruktor: Anstelle einer direkten Wertzuweisung durch den Benutzer, wird der Primärschlüssel automatisch durch den IdManager erstellt
     * @param value: Wert der eingetragenen Note
     * @param gradeType: Art der Note vom Datentyp GradeType (Klasse in der die Enumeration der Notenarten erzeugt wird)
     */

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

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(int value) {
        this.value = value;
    }

}



