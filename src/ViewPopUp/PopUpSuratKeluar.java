/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ViewPopUp;

import Kelas.Bagian;
import Kelas.Kategori;
import Kelas.SuratKeluar;
import Kelas.TimedJOptionPane;
import View.MenuSuratKeluar;
import static ViewPopUp.PopUpTambahBagian.tf_No;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author rizan
 */
public class PopUpSuratKeluar extends javax.swing.JDialog {

    private SuratKeluar suratKeluar;
    private MenuSuratKeluar menuSuratKeluar;

    /**
     * Creates new form PopUpSuratMasuk
     */
    public PopUpSuratKeluar(java.awt.Frame parent, boolean modal, SuratKeluar suratKeluar) throws SQLException {
        super(parent, modal);
        initComponents();
        this.suratKeluar = suratKeluar;

        tf_Tgl.setDate(new Date());
        cbKategoriSurat();
        cbBagianSurat();

        cb_Kategori.addActionListener(evt -> updateNoUrut());
        cb_Bagian.addActionListener(evt -> updateNoUrut()); 

        autoId();
    }

    void cbKategoriSurat() {
        try {
            cb_Kategori.removeAllItems();
            cb_Kategori.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_Kategori.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_Kategori.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            cb_Bagian.removeAllItems();
            cb_Bagian.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                String kodeBagian = data.getString("kode_bagian");
                String namaBagian = data.getString("nama_bagian");
                cb_Bagian.addItem(kodeBagian + " - " + namaBagian);
            }

            if (cb_Bagian.getItemCount() > 1) {
                cb_Bagian.setSelectedIndex(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data Bagian: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateNoUrut() {
        try {
            // Periksa jika tidak ada kategori atau bagian yang dipilih
            if (cb_Kategori.getSelectedItem() == null || cb_Bagian.getSelectedItem() == null) {
                tf_NoUrut.setText("");
                return;
            }

            String kategori = cb_Kategori.getSelectedItem().toString();
            String bagian = cb_Bagian.getSelectedItem().toString();

            // Pastikan kategori dan bagian valid
            if (kategori.equals("--Pilih Kategori Surat--") || bagian.equals("--Pilih Bagian Surat--")) {
                tf_NoUrut.setText("");
                return;
            }

            // Ambil kode kategori dan kode bagian dari item yang dipilih
            String kodeKategori = kategori.split(" - ")[0];
            String kodeBagian = bagian.split(" - ")[0];

            // Ambil nomor urut terakhir untuk bagian yang dipilih
            SuratKeluar bg = new SuratKeluar();
            int noUrut = bg.getNoMax(); // Sesuai metode AmbilNoSurat yang sudah diperbaiki

            // Ambil tanggal untuk format nomor surat
            Date tanggalSekarang = tf_Tgl.getDate();
            if (tanggalSekarang == null) {
                tf_NoUrut.setText("");
                return;
            }

            int bulan = tanggalSekarang.getMonth() + 1;
            String bulanRomawi = getRomawi(bulan);
            int tahun = tanggalSekarang.getYear() + 1900;

            // Format nomor urut sesuai dengan template
            String formattedNoUrut = String.format("%02d.%03d/%s/%s/%d",
                    Integer.parseInt(kodeKategori), noUrut, kodeBagian, bulanRomawi, tahun);

            // Set nomor urut pada field
            tf_NoUrut.setText(formattedNoUrut);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memperbarui nomor urut: " + e.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getRomawi(int bulan) {
        String[] bulanRomawi = {"I", "II",
            "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
        return bulanRomawi[bulan - 1];
    }

    public void refreshCbBagian(String selectedBagian) {
        try {
            cb_Bagian.removeAllItems();
            cb_Bagian.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                String kodeBagian = data.getString("kode_bagian");
                String namaBagian = data.getString("nama_bagian");
                String item
                        = kodeBagian + " - " + namaBagian;
                cb_Bagian.addItem(item);

                if (selectedBagian != null && kodeBagian.equals(selectedBagian)) {
                    cb_Bagian.setSelectedItem(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagalmemperbarui data Bagian:" + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE
            );
        }
    } 

    public void refreshCbKategori(String selectedBagian) {
        try {
            cb_Kategori.removeAllItems();
            cb_Kategori.addItem("--Pilih Bagian Surat--");

            Kategori bg = new Kategori();
            ResultSet data
                    = bg.Tampil_CbKategoriSurat();

            while (data.next()) {
                String kodeKategori
                        = data.getString("kode_kategori");
                String namaKategori
                        = data.getString("nama_kategori");
                String item = kodeKategori + " - "
                        + namaKategori;
                cb_Kategori.addItem(item);

                if (selectedBagian != null && kodeKategori.equals(selectedBagian)) {
                    cb_Kategori.setSelectedItem(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagalmemperbarui data Kategori:" + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cb_Kategori = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cb_Bagian = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        tf_NoUrut = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tf_Tgl = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        tf_Perihal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tf_Tujuan = new javax.swing.JTextField();
        tf_Upload = new javax.swing.JTextField();
        lb_Id = new javax.swing.JLabel();
        bt_Salin = new javax.swing.JButton();
        bt_Simpan = new javax.swing.JButton();
        bt_Ubah = new javax.swing.JButton();
        bt_Hapus = new javax.swing.JButton();
        bt_Lihat = new javax.swing.JButton();
        bt_Upload = new javax.swing.JButton();
        bt_Close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel1.setText("Form Tambah Surat Keluar");

        jLabel2.setText("Kategori");

        jLabel7.setText("Bagian");

        jLabel3.setText("Nomor");

        jLabel4.setText("Tanggal Dibuat");

        jLabel5.setText("Perihal");

        jLabel8.setText("Tujuan");

        bt_Salin.setText("Salin");
        bt_Salin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SalinActionPerformed(evt);
            }
        });

        bt_Simpan.setText("Simpan");
        bt_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SimpanActionPerformed(evt);
            }
        });

        bt_Ubah.setText("Ubah");
        bt_Ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_UbahActionPerformed(evt);
            }
        });

        bt_Hapus.setText("Hapus");
        bt_Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_HapusActionPerformed(evt);
            }
        });

        bt_Lihat.setText("Lihat Surat");
        bt_Lihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_LihatActionPerformed(evt);
            }
        });

        bt_Upload.setText("Upload File");
        bt_Upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_UploadActionPerformed(evt);
            }
        });

        bt_Close.setText("Close");
        bt_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_CloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(bt_Upload)
                            .addComponent(bt_Close))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_Id)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tf_Tgl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tf_Perihal, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tf_Upload, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(bt_Simpan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_Ubah)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_Hapus)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bt_Lihat))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cb_Bagian, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cb_Kategori, javax.swing.GroupLayout.Alignment.LEADING, 0, 160, Short.MAX_VALUE)))
                                .addComponent(tf_Tujuan, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tf_NoUrut, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_Salin)))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cb_Kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cb_Bagian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tf_NoUrut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_Salin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_Tgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tf_Perihal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tf_Tujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_Upload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_Upload))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Simpan)
                    .addComponent(bt_Ubah)
                    .addComponent(bt_Hapus)
                    .addComponent(bt_Lihat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Close)
                    .addComponent(lb_Id))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_CloseActionPerformed
        dispose();
    }//GEN-LAST:event_bt_CloseActionPerformed

    private void bt_SalinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SalinActionPerformed
        try {
            String textToCopy = tf_NoUrut.getText();

            if (textToCopy != null && !textToCopy.isEmpty()) {
                java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(textToCopy);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Nomor urut telah disalin ke Clipboard.", "Berhasil", JOptionPane.INFORMATION_MESSAGE, 1000);
            } else {
                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Nomor urut kosong, tidak ada yang disalin.", "Kesalahan", JOptionPane.ERROR_MESSAGE, 2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyalin teks.", "Kesalahan", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_SalinActionPerformed

    private void bt_UploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_UploadActionPerformed
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            String targetFolderPath = "File/FileSuratKeluar";
            File targetFolder = new File(targetFolderPath);

            if (!targetFolder.exists()) {
                if (!targetFolder.mkdirs()) {
                    System.out.println("Gagal membuat folder FileSurat!");
                    return;
                }
            }

            String fileBaseName = tf_NoUrut.getText().replace(".", "_").replace("/", "_");
            String fileExtension = getFileExtension(selectedFile.getName());

            File targetFile = new File(targetFolderPath, fileBaseName + fileExtension);
            int count = 1;
            while (targetFile.exists()) {
                targetFile = new File(targetFolderPath, fileBaseName + "(" + count + ")" + fileExtension);
                count++;
            }

            try {
                Files.copy(selectedFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                tf_Upload.setText(targetFile.getAbsolutePath());
            } catch (IOException e) {
                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Gagal mengunggah file", null, JOptionPane.ERROR_MESSAGE, 1000);
            }
        }
    }//GEN-LAST:event_bt_UploadActionPerformed

    private void bt_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SimpanActionPerformed
        try {
            SuratKeluar kodetambah = new SuratKeluar();

            kodetambah.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

            String kategori = cb_Kategori.getSelectedItem().toString();
            if (!kategori.equals("--Pilih Kategori Surat--")) {
                kodetambah.setKategori(kategori.split(" - ")[0]);
            } else {

                JOptionPane.showMessageDialog(this, "Pilih kategori surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String bagian = cb_Bagian.getSelectedItem().toString();
            if (!bagian.equals("--Pilih Bagian Surat--")) {
                kodetambah.setBagian(bagian.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih bagian surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nomorSurat = tf_NoUrut.getText();
            if (nomorSurat != null && !nomorSurat.isEmpty()) {
                kodetambah.setNomor(nomorSurat);
            } else {
                JOptionPane.showMessageDialog(this, "Nomor surat tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.util.Date tanggalDibuatUtil = tf_Tgl.getDate();
            if (tanggalDibuatUtil != null) {
                java.sql.Date tanggalDibuatSQL = new java.sql.Date(tanggalDibuatUtil.getTime());
                kodetambah.setTanggal_dibuat(tanggalDibuatSQL);
            } else {
                JOptionPane.showMessageDialog(this, "Tanggal dibuat tidak valid!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String perihal = tf_Perihal.getText();
            if (perihal != null && !perihal.isEmpty()) {
                kodetambah.setPerihal(perihal);
            } else {
                JOptionPane.showMessageDialog(this, "Perihal tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tujuan = tf_Tujuan.getText();
            if (tujuan != null && !tujuan.isEmpty()) {
                kodetambah.setTujuan(tujuan);
            } else {
                JOptionPane.showMessageDialog(this, "Tujuan tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            File file = new File(tf_Upload.getText());
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                String fileExtension = getFileExtension(file.getName());
                String formattedNamaFile = nomorSurat.replace(".", "_").replace("/", "_") + fileExtension;
                kodetambah.setFile(fis);
                kodetambah.setNama_file(formattedNamaFile);
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            kodetambah.KodeTambah();
            autoId();

            MenuSuratKeluar kt = new MenuSuratKeluar();
            kt.loadTabel();
            suratKeluar.notifyDataChanged();

            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Gagal menyimpan surat keluar: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_SimpanActionPerformed

    private void bt_UbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_UbahActionPerformed
        try {
            SuratKeluar kodeUbah = new SuratKeluar();

            int idSuratKeluar = Integer.parseInt(lb_Id.getText());
            SuratKeluar dataLama = kodeUbah.getDataLama(idSuratKeluar);

            kodeUbah.setId_suratkeluar(idSuratKeluar);

            String kategori = cb_Kategori.getSelectedItem().toString();
            if (!kategori.equals("--Pilih Kategori Surat--")) {
                kodeUbah.setKategori(kategori.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih kategori surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String bagian = cb_Bagian.getSelectedItem().toString();
            if (!bagian.equals("--Pilih Bagian Surat--")) {
                kodeUbah.setBagian(bagian.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih bagian surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.util.Date tanggalDibuatUtil = tf_Tgl.getDate();
            if (tanggalDibuatUtil != null) {
                java.sql.Date tanggalDibuatSQL = new java.sql.Date(tanggalDibuatUtil.getTime());
                kodeUbah.setTanggal_dibuat(tanggalDibuatSQL);
            } else {
                JOptionPane.showMessageDialog(this, "Tanggal tidak valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            kodeUbah.setPerihal(tf_Perihal.getText());
            kodeUbah.setTujuan(tf_Tujuan.getText());

            File file = new File(tf_Upload.getText().trim());
            if (file.exists() && file.isFile()) {
                FileInputStream fis = new FileInputStream(file);

                // Ambil nama file lama tanpa ekstensi
                String namaFileLama = dataLama.getNama_file();
                if (namaFileLama != null && namaFileLama.contains(".")) {
                    namaFileLama = namaFileLama.substring(0, namaFileLama.lastIndexOf("."));
                }

                // Ambil ekstensi file baru
                String fileExtension = getFileExtension(file.getName());

                // Gabungkan nama file lama dengan ekstensi file baru
                String namaFileBaru = namaFileLama + "." + fileExtension;

                // Set file baru dan nama file baru
                kodeUbah.setFile(fis);
                kodeUbah.setNama_file(namaFileBaru);
            } else {
                // Jika file tidak diubah, tetap gunakan file lama
                String namaFileLama = dataLama.getNama_file();
                if (namaFileLama != null) {
                    kodeUbah.setNama_file(namaFileLama);
                } else {
                    JOptionPane.showMessageDialog(this, "Nama file lama tidak ditemukan!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Panggil method untuk update data
            kodeUbah.KodeUbah();

            // Perbarui tabel setelah pembaruan
            MenuSuratKeluar ku = new MenuSuratKeluar();
            ku.loadTabel();
            suratKeluar.notifyDataChanged();

            // Tutup dialog
            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Gagal mengubah surat: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "File tidak ditemukan: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_UbahActionPerformed

    private void bt_HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_HapusActionPerformed
        try {
            if (lb_Id.getText().isEmpty()) {
                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Pilih data yang ingin dihapus!", null, JOptionPane.WARNING_MESSAGE, 1000);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                SuratKeluar kodeHapus = new SuratKeluar();
                kodeHapus.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

                kodeHapus.KodeHapus();

                MenuSuratKeluar kh = new MenuSuratKeluar();
                kh.loadTabel();
                suratKeluar.notifyDataChanged();
                dispose();

            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_bt_HapusActionPerformed

    private void bt_LihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_LihatActionPerformed
//        try {
//            String namaFile = tf_Upload.getText();
//            if (namaFile == null || namaFile.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Nama file tidak tersedia.", "Kesalahan", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            SuratKeluar suratKeluar = new SuratKeluar();
//            suratKeluar.setNama_file(namaFile);
//            byte[] fileData = suratKeluar.BukaFile();
//
//            if (fileData != null) {
//                File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + namaFile);
//                FileOutputStream fos = new FileOutputStream(tempFile);
//                fos.write(fileData);
//                fos.close();
//
//                if (Desktop.isDesktopSupported()) {
//                    Desktop.getDesktop().open(tempFile);
//                } else {
//                    JOptionPane.showMessageDialog(this, "Desktop tidak didukung pada sistem ini.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "File tidak ditemukan untuk surat ini.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuka file: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
//        }
try {
            String filePath = tf_Upload.getText().trim();
            File file = new File(filePath);

            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_LihatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SuratKeluar suratKeluar = new SuratKeluar();
                    new PopUpSuratKeluar(null, true, suratKeluar).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Close;
    private javax.swing.JButton bt_Hapus;
    private javax.swing.JButton bt_Lihat;
    private javax.swing.JButton bt_Salin;
    private javax.swing.JButton bt_Simpan;
    private javax.swing.JButton bt_Ubah;
    private javax.swing.JButton bt_Upload;
    public static javax.swing.JComboBox<String> cb_Bagian;
    public static javax.swing.JComboBox<String> cb_Kategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lb_Id;
    public static javax.swing.JTextField tf_NoUrut;
    public static javax.swing.JTextField tf_Perihal;
    public static com.toedter.calendar.JDateChooser tf_Tgl;
    public static javax.swing.JTextField tf_Tujuan;
    public static javax.swing.JTextField tf_Upload;
    // End of variables declaration//GEN-END:variables

    private void autoId() throws SQLException {
        SuratKeluar auto = new SuratKeluar();
        int newID = auto.autoId();
        lb_Id.setText(String.valueOf(newID));

    }

}
