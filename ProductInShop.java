public class ProductInShop {
    public int shopID;
    public float price;
    public int quantity;

    public ProductInShop(int shopID, int quantity, float price) {
        this.shopID = shopID;
        this.quantity = quantity;
        this.price = price;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return shopID + "," + price + "," + quantity;
    }
}
