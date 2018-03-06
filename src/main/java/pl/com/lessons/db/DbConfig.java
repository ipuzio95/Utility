/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.lessons.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author bfries
 */
public class DbConfig {

    private String host = "localhost";
    private String dbName = "";
    private String dbUser = "root";
    private String dbPass = "";

    public void czytajConfig(String dir) {
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(new File(dir + "/config.txt")));
            while ((line = br.readLine()) != null) {
                String param[] = line.split("=", -1);
                if (param.length != 2) {
                    continue;
                }
                if (param[0].equalsIgnoreCase("host")) {
                    host = param[1];
                    continue;
                }
                if (param[0].equalsIgnoreCase("user")) {
                    dbUser = param[1];
                    continue;
                }
                if (param[0].equalsIgnoreCase("dbName")) {
                    setDbName(param[1]);
                    continue;
                }
                if (param[0].equalsIgnoreCase("password")) {
                    dbPass = param[1];
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the dbUser
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * @param dbUser the dbUser to set
     */
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    /**
     * @return the dbPass
     */
    public String getDbPass() {
        return dbPass;
    }

    /**
     * @param dbPass the dbPass to set
     */
    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
