import java.util.InputMismatchException;
import java.util.Scanner;
import controllers.interfaces.IUserController;
import controllers.interfaces.IVehicleController;

public class MyApplication {
    private final IUserController userController;
    private final IVehicleController vehicleController;
    private final Scanner scanner = new Scanner(System.in);
    private boolean isLoggedIn = false; // Флаг авторизации

    public MyApplication(IUserController userController, IVehicleController vehicleController) {
        this.userController = userController;
        this.vehicleController = vehicleController;
    }

    public void start() {
        while (true) {
            showMainMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очищаем буфер
                if (!isLoggedIn) { // Действия до входа в аккаунт
                    switch (choice) {
                        case 1 -> createUserMenu();
                        case 2 -> updateUserMenu();
                        case 3 -> getUserByIdMenu();
                        case 4 -> getAllUsersMenu();
                        case 5 -> loginUserMenu();
                        case 0 -> {
                            System.out.println("Программа завершена.");
                            return;
                        }
                        default -> System.out.println("Выберите действие от 0 до 5.");
                    }
                } else { // Действия после входа в аккаунт
                    switch (choice) {
                        case 1 -> showVehicleCatalog();
                        case 2 -> {
                            System.out.println("Вы вышли из аккаунта. Программа завершена.");
                            return;
                        }
                        default -> System.out.println("Выберите действие от 1 до 2.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число.");
                scanner.nextLine(); // Сбрасываем неправильный ввод
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\nДобро пожаловать в User Management Application!");
        if (isLoggedIn) { // Меню для авторизованного пользователя
            System.out.println("Выберите действие:");
            System.out.println("1. Каталог машин");
            System.out.println("2. Выйти");
        } else { // Меню для неавторизованного пользователя
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить нового пользователя");
            System.out.println("2. Обновить существующего пользователя");
            System.out.println("3. Получить данные пользователя по ID");
            System.out.println("4. Показать всех пользователей");
            System.out.println("5. Войти в аккаунт");
            System.out.println("0. Выйти");
        }
        System.out.print("Введите номер действия: ");
    }

    private void createUserMenu() {
        System.out.println("Введите имя пользователя:");
        String name = scanner.nextLine();
        System.out.println("Введите email пользователя:");
        String email = scanner.nextLine();
        System.out.println("Введите номер телефона пользователя:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Введите пароль пользователя:");
        String password = scanner.nextLine();
        System.out.println("Введите роль пользователя (client/admin):");
        String role = scanner.nextLine();

        String response = userController.createUser(name, email, phoneNumber, password, role);
        System.out.println(response);
    }

    private void updateUserMenu() {
        System.out.println("Введите имя пользователя для обновления:");
        String name = scanner.nextLine();
        System.out.println("Введите новый email (оставьте пустым для сохранения текущего):");
        String email = scanner.nextLine();
        System.out.println("Введите новый номер телефона (оставьте пустым для сохранения текущего):");
        String phoneNumber = scanner.nextLine();
        System.out.println("Введите новый пароль (оставьте пустым для сохранения текущего):");
        String password = scanner.nextLine();
        System.out.println("Введите новую роль (оставьте пустым для сохранения текущей):");
        String role = scanner.nextLine();

        String response = userController.updateUser(name, email, phoneNumber, password, role);
        System.out.println(response);
    }

    private void getUserByIdMenu() {
        System.out.println("Введите ID пользователя:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Очищаем буфер

        String response = userController.getUserById(id);
        System.out.println(response);
    }

    private void getAllUsersMenu() {
        String response = userController.getAllUsers();
        System.out.println(response);
    }

    private void loginUserMenu() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        String response = userController.loginUser(email, password);
        System.out.println(response);

        if (response.startsWith("Login successful")) {
            isLoggedIn = true; // Устанавливаем флаг входа
        }
    }

    private void showVehicleCatalog() {
        while (true) {
            System.out.println("\nКаталог машин:");
            System.out.println("1. Показать весь каталог");
            System.out.println("2. Фильтр по типу машин");
            System.out.println("3. Фильтр по марке машин");
            System.out.println("0. Назад");
            System.out.print("Выберите опцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер
            switch (choice) {
                case 1 -> System.out.println(vehicleController.getAllVehicles());
                case 2 -> {
                    System.out.print("Введите тип машин (Crossover, Sedan, SUV): ");
                    String type = scanner.nextLine();
                    System.out.println(vehicleController.getVehiclesByType(type));
                }
                case 3 -> {
                    System.out.print("Введите марку машин (Kia, Toyota, BMW, Mercedes, Hyundai: ");
                    String brand = scanner.nextLine();
                    System.out.println(vehicleController.getVehiclesByBrand(brand));
                }
                case 0 -> {
                    return; // Возврат в главное меню
                }
                default -> System.out.println("Выберите действие от 0 до 3.");
            }
        }
    }
}
