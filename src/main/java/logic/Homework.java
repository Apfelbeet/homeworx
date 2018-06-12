package logic;


import java.util.Calendar;
import java.util.Date;

/**
 * Saves all information of a homework
 *
 * @version 1.1
 */

public class Homework extends Task{

    public Homework(int id, String description, Calendar deadline, int priority) {
        super(id, description, deadline, priority);
    }

    public Homework(String description, Calendar deadline, int priority) {
        super(description, deadline, priority);
    }

}
