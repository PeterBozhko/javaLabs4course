import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DAO {
    int findShopId(String shop) throws SQLException;

    String findShopName(Integer shopID) throws SQLException;

    void createShop(String shop) throws SQLException, IOException;

    void createProduct (String product) throws SQLException, IOException;

    float findProductSet (List<ProductDAO> products, int shop) throws SQLException, IOException;

    void buyProductSet (List<ProductDAO> products, int shop) throws SQLException, IOException;

    void addProducts (List<ProdDAO> products, int shop) throws SQLException, IOException;

    float findProductCost (String product, int shop) throws SQLException, IOException;

    List<ProdDAO> findShoppingList (int shop, float money) throws IOException, SQLException;

    List<Integer> findShopList() throws SQLException;
}
