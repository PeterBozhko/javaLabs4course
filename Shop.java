public class Shop {
    private int shopID;
    private String name;
    private String shopAddress;

    public Shop(int shopID, String name) {
        this.shopID = shopID;
        this.name = name;
    }

    public Shop(int shopID, String name, String shopAddress) {
        this.shopID = shopID;
        this.name = name;
        this.shopAddress = shopAddress;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public Shop(String name) {
        this.name = name;
    }

    public int getShopID() {
        return shopID;
    }

    public String getName() {
        return name;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Shop = '" + name + '\'';
    }
}
