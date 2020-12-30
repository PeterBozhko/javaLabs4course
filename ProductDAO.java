public class ProductDAO {
    private String name;
    public int quantity;

    public ProductDAO(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public ProductDAO(String name) {
        this.name = name;
        this.quantity = 1;
    }

    public ProductDAO() {
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +", quantity=" + quantity;
    }
}
