import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Sorts.descending;

import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class QueryEight {

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

    public static double getProductPrice(MongoCollection collection, int productId) {
        FindIterable<Document> dualCondition = collection.find(Filters.eq("id", productId));
        dualCondition.sort(descending("id"));
        double productPrice = 0.0;
        for (Document doc : dualCondition) {
            productPrice = (double) doc.get("price");
            break;
        }
        return productPrice;

    }

    public static void main(String[] args) {

        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);


        runQ8();

    }

    public static void runQ8() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection customerCollection = db.getCollection("customer");
        MongoCollection productCollection = db.getCollection("product");
        MongoCollection paymentCollection = db.getCollection("payment");
        MongoCollection itemCollection = db.getCollection("item");

        //Taking the Customer inputs
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your customer ID before ordering");
        int custId = sc.nextInt();

        //Taking the Product inputs
        System.out.println("Enter product ID that you want to buy -> between 1 to 200");
        int prodID = sc.nextInt();

        // Taking Item information
        System.out.println("Enter the number of items for the product you entered previously");
        int itemCount = sc.nextInt();

        //Preparing a Item document
        for (int i = 0; i < itemCount; i++) {
            Document itemDocument = new Document();
            System.out.println("Enter quantity for item " + (i + 1));
            int quantity = sc.nextInt();
            itemDocument.append("id", getCurrentId(itemCollection) + 1);
            itemDocument.append("orders", getCurrentId(orderCollection));
            itemDocument.append("product", prodID);
            itemDocument.append("quantity", quantity);
            //Inserting the document into the item collection
            db.getCollection("item").insertOne(itemDocument);
        }

        //Preparing a Orders document
        Document orderDocument = new Document();
        orderDocument.append("id", getCurrentId(orderCollection) + 1);
        orderDocument.append("date", getCurrentDateTime());
        orderDocument.append("payment", getCurrentId(paymentCollection) + 1);
        orderDocument.append("customer", custId);
        orderDocument.append("saleDate", getCurrentDateTime());
        orderDocument.append("totalPrice", getProductPrice(productCollection, prodID));

        //Inserting the document into the order collection
        db.getCollection("orders").insertOne(orderDocument);

        System.out.println("orderDocument inserted successfully");
        System.out.println("itemDocument inserted successfully");
    }
}
