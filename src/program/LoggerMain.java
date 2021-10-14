package program;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class LoggerMain {
    private static Logger logger;

    static {
        try (FileInputStream configFile = new FileInputStream("src\\program\\logs.config")) {
            LogManager.getLogManager().readConfiguration(configFile);
            logger = Logger.getLogger(LoggerMain.class.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
