import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileDAO implements DAO{
    private File shops;
    private File products;
    public List<Availability> data;
    private List<Shop> shopList;

    public FileDAO(String file1, String file2) throws IOException {
        shops = new File(file1);
        products = new File(file2);
        shopList = new ArrayList<>();
        data = new ArrayList<>();
        if (!shops.exists()) {
            shops.createNewFile();
        } else {
            updateShopBuf();
        }
        if (!products.exists()) {
            products.createNewFile();
        } else {
            updateProductBuf();
        }
    }

    private void updateProductBuf() throws IOException {
        FileReader file = new FileReader(products);
        Scanner scan = new Scanner(file);
        boolean flag;
        while (scan.hasNextLine()) {
            List<ProductInShop> prodList = new ArrayList<>();
            String string = scan.nextLine();
            String product = null;
            int shopID = 0;
            int quantity = 0;
            float cost = 0;
            flag = false;
            int i = 0;
            for (String buf : string.split(",")) {
                if (!flag) {
                    product = buf;
                    flag = true;
                } else {
                    if (i % 3 == 0) {
                        shopID = Integer.parseInt(buf);
                        i++;
                    } else if (i % 3 == 1) {
                        quantity = Integer.parseInt(buf);
                        i++;
                    } else if (i % 3 == 2) {
                        cost = Float.parseFloat(buf);
                        i++;
                        prodList.add(new ProductInShop(shopID, quantity, cost));
                    }
                }
            }
            data.add(new Availability(product, prodList));
        }
        file.close();
    }

    private void updateShopBuf() throws IOException {
        FileReader file = new FileReader(shops);
        Scanner scan = new Scanner(file);
        boolean flag;
        while (scan.hasNextLine()) {
            String string = scan.nextLine();
            String shop = null;
            int shopID = 0;
            flag = false;
            for (String buf : string.split(",", 2)) {
                if (!flag) {
                    shopID = Integer.parseInt(buf);
                    flag = true;
                } else {
                    shop = buf;
                }
            }
            shopList.add(new Shop(shopID, shop));
        }
        file.close();
    }

    @Override
    public void createShop(String shop) throws IOException {
        shopList.add(new Shop(shopList.size() + 1, shop));
        updateShopFile();
    }

    @Override
    public void createProduct(String product) throws IOException {
        data.add(new Availability(product));
        updateProductFile();
    }

    @Override
    public void addProducts(List<ProdDAO> products, int shop) throws IOException {
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (products.get(i).getName().equals(data.get(j).getProductName())) {
                    addProduct(products.get(i), data.get(j), shop);
                }
            }
        }
        updateProductFile();
    }

    private void addProduct(ProdDAO product, Availability availability, int shopID) {
        for (int i = 0; i < availability.getInfoList().size(); i++) {
            if (shopID == availability.getInfoList().get(i).getShopID()) {
                int quantity = availability.getInfoList().get(i).getQuantity() + product.getQuantity();
                availability.getInfoList().get(i).setQuantity(quantity);
                availability.getInfoList().get(i).setPrice(product.getCost());
                return;
            }
        }
        availability.getInfoList().add(new ProductInShop(shopID, product.getQuantity(), product.getCost()));
    }

    @Override
    public float findProductCost(String product, int shopID) throws IOException {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getProductName().equals(product)) {
                for (int j = 0; j < data.get(i).getInfoList().size(); j++) {
                    if (shopID == data.get(i).getInfoList().get(j).getShopID()) {
                        return data.get(i).getInfoList().get(j).getPrice();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public List<ProdDAO> findShoppingList(int shop, float money) throws IOException {
        List<ProdDAO> products = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getInfoList().size(); j++) {
                if (data.get(i).getInfoList().get(j).getShopID() == shop) {
                    if (money >= data.get(i).getInfoList().get(j).getPrice()) {
                        products.add(new ProdDAO(data.get(i).getProductName(), data.get(i).getInfoList().get(j).getPrice(), data.get(i).getInfoList().get(j).getQuantity()));
                    }
                }
            }
        }
        return products;
    }

    @Override
    public float findProductSet (List<ProductDAO> products, int shop) throws IOException {
        float sum = 0;
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (products.get(i).getName().equals(data.get(j).getProductName())) {
                    float buf = findSum(products.get(i), data.get(j), shop);
                    if (buf != -1) {
                        sum += buf;
                    } else {
                        return 0;
                    }
                }
            }
        }
        return sum;
    }

    @Override
    public void buyProductSet (List<ProductDAO> products, int shop) throws IOException {
        for (ProductDAO product : products) {
            for (Availability availability : data) {
                if (product.getName().equals(availability.getProductName())) {
                    int size = availability.getInfoList().size();
                    for (int i = 0; i < size; i++) {
                        if (availability.getInfoList().get(i).getShopID() == shop) {
                            int q = availability.getInfoList().get(i).getQuantity() - product.getQuantity();
                            if (q != 0) {
                                availability.getInfoList().get(i).setQuantity(q);
                            } else {
                                availability.getInfoList().remove(availability.getInfoList().get(i));
                            }
                        }
                    }
                }
            }
        }
        updateProductFile();
    }

    private float findSum (ProductDAO product, Availability availability, int shopID) {
        float sum = -1;
        int size = availability.getInfoList().size();
        for (int i = 0; i < size; i++) {
            if (availability.getInfoList().get(i).getShopID() == shopID && availability.getInfoList().get(i).getQuantity() >= product.getQuantity()) {
                sum = product.getQuantity() * availability.getInfoList().get(i).getPrice();
                return sum;
            }
        }
        return sum;
    }

    @Override
    public int findShopId (String shop) {
        for (int i = 0; i < shopList.size(); i++) {
            if (shop.equals(shopList.get(i).getName())) {
                return shopList.get(i).getShopID();
            }
        }
        return 0;
    }

    @Override
    public String findShopName(Integer shopID){
        for (int i = 0; i < shopList.size(); i++) {
            if (shopID  == shopList.get(i).getShopID()) {
                return shopList.get(i).getName();
            }
        }
        return null;
    }

    @Override
    public List<Integer> findShopList() throws SQLException {
        List<Integer> shops = new ArrayList<>();
        for (int i = 0; i < shopList.size(); i++) {
            shops.add(shopList.get(i).getShopID());
        }
        return shops;
    }

    public void updateShopFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(shops));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        for (int i = 0; i < shopList.size(); i++) {
            csvPrinter.printRecord(i + 1, shopList.get(i).getName());
        }
        csvPrinter.close();
        writer.close();
    }

    public void updateProductFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(products));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> buf = new ArrayList<>();
            buf.add(data.get(i).getProductName());
            for (int j = 0; j < data.get(i).infoList.size(); j++) {
                buf.add(String.valueOf(data.get(i).getInfoList().get(j).getShopID()));
                buf.add(String.valueOf(data.get(i).getInfoList().get(j).getQuantity()));
                buf.add(String.valueOf(data.get(i).getInfoList().get(j).getPrice()));
            }
            csvPrinter.printRecord(buf);
        }
        csvPrinter.close();
        writer.close();
    }
}
