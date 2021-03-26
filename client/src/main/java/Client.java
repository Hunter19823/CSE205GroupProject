import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Client {
    private static final Logger LOGGER = LogManager.getLogger(Client.class);

    public static void main(String[] args)
    {
        LOGGER.info("Hello from Client!");
        LOGGER.info("Info");
        LOGGER.warn("Warning");
        LOGGER.error("Error!");
        LOGGER.debug("Debug.");
    }
}
