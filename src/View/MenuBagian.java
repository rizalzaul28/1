/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Kelas.Bagian;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import ViewPopUp.PopUpTambahBagian;

/**
 *
 * @author rizan
 */
public class MenuBagian extends javax.swing.JPanel implements Bagian.DataChangeListener {

    /**
     * Creates new form MenuBagian
     */
    private Bagian bagian;

    public MenuBagian() throws SQLException {
        initComponents();
        bagian = new Bagian();
        bagian.addDataChangeListener(this);
        loadTabel();
    }

    public void loadTabel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kode Kategori Surat");
        model.addColumn("Nama Kategori Surat");
        model.addColumn("Nomor Urut");

        try {
            Bagian bg = new Bagian();
            ResultSet data = bg.KodeTampil();

            while (data.next()) {
                model.addRow(new Object[]{
                    data.getString("id"),
                    data.getString("kode_bagian"),
                    data.getString("nama_bagian"),
                    data.getString("no_urut"),});
            }

            data.close();
        } catch (SQLException sQLException) {
        }

        tb_Bagian.setModel(model);

        tb_Bagian.getColumnModel().getColumn(0).setMinWidth(0);
        tb_Bagian.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_Bagian.getColumnModel().getColumn(0).setWidth(0);

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Bagian = new javax.swing.JTable();
        bt_Tambah = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Sitka Display", 1, 24)); // NOI18N
        jLabel1.setText("Menu Bagian Surat");

        tb_Bagian.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_Bagian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_BagianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_Bagian);

        bt_Tambah.setText("Tambah Bagian Surat");
        bt_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1092, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bt_Tambah)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addComponent(bt_Tambah)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void bt_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahActionPerformed
        try {
            PopUpTambahBagian popUp = new PopUpTambahBagian(null, true, bagian);
            popUp.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MenuBagian.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_bt_TambahActionPerformed

    private void tb_BagianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_BagianMouseClicked
        try {
            int baris = tb_Bagian.rowAtPoint(evt.getPoint());

            if (baris != -1 && tb_Bagian.getValueAt(baris, 0) != null) {
                String id = tb_Bagian.getValueAt(baris, 0).toString();
                String kode = tb_Bagian.getValueAt(baris, 1).toString();
                String nama = tb_Bagian.getValueAt(baris, 2).toString();
                String no = tb_Bagian.getValueAt(baris, 3).toString();
                PopUpTambahBagian tmk = new PopUpTambahBagian(null, true, bagian);
                PopUpTambahBagian.lb_Id.setText(id);
                PopUpTambahBagian.tf_Kode.setText(kode);
                PopUpTambahBagian.tf_Nama.setText(nama);
                PopUpTambahBagian.tf_No.setText(no);
                tmk.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuBagian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_BagianMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Bagian;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onDataChanged() {
        loadTabel();
    }
}
