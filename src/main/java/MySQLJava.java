import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from test_table;");

            /* getResultSet(resultSet);*/

            getResultSet(resultSet);

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


    // dont know what you would like to achive
    private void getResultSet(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            Integer id = resultSet.getInt(TestTableColumns.id.toString());
            String text = resultSet.getString(TestTableColumns.ACTION.toString());
            String fileName = resultSet.getString(TestTableColumns.FILE_NAME.toString());
            String fileContent = resultSet.getString(TestTableColumns.FILE_CONTENT.toString());

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

    //remote this enum
    enum TestTableColumns {
        id, ACTION, FILE_NAME, FILE_CONTENT
    }

}
