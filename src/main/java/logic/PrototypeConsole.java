package logic;

import java.util.Scanner;

public class PrototypeConsole {
    private static Scanner scanner;
    private static Schedule schedule;

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        System.out.println("PrototypeConsole started");
        scanner = new Scanner(System.in);
        schedule = new Schedule();
        while (command(scanner.nextLine())) {}
        System.out.println("PrototypeConsole terminated");
    }

    private static boolean command(String line) {
        String[] splitedString = line.split(" ");
        if(splitedString[0].equals("exit") ) {
            return false;
        }else if(splitedString[0].equals("reset") ) {
            schedule = new Schedule();
            System.out.println("Schedule reset");
        }
        return true;
    }
}
