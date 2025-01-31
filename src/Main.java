import controllers.UserController;
import controllers.VehicleController;
import controllers.CharacteristicsController;
import controllers.interfaces.IUserController;
import controllers.interfaces.IVehicleController;
import controllers.interfaces.ICharacteristicsController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.UserRepository;
import repositories.VehicleRepository;
import repositories.CharacteristicsRepository;
import repositories.interfaces.IUserRepository;
import repositories.interfaces.IVehicleRepository;
import repositories.interfaces.ICharacteristicsRepository;

public class Main {
    public static void main(String[] args) {
        try {
            IDB db = new PostgresDB("jdbc:postgresql://localhost:5433", "postgres", "1315162827", "Sale");

            IUserRepository userRepo = new UserRepository(db);
            IVehicleRepository vehicleRepo = new VehicleRepository(db);
            ICharacteristicsRepository characteristicsRepo = new CharacteristicsRepository(db);

            IUserController userController = new UserController(userRepo);
            IVehicleController vehicleController = new VehicleController(vehicleRepo);
            ICharacteristicsController characteristicsController = new CharacteristicsController(characteristicsRepo);

            MyApplication app = new MyApplication(userController, vehicleController, characteristicsController);
            app.start();
        } catch (Exception e) {
            System.out.println("Failed to connect to PostgreSQL database: " + e.getMessage());
        }
    }
}
