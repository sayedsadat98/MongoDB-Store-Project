import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Sorts.descending;

import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class QueryTen {
    public static int getCurrentId(MongoCollection Collection) {
        FindIterable<Document> getMaxID = Collection.find().sort(descending("id"));
        int maxId = 0;
        for (Document doc : getMaxID) {
            maxId = (int) doc.get("id");
            break;
        }
        return maxId;
    }

    public static String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss.SSSSSS");
        LocalDateTime currentDateTime = LocalDateTime.now();
        ;
        return currentDateTime.toString();
    }

    public static void main(String[] args) {
        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        runQ10();

    }

    public static void runQ10() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection carrierCollection = db.getCollection("carrier");
        MongoCollection deliveryDocument = db.getCollection("delivery");

        MongoCollection paymentCollection = db.getCollection("payment");

        //Taking the inputs
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the carrier");
        String name = sc.nextLine();
        System.out.println("Enter the contacts of the carrier");
        String contacts = sc.nextLine();
        System.out.println("Enter a valid id of the product which you want to deliver");
        int prodId = sc.nextInt();

        //Preparing a Carrier document
        Document carrierDocument = new Document();
        carrierDocument.append("id", getCurrentId(carrierCollection) + 1);
        carrierDocument.append("name", name);
        carrierDocument.append("contacts", contacts);
        //Inserting the document into the payment collection
        db.getCollection("carrier").insertOne(carrierDocument);
        System.out.println("carrierDocument inserted successfully");

        //Preparing a Delivery document
        Document deliverydoc = new Document();
        deliverydoc.append("id", getCurrentId(deliveryDocument) + 1);
        deliverydoc.append("carrier", getCurrentId(carrierCollection));
        deliverydoc.append("deliveryDate", getCurrentDateTime());

        //Inserting the document into the payment collection
        db.getCollection("delivery").insertOne(deliverydoc);
        System.out.println("deliveryDocument inserted successfully");
    }
}
