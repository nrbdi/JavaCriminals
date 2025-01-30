import java.util.Scanner;
import controllers.interfaces.IUserController;
import controllers.interfaces.IVehicleController;
import controllers.interfaces.ICharacteristicsController;

public class MyApplication{
    private final IUserController userController;
    private final IVehicleController vehicleController;
    private final ICharacteristicsController characteristicsController;
    private final Scanner scanner = new Scanner(System.in);
    private boolean isLoggedIn = false; // Флаг авторизации

    public MyApplication(IUserController userController, IVehicleController vehicleController, ICharacteristicsController characteristicsController) {
        this.userController = userController;
        this.vehicleController = vehicleController;
        this.characteristicsController = characteristicsController;
    }

    public void start() {
        while (true) {
            if (!isLoggedIn) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\nДобро пожаловать в User Management Application!");
        System.out.println("1. Добавить нового пользователя");
        System.out.println("2. Обновить существующего пользователя");
        System.out.println("3. Получить данные пользователя по ID");
        System.out.println("4. Показать всех пользователей");
        System.out.println("5. Войти в аккаунт");
        System.out.println("0. Выйти");
        System.out.print("Введите номер действия: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 5 -> loginUserMenu();
            case 0 -> {
                System.out.println("Программа завершена.");
                System.exit(0);
            }
            default -> System.out.println("Выберите действие от 0 до 5.");
        }
    }

    private void showMainMenu() {
        System.out.println("\nДобро пожаловать в User Management Application!");
        System.out.println("1. Каталог машин");
        System.out.println("2. Выйти");
        System.out.print("Введите номер действия: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> showVehicleCatalog();
            case 2 -> {
                isLoggedIn = false; // Выход из аккаунта
                System.out.println("Вы вышли из аккаунта.");
            }
            default -> System.out.println("Выберите действие от 1 до 2.");
        }
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
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    String allVehicles = vehicleController.getAllVehicles();
                    System.out.println(allVehicles);
                    showVehicleDetailsMenu();
                }
                case 2 -> {
                    System.out.print("Введите тип машин ( Crossover, Sedan, SUV): ");
                    String type = scanner.nextLine();
                    String vehiclesByType = vehicleController.getVehiclesByType(type);
                    System.out.println(vehiclesByType);
                    showVehicleDetailsMenu();
                }
                case 3 -> {
                    System.out.print("Введите марку машин ( Kia, Hyundai, Mercedes, Toyota, BMW): ");
                    String brand = scanner.nextLine();
                    String vehiclesByBrand = vehicleController.getVehiclesByBrand(brand);
                    System.out.println(vehiclesByBrand);
                    showVehicleDetailsMenu();
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Выберите действие от 0 до 3.");
            }
        }
    }

    private void showVehicleDetailsMenu() {
        while (true) {
            System.out.println("\n1. Подробные характеристики машины");
            System.out.println("2. Назад");
            System.out.print("Выберите опцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("Введите ID машины: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    String details = characteristicsController.getCharacteristicsByVehicleId(id);
                    if (details == null || details.isEmpty()) {
                        System.out.println("Характеристики машины с ID " + id + " не найдены.");
                    } else {
                        System.out.println("Характеристики машины:");
                        System.out.println(details);
                    }
                }
                case 2 -> {
                    return;
                }
                default -> System.out.println("Выберите действие от 1 до 2.");
            }
        }
    }
}
