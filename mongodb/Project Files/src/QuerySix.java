import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class QuerySix {

    public static void main(String[] args) {
        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        runQ6();

    }

    public static void runQ6() {
        //        1. Connection to the database

        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection itemCollection = db.getCollection("item");
        MongoCollection productCollection = db.getCollection("product");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Product ID for Query 6");
        int productId = sc.nextInt();

        FindIterable<Document> productResult = productCollection.find(Filters.eq("id", productId));
        productResult.forEach((Block<? super Document>) d -> {
            System.out.println("Product ID: " + d.get("id"));
            System.out.println("Product price: " + d.get("price"));
            System.out.println("Product description: " + d.get("description"));
            itemCollection.find(Filters.eq("product", d.get("id"))).forEach((Block<? super Document>) m -> {
                System.out.println("Item id is " + m.get("id"));
                System.out.println("Item quantity is " + m.get("quantity"));
                orderCollection.find(Filters.eq("id", m.get("orders"))).forEach((Block<? super Document>) t -> {
                    System.out.println("Order ID: " + t.get("id"));
                    System.out.println("Order saleDate: " + t.get("saleDate"));
                    System.out.println("Order totalPrice: " + t.get("totalPrice"));

                });
            });
        });
    }
}
