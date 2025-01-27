import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<Sale> cars = new ArrayList<>();
        cars.add(new Sale("Toyota Camry"));
        cars.add(new Sale("Honda Civic"));
        cars.add(new Sale("BMW X5"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nСписок машин:");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println((i + 1) + ". " + cars.get(i));
            }

            System.out.println("\nВыберите действие:");
            System.out.println("1. Купить машину");
            System.out.println("2. Забронировать машину");
            System.out.println("3. Посмотреть статус машины");
            System.out.println("4. Выйти");

            int action = scanner.nextInt();

            if (action == 4) {
                System.out.println("Выход из программы.");
                break;
            }

            System.out.println("Выберите номер машины:");
            int carNumber = scanner.nextInt();

            if (carNumber < 1 || carNumber > cars.size()) {
                System.out.println("Некорректный номер машины.");
                continue;
            }

            Sale selectedCar = cars.get(carNumber - 1);

            switch (action) {
                case 1 -> {
                    if ("доступна".equals(selectedCar.getStatus())) {
                        selectedCar.setStatus("куплена");
                        System.out.println("Вы купили машину: " + selectedCar.getName());
                    } else {
                        System.out.println("Машина " + selectedCar.getName() + " недоступна для покупки (статус: " + selectedCar.getStatus() + ").");
                    }
                }
                case 2 -> {
                    if ("доступна".equals(selectedCar.getStatus())) {
                        selectedCar.setStatus("забронирована");
                        System.out.println("Вы забронировали машину: " + selectedCar.getName());
                    } else {
                        System.out.println("Машина " + selectedCar.getName() + " недоступна для бронирования (статус: " + selectedCar.getStatus() + ").");
                    }
                }
                case 3 -> {
                    System.out.println("Информация о машине: " + selectedCar);
                }
                default -> System.out.println("Некорректное действие.");
            }
        }

        scanner.close();
    }
}
