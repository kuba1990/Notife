//change class name
//remove action param - put it to enum class if you have to
public class DatabaseRecord {

    //pass this arguments in String[] args
    MySQLJava dao = new MySQLJava(Main.MYSQL_DRIVER, Main.MYSQL_URL);

    //change void to modelObj
    public void modify(String action, String fileName, String fileContent) {
        try {
            dao.readData(action, fileName, fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void delete(String action, String fileName) {
        try {
            dao.readData(action, fileName, "null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //cahnge void to modelObj
    public void add(String action, String fileName, String fileContent) {
        try {
            dao.readData(action, fileName, fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
