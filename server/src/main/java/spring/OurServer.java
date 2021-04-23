package spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.controller.AccountController;
import spring.entity.Account;
import spring.entity.Category;
import spring.manager.AccountManager;
import spring.manager.ItemManager;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@SpringBootApplication(exclude={ })
public class OurServer {
    private static final Logger LOGGER = LogManager.getLogger(OurServer.class);

    private final ItemManager itemManager;

    private final AccountManager accountManager;

    public OurServer( ItemManager itemManager, AccountManager accountManager )
    {
        this.itemManager = itemManager;
        this.accountManager = accountManager;
    }


//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource source = new DriverManagerDataSource();
//        source.setDriverClassName("org.postgresql.Driver");
//        source.setUrl("jdbc:postgresql://localhost:5432/postgres");
//        source.setUsername("postgres");
//        source.setPassword("password");
//        return source;
//    }

    public static void main(String... args)
    {
        SpringApplication.run(OurServer.class,args);
        LOGGER.info("Hello from server.");
        LOGGER.info("Info");
        LOGGER.warn("Warning");
        LOGGER.error("Error!");
        LOGGER.debug("Debug.");
    }

    @PostConstruct
    public void init(){
        try {
            itemManager.registerCategory("produce", "Fresh Produce");
            itemManager.registerCategory("meat", "Fresh Meats");
            itemManager.registerCategory("dairy", "Fresh Dairy");
            itemManager.registerCategory("candy", "Delicious Candies");
            itemManager.registerCategory("juice", "Freshly Squeezed Juices");
            itemManager.registerCategory("beverages", "Fresh Beverages");
            itemManager.registerCategory("canned foods", "delicious canned foods");
            itemManager.registerCategory("snacks", "a appetizing snack");
            itemManager.registerCategory("cereal", "something to start the day");
            itemManager.registerCategory("frozen foods", "warm it up for a delicious meal");
            itemManager.registerCategory("pasta", "delicious pasta and noodles");
            itemManager.registerCategory("soup", "delicious soups");
            itemManager.registerItem("Pear", "The freshest of pears straight from farmers", 35, BigDecimal.valueOf(1), "produce");
            itemManager.registerItem("Apple", "The freshest of apples just for you", 40, BigDecimal.valueOf(1), "produce");
            itemManager.registerItem("pack of water", "Bottled water from the peaks of mountains", 50, BigDecimal.valueOf(10), "beverages");
            itemManager.registerItem("steak", "the juiciest of steaks yet", 50, BigDecimal.valueOf(2.99), "meat");
            itemManager.registerItem("Lettuce", "the greenest of lettuce", 30, BigDecimal.valueOf(4.99), "produce");
            itemManager.registerItem("Broccoli", "The best broccoli you will ever have", 70, BigDecimal.valueOf(0.75), "produce");
            itemManager.registerItem("Onion", "delicious onions", 45, BigDecimal.valueOf(0.5), "produce");
            itemManager.registerItem("Milk", "tasty milk", 45, BigDecimal.valueOf(2.99), "dairy");
            itemManager.registerItem("chocolate bar", "a tasty snack", 20, BigDecimal.valueOf(1.99), "candy");
            itemManager.registerItem("ice cream", "delicious ice cream", 20, BigDecimal.valueOf(2.99), "candy");
            itemManager.registerItem("apple juice", "yummy apple juice", 20, BigDecimal.valueOf(3.99), "juice");
            itemManager.registerItem("grapes", "fresh and juicy grapes", 40, BigDecimal.valueOf(1.99), "produce");
            itemManager.registerItem("strawberries", "fresh and tasty strawberries", 40, BigDecimal.valueOf(2.99), "produce");
            itemManager.registerItem("orange juice", "tasty orange juice", 20, BigDecimal.valueOf(6.99), "juice");
            itemManager.registerItem("Chicken", "delicious chicken", 40, BigDecimal.valueOf(2.49), "meat");
            itemManager.registerItem("Cookies", "tasty cookies", 40, BigDecimal.valueOf(1.29), "candy");
            itemManager.registerItem("Soda", "refreshing soda", 40, BigDecimal.valueOf(2.99), "beverages");
            itemManager.registerItem("Coffee", "coffee for your day", 40, BigDecimal.valueOf(1.99), "beverages");
            itemManager.registerItem("pork", "delicious pork", 40, BigDecimal.valueOf(5.99), "meat");
            itemManager.registerItem("ribs", "delicious prime ribs", 40, BigDecimal.valueOf(5.99), "meat");
            itemManager.registerItem("gummy worms", "yummy gummy worms", 35, BigDecimal.valueOf(2.49), "candy");
            itemManager.registerItem("cheese", "naturally produced cheese", 40, BigDecimal.valueOf(2.99), "dairy");
            itemManager.registerItem("turkey", "a delicious turkey for the occassion", 20, BigDecimal.valueOf(7.99), "meat");
            itemManager.registerItem("lemonade", "refreshing lemonade", 30, BigDecimal.valueOf(5.49), "juice");
            itemManager.registerItem("tomato", "fresh and natural tomatoes", 35, BigDecimal.valueOf(1.39), "produce");
            itemManager.registerItem("Soup", "nutritious soup", 35, BigDecimal.valueOf(3.49), "soup");
            itemManager.registerItem("Yogurt", "healthy and delicious yogurt", 65, BigDecimal.valueOf(2.99), "dairy");
            itemManager.registerItem("crackers", "a snack to sate your appetite", 25, BigDecimal.valueOf(4.49), "snacks");
            itemManager.registerItem("tea", "a cup of tea in the morning", 35, BigDecimal.valueOf(1.19), "beverages");
            itemManager.registerItem("energy drinks", "a drink to fuel you for hours", 35, BigDecimal.valueOf(2.29), "beverages");
            itemManager.registerItem("protein shake", "a drink to provide you proteins", 20, BigDecimal.valueOf(2.29), "beverages");
            itemManager.registerItem("carrots", "fresh carrots", 35, BigDecimal.valueOf(1.29), "produce");
            itemManager.registerItem("honey cereal", "honey-flavored cereal for your day", 25, BigDecimal.valueOf(3.29), "cereal");
            itemManager.registerItem("pizza", "microwave pizza", 25, BigDecimal.valueOf(4.99), "frozen foods");
            itemManager.registerItem("spaghetti", "delicious spaghetti", 25, BigDecimal.valueOf(2.99), "pasta");
            itemManager.registerItem("Ramen", "delicious microwave ramen", 45, BigDecimal.valueOf(1.99), "pasta");
            itemManager.registerItem("fish", "delicious fish", 35, BigDecimal.valueOf(2.99), "meat");
            itemManager.registerItem("Corn", "freshly picked corn", 35, BigDecimal.valueOf(2.39), "produce");
        }catch (IllegalArgumentException e){
            LOGGER.error(e);
        }

    }
}
