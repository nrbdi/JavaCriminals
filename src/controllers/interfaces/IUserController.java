package controllers.interfaces;

public interface IUserController {
    String createUser(String name, String email, String phoneNumber, String password, String role, double cash);
    String updateUser(int id, String name, String email, String phoneNumber, String password, String role, double cash);
    String getUserById(int id);
    String getAllUsers();
    String loginUser(String email, String password);
    boolean updateUserBalance(int userId, double newBalance); // Метод обновления баланса
}
