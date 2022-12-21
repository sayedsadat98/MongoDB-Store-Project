import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;


public class MongoMain {
    public static void main(String[] args) {
        // disable the logging into console
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);

        QueryOne q1 = new QueryOne();
        q1.runQ1();
        System.out.println("==========End of Query 1============");

        QueryTwo q2 = new QueryTwo();
        q2.runQ2();
        System.out.println("==========End of Query 2============");

        QueryThree q3 = new QueryThree();
        q3.runQ3();
        System.out.println("==========End of Query 3============");

        QueryFour q4 = new QueryFour();
        q4.runQ4();
        System.out.println("==========End of Query 4============");

        QueryFive q5 = new QueryFive();
        q5.runQ5();
        System.out.println("==========End of Query 5============");

        QuerySix q6 = new QuerySix();
        q6.runQ6();
        System.out.println("==========End of Query 6============");

        QuerySeven q7 = new QuerySeven();
        q7.runQ7();
        System.out.println("==========End of Query 7============");

        QueryEight q8 = new QueryEight();
        q8.runQ8();
        System.out.println("==========End of Query 8============");

        QueryNine q9 = new QueryNine();
        q9.runQ9();
        System.out.println("==========End of Query 9============");

        QueryTen q10 = new QueryTen();
        q10.runQ10();
        System.out.println("==========End of Query 10============");


    }
}


