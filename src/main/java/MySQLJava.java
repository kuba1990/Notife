import java.sql.*;

public class MySQLJava {

    private final String jdbcDriverStr;
    private final String jdbcURL;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public MySQLJava(String jdbcDriverStr, String jdbcURL) {
        this.jdbcDriverStr = jdbcDriverStr;
        this.jdbcURL = jdbcURL;
        //prepare connection object here

    }

    //change name of this fc
    public void readData(String action, String fileName, String fileContent) throws Exception {
        try {

/*// remove those lines:
            Class.forName(jdbcDriverStr);
            connection = DriverManager.getConnection(jdbcURL);*/
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from test_table;");
            /* getResultSet(resultSet);*/
//
            preparedStatement = connection.prepareStatement("insert into test_table values (DEFAULT ,?,?,?)");
            preparedStatement.setString(1, action);
            preparedStatement.setString(2, fileName);
            preparedStatement.setString(3, fileContent);
            preparedStatement.executeUpdate();
        } finally {
            close();
        }
    }


    private void close() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
        }
    }
}
