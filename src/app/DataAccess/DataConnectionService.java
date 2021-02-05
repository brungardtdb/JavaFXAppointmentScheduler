package DataAccess;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

public class DataConnectionService {
    private final String cnnString;
    private String cnn;
    private String port;
    private String dbUserName;
    private String dbName;
    private String dbPassword;
    private Connection connection;

    public DataConnectionService(Properties properties)
    {
        cnn = properties.getProperty("cnn");
        port = properties.getProperty("port");
        dbUserName = properties.getProperty("username");
        dbName = properties.getProperty("dbname");
        dbPassword = properties.getProperty("password");
        cnnString = "jdbc:mysql://" + cnn + "/" + dbName;
    }

    public void ConnectToDB() throws Exception
    {
        connection = DriverManager.getConnection(cnnString, dbUserName, dbPassword);
    }

    public void DisconnectFromDB() throws Exception
    {
        connection.close();
    }
}
