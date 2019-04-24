/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliherbalpos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gen781
 */
public class user extends javax.swing.JDialog {
    Connection conn = new koneksi().connect();
    /**
     * Creates new form user
     */
    public user(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtIdKar.setEditable(false);
        txtNama.requestFocus();
        tampilUser();
        reset();
    }
    
    private void nomorAuto(){
        String sql= "SELECT MAX(right(idKar,2)) AS no FROM karyawan";
        try{ 
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.first() == false){
                    txtIdKar.setText("dh001");
                }
                else{
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String nomor = String.valueOf(auto_id);
                    int pjgNomor = nomor.length();
                    for(int a=0;a<3-pjgNomor;a++){
                        nomor = "0" + nomor;
                    }
                    txtIdKar.setText("dh" + nomor);
                }       
            }
        }   
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),"Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    } 
    
    private void reset(){
        txtNama.setText("");
        cmbJabatan.setSelectedIndex(0);
        pwdPass.setText("");
        tblDataUser.clearSelection();
        nomorAuto();
    }
    
    private void tampilUser(){
        Object header[]={"ID Karyawan","Nama","Jabatan","Password"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tblDataUser.setModel(data);
        
        String sql = "SELECT * FROM karyawan";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
               
                String kolom[] = {kolom1, kolom2, kolom3, kolom4};
                data.addRow(kolom);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"error :"+e.getMessage());
        }
    }
    
    private void tambahUser(){
        if (txtIdKar.getText().equals("")||(txtNama.getText().equals(""))||cmbJabatan.getSelectedItem().equals("------Pilih------")||(pwdPass.getText().equals(""))){
	    JOptionPane.showMessageDialog(this, "Data belum lengkap, silahkan lengkapi data terlebih dahulu","Perhatian",JOptionPane.WARNING_MESSAGE);
            txtNama.requestFocus();
        } 
        else {
            String idkar =this.txtIdKar.getText();
            String nama=this.txtNama.getText();
            String jabatan=(String)this.cmbJabatan.getSelectedItem();
            String pass=this.pwdPass.getText();
            String sql = "INSERT INTO karyawan (idKar,nmKar,jabatan,pass)VALUES(?,?,?,?)";
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,idkar);
                ps.setString(2,nama);
                ps.setString(3,jabatan);
                ps.setString(4,pass);
                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null,"Data berhasil ditambahkan","Informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilUser();
            
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }
            reset();
        }
    }  
    
    
    private void ubahUser(){
        if (tblDataUser.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih data pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            if (txtIdKar.getText().equals("")||(txtNama.getText().equals(""))||cmbJabatan.getSelectedItem().equals("------Pilih------")||(pwdPass.getText().equals(""))){
                JOptionPane.showMessageDialog(this, "Data belum lengkap, silahkan lengkapi data terlebih dahulu","Perhatian",JOptionPane.WARNING_MESSAGE);
                txtNama.requestFocus();
            } 
            else{
                String sql = "UPDATE karyawan SET nmKar = ?, jabatan = ?, pass = ? WHERE idKar=?";
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, txtNama.getText());
                    ps.setString(2, cmbJabatan.getSelectedItem().toString());
                    ps.setString(3, pwdPass.getText());
                    ps.setString(4, tblDataUser.getValueAt(tblDataUser.getSelectedRow(),0).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil diubah","Informasi",JOptionPane.INFORMATION_MESSAGE);
                    tampilUser();
                } 
                catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Data gagal diubah",null,JOptionPane.ERROR_MESSAGE);
                }
                reset();
            }
        }
    }
    
    private void hapusUser(){
        if (tblDataUser.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih data pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            int ok=JOptionPane.showConfirmDialog(null,"Apakah yakin untuk menghapus data?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            String sql = "DELETE FROM karyawan WHERE idKar=?";
            if(ok==0) {
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, tblDataUser.getValueAt(tblDataUser.getSelectedRow(),0).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
                    tampilUser();
                } 
                catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Data gagal dihapus",null,JOptionPane.ERROR_MESSAGE);
                }
                reset();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIdKar = new javax.swing.JLabel();
        txtIdKar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblJabatan = new javax.swing.JLabel();
        cmbJabatan = new javax.swing.JComboBox();
        lblPass = new javax.swing.JLabel();
        pwdPass = new javax.swing.JPasswordField();
        btnTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataUser = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lblIdKar.setText("ID Karyawan");

        txtIdKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdKarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Data Karyawan");

        lblNama.setText("Nama");

        lblJabatan.setText("Jabatan");

        cmbJabatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "------Pilih------", "Pemilik", "Karyawan" }));

        lblPass.setText("Password");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        tblDataUser.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDataUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataUserMouseClicked(evt);
            }
        });
        tblDataUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDataUserKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDataUserKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataUser);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jButton1.setText("Tutup");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNama)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIdKar)
                                    .addComponent(lblJabatan)
                                    .addComponent(lblPass))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdKar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(pwdPass, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbJabatan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIdKar)
                            .addComponent(txtIdKar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNama)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblJabatan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPass)
                            .addComponent(pwdPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        tambahUser();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahUser();
        
    }//GEN-LAST:event_btnUbahActionPerformed

    private void tblDataUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataUserMouseClicked
        // TODO add your handling code here:
        txtIdKar.setText(tblDataUser.getValueAt(tblDataUser.getSelectedRow(),0).toString());
        txtNama.setText(tblDataUser.getValueAt(tblDataUser.getSelectedRow(),1).toString());
        cmbJabatan.setSelectedItem(tblDataUser.getValueAt(tblDataUser.getSelectedRow(),2).toString());
        pwdPass.setText(tblDataUser.getValueAt(tblDataUser.getSelectedRow(),3).toString());
    }//GEN-LAST:event_tblDataUserMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusUser();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtIdKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdKarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdKarActionPerformed

    private void tblDataUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDataUserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDataUserKeyPressed

    private void tblDataUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDataUserKeyReleased
        // TODO add your handling code here:
        txtIdKar.setText(tblDataUser.getValueAt(tblDataUser.getSelectedRow(),0).toString());
        txtNama.setText(tblDataUser.getValueAt(tblDataUser.getSelectedRow(),1).toString());
        cmbJabatan.setSelectedItem(tblDataUser.getValueAt(tblDataUser.getSelectedRow(),2).toString());
        pwdPass.setText(tblDataUser.getValueAt(tblDataUser.getSelectedRow(),3).toString());
    }//GEN-LAST:event_tblDataUserKeyReleased

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
            java.util.logging.Logger.getLogger(user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(user.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                user dialog = new user(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbJabatan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblIdKar;
    private javax.swing.JLabel lblJabatan;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblPass;
    private javax.swing.JPasswordField pwdPass;
    private javax.swing.JTable tblDataUser;
    private javax.swing.JTextField txtIdKar;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
