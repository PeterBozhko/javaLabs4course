import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Service {
    public DAO DAO;

    public Service(String fileName) {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream(fileName);
            property.load(fis);
            if (property.getProperty("data_store").equals("DataBase")){
                String host = property.getProperty("db.host");
                String login = property.getProperty("db.login");
                String password = property.getProperty("db.password");
                System.out.println("HOST: " + host
                        + ", LOGIN: " + login
                        + ", PASSWORD: " + password);
                DAO = new DataBaseDAO(host,login,password);
            }
            if (property.getProperty("data_store").equals("Files")){
                String fileShops = property.getProperty("f.shops");
                String fileProducts = property.getProperty("f.products");
                DAO = new FileDAO(fileShops, fileProducts);
            }


        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    public void createShop (String shop) throws SQLException, IOException {
        DAO.createShop(shop);
    }

    public void createProduct (String product) throws SQLException, IOException {
        DAO.createProduct(product);
    }

    public void addProducts (List<Prod> products, String shop) throws SQLException, IOException {
        List<ProdDAO> prodDAO = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            prodDAO.add(new ProdDAO(products.get(i).getName(), products.get(i).getCost(), products.get(i).getQuantity()));
        }
        int shopID = DAO.findShopId(shop);
        DAO.addProducts(prodDAO, shopID);
    }

    public String findLowestPrice(String product) throws SQLException, IOException {
        List<Integer> shopList = DAO.findShopList();
        float minPrice = Float.MAX_VALUE;
        Integer shop = null;
        for (Integer shopID: shopList) {
            float buf = DAO.findProductCost(product, shopID);
            if (buf == 0) {
                continue;
            }
            if (buf < minPrice) {
                minPrice = buf;
                shop = shopID;
            }
        }
        return DAO.findShopName(shop);
    }

    public Float buyProducts(List<Product> products, String shop) throws IOException, SQLException {
        int shopID = DAO.findShopId(shop);
        List<ProductDAO> productDAO = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            productDAO.add(new ProductDAO(products.get(i).getName(), products.get(i).getQuantity()));
        }
        float sum = DAO.findProductSet(productDAO, shopID);
        if (sum == 0) {
            return null;
        } else {
            DAO.buyProductSet(productDAO, shopID);
            return sum;
        }
    }

    public List<Product> findShoppingList (String shop, float money) throws IOException, SQLException {
        int shopID = DAO.findShopId(shop);
        List<ProdDAO> products = new ArrayList<>();
        List<Product> data = new ArrayList<>();
        products = DAO.findShoppingList(shopID, money);
        for (int i = 0; i < products.size(); i++) {
            int count = (int) (money / products.get(i).getCost());
            count = Integer.min(count, products.get(i).getQuantity());
            data.add(new Product(products.get(i).getName(), count));
        }
        return data;
    }

    public String findLowestCostOfSet(List<Product> products) throws IOException, SQLException {
        List<Integer> shopList = DAO.findShopList();
        List<ProductDAO> productDAO = products.stream()
                .map(product -> new ProductDAO(product.getName(), product.getQuantity()))
                .collect(Collectors.toList());
        float minSum = Float.MAX_VALUE;
        Integer shop = null;
        for (Integer shopID: shopList) {
            float buf = DAO.findProductSet(productDAO, shopID);
            if (buf == 0) {
                continue;
            }
            if (buf < minSum) {
                minSum = buf;
                shop = shopID;
            }
        }
        if (shop == null) {
            return null;
        } else {
            return DAO.findShopName(shop) + " " +minSum;
        }
    }
}