import controllers.AdministrationController;
import controllers.UserController;
import controllers.VehicleController;
import data.PostgresDB;
import repositories.AdministrationRepository;
import repositories.UserRepository;
import repositories.VehicleRepository;

public class Main {
    public static void main(String[] args) {
        PostgresDB db = new PostgresDB("jdbc:postgresql://localhost:5433", "postgres", "1315162827", "Sale");

        UserRepository userRepository = new UserRepository(db);
        VehicleRepository vehicleRepository = new VehicleRepository(db);
        AdministrationRepository adminRepository = new AdministrationRepository(db);

        UserController userController = new UserController(userRepository);
        VehicleController vehicleController = new VehicleController(vehicleRepository, userController);
        AdministrationController adminController = new AdministrationController(adminRepository, userController);

        ApplicationMenu appMenu = new ApplicationMenu(userController, vehicleController, adminController);
        appMenu.start();
    }
}
