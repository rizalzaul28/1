/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rizan
 */
public class SuratKeluar {

    int id_suratkeluar;
    String kategori, bagian, nomor, perihal, tujuan, nama_file;
    java.sql.Date tanggal_dibuat;
    FileInputStream file;

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

    public SuratKeluar() throws SQLException {
        Koneksi koneksi = new Koneksi();
        conn = koneksi.koneksiDB();
    }

    public int getId_suratkeluar() {
        return id_suratkeluar;
    }

    public void setId_suratkeluar(int id_suratkeluar) {
        this.id_suratkeluar = id_suratkeluar;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getBagian() {
        return bagian;
    }

    public void setBagian(String bagian) {
        this.bagian = bagian;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getPerihal() {
        return perihal;
    }

    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }

    public Date getTanggal_dibuat() {
        return tanggal_dibuat;
    }

    public void setTanggal_dibuat(Date tanggal_dibuat) {
        this.tanggal_dibuat = tanggal_dibuat;
    }

    public FileInputStream getFile() {
        return file;
    }

    public void setFile(FileInputStream file) {
        this.file = file;
    }

    public void TambahSuratKeluar() {
        query = "INSERT INTO suratkeluar (id_suratkeluar, kategori, bagian, nomor, tanggal_dibuat, perihal, tujuan, nama_file, file)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id_suratkeluar);
            ps.setString(2, kategori);
            ps.setString(3, bagian);
            ps.setString(4, nomor);
            ps.setDate(5, tanggal_dibuat);
            ps.setString(6, perihal);
            ps.setString(7, tujuan);
            ps.setString(8, nama_file);
            ps.setBlob(9, file);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Surat berhasil ditambahkan!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

            notifyDataChanged();
        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal menambahkan surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 1000);
        }
    }

    public void UbahSuratKeluar() {
        query = "UPDATE suratkeluar SET kategori = ?, bagian = ?, nomor = ?, tanggal_dibuat = ?, perihal = ?, tujuan = ?, nama_file = ?, file = ? WHERE id_suratkeluar = ?";

        try {
            ps = conn.prepareStatement(query);

            ps.setString(1, kategori);
            ps.setString(2, bagian);
            ps.setString(3, nomor);
            ps.setDate(4, tanggal_dibuat);
            ps.setString(5, perihal);
            ps.setString(6, tujuan);
            ps.setString(7, nama_file);
            ps.setBlob(8, file);
            ps.setInt(9, id_suratkeluar);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Surat berhasil diubah!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal mengubah surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 1000);
        }
    }

    public void HapusSuratKeluar() {
        query = "DELETE FROM suratkeluar WHERE id_suratkeluar = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id_suratkeluar);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Data surat keluar berhasil dihapus!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal menghapus surat keluar!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 1000);
        }
    }

    // Tambahkan parameter untuk filter kategori
    // Tambahkan parameter untuk filter kategori
    public ResultSet KodeTampil(String filterKategori) {
        try {
            if (filterKategori == null || filterKategori.isEmpty()) {
                // Query tanpa filter
                query = "SELECT id_suratkeluar, kategori, bagian, nomor, tanggal_dibuat, perihal, tujuan, nama_file FROM suratkeluar";
                PreparedStatement ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
            } else {
                // Query dengan filter kategori
                query = "SELECT id_suratkeluar, kategori, bagian, nomor, tanggal_dibuat, perihal, tujuan, nama_file FROM suratkeluar WHERE kategori = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, filterKategori);
                rs = ps.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int autoNoSurat() {
        int newID = 1;

        try {
            String query = "SELECT MAX(id_suratkeluar) AS max_id FROM suratkeluar";
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

    public byte[] BukaFile() throws SQLException {
        byte[] IsiFile = null;
        query = "SELECT file FROM suratkeluar WHERE nama_file = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, nama_file);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            IsiFile = rs.getBytes("file");
        }

        rs.close();
        ps.close();

        return IsiFile;

    }

}
