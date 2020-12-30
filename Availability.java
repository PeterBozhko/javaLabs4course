import java.util.ArrayList;
import java.util.List;

public class Availability {
    public String productName;
    public List<ProductInShop> infoList;

    public Availability(String productName, List<ProductInShop> infoList) {
        this.infoList = new ArrayList<>();
        this.productName = productName;
        this.infoList = infoList;
    }

    public Availability(String productName) {
        this.infoList = new ArrayList<>();
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<ProductInShop> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<ProductInShop> infoList) {
        this.infoList = infoList;
    }
}
