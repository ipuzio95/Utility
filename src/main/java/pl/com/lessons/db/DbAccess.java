/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.lessons.db;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

public class DbAccess {

    private static Connection con = null;
    private final DbConfig conf = new DbConfig();

    public DbAccess() 
    {
        conf.czytajConfig("run");
    }
    
    public DbAccess(String dir) {
        conf.czytajConfig(dir);
    }

    public final Connection getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connString = "jdbc:mysql://" + conf.getHost() + "/" + conf.getDbName();
            con = (Connection) DriverManager.getConnection(connString, conf.getDbUser(), conf.getDbPass());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return con;
    }
}
