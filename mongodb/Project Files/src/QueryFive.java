import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class QueryFive {

    public static void main(String[] args) {

        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);


    }

    public static void runQ5() {
        //        1. Connection to the database

        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection customerCollection = db.getCollection("customer");
        MongoCollection itemCollection = db.getCollection("item");
        MongoCollection productCollection = db.getCollection("product");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Customer ID for Query 5");
        int custId = sc.nextInt();


        FindIterable<Document> customerResult = customerCollection.find(Filters.eq("id", custId));
        customerResult.forEach((Block<? super Document>) d -> {
            System.out.println("Customer ID: " + d.get("id"));
            System.out.println("Customer Name: " + d.get("name"));
            System.out.println("Customer Contact: " + d.get("contacts"));
            orderCollection.find(Filters.eq("customer", d.get("id"))).forEach((Block<? super Document>) m -> {
                System.out.println("Order id is " + m.get("id"));
                System.out.println("Order saleDate is " + m.get("saleDate"));
                System.out.println("Order totalPrice is " + m.get("totalPrice"));
                itemCollection.find(Filters.eq("orders", m.get("id"))).forEach((Block<? super Document>) t -> {
                    System.out.println("Item ID: " + t.get("id"));
                    System.out.println("Item quantity: " + t.get("quantity"));
                    productCollection.find(Filters.eq("id", t.get("product"))).forEach((Block<? super Document>) x -> {
                        System.out.println("Product ID: " + x.get("id"));
                        System.out.println("Product price: " + x.get("price"));
                        System.out.println("Product description: " + x.get("description"));
                    });
                });
            });
        });
    }
}
