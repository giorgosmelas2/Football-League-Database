import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseDriver {
    
    static String driverClassName = "oracle.jdbc.OracleDriver" ;
    static String url = "jdbc:oracle:thin:@192.168.6.21:1521:dblabs" ;
    static Connection dbConnection = null;
    static String username = "username";
    static String passwd = "password";
    static Statement  statement = null;
    static ResultSet rs = null;
    private static ResultSetMetaData metaData;
    private String selectString;
    private String[] columnNames;
    private DatabaseTable dbTable;
    
    public DatabaseDriver(){}
    
    public void showTable(String tableName, DatabaseTable dbTable) throws ClassNotFoundException{
        try{
            Class.forName(driverClassName);
            dbConnection = DriverManager.getConnection(url, username,passwd);      
            statement = dbConnection.createStatement();
                    
            selectString = "SELECT * FROM " + tableName ;
            rs = statement.executeQuery(selectString);       
             
            metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            columnNames = new String[columnCount];
            
            for(int i = 1; i<=columnCount; i++){
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            
            dbTable.setColumns(columnCount, columnNames);
            while (rs.next()) {
                dbTable.addData(rs);
            }

            rs.close();
            
            statement.close();
            dbConnection.close();
        }catch(NullPointerException | SQLException e){
            e.printStackTrace();
        }
    }
}

