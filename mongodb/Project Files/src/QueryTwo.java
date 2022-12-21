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

public class QueryTwo {
    static int customerID;
    static int paymentID;

    public static void main(String[] args) {
        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);


        runQ2();
    }

    public static void runQ2() {
        //        1. Connection to the database

        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection customerCollection = db.getCollection("customer");
        MongoCollection paymentCollection = db.getCollection("payment");
        MongoCollection productCollection = db.getCollection("product");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Order ID for Query 2");
        int orderId = sc.nextInt();

        FindIterable<Document> resultOrder = orderCollection.find(Filters.eq("id", orderId));
        resultOrder.forEach((Block<? super Document>) d -> {
            customerID = (int) d.get("customer");
            paymentID = (int) d.get("payment");
            System.out.println("Order ID: " + d.get("id"));
            System.out.println("Order Sale Date: " + d.get("saleDate"));
            System.out.println("Order Total Price: " + d.get("totalPrice"));
        });

        Bson lookStage = lookup("orders", "id", "customer", "Order->Cust");
        Bson matchStage = match(eq("id", customerID));

        customerCollection.aggregate(
                asList(lookStage, matchStage)
        ).forEach((Block<? super Document>) n -> {
            System.out.println("Customer ID:" + n.get("id"));
            System.out.println("Customer Name: " + n.get("name"));
            System.out.println("Contact:" + n.get("contacts"));

        });

        Bson lookStage2 = lookup("orders", "id", "payment", "Order-Payment");
        Bson matchStage2 = match(eq("id", paymentID));


        paymentCollection.aggregate(
                asList(matchStage2, lookStage2)
        ).forEach((Block<? super Document>) n -> {
            System.out.println("Payment ID IS " + n.get("id"));
            System.out.println("Payment Date IS " + n.get("paymentDate"));

        });
    }
}
