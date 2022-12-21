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

public class QueryNine {

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

    public static int getCurrentId(MongoCollection Collection) {
        FindIterable<Document> getMaxID = Collection.find().sort(descending("id"));
        int maxId = 0;
        for (Document doc : getMaxID) {
            maxId = (int) doc.get("id");
            break;
        }
        return maxId;
    }

    public static String getCurrentDateTime(MongoCollection collection) {
        FindIterable<Document> dualCondition = collection.find().sort(descending("id"));
        dualCondition.sort(descending("id"));
        String currentDate = "";

        for (Document doc : dualCondition) {
            currentDate = (String) doc.get("paymentDate");
            break;
        }
        return currentDate;
    }

    public static void main(String[] args) {
        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
        runQ9();

    }

    public static void runQ9() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("onlinestore");
        MongoCollection orderCollection = db.getCollection("orders");
        MongoCollection paymentCollection = db.getCollection("payment");

        //Taking the inputs
        Scanner sc = new Scanner(System.in);
        System.out.println("Entering the payment data for query 8...");

        //Preparing a Payment document
        Document paymentDocument = new Document();
        paymentDocument.append("id", getCurrentId(paymentCollection));
        paymentDocument.append("paymentDate", getCurrentDateTime(paymentCollection));
        //Inserting the document into the payment collection
        db.getCollection("payment").insertOne(paymentDocument);
        System.out.println("paymentDocument inserted successfully");
    }
}
