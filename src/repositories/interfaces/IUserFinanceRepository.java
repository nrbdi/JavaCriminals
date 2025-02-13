package repositories.interfaces;

public interface IUserFinanceRepository {
    boolean updateUserBalance(int userId, double newBalance);
    double getUserBalance(int userId);
}
