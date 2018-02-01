import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseConsumerThread extends Thread {
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/sys?"
            + "user=root&password=eagles123";

    private MySQLJava db = new MySQLJava(MYSQL_DRIVER, MYSQL_URL);

    public void run() {

        while(true){ // nie ma sposobu na opuszczenie tej petli
            QueueNotify actionFileContent = null;
            try {
                // to co mowilismy - queue przekazywane przez referencje powinno byc, a nie przez taki konstrukt
                actionFileContent=Queue.sharedQueue.take();
            } catch (InterruptedException e) { //Interrupted exception to nie jest nic strasznego wlasciwie, to znaczy, ze "uzytkownik" przestal czekac na rezultat, mozna to obsluzyc po prostu logujac taka informacja
            // swoja droga chyba jak Ci poleci taki interrupted to chyba nie ma co dalej robic... bo nie bedziesz mial actionFileContent (tj. bedzie nullem)
                e.printStackTrace();
            }

            Connection con = null;
            PreparedStatement ps = null;
            try {
                //polacz sie z baza raz, a rozlacz dopiero jak skonczysz z nia dzialac
                con = db.connect();
                ps = db.writeData(con, actionFileContent);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {

                db.close(con, ps);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
