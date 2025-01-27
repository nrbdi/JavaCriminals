import controllers.interfaces.IUserController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final IUserController userController;
    private final Scanner scanner = new Scanner(System.in);

    public MyApplication(IUserController userController) {
        this.userController = userController;
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1: getAllUsersMenu(); break;
                    case 2: getUserByIdMenu(); break;
                    case 3: createUserMenu(); break;
                    default: return;
                }
            }catch (InputMismatchException e) {
                System.out.println("Input must be number");
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void createUserMenu() {
        System.out.println("Please enter a user name: ");
        String name = scanner.next();
        System.out.println("Please enter a surname: ");
        String surname = scanner.next();
        System.out.println("Please enter a gender: ");
        String gender = scanner.next();

        String response = userController.createUser(name, surname, gender);
        System.out.println(response);
    }

    private void getUserByIdMenu() {
        System.out.println("Enter user id");
        int id = scanner.nextInt();
        String response = userController.getUserById(id);
        System.out.println(response);
    }

    private void getAllUsersMenu() {
        String response =userController.getAllUsers();
        System.out.println(response);
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to My Application!");
        System.out.println("Select an option (1-3):");
        System.out.println("1. Get all users");
        System.out.println("2. Get user by id");
        System.out.println("3. Create a new user");
        System.out.println("0. Exit");
        System.out.print("Enter option (1-3): ");
    }
}
