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

public class QueryThree {
    public static void main(String[] args) {
        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        runQ3();

    }

    public static void runQ3() {
        //        1. Connection to the database

        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection customerCollection = db.getCollection("customer");
        MongoCollection deliveryCollection = db.getCollection("delivery");
        MongoCollection carrierCollection = db.getCollection("carrier");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Customer ID for Query 3");
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
                deliveryCollection.find(Filters.eq("id", d.get("id"))).forEach((Block<? super Document>) x -> {
                    System.out.println("Delivery date is " + x.get("deliveryDate"));
                    carrierCollection.find(Filters.eq("id", x.get("carrier"))).forEach((Block<? super Document>) y -> {
                        System.out.println("Carrier ID: " + y.get("id"));
                        System.out.println("Carrier Name: " + y.get("name"));
                        System.out.println("Carrier Contact: " + y.get("contacts"));
                    });
                });

            });
        });
    }
}
