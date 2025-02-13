package controllers.interfaces;

public interface IUserFinanceController {
    boolean updateUserBalance(int userId, double newBalance);
}
