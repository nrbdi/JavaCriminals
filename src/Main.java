import controllers.*;
import controllers.interfaces.*;
import data.PostgresDB;
import repositories.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PostgresDB db = new PostgresDB("jdbc:postgresql://localhost:5433", "postgres", "1315162827", "Sale");

        UserRepository userRepository = new UserRepository(db);
        VehicleRepository vehicleRepository = new VehicleRepository(db);
        CharacteristicsRepository characteristicsRepository = new CharacteristicsRepository(db);
        AdministrationRepository adminRepository = new AdministrationRepository(db);

        UserController userController = new UserController(userRepository);
        CharacteristicsController characteristicsController = new CharacteristicsController(characteristicsRepository);
        VehicleController vehicleController = new VehicleController(vehicleRepository, userController);
        AdministrationController adminController = new AdministrationController(adminRepository);

        MyApplication userApp = new MyApplication(userController, vehicleController, characteristicsController);
        MyApplication_2 adminApp = new MyApplication_2(adminController, userController);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Management Application!");
        System.out.println("Please choose your role:");
        System.out.println("1. User");
        System.out.println("2. Admin");
        System.out.println("3. Manager");
        System.out.print("Enter your choice: ");

        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        switch (roleChoice) {
            case 1 -> userApp.start();
            case 2 -> adminApp.start("admin");
            case 3 -> adminApp.start("manager");
            default -> System.out.println("Invalid choice. Exiting application.");
        }
    }
}
