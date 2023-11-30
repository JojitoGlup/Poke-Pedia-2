import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConfiguracionLogger {

    private static final String LOG_FILE_PATH = "Bitacora-de-errores.log";
    private static Logger logger;

    static {
        configureLogger();
    }

    public static Logger getLogger() {
        return logger;
    }

    private static void configureLogger() {
        logger = Logger.getLogger(ConfiguracionLogger.class.getName());

        try {
            FileHandler fileHandler = new FileHandler(Paths.get(LOG_FILE_PATH).toString(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);

            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            String errorMessage = "Error al configurar el logger: " + e.getMessage();
            System.err.println(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }
}