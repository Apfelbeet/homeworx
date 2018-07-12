package logic;

import java.util.Calendar;

/**
 * Die Klasse Task dient als Oberklasse und somit als Generalisierung der spezialisierten Unterklassen Reminder und Homework.
 */
public abstract class Task {

    private int id;
    private String description;
    private Calendar deadline;
    private int priority;

    /**
     * Konstruktor der Klasse zur Erzeugung von Objekten und einer ersten Wertzuweisung von deren Attributen
     * @param id: Primärschlüssel zur direkten Identifikation des Objektes
     * @param description: Beschreibung der zu erledigenden Aufgabe
     * @param deadline: Datum, bis zu dem die Aufgabe zu erledigen ist
     * @param priority: Wichtigkeit der zu erledigenden Aufgabe
     */
    public Task(int id, String description, Calendar deadline, int priority) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }
    /**
     * Zweiter Konstruktor: Anstelle einer direkten Wertzuweisung durch den Benutzer, wird der Primärschlüssel automatisch durch den IdManager erstellt
     * @param description: Beschreibung der zu erledigenden Aufgabe
     * @param deadline: Datum, bis zu dem die Aufgabe zu erledigen ist
     * @param priority: Wichtigkeit der zu erledigenden Aufgabe
     */
    public Task(String description, Calendar deadline, int priority) {
        this(IdManager.generateId(), description, deadline, priority);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }



    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
