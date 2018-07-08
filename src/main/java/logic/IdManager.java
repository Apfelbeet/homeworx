package logic;

import data.DataManager;

/**
 * Die Klasse IdManager erzeugt automatisch eine id, die als Primärschlüssel zur genauen Identifikation der Objekte dient.
 *
 * @version 1.1
 */

public class IdManager {
    /**
     * Attribut, das den Wert der aktuellen, zuletzt vergebenen id beinhaltet
     */
    private static int Current_Id;

    static {
        Current_Id = 0;
    }
    /**
     * Methode, die beim Erstellen eines Objektes anderer Klassen aufgerufen wird und diesen eine id als Primärschlüssel zuweist.
     * Dazu wird der bisherige Wert des Attributes Current_Id um 1 erhöht und die Id somit nicht zufällig erstellt sondern schrittweise und mit System.
     * Anschließend wird dieser Wert vom DataManager gespeichert, um so zu vermeiden, dass eine Id beim nächsten Starten des Programmes erneut vergeben wird.
     * Zuletzt wird der nun neue Wert des Attributs ausgegeben und auf diese Weise dem Aufrufer weitergegeben.
     */
    public static int generateId() {
        Current_Id++;
        DataManager.saveId();
        return Current_Id;
    }

    public static void setId(int id) {
        Current_Id = id;
    }

    public static int getId() {
        return Current_Id;
    }
}