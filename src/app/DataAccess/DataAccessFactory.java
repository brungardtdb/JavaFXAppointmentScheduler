package DataAccess;

import DataAccess.Enums.DataType;
import DataAccess.Interfaces.IAppointmentData;
import DataAccess.Interfaces.ICustomerData;
import DataAccess.SQLDataServices.AppointmentDataService;
import DataAccess.SQLDataServices.CustomerDataService;
import DataAccess.SQLDataServices.DataConnectionService;

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
    private final String noSQLError = "NOSQL has not yet been implemented!";
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
            case SQL: CreateConnection();
            case NOSQL: throw new UnsupportedOperationException(noSQLError);
            default:throw new UnsupportedOperationException();
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
            case SQL:
                this.dataConnectionService = new DataConnectionService(this.properties);
                connection = this.dataConnectionService.GetDBConnection();
                break;
            case NOSQL: throw new UnsupportedOperationException(noSQLError);
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
            case SQL: this.dataConnectionService.ConnectToDB();
                    break;
            case NOSQL:
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
            case SQL: this.dataConnectionService.DisconnectFromDB();
                break;
            case NOSQL:
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
            case SQL: return new CustomerDataService(this.connection);
            case NOSQL:
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
            case SQL: return new AppointmentDataService(this.connection);
            case NOSQL:
            default: throw new UnsupportedOperationException();
        }
    }
}
