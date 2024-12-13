/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rizan
 */
public class Koneksi {

    private Connection KonekstorSQL;
    private String host = "localhost";
    private String db = "manajemensurat";
    private String user = "root";
    private String password = "calonsarjana";
    private String port = "3306";
    private String url = "jdbc:mysql://" + host + ":" + port + "/" + db;

    public Connection koneksiDB() throws SQLException {

        try {
            KonekstorSQL = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi berhasil");
        } catch (SQLException sQLException) {
            System.out.println("Koneksi gagal");
        }

        return KonekstorSQL;

    }
}
