/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.sql.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rizan
 */
public class Bagian {

    private int id, jumlah = 0;
    private String kode_bagian, nama_bagian, no_urut;

    private Connection conn;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    private List<DataChangeListener> listeners = new ArrayList<>();

    public interface DataChangeListener {

        void onDataChanged();
    }

    public void addDataChangeListener(DataChangeListener listener) {
        listeners.add(listener);
    }

    public void removeDataChangeListener(DataChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifyDataChanged() {
        for (DataChangeListener listener : listeners) {
            listener.onDataChanged();
        }
    }

    public Bagian() throws SQLException {
        Koneksi koneksi = new Koneksi();
        conn = koneksi.koneksiDB();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKode_bagian() {
        return kode_bagian;
    }

    public void setKode_bagian(String kode_bagian) {
        this.kode_bagian = kode_bagian;
    }

    public String getNama_bagian() {
        return nama_bagian;
    }

    public void setNama_bagian(String nama_bagian) {
        this.nama_bagian = nama_bagian;
    }

    public String getNo_urut() {
        return no_urut;
    }

    public void setNo_urut(String no_urut) {
        this.no_urut = no_urut;
    }

    // Method untuk menambah data (KodeTambah)
    public void KodeTambah() {
        query = "INSERT INTO bagian (id, kode_bagian, nama_bagian, no_urut) VALUES (?,?,?,?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, kode_bagian);
            ps.setString(3, nama_bagian);
            ps.setString(4, no_urut);
            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Bagian Surat berhasil ditambahkan!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

            notifyDataChanged();

        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Bagian Surat gagal ditambahkan!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk mengubah data (KodeUbah)
    public void KodeUbah() {
        query = "UPDATE bagian SET kode_bagian = ?, nama_bagian = ?, no_urut = ? WHERE id = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, kode_bagian);
            ps.setString(2, nama_bagian);
            ps.setString(3, no_urut);
            ps.setInt(4, id);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat berhasil diubah!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

            notifyDataChanged();

        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat gagal diubah!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menghapus data (KodeHapus)
    public void KodeHapus() {
        query = "DELETE FROM bagian WHERE id = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat berhasil dihapus!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

            notifyDataChanged();

        } catch (Exception e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat gagal dihapus!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menampilkan data (KodeTampil)
    public ResultSet KodeTampil() {
        query = "SELECT * FROM bagian";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Data Gagal Ditampilkan!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
        return rs;
    }

    // Method untuk menampilkan data (cb_Bagian)
    public ResultSet Tampil_CbBagianSurat() {

        try {
            query = "SELECT DISTINCT kode_bagian, nama_bagian FROM bagian";
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Data Gagal Ditampilkan!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;

    }

    // Method untuk menghasilkan id otomatis (autoIdBagian)
    public int autoIdBagian() {
        int newID = 1;

        try {

            String query = "SELECT MAX(id) AS max_id FROM bagian";
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
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal menghasilkan nomor urut baru!", null, JOptionPane.ERROR_MESSAGE, 3000);
            e.printStackTrace();
        }

        return newID;
    }

    // Method untuk mengambil no terakhir
    public int autoNoSBagian() {
        int newID = 1;

        try {

            String query = "SELECT MAX(no_urut) AS max_no FROM bagian";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                String lastNoUrut = rs.getString("max_no");

                if (lastNoUrut != null && !lastNoUrut.isEmpty()) {

                    String numericPart = lastNoUrut.split("\\.")[0];
                    newID = Integer.parseInt(numericPart) + 1;
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal menghasilkan nomor urut baru!", null, JOptionPane.ERROR_MESSAGE, 3000);
            e.printStackTrace();
        }

        return newID;
    }

    // Method untuk menampilkan jumlah Bagian
    public int TampilJumlahBagian() {
        query = "SELECT COUNT(*) AS jumlah FROM bagian";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            if (rs.next()) {
                jumlah = rs.getInt("jumlah");
            }

            rs.close();
            st.close();
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data gagal ditampilkan: " + sQLException.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return jumlah;
    }

}
