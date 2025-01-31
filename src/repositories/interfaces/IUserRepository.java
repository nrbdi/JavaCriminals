package repositories.interfaces;

import models.User;

import java.util.List;

public interface IUserRepository {
    boolean createUser(User user);
    boolean updateUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();

    User getUserByEmail(String email);
}
