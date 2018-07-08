package logic;

/**
 *
 */
public class SchoolDay {
    /**
     * Referenz auf die einzelnen am Tag gehaltenen Unterrichtsstunden
     */
    Lesson[] lessons;
    /**
     * Konstruktor zum Erstellen eines Schultages
     * Anstelle einer direkten Wertzuweisung durch den Benutzer, wird der Primärschlüssel automatisch erstellt.
     * @param length: Festlegen der Anzahl der Unterrichtsstunden am jeweiligen Tag
     *
     */
    public SchoolDay(int length){
        lessons = new Lesson[length];
    }

    public Lesson getLesson(int index){
        return lessons[index];
    }

    public void setLessons(Lesson[] lessons) {
        this.lessons = lessons;
    }

    public void setLesson(int index, Lesson l){
        this.lessons[index] = l;
    }

    public int getLessonLength(int index){
        return this.lessons[index].getLength();
    }

    public void setLessonLength(int index, int length){
        this.lessons[index].setLength(length);
    }

    public Lesson[] getLessons(){
        return lessons;
    }
    /**
     * Methode zum Herausfinden des Index' ("Position/Platzierung" im Array), um anschließend auf dieses zugreifen zu können
     * * @param description: Beschreibung der zu erledigenden Hausaufgabe
     * @param l: Referenz auf die Klasse Lesson
     * Mittels einer For-Schleife werden alle Elemente des Arrays lessons mit dem eingegebenen Parameter abgeglichen.
     * Entspricht das aktuell zu vergleichende Element dem eingegebenen Objekt, so wird i als Variable für die "Position/Platzierung" ausgegeben.
     * Sollten alle Elemente des Arrays vergeblich auf Gleichheit überprüft werden, so wird -1 zurückgegeben.
     * Durch diesen für ein Array-Index unmöglichen Wert wird das Nicht-Existieren des Objektes und damit seiner "Position/Platzierung" dargestellt.
     */
    public int getLessonIndex(Lesson l){
        for(int i = 0; i < lessons.length; i++) {
            if(lessons[i] == l) return i;
        }
        return -1;
    }


}
