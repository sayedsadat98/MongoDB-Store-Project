import ch.qos.logback.classic.Logger;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Scanner;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;
import static java.util.Arrays.asList;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;


public class QueryOne {
    static int customerID;

    public static void main(String[] args) {
        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        runQ1();

    }

    public static void runQ1() {
        //        1. Connection to the database

        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection customerCollection = db.getCollection("customer");
        MongoCollection itemCollection = db.getCollection("item");
        MongoCollection productCollection = db.getCollection("product");


        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Order ID for Query 1");
        int orderId = sc.nextInt();

        FindIterable<Document> resultOrder = orderCollection.find(Filters.eq("id", orderId));
        resultOrder.forEach((Block<? super Document>) d -> {
            customerID = (int) d.get("customer");
            System.out.println("Order ID: " + d.get("id"));
            System.out.println("Order Sale Date: " + d.get("saleDate"));
            System.out.println("Order Total Price: " + d.get("totalPrice"));
        });

        Bson lookStage = lookup("orders", "id", "customer", "Order->Cust");
        Bson matchStage = match(eq("id", customerID));

        customerCollection.aggregate(
                asList(lookStage, matchStage)
        ).forEach((Block<? super Document>) n -> {
            System.out.println("Customer ID IS " + n.get("id"));
            System.out.println("Contact IS " + n.get("contacts"));

        });


        Bson lookStage2 = lookup("orders", "product", "id", "Item-Order");
        Bson matchStage2 = match(eq("orders", orderId));


        itemCollection.aggregate(
                asList(matchStage2, lookStage2)
        ).forEach((Block<? super Document>) m -> {
            System.out.println("Item id is " + m.get("id"));
            System.out.println("Item Quantity is " + m.get("quantity"));
            productCollection.find(Filters.eq("id", m.get("product"))).forEach((Block<? super Document>) d -> {
                System.out.println("Product ID: " + d.get("id"));
                System.out.println("Product SaleDate: " + d.get("price"));
                System.out.println("Product TotalPrice: " + d.get("description"));
            });

        });
    }

}
