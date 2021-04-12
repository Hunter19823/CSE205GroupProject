package spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@SpringBootApplication(exclude={ })
public class OurServer {
    private static final Logger LOGGER = LogManager.getLogger(OurServer.class);

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("org.postgresql.Driver");
        source.setUrl("jdbc:postgresql://localhost:5432/postgres");
        source.setUsername("postgres");
        source.setPassword("password");
        return source;
    }

    public static void main(String... args)
    {
        LOGGER.info("Hello from server.");
        LOGGER.info("Info");
        LOGGER.warn("Warning");
        LOGGER.error("Error!");
        LOGGER.debug("Debug.");
        SpringApplication.run(OurServer.class,args);
    }

    @PostConstruct
    public void init(){
        try {
            //itemManagement.registerItem("Flex Duck","It can seal anything!",5, BigDecimal.valueOf(25.25));
            //itemManagement.registerItem("Flex Seal","It even works underwater!",3, BigDecimal.valueOf(49.99));
        }catch (IllegalArgumentException e){}

    }
}
