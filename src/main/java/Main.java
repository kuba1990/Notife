public class Main {
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/sys?"
            + "user=root&password=eagles123";


    private static String path = "/home/jwisniowski/Desktop/Notify";

    public static void main(String[] args) throws Exception {
        NotifeFolder notifeFolder = new NotifeFolder();
        notifeFolder.notify(path);//radar path
        // notifeFolder.notifyContent(path);//check out all file in path*/
        MySQLJava dao = new MySQLJava(MYSQL_DRIVER,MYSQL_URL);
    }
}
