package NotificationComponent.service;

import NotificationComponent.model.AnsiColor;

public class NotificationService {
    public static void send(String notification){
        System.out.println(AnsiColor.BLUE + "=== " + notification + " ===" + AnsiColor.RESET);
    }
    public static void alert(String notification){
        System.out.println(AnsiColor.RED + "=== " + notification + " ===" + AnsiColor.RESET);
    }
}
