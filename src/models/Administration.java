package models;

public class Administration {
    private int id;          // Уникальный ID администратора или менеджера
    private String name;     // Имя администратора или менеджера
    private String email;    // Электронная почта
    private String password; // Пароль
    private String role;     // Роль (admin или manager)

    // Конструктор
    public Administration(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Переопределение метода toString()
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Email: %s, Role: %s", id, name, email, role);
    }
}
