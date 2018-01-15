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
    }

    public void readData(String action, String fileName, String fileContent) throws Exception {

        try {
            Class.forName(jdbcDriverStr);
            connection = DriverManager.getConnection(jdbcURL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from test_table;");
            getResultSet(resultSet);

            preparedStatement = connection.prepareStatement("insert into test_table values (DEFAULT ,?,?,?)");
            preparedStatement.setString(1, action);
            preparedStatement.setString(2,fileName);
            preparedStatement.setString(3,fileContent);
            preparedStatement.executeUpdate();
        } finally {
            close();
        }
    }

    private void getResultSet(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            Integer id = resultSet.getInt(TestTableColumns.id.toString());
            String text = resultSet.getString(TestTableColumns.ACTION.toString());
            String fileName = resultSet.getString(TestTableColumns.FILE_NAME.toString());
            String fileContent = resultSet.getString(TestTableColumns.FILE_CONTENT.toString());
            System.out.println("id: " + id);
            System.out.println("text: " + text);
            System.out.println("file name: " + fileName);
            System.out.println("file content: " + fileContent);
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

    enum TestTableColumns {
        id, ACTION, FILE_NAME,FILE_CONTENT
    }
}