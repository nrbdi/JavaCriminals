import controllers.AdministrationController;
import controllers.UserController;
import controllers.VehicleController;
import controllers.UserFinanceController;
import data.PostgresDB;
import repositories.AdministrationRepository;
import repositories.UserRepository;
import repositories.VehicleRepository;
import repositories.UserFinanceRepository;

public class Main {
    public static void main(String[] args) {
        PostgresDB db = PostgresDB.getInstance(
                "jdbc:postgresql://localhost:5433",
                "postgres",
                "1315162827",
                "Sale"
        );

        UserRepository userRepository = new UserRepository(db);
        VehicleRepository vehicleRepository = new VehicleRepository(db);
        AdministrationRepository adminRepository = new AdministrationRepository(db);
        UserFinanceRepository userFinanceRepository = new UserFinanceRepository(db);

        UserController userController = new UserController(userRepository);
        AdministrationController adminController = new AdministrationController(adminRepository, userController);
        UserFinanceController userFinanceController = new UserFinanceController(userFinanceRepository);
        VehicleController vehicleController = new VehicleController(vehicleRepository, adminController, userFinanceController);

        ApplicationMenu appMenu = new ApplicationMenu(userController, vehicleController, adminController);
        appMenu.start();
    }
}
