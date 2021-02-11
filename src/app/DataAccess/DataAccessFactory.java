package DataAccess;

import DataAccess.Enums.DataType;
import DataAccess.Interfaces.IAppointmentData;
import DataAccess.Interfaces.ICustomerData;
import DataAccess.MYSQLDataServices.AppointmentDataService;
import DataAccess.MYSQLDataServices.CustomerDataService;
import DataAccess.MYSQLDataServices.DataConnectionService;

import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Factory for data access, used to hide implementation from business logic.
 * Currently this only supports SQL, but is set up to be extended in the future should we choose to implement another
 * database solution like NOSQL.
 */
public class DataAccessFactory
{
    private final DataType dataType;
    private final Properties properties;
    private final String mongoError = "MongoDB has not yet been implemented!";
    private DataConnectionService dataConnectionService;
    private Connection connection;


    /**
     * Constructor for data access factory, takes data access type and project properties as arguments.
     *
     * @param dataType
     * @param properties
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public DataAccessFactory(DataType dataType, Properties properties) throws Exception {
        this.dataType = dataType;
        this.properties = properties;

        switch (this.dataType)
        {
            case MYSQL: this.dataConnectionService = new DataConnectionService(this.properties);
            break;
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Creates a connection to the database, throws an exception for unsupported database types.
     *
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    private void CreateConnection() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL:
                this.connection = this.dataConnectionService.GetDBConnection();
                break;
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Method for connecting to the database.
     *
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public void ConnectToDB() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: this.dataConnectionService.ConnectToDB();
            CreateConnection();
                    break;
            case MONGODB:
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Method for disconnecting from database.
     *
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public void DisconnectFromDB() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: this.dataConnectionService.DisconnectFromDB();
                break;
            case MONGODB:
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets service for customer data based on corresponding database type.
     *
     * @return Returns customer data service.
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public ICustomerData GetCustomerDataService() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: return new CustomerDataService(this.connection, this.dataConnectionService.GetDBName());
            case MONGODB:
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets service for appointment data based on corresponding database type.
     *
     * @return Returns appointment data service.
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public IAppointmentData GetAppointmentDataService() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: return new AppointmentDataService(this.connection, this.dataConnectionService.GetDBName());
            case MONGODB:
            default: throw new UnsupportedOperationException();
        }
    }
}
