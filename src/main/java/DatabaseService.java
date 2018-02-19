import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseService {
    private static Logger LOGGER = Logger.getLogger("InfoLogging");
    private final String jdbcDriverStr;
    private final String jdbcURL;
    private PreparedStatement preparedStatement;

    public DatabaseService(String jdbcDriverStr, String jdbcURL) {
        this.jdbcDriverStr = jdbcDriverStr;
        this.jdbcURL = jdbcURL;

    }
    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriverStr);
        Connection connection = DriverManager.getConnection(jdbcURL);
        return connection;
    }

    public PreparedStatement prepareStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("insert into test_table values (DEFAULT ,?,?,?)");
    }

    public void close(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            LOGGER.info(e + "connection lost ");
        }
    }

}

