import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    public static void main(String[] args)
    {
        LOGGER.info("Hello from server.");
        LOGGER.info("Info");
        LOGGER.warn("Warning");
        LOGGER.error("Error!");
        LOGGER.debug("Debug.");
    }
}
