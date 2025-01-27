public class Sale{
    private String name;
    private String status;

    public Sale(String name) {
        this.name = name;
        this.status = "доступна";
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Машина: " + name + " | Статус: " + status;
    }
}
