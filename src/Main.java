import controllers.UserController;
import controllers.interfaces.IUserController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.UserRepository;
import repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbs:postgresql://localhost:5432","postgres","0000","название database");
        IUserRepository repo = new UserRepository(db);
        IUserController controller = new UserController(repo);
        MyApplication app = new MyApplication(controller);
        app.start();
        db.close();

    }
}
