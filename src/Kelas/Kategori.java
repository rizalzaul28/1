/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author rizan
 */
public class Kategori {

    int id;
    String kode_kategori, nama_kategori;

    private Connection conn;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    public Kategori() throws SQLException {
        Koneksi koneksi = new Koneksi();
        conn = koneksi.koneksiDB();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKode_kategori() {
        return kode_kategori;
    }

    public void setKode_kategori(String kode_kategori) {
        this.kode_kategori = kode_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public void KodeTambah() {
        query = "INSERT INTO kategori (id, kode_kategori,nama_kategori) VALUES (?,?,?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, kode_kategori);
            ps.setString(3, nama_kategori);
            ps.executeUpdate();
            ps.close();
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat berhasil ditambahkan!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat gagal ditampilkan!", null, JOptionPane.ERROR_MESSAGE, 1000);
        }
    }

    public void KodeUbah() {
        query = "UPDATE kategori SET kode_kategori = ?, nama_kategori = ? WHERE id = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, kode_kategori);
            ps.setString(2, nama_kategori);
            ps.setInt(3, id);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat berhasil diubah!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat gagal diubah!", null, JOptionPane.ERROR_MESSAGE, 1000);
        }
    }

    public void KodeHapus() {

        query = "DELETE FROM kategori WHERE id = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kode Surat berhasil dihapus!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (Exception e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kode Surat gagal dihapus!", null, JOptionPane.ERROR_MESSAGE, 1000);
        }

    }

    public ResultSet KodeTampil() {
        query = "SELECT * FROM kategori";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Data gagal ditampilkan", null, JOptionPane.ERROR_MESSAGE, 1000);
        }

        return rs;
    }

    public ResultSet Tampil_CbKategoriSurat() {

        try {
            query = "SELECT * FROM kategori";
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan!");
        }

        return rs;

    }

    public int autoNoSurat() {
        int newID = 1;

        try {

            String query = "SELECT MAX(id) AS max_id FROM kategori";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                String lastNoUrut = rs.getString("max_id");

                if (lastNoUrut != null && !lastNoUrut.isEmpty()) {

                    String numericPart = lastNoUrut.split("\\.")[0];
                    newID = Integer.parseInt(numericPart) + 1;
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghasilkan nomor urut baru!");
            e.printStackTrace();
        }

        return newID;
    }

}
