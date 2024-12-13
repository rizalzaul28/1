package Main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Icon;

public class MenuItem extends javax.swing.JPanel {

    public ArrayList<MenuItem> getSubMenu() {
        return subMenu;
    }

    private final ArrayList<MenuItem> subMenu = new ArrayList<>();
    private ActionListener act;

    public MenuItem(Icon icon, boolean sbm, Icon iconSub, String Dasboard, ActionListener act, MenuItem... subMenu) {
        initComponents();

        lb_Icon.setIcon(icon);
        lb_IconSub.setIcon(iconSub);
        lb_Text.setText(Dasboard);
        lb_IconSub.setVisible(sbm);

        if (act != null) {
            this.act = act;

        }
        this.setSize(new Dimension(Integer.MAX_VALUE, 45));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 45));
        for (int i = 0; i < subMenu.length; i++) {
            this.subMenu.add(subMenu[i]);
            subMenu[i].setVisible(false);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        pn_Dasar = new javax.swing.JPanel();
        lb_Icon = new javax.swing.JLabel();
        lb_Text = new javax.swing.JLabel();
        lb_IconSub = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        pn_Dasar.setBackground(new java.awt.Color(0, 0, 255));
        pn_Dasar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pn_DasarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pn_DasarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pn_DasarMousePressed(evt);
            }
        });

        lb_Icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lb_Text.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N

        lb_IconSub.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout pn_DasarLayout = new javax.swing.GroupLayout(pn_Dasar);
        pn_Dasar.setLayout(pn_DasarLayout);
        pn_DasarLayout.setHorizontalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addComponent(lb_Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_IconSub, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_Text, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_DasarLayout.setVerticalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_Icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_IconSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Text, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_Dasar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_Dasar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private boolean showing = false;

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (showing) {
            hideMenu();
        } else {
            showMenu();
        }
        if (act != null) {
            act.actionPerformed(null);
        }
    }//GEN-LAST:event_formMousePressed

    private void pn_DasarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pn_DasarMouseEntered
        pn_Dasar.setBackground(new Color(51, 51, 255));
    }//GEN-LAST:event_pn_DasarMouseEntered

    private void pn_DasarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pn_DasarMouseExited
        pn_Dasar.setBackground(new Color(0, 0, 255));
    }//GEN-LAST:event_pn_DasarMouseExited

    private void pn_DasarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pn_DasarMousePressed
        if (showing) {
            hideMenu();
        } else {
            showMenu();
        }
        if (act != null) {
            act.actionPerformed(null);
        }
    }//GEN-LAST:event_pn_DasarMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lb_Icon;
    private javax.swing.JLabel lb_IconSub;
    private javax.swing.JLabel lb_Text;
    private javax.swing.JPanel pn_Dasar;
    // End of variables declaration//GEN-END:variables
        private void hideMenu() {
        if (!showing) {
            return;
        }
        new Thread(() -> {
            for (int i = subMenu.size() - 1; i >= 0; i--) {
                sleep();
                subMenu.get(i).setVisible(false);
                subMenu.get(i).hideMenu();
            }
            getParent().repaint();
            getParent().revalidate();
            showing = false;
        }).start();
    }

        private void showMenu() {
        if (showing) {
            return;
        }
        showing = true;
        new Thread(() -> {
            for (int i = 0; i < subMenu.size(); i++) {
                sleep();
                subMenu.get(i).setVisible(true);
            }
            getParent().repaint();
            getParent().revalidate();
        }).start();
    }

        private void sleep() {
        try {
            Thread.sleep(20);
        } catch (Exception e) {
        }
    }
        
        

}
