package eventhubs.latency.bare;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static Properties properties = new Properties();

    private static Properties getConfiguration(){
        try (InputStream is = Configuration.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        return properties;
    }

    public static String getConfiguration(String key) {
        return getConfiguration().getProperty(key);
    }
}
