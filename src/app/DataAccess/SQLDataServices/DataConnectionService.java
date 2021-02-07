package DataAccess.SQLDataServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


/**
 * Service to handle connecting to database
 */
public class DataConnectionService
{
    private final String cnnString;
    private String cnn;
    private String port;
    private String dbUserName;
    private String dbName;
    private String dbPassword;
    private Connection connection;

    /**
     * Class constructor, takes in project properties to build connection string
     *
     * @param properties
     */
    public DataConnectionService(Properties properties)
    {
        cnn = properties.getProperty("cnn");
        port = properties.getProperty("port");
        dbUserName = properties.getProperty("username");
        dbName = properties.getProperty("dbname");
        dbPassword = properties.getProperty("password");
        cnnString = "jdbc:mysql://" + cnn + "/" + dbName;
    }

    /**
     * Method to used by service to connect to database
     *
     * @throws Exception
     */
    public void ConnectToDB() throws Exception
    {
        connection = DriverManager.getConnection(cnnString, dbUserName, dbPassword);
    }

    /**
     * Method used to terminate database connection
     *
     * @throws Exception
     */
    public void DisconnectFromDB() throws Exception
    {
        connection.close();
    }


    /**
     * Method used by service to get database connection
     *
     * @return Returns the database connection.
     */
    public Connection GetDBConnection()
    {
        return this.connection;
    }
}
