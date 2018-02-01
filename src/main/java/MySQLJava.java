import java.sql.*;

public class MySQLJava {

    private final String jdbcDriverStr;
    private final String jdbcURL;
    // Nie jest to potrzebne jako property klasy
    private PreparedStatement preparedStatement;

    public MySQLJava(String jdbcDriverStr, String jdbcURL) {
        this.jdbcDriverStr = jdbcDriverStr;
        this.jdbcURL = jdbcURL;

    }
    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriverStr); //call bez sensu chyba troche.... nic z tym nie robisz
        // Rezultat mozesz od razu zwrocic bez przypiswania do zmiennej
        Connection connection = DriverManager.getConnection(jdbcURL);
        return connection;
    }
    public PreparedStatement writeData(Connection connection, QueueNotify queueNotify) throws Exception {
            //SQL to prepared statemtn mozesz sobie wyciagnac... akurat tutaj nawet to zmiennej statycznej
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
            // co jak przy close poleci exception? Kod pod spodem sie nie wykona... a moze jednak chcialbys zeby wykonal sie niezaleznie od tego?
            if (connection != null) connection.close();
        } catch (Exception e) {
            // polkniety exception... chociaz zalogowany powinnien byc
        }
    }

}

