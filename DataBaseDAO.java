import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DataBaseDAO implements DAO {

    private Connection connection;

    public DataBaseDAO(String host, String login, String password) {
        Connection connection = getConnection(host, login, password);
        if (connection != null) {
            System.out.println("Connected");
        } else {
            System.out.println("NOT Connected");
        }
        this.connection = connection;
    }

    public static Connection getConnection(String host, String login, String password) {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", login);
            connectionProps.put("password", password);
            return DriverManager.getConnection(
                    "jdbc:sqlserver://" + host, connectionProps);
        } catch( SQLException e){ e.printStackTrace(); }
        return null;
    }

    @Override
    public void createShop(String shop) throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        String sql = "INSERT INTO Shop VALUES('" + shop + "')";
        statement.executeUpdate(sql);
    }

    @Override
    public void createProduct(String product) throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        String sql = "INSERT INTO Product VALUES('" + product + "')";
        statement.executeUpdate(sql);
    }

    @Override
    public float findProductSet(List<ProductDAO> products, int shopID) throws SQLException {
        float sum = 0;
        Statement statement = null;
        String query = "select * from ProductInShop where shopID = " + shopID;
        try {
            int count = products.size();
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                String productName = set.getString("ProductName");
                int quantity = set.getInt("Quantity");
                float price = set.getFloat("Price");
                for (int i = 0; i < products.size(); i++) {
                    if (productName.equals(products.get(i).getName())) {
                        if (quantity >= products.get(i).getQuantity()) {
                            sum += products.get(i).getQuantity() * price;
                            count--;
                            break;
                        } else {
                            return 0;
                        }
                    }
                }
            }
            if (count == 0) {
                return sum;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) { statement.close(); }
        }
    }

    @Override
    public void buyProductSet (List<ProductDAO> products, int shopID) throws SQLException {
        Statement statement = null;
        try {
            for (int i = 0; i < products.size(); i++) {
                statement = connection.createStatement();
                String query = "UPDATE ProductInShop SET Quantity = Quantity - " + products.get(i).getQuantity() +
                        " WHERE shopID = " + shopID + " AND ProductName = '" + products.get(i).getName() + "'";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) { statement.close(); }
        }
    }

    @Override
    public void addProducts(List<ProdDAO> products, int shopID) throws SQLException {
        Statement statement = null;
        Statement statementbuf = null;
        if (shopID == 0) {
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                statement = connection.createStatement();
                if (productExist(products.get(i), shopID)) {
                    statement.executeUpdate("UPDATE ProductInShop SET Quantity = Quantity + " + products.get(i).getQuantity() + ", Price=" + products.get(i).getCost() +
                            " WHERE shopID =" + shopID + " AND ProductName ='" + products.get(i).getName() + "'");
                } else {
                    statement.executeUpdate("INSERT INTO ProductInShop VALUES(" + shopID + ", '" + products.get(i).getName() + "'," +
                            products.get(i).getQuantity() + ",'" + products.get(i).getCost() + "')");
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) { statement.close(); }
        }

    }

    @Override
    public float findProductCost(String product, int shopID) throws SQLException {
        Statement statement = null;
        String query = "select * from ProductInShop where shopID = " + shopID + " and ProductName = '" + product + "'";
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                return set.getFloat("Price");
            }
            return 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) { statement.close(); }
        }
    }

    @Override
    public List<ProdDAO> findShoppingList(int shopID, float money) throws SQLException {
        List<ProdDAO> data = new ArrayList<>();
        Statement statement = null;
        String query = "select * from ProductInShop where Price <= " + money + " and shopID = " + shopID;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                String productName = set.getString("ProductName");
                int quantity = set.getInt("Quantity");
                float price = set.getFloat("Price");
                data.add(new ProdDAO(productName, price, quantity));
            }
            return data;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) { statement.close(); }
        }
    }

    @Override
    public int findShopId(String shop) throws SQLException {
        Statement statement;
        String query = "select * from Shop";
        statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        while (set.next()) {
            int shopID = set.getInt("ShopID");
            String shopName = set.getString("ShopName");
            if (shopName.equals(shop)) {
                return shopID;
            }
        }
        return 0;
    }

    @Override
    public String findShopName(Integer shop) throws SQLException {
        Statement statement;
        String query = "select * from Shop";
        statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        while (set.next()) {
            int shopID = set.getInt("ShopID");
            String shopName = set.getString("ShopName");
            if (shopID == shop) {
                return shopName;
            }
        }
        return null;
    }

    public boolean productExist(ProdDAO product, int id) throws SQLException {
        Statement statement = null;
        String query = "select * from ProductInShop";
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                int shopID = set.getInt("ShopID");
                String productName = set.getString("ProductName");
                if (shopID == id && productName.equals(product.getName())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) { statement.close(); }
        }
        return false;
    }

    @Override
    public List<Integer> findShopList() throws SQLException {
        List<Integer> shopList = new ArrayList<>();
        Statement statement = null;
        String query = "select ShopID from Shop";
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                int shopID = set.getInt("ShopID");
                shopList.add(shopID);
            }
            return shopList;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) { statement.close(); }
        }
    }
}
