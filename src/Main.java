import controllers.UserController;
import controllers.VehicleController;
import controllers.interfaces.IUserController;
import controllers.interfaces.IVehicleController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.UserRepository;
import repositories.VehicleRepository;
import repositories.interfaces.IUserRepository;
import repositories.interfaces.IVehicleRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5433", "postgres", "1315162827", "Sale");

        IUserRepository user_repo = new UserRepository(db);
        IVehicleRepository vehicle_repo = new VehicleRepository(db);

        IUserController user_controller = new UserController(user_repo);
        IVehicleController vehicle_controller = new VehicleController(vehicle_repo);

        MyApplication app = new MyApplication(user_controller, vehicle_controller);
        app.start();

        db.close();
    }
}
