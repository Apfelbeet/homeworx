
import logic.*;

public class testMain {

    public static void main(String[] args) {
        System.out.println("Hallo");
        Schedule s = new Schedule(5, false);
        s.getNextLessonTime(new Subject());
    }
}
