import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import services.AppService;

public class BookingApp {
    private static int userId;
    private static boolean running = true;
    private static Scanner sc = new Scanner(System.in);
    public static void start(){
        while(running) {
            System.out.println("Welcome to Booking app!\n" +
                    "1. Authorise\n" +
                    "2. Create an account");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a number");
                sc.next();
            }
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 1) {
                int login = login();
                if (login == 0){
                    continue;
                }
                else if (login == 1){
                    runPatient();
                }
                else {
                    runDoctor();
                }
            }
            else if (ch == 2){
                int account = createAccount();
                if (account == 0){
                    continue;
                }
                else if (account == 1){
                    runPatient();
                }
                else {
                    runDoctor();
                }
            }
            else{
                System.out.println("ERROR: there is no such option. Please try again");
            }
        }
    }
    private static void runPatient() {
        while (running) {
            System.out.println("1. Book an appointment\n" +
                    "2. Cancel an appointment\n" +
                    "3. View doctors schedule\n" +
                    "4. View upcoming appointments\n" +
                    "5. View doctors list\n" +
                    "6. quit");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a number");
                sc.next();
            }
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1:
                    bookAppointment();
                    break;
                case 2:
                    cancelAppointment();
                    break;
                case 3:
                    viewDoctorsSchedule();
                    break;
                case 4:
                    AppService.viewUpcomingAppointments(userId, false);
                    break;
                case 5:
                    AppService.viewDoctorsList();
                    break;
                case 6:
                    quit();
                    break;
                default:
                    System.out.println("There is no such option!");
            }
        }
    }
    private static void runDoctor(){
        while (running) {
            System.out.println("1. View upcoming appointments\n" +
                    "2. Change schedule\n" +
                    "3. quit");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a number");
                sc.next();
            }
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1:
                    AppService.viewUpcomingAppointments(userId, true);
                    break;
                case 2:
                    changeSchedule();
                    break;
                case 3:
                    quit();
                    break;
                default:
                    System.out.println("There is no such option!");
            }
        }
    }
    private static int login(){ //returns 0 if error, 1 if patient and 2 if doctor
        System.out.println("Chose who are you: \n" +
                "1. Patient \n" +
                "2. doctor");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a number");
            sc.next();
        }
        int ch = sc.nextInt();
        sc.nextLine();
        if(ch == 1){
            if(isUserLogined(false)){
                return 1;
            }
            else{
                return 0;
            }
        }
        else if(ch == 2){
            if(isUserLogined(true)){
                return 2;
            }
            else{
                return 0;
            }
        }
        else{
            System.out.println("ERROR: there is no such option, please try again");
            return 0;
        }
    }
    private static boolean isUserLogined(boolean isDoctor){
        System.out.println("Email: ");
        String email = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();
        if (AppService.isUserVerified(email, password, isDoctor)){
            System.out.println("Verified successfully!");
            userId = AppService.getUserId(email, isDoctor);
            return true;
        }
        else{
            System.out.println("ERROR: incorrect email or password");
            return false;
        }
    }
    private static int createAccount(){ //returns same as login
        System.out.println("Chose who are you: \n" +
                "1. Patient \n" +
                "2. doctor");
        while(!sc.hasNextInt()){
            System.out.println("Please enter a number");
            sc.next();
        }
        int ch = sc.nextInt();
        sc.nextLine();
        if (ch == 1){
            System.out.println("Email: ");
            String email = sc.nextLine();
            System.out.println("Password: ");
            String password = sc.nextLine();
            System.out.println("Your name: ");
            String name = sc.nextLine();
            System.out.println("Phone number: ");
            String phone = sc.nextLine();
            System.out.println("Trying to create account...");
            if(AppService.isPatientCreated(name, email, phone, password)){
                System.out.println("Account created successfully!");
                userId = AppService.getUserId(email, false);
                return 1;
            }
            else{
                System.out.println("Failed to create an account");
                return 0;
            }
        }
        else if (ch == 2){
            System.out.println("Email: ");
            String email = sc.nextLine();
            System.out.println("Password: ");
            String password = sc.nextLine();
            System.out.println("Your name: ");
            String name = sc.nextLine();
            System.out.println("Your specialization: ");
            String specialization = sc.nextLine();
            System.out.println("Phone number: ");
            String phone = sc.nextLine();
            System.out.println("From what time you are available (in HH:MM format): ");
            LocalTime avFrom = LocalTime.parse(sc.nextLine());
            System.out.println("Until what time you are available (in HH:MM format): ");
            LocalTime avTo = LocalTime.parse(sc.nextLine());
            System.out.println("Trying to crate an account");
            if (AppService.isDoctorCreated(name, specialization, email, phone, avFrom, avTo, password)){
                System.out.println("Account created successfully!");
                userId = AppService.getUserId(email, true);
                return 2;
            }
            else{
                System.out.println("Failed to create an account");
                return 0;
            }
        }
        else{
            System.out.println("ERROR: there is no such option, please try again");
            return 0;
        }
    }
    private static void bookAppointment(){
        System.out.println("Doctor id: ");
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a number");
            sc.next();
        }
        int doctorId = sc.nextInt();
        sc.nextLine();
        System.out.println("Date of an appointment in yyyy-MM-dd format: ");
        LocalDate appointmentDate = LocalDate.parse(sc.nextLine());
        System.out.println("Please choose the time slot:" +
                "1. 09:00 - 09:30\n" +
                "2. 09:30 - 10:00\n" +
                "3. 10:00 - 10:30\n" +
                "4. 10:30 - 11:00\n" +
                "5. 11:00 - 11:30\n" +
                "6. 11:30 - 12:00\n" +
                "7. 12:00 - 12:30\n" +
                "8. 12:30 - 13:00\n" +
                "9. 13:00 - 13:30\n" +
                "10. 13:30 - 14:00\n" +
                "11. 14:00 - 14:30\n" +
                "12. 14:30 - 15:00\n" +
                "13. 15:00 - 15:30\n" +
                "14. 15:30 - 16:00\n" +
                "15. 16:00 - 16:30\n" +
                "16. 16:30 - 17:00\n" +
                "17. 17:00 - 17:30\n" +
                "18. 17:30 - 18:00\n" +
                "19: 18:00 - 18:30\n" +
                "20. 18:30 - 19:00\n" +
                "21. 19:00 - 19:30\n" +
                "22. 19:30 - 20:00\n" +
                "23. 20:00 - 20:30\n" +
                "24. 20:30 - 21:00");
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a number");
            sc.next();
        }
        int ch = sc.nextInt();
        String time = "";
        switch(ch){
            case 1: time = "09:00"; break;
            case 2: time = "09:30"; break;
            case 3: time = "10:00"; break;
            case 4: time = "10:30"; break;
            case 5: time = "11:00"; break;
            case 6: time = "11:30"; break;
            case 7: time = "12:00"; break;
            case 8: time = "12:30"; break;
            case 9: time = "13:00"; break;
            case 10: time = "13:30"; break;
            case 11: time = "14:00"; break;
            case 12: time = "14:30"; break;
            case 13: time = "15:00"; break;
            case 14: time = "15:30"; break;
            case 15: time = "16:00"; break;
            case 16: time = "16:30"; break;
            case 17: time = "17:00"; break;
            case 18: time = "17:30"; break;
            case 19: time = "18:00"; break;
            case 20: time = "18:30"; break;
            case 21: time = "19:00"; break;
            case 22: time = "19:30"; break;
            case 23: time = "20:00"; break;
            case 24: time = "20:30"; break;
        }
        LocalTime timeSlot = LocalTime.parse(time);
        System.out.println("Booking the appointment...");
        if(AppService.bookAppointment(userId, doctorId, appointmentDate, timeSlot)) {
            System.out.println("Appointment booked successfully!");
        } else{
            System.out.println("Failed to book an appointment.");
        }
    }
    private static void cancelAppointment(){
        System.out.println("AppointmentId: ");
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a number");
            sc.next();
        }
        int appointmentId = sc.nextInt();
        if (AppService.isAppointmentCancelledSuccessfully(userId, appointmentId)) {
            System.out.println("Appointment canceled successfully!");
        }
        else{
            System.out.println("failed to cancel appointment, try again");
        }
    }
    private static void viewDoctorsSchedule(){
        System.out.println("Doctor id: ");
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a number");
            sc.next();
        }
        int doctorId = sc.nextInt();
        sc.nextLine();
        System.out.println("Date in yyyy-MM-dd format: ");
        LocalDate viewingDate = LocalDate.parse(sc.nextLine());
        AppService.viewDoctorsSchedule(doctorId, viewingDate);
    }
    private static void quit(){
        running = false;
    }
    private static void changeSchedule(){
        System.out.println("Available from in HH:MM format: ");
        LocalTime avFrom = LocalTime.parse(sc.nextLine());
        System.out.println("Available until in HH:MM format: ");
        LocalTime avTo = LocalTime.parse(sc.nextLine());
        AppService.updateSchedule(userId, avFrom, avTo);
    }
}
