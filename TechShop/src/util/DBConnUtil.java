package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection() throws SQLException, IOException {
        String dbURL = DBPropertyUtil.getConnectionString();
        String username = DBPropertyUtil.getUsername();
        String password = DBPropertyUtil.getPassword();
        return DriverManager.getConnection(dbURL, username, password);
    }
}
