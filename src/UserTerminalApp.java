import java.util.Scanner;

public class UserTerminalApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.println("Выберите действие:");
        System.out.println("1. Добавить нового пользователя");
        System.out.println("2. Обновить существующего пользователя");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            User newUser = new User();
            System.out.println("Введите имя:");
            newUser.setName(scanner.nextLine());
            System.out.println("Введите email:");
            newUser.setEmail(scanner.nextLine());
            System.out.println("Введите номер телефона:");
            newUser.setPhoneNumber(scanner.nextLine());
            System.out.println("Введите пароль:");
            newUser.setPassword(scanner.nextLine());
            System.out.println("Введите роль (client/admin):");
            newUser.setRole(scanner.nextLine());

            userDAO.saveUser(newUser);
            System.out.println("Пользователь успешно добавлен!");
        } else if (choice == 2) {
            System.out.println("Введите ID пользователя для обновления:");
            int userId = scanner.nextInt();
            scanner.nextLine();

            User existingUser = userDAO.getUserById(userId);
            if (existingUser != null) {
                System.out.println("Текущие данные пользователя:");
                System.out.println("Имя: " + existingUser.getName());
                System.out.println("Email: " + existingUser.getEmail());
                System.out.println("Номер телефона: " + existingUser.getPhoneNumber());
                System.out.println("Роль: " + existingUser.getRole());

                System.out.println("\nВведите новые данные:");
                System.out.println("Введите имя (оставьте пустым для сохранения текущего):");
                String name = scanner.nextLine();
                if (!name.isEmpty()) existingUser.setName(name);

                System.out.println("Введите email (оставьте пустым для сохранения текущего):");
                String email = scanner.nextLine();
                if (!email.isEmpty()) existingUser.setEmail(email);

                System.out.println("Введите номер телефона (оставьте пустым для сохранения текущего):");
                String phoneNumber = scanner.nextLine();
                if (!phoneNumber.isEmpty()) existingUser.setPhoneNumber(phoneNumber);

                System.out.println("Введите пароль (оставьте пустым для сохранения текущего):");
                String password = scanner.nextLine();
                if (!password.isEmpty()) existingUser.setPassword(password);

                System.out.println("Введите роль (оставьте пустым для сохранения текущей):");
                String role = scanner.nextLine();
                if (!role.isEmpty()) existingUser.setRole(role);

                userDAO.updateUser(existingUser);
                System.out.println("Данные пользователя успешно обновлены!");
            } else {
                System.out.println("Пользователь с таким ID не найден.");
            }
        } else {
            System.out.println("Неверный выбор. Программа завершена.");
        }

        scanner.close();
    }
}
