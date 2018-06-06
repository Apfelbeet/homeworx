package logic;

public class IdManager {

    private static int Current_Id;

    static {
        Current_Id = 0;
    }

    public static int generateId() {
        return Current_Id++;
    }

    public static void setId(int id) {
        Current_Id = id;
    }

    public static int getId() {
        return Current_Id;
    }
}
