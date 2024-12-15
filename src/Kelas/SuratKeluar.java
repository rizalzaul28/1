/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.io.FileInputStream;
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

    int id_suratkeluar, jumlah = 0;
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

    // Method untuk menambah data (KodeTambah)
    public void KodeTambah() {
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
            timedPane.showTimedMessage("Gagal menambahkan surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk mengubah data (KodeUbah)
    public void KodeUbah() {
        query = "UPDATE suratkeluar SET kategori = ?, bagian = ?, tanggal_dibuat = ?, perihal = ?, tujuan = ?, nama_file = ?, file = ? WHERE id_suratkeluar = ?";

        try {
            ps = conn.prepareStatement(query);

            ps.setString(1, kategori);
            ps.setString(2, bagian);
            ps.setDate(3, tanggal_dibuat);
            ps.setString(4, perihal);
            ps.setString(5, tujuan);

            if (file != null) {
                // Jika file diubah, set nama_file dan file baru
                ps.setString(6, nama_file);
                ps.setBlob(7, file);
            } else {
                // Jika file tidak diubah, ambil nilai lama dari database
                String querySelect = "SELECT nama_file, file FROM suratkeluar WHERE id_suratkeluar = ?";
                PreparedStatement psSelect = conn.prepareStatement(querySelect);
                psSelect.setInt(1, id_suratkeluar);
                ResultSet rs = psSelect.executeQuery();

                if (rs.next()) {
                    ps.setString(6, rs.getString("nama_file"));
                    ps.setBlob(7, rs.getBlob("file"));
                }

                rs.close();
                psSelect.close();
            }

            ps.setInt(8, id_suratkeluar);
            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Surat berhasil diubah!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal mengubah surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menghapus data (KodeHapus)
    public void KodeHapus() {
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
            timedPane.showTimedMessage("Gagal menghapus surat keluar!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk mengambil data lama
    public SuratKeluar getDataLama(int idSuratKeluar) throws SQLException {
        String query = "SELECT * FROM suratkeluar WHERE id_suratkeluar = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idSuratKeluar);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SuratKeluar dataLama = new SuratKeluar();
                dataLama.setId_suratkeluar(rs.getInt("id_suratkeluar"));
                dataLama.setKategori(rs.getString("kategori"));
                dataLama.setBagian(rs.getString("bagian"));
                dataLama.setTanggal_dibuat(rs.getDate("tanggal_dibuat"));
                dataLama.setPerihal(rs.getString("perihal"));
                dataLama.setTujuan(rs.getString("tujuan"));
                dataLama.setNama_file(rs.getString("nama_file"));
                return dataLama;
            } else {
                throw new SQLException("Data surat keluar tidak ditemukan!");
            }
        }
    }

    // Method untuk memfilter data berdasarkan Kategori
    public ResultSet KodeTampilByKategori(String filterKategori) {
        try {
            if (filterKategori == null || filterKategori.isEmpty()) {
                query = "SELECT id_suratkeluar, kategori, bagian, nomor, tanggal_dibuat, perihal, tujuan, nama_file FROM suratkeluar";
                PreparedStatement ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
            } else {
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

    // Method untuk memfilter data berdasarkan Bagian
    public ResultSet KodeTampilByBagian(String filterBagian) {
        try {
            if (filterBagian == null || filterBagian.isEmpty()) {
                // Query tanpa filter
                query = "SELECT id_suratkeluar, kategori, bagian, nomor, tanggal_dibuat, perihal, tujuan, nama_file FROM suratkeluar";
                PreparedStatement ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
            } else {
                // Query dengan filter bagian
                query = "SELECT id_suratkeluar, kategori, bagian, nomor, tanggal_dibuat, perihal, tujuan, nama_file FROM suratkeluar WHERE bagian = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, filterBagian);
                rs = ps.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Method untuk memfilter data berdasarkan Tanggal
    public ResultSet KodeTampilByTanggal(java.sql.Date tanggal) {
        try {
            query = "SELECT id_suratkeluar, kategori, bagian, nomor, tanggal_dibuat, perihal, tujuan, nama_file FROM suratkeluar WHERE tanggal_dibuat = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1, tanggal);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Method untuk menggabungkan filter
    public ResultSet KodeTampilByFilters(String filterKategori, String filterBagian, java.sql.Date filterTanggal) {
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT id_suratkeluar, kategori, bagian, nomor, tanggal_dibuat, perihal, tujuan, nama_file FROM suratkeluar WHERE 1=1");

            if (filterKategori != null && !filterKategori.isEmpty()) {
                queryBuilder.append(" AND kategori = ?");
            }
            if (filterBagian != null && !filterBagian.isEmpty()) {
                queryBuilder.append(" AND bagian = ?");
            }
            if (filterTanggal != null) {
                queryBuilder.append(" AND tanggal_dibuat = ?");
            }

            PreparedStatement ps = conn.prepareStatement(queryBuilder.toString());

            int index = 1;
            if (filterKategori != null && !filterKategori.isEmpty()) {
                ps.setString(index++, filterKategori);
            }
            if (filterBagian != null && !filterBagian.isEmpty()) {
                ps.setString(index++, filterBagian);
            }
            if (filterTanggal != null) {
                ps.setDate(index++, filterTanggal);
            }

            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Method untuk menampilkan jumlah Surat Keluar
    public int TampilJumlahBagian() {
        query = "SELECT COUNT(*) AS jumlah FROM suratkeluar";

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

    // Method untuk membuat nomor id otomatis (autoId)
    public int autoId() {
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

    // Method untuk mengambil nomor urut terakhir berdasarkan bagian
    public int getNoMax() {
        int maxNo = 0;

        try {
            String query = "SELECT MAX(no_urut) AS max_no FROM bagian";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                maxNo = rs.getInt("max_no");
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal mengambil angka tertinggi!", null, JOptionPane.ERROR_MESSAGE, 3000);
            e.printStackTrace();
        }

        return maxNo;
    }

    // Method untuk membuka File
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
