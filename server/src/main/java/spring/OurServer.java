package spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.entity.Category;
import spring.manager.ItemManager;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Optional;

@SpringBootApplication(exclude={ })
public class OurServer {
    private static final Logger LOGGER = LogManager.getLogger(OurServer.class);

    private final ItemManager itemManager;

    public OurServer( ItemManager itemManager )
    {
        this.itemManager = itemManager;
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
            Optional<Category> categoryOptional = itemManager.findCategoryById("produce");
            LOGGER.info("Category: {}",categoryOptional.get().getCategoryName());
            LOGGER.info("Category Items: {}",categoryOptional.get().getItemCategories());

            Page<BigInteger> itemPage = itemManager.findItemIdsInCategory("produce", Pageable.unpaged());

            LOGGER.info("Total Elements: {}",itemPage.getTotalElements());
            LOGGER.info("Total Pages: {}",itemPage.getTotalPages());

            itemPage.getContent().parallelStream().forEach(
                    item -> LOGGER.info("ItemID: {}",item)
            );
            //itemManagement.registerItem("Flex Duck","It can seal anything!",5, BigDecimal.valueOf(25.25));
            //itemManagement.registerItem("Flex Seal","It even works underwater!",3, BigDecimal.valueOf(49.99));
        }catch (IllegalArgumentException e){
            LOGGER.error(e);
        }

    }
}
