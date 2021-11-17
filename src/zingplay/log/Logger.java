package zingplay.log;

public class Logger extends BaseLog4jLogger {
    static Logger instance = new Logger("SystemOffer");

    public Logger(String category) {
        super(category);
    }

    public static Logger getInstance() {
        return instance;
    }
}
