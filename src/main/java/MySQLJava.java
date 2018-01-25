import java.sql.*;

public class MySQLJava {

    private final String jdbcDriverStr;
    private final String jdbcURL;
    private PreparedStatement preparedStatement;

    public MySQLJava(String jdbcDriverStr, String jdbcURL) {
        this.jdbcDriverStr = jdbcDriverStr;
        this.jdbcURL = jdbcURL;

    }
    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriverStr);
        Connection connection = DriverManager.getConnection(jdbcURL);
        return connection;
    }
    public PreparedStatement writeData(Connection connection, QueueNotify queueNotify) throws Exception {
            preparedStatement = connection.prepareStatement("insert into test_table values (DEFAULT ,?,?,?)");
            preparedStatement.setString(1, queueNotify.getAction());
            preparedStatement.setString(2, queueNotify.getName());
            preparedStatement.setString(3, queueNotify.getContent());
            preparedStatement.executeUpdate();

            return preparedStatement;
    }

    public void close (Connection connection,PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
        }
    }

}

