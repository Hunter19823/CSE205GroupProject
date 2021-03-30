package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@SpringBootApplication
public class TestApplication {
    @Autowired
    public ItemManagement itemManagement;

//    @PostConstruct
//    public void init(){
//        try {
//            itemManagement.registerItem("Flex Tape","It can seal anything!",5, BigDecimal.valueOf(25.25));
//            itemManagement.registerItem("Flex Seal","It even works underwater!",3, BigDecimal.valueOf(49.99));
//        }catch (IllegalArgumentException e){}
//    }
}
