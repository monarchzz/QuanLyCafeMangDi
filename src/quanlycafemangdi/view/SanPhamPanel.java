/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycafemangdi.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import quanlycafemangdi.Util;
import quanlycafemangdi.model.SanPham;

/**
 *
 * @author monar
 */
public class SanPhamPanel extends javax.swing.JPanel {

    /**
     * Creates new form SanPhamPanel
     */
    public static final int BAN_HANG = 0;
    public static final int CONG_THUC = 1;
    public static final int DANG_KY = 2;
    
    private final IOnClickSPPanel onClickSPPanel;
    private final SanPham sanPham;
    private final int type;
    
    public interface IOnClickSPPanel {
        public void onClickItem(SanPham sanPham, int type);
    }
    
    public SanPhamPanel(SanPham sanPham, int type, IOnClickSPPanel onClickSPPanel) {
        initComponents();
        
        this.sanPham = sanPham;
        this.type = type; 
        this.onClickSPPanel = onClickSPPanel;
       
        switch(type){
            case BAN_HANG -> taoSanPham();
            case CONG_THUC -> taoCongThuc();
            case DANG_KY -> taoCongThuc();
        }
        
        
    }
    
    private void taoSanPham(){
        tenSPLb.setText( sanPham.getTenSP() );
        giaLb.setText(Util.formatCurrency( sanPham.getGia()) );
    }
    private void taoCongThuc(){
        tenSPLb.setText(sanPham.getTenSP());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tenSPLb = new javax.swing.JLabel();
        giaLb = new javax.swing.JLabel();

        setBackground(new java.awt.Color(241, 248, 233));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setFocusCycleRoot(true);
        setFocusTraversalPolicyProvider(true);
        setInheritsPopupMenu(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        tenSPLb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tenSPLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tenSPLb.setPreferredSize(new java.awt.Dimension(40, 15));

        giaLb.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        giaLb.setForeground(new java.awt.Color(255, 0, 0));
        giaLb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        giaLb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(giaLb, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(tenSPLb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tenSPLb, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(giaLb)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        this.setBackground(new Color(251, 233, 231));
        Timer timer = new Timer(70, (ActionEvent e) -> {
            setBackground(new Color(241,248,233));
        });
        timer.setRepeats(false);
        timer.start();
        
        onClickSPPanel.onClickItem(sanPham, type);
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel giaLb;
    private javax.swing.JLabel tenSPLb;
    // End of variables declaration//GEN-END:variables
}
