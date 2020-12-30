import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        Service service = new Service("settings.property");
        //service.createShop("Пятерочка");
        //service.createShop("Магнит");
        //service.createShop("Ашан");
        //service.createProduct("Шоколадка");
        //service.createProduct("Печенька");
        //service.createProduct("Кофе");
        //service.createProduct("Чай");

//        service.addProducts(Arrays.asList(new Prod("Шоколадка", 65, 100),
//                new Prod( "Печенька", 10, 154)),"Пятерочка");
//
//        service.addProducts(Arrays.asList(new Prod("Шоколадка", 60, 77)), "Магнит");
//        Product product = new Product("Шоколадка", 3);
//
//        System.out.println(service.findLowestCostOfSet(Collections.singletonList(product)));
//
//        service.addProducts(Arrays.asList(new Prod("Чай", 79, 25),
//                new Prod( "Кофе", 144, 15)),"Пятерочка");
//
//        service.addProducts(Arrays.asList(new Prod("Печенька", 12, 55),
//                new Prod( "Кофе", 145, 25)),"Магнит");
//        System.out.println(service.findLowestPrice("Кофе"));
//
//        service.addProducts(Arrays.asList(new Prod("Чай", 90, 15),
//                new Prod( "Кофе", 141, 12)),"Магнит");
//
//        System.out.println(service.findLowestPrice("Кофе"));
//
//        System.out.println(service.buyProducts(Arrays.asList(new Product("Кофе", 4),
//                new Product( "Печенька", 3)), "Магнит"));
//
//        System.out.println(service.findShoppingList("Пятерочка", 200));

        System.out.println(service.findLowestCostOfSet(new ArrayList<>(Arrays.asList(new Product("Шоколадка",5 ),
                                                        new Product( "Печенька", 2)))));
    }
}
