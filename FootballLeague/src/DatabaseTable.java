import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseTable extends DefaultTableModel{
   private Vector<String> columnNames;

    public DatabaseTable(int columnCount, String[] columnNames) {
        super(columnNames, 0); // 0 indicates initial row count
        this.columnNames = new Vector<>();
        for (String columnName : columnNames) {
            this.columnNames.add(columnName);
        }
    }

    public void setColumns(int columnCount, String[] columnNames) {
        this.columnNames.clear();
        for (int i = 0; i < columnCount; i++) {
            this.columnNames.add(columnNames[i]);
        }
        fireTableStructureChanged(); // Notify the table model that the structure has changed
    }
    
     public void addData(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> rowData = new Vector<>();

            for (int i = 1; i <= columnCount; i++) {
                rowData.add(rs.getObject(i));
            }

            addRow(rowData); // Add the row to the table model
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
