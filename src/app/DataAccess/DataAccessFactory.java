package app.DataAccess;

import app.DataAccess.Enums.DataType;
import app.DataAccess.Interfaces.*;
import app.DataAccess.MYSQLDataServices.*;

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
     * @param dataType The specified database type.
     * @param properties The properties for the project.
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
            case MONGODB: throw new UnsupportedOperationException(mongoError);
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
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets service for customer data based on corresponding database type.
     *
     * @return The customer data service.
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public ICustomerData GetCustomerDataService() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: return new CustomerDataService(this.connection, this.dataConnectionService.GetDBName());
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets service for appointment data based on corresponding database type.
     *
     * @return The appointment data service.
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public IAppointmentData GetAppointmentDataService() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: return new AppointmentDataService(this.connection, this.dataConnectionService.GetDBName());
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets service for country data based on corresponding database type.
     *
     * @return The country data service.
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public ICountryData GetCountryDataService() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: return new CountryDataService(this.connection, this.dataConnectionService.GetDBName());
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets service for division data based on corresponding database type.
     *
     * @return The division data service.
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public IDivisionData GetDivisionDataService() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: return new DivisionDataService(this.connection, this.dataConnectionService.GetDBName());
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets service for contact data based on corresponding database type.
     *
     * @return The contact data service.
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public IContactData GetContactDataService() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: return new ContactDataService(this.connection, this.dataConnectionService.GetDBName());
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets service for user data based on corresponding database type.
     *
     * @return The user data service.
     * @throws Exception Throws UnsupportedOperationException if unsupported database type is used.
     */
    public IUserData GetUserDataService() throws Exception
    {
        switch (this.dataType)
        {
            case MYSQL: return new UserDataService(this.connection, this.dataConnectionService.GetDBName());
            case MONGODB: throw new UnsupportedOperationException(mongoError);
            default: throw new UnsupportedOperationException();
        }
    }
}
