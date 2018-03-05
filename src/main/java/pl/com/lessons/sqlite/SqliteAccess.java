package pl.com.lessons.sqlite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sqlitetutorial.net
 */
public class SqliteAccess {

    /**
     * Connect to a sample database
     *
     * @param dbPath
     * @return 
     */
    public static Connection connect(String dbPath) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/" + dbPath;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            return conn;

        } catch (SQLException e) {
            System.out.println("error " + e.getMessage());
            return null;
        } 
    }

    public static void createNewDatabase(String dbPath) {

        String url = "jdbc:sqlite:C:/" + dbPath;

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable(String dbPath, String tableName) {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/" + dbPath;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String dbPath, String tableName, Integer id, String name, float capacity)
    {
        String sql = "INSERT INTO " + tableName + "(name,capacity) VALUES(?,?, ?)";

        try {
            Connection conn = connect(dbPath);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setFloat(3, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            System.out.println("error " + e.getMessage());
        }
    }
    
      public void selectAll()
      {
            String sql = "SELECT id, name, capacity FROM warehouses";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name") + "\t" +
                                   rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    

    public static void main(String[] args)
    {
        /*
        createNewDatabase("develop-lessons/sqlite_db/test.db");
        createNewTable("develop-lessons/sqlite_db/test.db", "tabela1");
        connect("develop-lessons/sqlite_db/test.db");
        */
        insert("develop-lessons/sqlite_db/test.db", "tabela1", 1, "test1", 1);
    }
}
