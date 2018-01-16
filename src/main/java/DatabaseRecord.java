public class DatabaseRecord {

    MySQLJava dao = new MySQLJava(Main.MYSQL_DRIVER, Main.MYSQL_URL);

    public void  modify(String action, String fileName, String fileContent){

        try {
            dao.readData(action,fileName,fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void  delete(String action, String fileName){
        try {
            dao.readData(action,fileName,"null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void  add(String action, String fileName, String fileContent){
        try {
            dao.readData(action,fileName,fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
