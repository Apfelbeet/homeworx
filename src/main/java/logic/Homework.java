package logic;


import java.util.Calendar;
import java.util.Date;

/**
 * Saves all information of a homework
 *
 * @version 1.1
 */
// Homework als Unterklasse zu Task; Homework ist nur eine mögliche Task
public class Homework extends Task{
    /**
     * Konstruktor der den Konstruktor der Oberklasse Task durch das Schlüsselwort super aufruft.
     * @param id: Primärschlüssel zur direkten Identifikation des Objektes.
     * @param description: Beschreibung der zu erledigenden Hausaufgabe.
     * @param deadline: Datum bis zu dem die Hausaufgabe zu erledigen ist.
     * @param priority: Beschreibt die Wichtigkeit der Hausaufgabe.
     */
    public Homework(int id, String description, Calendar deadline, int priority) {
        super(id, description, deadline, priority);
    }
    /**
     * Konstruktor der den Konstruktor der Oberklasse Task durch das Schlüsselwort super aufruft.
     * Anstelle einer direkten Wertzuweisung durch den Benutzer, wird der Primärschlüssel automatisch erstellt.
     * @param description: Beschreibung der zu erledigenden Hausaufgabe.
     * @param deadline: Datum bis zu dem die Hausaufgabe zu erledigen ist.
     * @param priority: Beschreibt die Wichtigkeit der Hausaufgabe.
     */
    public Homework(String description, Calendar deadline, int priority) {
        super(description, deadline, priority);
    }

}
