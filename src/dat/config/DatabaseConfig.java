package dat.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private String url;
    private String user;
    private String password;

    public DatabaseConfig() {loadProperties();}

    public void loadProperties() {
        Properties prop = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("db.properties file not found!");
            }

            prop.load(input);

            this.url = prop.getProperty("db.url");
            this.user = prop.getProperty("db.user");
            this.password = prop.getProperty("db.password");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public String getUrl() {return url;}
    public String getUser() {return user;}
    public String getPassword() {return password;}
}
