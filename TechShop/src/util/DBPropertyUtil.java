package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException("❌ Could not find 'db.properties' in classpath.");
            }
            props.load(input);
        }
        return props;
    }

    public static String getConnectionString() throws IOException {
        return loadProperties().getProperty("db.url");
    }

    public static String getUsername() throws IOException {
        return loadProperties().getProperty("db.username");
    }

    public static String getPassword() throws IOException {
        return loadProperties().getProperty("db.password");
    }
}
