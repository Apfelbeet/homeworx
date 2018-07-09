package logic;

import java.util.Calendar;
import java.util.Date;
/**
 * Die Klasse Homework als Unterklasse zur Klasse Task beinhaltet alle Informationen einer Hausaufgabe.
 *
 * @version 1.1
 */
public class Reminder extends Task {
    /**
     * Konstruktor, der den Konstruktor der Oberklasse Task durch das Schlüsselwort super aufruft
     * @param id: Primärschlüssel zur direkten Identifikation des Objektes
     * @param description: Beschreibung der Erinnerung
     * @param deadline: Datum, bis zu dem die Erinnerung zu erledigen ist
     * @param priority: Beschreibt die Wichtigkeit der Erinnerung
     */
    public Reminder(int id, String description, Calendar deadline, int priority) {
        super(id, description, deadline, priority);
    }
    /**
     * Konstruktor, der den Konstruktor der Oberklasse Task durch das Schlüsselwort super aufruft.
     * Anstelle einer direkten Wertzuweisung durch den Benutzer, wird der Primärschlüssel automatisch erstellt.
     * @param description: Beschreibung der Erinnerung
     * @param deadline: Datum, bis zu dem die Erinnerung zu erledigen ist
     * @param priority: Beschreibt die Wichtigkeit der Erinnerung
     */
    public Reminder(String description, Calendar deadline, int priority) {
        super(description, deadline, priority);
    }

}
