/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Kelas.Bagian;
import Kelas.Kategori;
import Kelas.SuratKeluar;
import ViewPopUp.PopUpSuratKeluar;
import static ViewPopUp.PopUpSuratKeluar.lb_Id;
import static ViewPopUp.PopUpSuratKeluar.cb_Bagian;
import static ViewPopUp.PopUpSuratKeluar.cb_Kategori;
import static ViewPopUp.PopUpSuratKeluar.tf_NoUrut;
import static ViewPopUp.PopUpSuratKeluar.tf_Perihal;
import static ViewPopUp.PopUpSuratKeluar.tf_Tujuan;
import static ViewPopUp.PopUpSuratKeluar.tf_Upload;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rizan
 */
public class MenuSuratKeluar extends javax.swing.JPanel implements SuratKeluar.DataChangeListener {

    /**
     * Creates new form MenuSuratKeluar
     */
    private SuratKeluar suratKeluar;

    public MenuSuratKeluar() throws SQLException {
        initComponents();
        suratKeluar = new SuratKeluar();
        suratKeluar.addDataChangeListener(this);
        loadTabel();
        cbKategoriSurat();
        cbBagianSurat();

        cb_KategoriMenu.addActionListener(evt -> filterTabelByKategori());
    }

    public void loadTabel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            SuratKeluar bg = new SuratKeluar();
            try (ResultSet data = bg.KodeTampil(null)) {
                while (data.next()) {
                    model.addRow(new Object[]{
                        data.getString("id_suratkeluar"),
                        data.getString("kategori"),
                        data.getString("bagian"),
                        data.getString("nomor"),
                        data.getString("tanggal_dibuat"),
                        data.getString("perihal"),
                        data.getString("tujuan"),
                        data.getString("nama_file"),});
                }
            }
        } catch (SQLException sQLException) {
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);

    }

    void cbKategoriSurat() {
        try {
            cb_KategoriMenu.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_KategoriMenu.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_KategoriMenu.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            cb_BagianMenu.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_BagianMenu.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_BagianMenu.setSelectedItem("--Pilih Bagian Surat--"); // Pilih default option
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterTabelByKategori() {
        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
        if (selectedKategori.equals("--Pilih Kategori Surat--")) {
            loadTabel();
            return;
        }

        String kodeKategori = selectedKategori.split(" - ")[0]; // Ambil kode kategori
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            SuratKeluar bg = new SuratKeluar();
            ResultSet data = bg.KodeTampil(kodeKategori); // Anda dapat menyesuaikan query di KodeTampil() untuk menerima parameter filter

            while (data.next()) {
                if (data.getString("kategori").equals(kodeKategori)) {
                    model.addRow(new Object[]{
                        data.getString("id_suratkeluar"),
                        data.getString("kategori"),
                        data.getString("bagian"),
                        data.getString("nomor"),
                        data.getString("tanggal_dibuat"),
                        data.getString("perihal"),
                        data.getString("tujuan"),
                        data.getString("nama_file"),});
                }
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    }

  /**  private void filterTabelByBagian(java.awt.event.ActionEvent evt) {
        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();

        if (selectedBagian.equals("--Pilih Bagian Surat--")) {
            loadTabel(); // Menampilkan semua data jika tidak ada filter
        } else {
            String filterBagian = selectedBagian.split(" - ")[0]; // Ambil kode bagian saja
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn(null);
            model.addColumn("Kategori");
            model.addColumn("Bagian");
            model.addColumn("Nomor");
            model.addColumn("Tanggal");
            model.addColumn("Perihal");
            model.addColumn("Tujuan");
            model.addColumn("Nama File");

            try {
                SuratKeluar bg = new SuratKeluar();
                ResultSet data = bg.KodeTampilByBagian(filterBagian);

                while (data.next()) {
                    model.addRow(new Object[]{
                        data.getString("id_suratkeluar"),
                        data.getString("kategori"),
                        data.getString("bagian"),
                        data.getString("nomor"),
                        data.getString("tanggal_dibuat"),
                        data.getString("perihal"),
                        data.getString("tujuan"),
                        data.getString("nama_file"),});
                }

                data.close();
            } catch (SQLException sQLException) {
                sQLException.printStackTrace();
            }

            tb_SuratKeluar.setModel(model);

            tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
            tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
            tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
        }
    } */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_Main = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cb_KategoriMenu = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cb_BagianMenu = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        tf_Tgl = new com.toedter.calendar.JDateChooser();
        bt_Tambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_SuratKeluar = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        pn_Main.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel1.setText("Menu Surat Keluar");

        jLabel2.setText("Kategori");

        jLabel3.setText("Bagian");

        jLabel4.setText("Tanggal");

        bt_Tambah.setText("Tambah Surat Keluar");
        bt_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahActionPerformed(evt);
            }
        });

        tb_SuratKeluar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_SuratKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_SuratKeluarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_SuratKeluar);

        javax.swing.GroupLayout pn_MainLayout = new javax.swing.GroupLayout(pn_Main);
        pn_Main.setLayout(pn_MainLayout);
        pn_MainLayout.setHorizontalGroup(
            pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1092, Short.MAX_VALUE)
                    .addGroup(pn_MainLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pn_MainLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_Tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_Tambah)))
                .addContainerGap())
        );
        pn_MainLayout.setVerticalGroup(
            pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(72, 72, 72)
                .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_MainLayout.createSequentialGroup()
                        .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(bt_Tambah))
                            .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tf_Tgl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE))
                    .addGroup(pn_MainLayout.createSequentialGroup()
                        .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(pn_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void bt_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahActionPerformed
        try {
            PopUpSuratKeluar pusk = new PopUpSuratKeluar(null, true, suratKeluar);
            pusk.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MenuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_TambahActionPerformed

    private void tb_SuratKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_SuratKeluarMouseClicked
        try {

            PopUpSuratKeluar pusk = new PopUpSuratKeluar(null, true, suratKeluar);

            int baris = tb_SuratKeluar.getSelectedRow();
            if (baris != -1 && tb_SuratKeluar.getValueAt(baris, 0) != null) {

                String Id = tb_SuratKeluar.getValueAt(baris, 0).toString();
                String Kategori = tb_SuratKeluar.getValueAt(baris, 1).toString();
                String Bagian = tb_SuratKeluar.getValueAt(baris, 2).toString();
                String Nomor = tb_SuratKeluar.getValueAt(baris, 3).toString();
                String Tanggal = tb_SuratKeluar.getValueAt(baris, 4).toString();
                String Perihal = tb_SuratKeluar.getValueAt(baris, 5).toString();
                String Tujuan = tb_SuratKeluar.getValueAt(baris, 6).toString();
                String File = tb_SuratKeluar.getValueAt(baris, 7).toString();

                lb_Id.setText(Id);

                for (int i = 0; i < cb_Kategori.getItemCount(); i++) {
                }

                for (int i = 0; i < cb_Kategori.getItemCount(); i++) {
                    String item = cb_Kategori.getItemAt(i);
                    if (item.startsWith(Kategori + " -")) {
                        cb_Kategori.setSelectedIndex(i);
                        break;
                    }
                }
                pusk.refreshCbKategori(Kategori);

                for (int i = 0; i < cb_Bagian.getItemCount(); i++) {
                }

                for (int i = 0; i < cb_Bagian.getItemCount(); i++) {
                    String item = cb_Bagian.getItemAt(i);
                    if (item.startsWith(Bagian + " -")) {
                        cb_Bagian.setSelectedIndex(i);
                        break;
                    }
                }

                pusk.refreshCbBagian(Bagian);

                tf_NoUrut.setText(Nomor);

                if (Tanggal != null && !Tanggal.isEmpty()) {
                    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = formatter.parse(Tanggal);
                    tf_Tgl.setDate(date);
                }

                tf_Perihal.setText(Perihal);
                tf_Tujuan.setText(Tujuan);
                tf_Upload.setText(File);

                pusk.setVisible(true);
            }
        } catch (SQLException | java.text.ParseException ex) {
            Logger.getLogger(MenuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_SuratKeluarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Tambah;
    private javax.swing.JComboBox<String> cb_BagianMenu;
    private javax.swing.JComboBox<String> cb_KategoriMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pn_Main;
    private javax.swing.JTable tb_SuratKeluar;
    private com.toedter.calendar.JDateChooser tf_Tgl;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onDataChanged() {
        loadTabel();
    }

}
