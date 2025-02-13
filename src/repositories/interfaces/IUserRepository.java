package repositories.interfaces;

import models.User;

public interface IUserRepository {
    boolean createUser(User user);
    User getUserByEmail(String email);
}
