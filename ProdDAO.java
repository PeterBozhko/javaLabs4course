public class ProdDAO {
    private String name;
    private float cost;
    private int quantity;

    public ProdDAO(String name, float cost, int quantity) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public float getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }
}
