package logic;

public class Lesson {
    private int length;
    private Subject subject;
    private int id;


    /**
     * Konstruktor der beim Erzeugen einer Unterrichtseinheit aufgerufen wird
     * @param id: Primärschlüssel zur direkten Identifikation des Objektes
     * @param length: Länge der Unterrichtseinheit
     * @param subject: Referenz auf das in der Stunde unterrichtete Fach
     */
    public Lesson(int id, int length, Subject subject) {
        this.id = id;
        this.length = length;
        this.subject = subject;
    }
    /**
     * Konstruktor der beim Erzeugen einer Unterrichtseinheit aufgerufen wird
     * Anstelle einer direkten Wertzuweisung durch den Benutzer, wird der Primärschlüssel automatisch durch den IdManager erstellt.
     * @param length: Länge der Unterrichtseinheit
     * @param subject: Referenz auf die Klasse Subjekt, in dem die Hausaufgabe aufgegeben worden ist
     */
    public Lesson(int length, Subject subject){
        this(IdManager.generateId(), length, subject);
    }

    public int getId() {
        return id;
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

    public String toString(){
        return getSubject().getName();
    }
}
