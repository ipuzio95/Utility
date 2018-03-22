package pl.com.lessons.test;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import pl.com.lessons.db.DbAccess;
import pl.com.lessons.file.FileHelper;
import pl.com.lessons.mail.MailHelper;

public class Main {

    private Connection con = null;

    public static void main(String[] args) {
        Main instance = new Main();
        DbAccess dbAccess = new DbAccess(".");

        instance.wez_pracownikow(dbAccess);
        instance.wez_samochody(dbAccess);
        instance.sendMail();
    }

    public void wez_pracownikow(DbAccess dbAccess) {
        try {
            if (con == null) {
                con = dbAccess.getConnection();
            }
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("SELECT imie, nazwisko, adres, stanowisko from pracownicy");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String imie = rs.getString("imie");
                String nazwisko = rs.getString("nazwisko");
                String adres = rs.getString("adres");
                String stanowisko = rs.getString("stanowisko");
                System.out.println(imie + " " + nazwisko + " " + adres + " " + stanowisko);
            }
            rs.close();
            con.close();
            con = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wez_samochody(DbAccess dbAccess) {
        try {
            if (con == null) {
                con = dbAccess.getConnection();
            }
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("call wez_samochody_pracownicze()");
            ResultSet rs = ps.executeQuery();
            String filePath = "samochody.txt";
            while (rs.next()) {
                String imie = rs.getString("imie");
                String nazwisko = rs.getString("nazwisko");
                String stanowisko = rs.getString("stanowisko");
                String rejestracja = rs.getString("rej");
                FileHelper.writeToFile(imie + " " + nazwisko + " " + stanowisko + " " + rejestracja + "\n", filePath, true);

            }

            rs.close();
            con.close();
            con = null;

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    private void sendMail()
    {
        try 
        {
            MailHelper.sendMail("host", "od", "do", "temat", "", "user", "haslo!");
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
