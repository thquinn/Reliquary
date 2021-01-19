package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReliquaryLogger {
    private static final Logger logger = LogManager.getLogger("Reliquary");

    public static void log(String s) {
        logger.info(s);
    }
}
