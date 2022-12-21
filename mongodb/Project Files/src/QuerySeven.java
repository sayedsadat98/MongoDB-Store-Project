import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class QuerySeven {


    public static void main(String[] args) {
        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        runQ7();

    }

    public static void runQ7() {
        //        1. Connection to the database

        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection categoryCollection = db.getCollection("category");
        MongoCollection productCollection = db.getCollection("product");
        MongoCollection supplierCollection = db.getCollection("supplier");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Supplier ID for Query 7");
        int supplierId = sc.nextInt();

        FindIterable<Document> supplierResult = supplierCollection.find(Filters.eq("id", supplierId));
        supplierResult.forEach((Block<? super Document>) d -> {
            System.out.println("Supplier ID: " + d.get("id"));
            System.out.println("Supplier Name: " + d.get("name"));
            System.out.println("Supplier contacts: " + d.get("contacts"));
            productCollection.find(Filters.eq("supplier", d.get("id"))).forEach((Block<? super Document>) m -> {
                System.out.println("Product id is " + m.get("id"));
                System.out.println("Product price is " + m.get("price"));
                System.out.println("Product description is " + m.get("description"));

                categoryCollection.find(Filters.eq("id", m.get("category"))).forEach((Block<? super Document>) t -> {
                    System.out.println("Category ID: " + t.get("id"));
                    System.out.println("Category description: " + t.get("description"));

                });
            });
        });
    }
}
