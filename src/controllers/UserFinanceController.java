package controllers;
import controllers.interfaces.IUserFinanceController;
import repositories.interfaces.IUserFinanceRepository;
import utils.Validator;

public class UserFinanceController implements IUserFinanceController {
    private final IUserFinanceRepository userFinanceRepository;

    public UserFinanceController(IUserFinanceRepository userFinanceRepository) {
        this.userFinanceRepository = userFinanceRepository;
    }

    @Override
    public boolean updateUserBalance(int userId, double newBalance) {
        if (!Validator.isPositiveInteger(userId)) {
            System.out.println("Error: User ID must be a positive number.");
            return false;
        }
        if (!Validator.isPositiveNumber(newBalance)) {
            System.out.println("Error: Balance must be a positive number.");
            return false;
        }
        return userFinanceRepository.updateUserBalance(userId, newBalance);
    }
}
