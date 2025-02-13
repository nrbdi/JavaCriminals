package controllers.interfaces;

import models.User;

public interface IUserController {
    String createUser(String name, String email, String phoneNumber, String password, String role, double cash);
    User loginUser(String email, String password);
}
