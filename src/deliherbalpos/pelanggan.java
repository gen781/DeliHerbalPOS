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
public class pelanggan extends javax.swing.JDialog {
    Connection conn = new koneksi().connect();
    /**
     * Creates new form pelanggan
     */
    public pelanggan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtNamaPel.requestFocus();
        txtIDPel.setEditable(false);
        tampilPelanggan();
        reset();
    }
    
    private void nomorAuto(){
        String sql= "SELECT MAX(right(idPel,4)) AS no FROM pelanggan";
        try{ 
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.first() == false){
                    txtIDPel.setText("pdh00001");
                }
                else{
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String nomor = String.valueOf(auto_id);
                    int pjgNomor = nomor.length();
                    for(int a=0;a<5-pjgNomor;a++){
                        nomor = "0" + nomor;
                    }
                    txtIDPel.setText("pdh" + nomor);
                }       
            }
        }   
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),"Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    } 
    
    private void reset(){
        txtNamaPel.setText("");
        txtTelp.setText("");
        txtAlamat.setText("");
        tblDataPel.clearSelection();
        nomorAuto();
    }
    
    private void tampilPelanggan(){
        Object header[]={"ID Pelanggan","Nama","No.Telp/HP","Alamat"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tblDataPel.setModel(data);
        
        String sql = "SELECT * FROM pelanggan";
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
    
    private void tambahPelanggan(){
        if (txtIDPel.getText().equals("")||(txtNamaPel.getText().equals(""))||(txtTelp.getText().equals(""))||txtAlamat.getText().equals("")){
	    JOptionPane.showMessageDialog(this, "Data belum lengkap, silahkan lengkapi data terlebih dahulu","Perhatian",JOptionPane.WARNING_MESSAGE);
            txtIDPel.requestFocus();
        } 
        else {
            String idpel =this.txtIDPel.getText();
            String nama=this.txtNamaPel.getText();
            String telp=this.txtTelp.getText();
            String alamat=this.txtAlamat.getText();
            String sql = "INSERT INTO pelanggan (idPel,nmPel,telpPel,almtPel)VALUES(?,?,?,?)";
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,idpel);
                ps.setString(2,nama);
                ps.setString(3,telp);
                ps.setString(4,alamat);
                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null,"Data berhasil ditambahkan","Informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilPelanggan();
            
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }
            reset();
        }
    }  
    
    
    private void ubahPelanggan(){
        if (tblDataPel.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih data pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            if (txtIDPel.getText().equals("")||(txtNamaPel.getText().equals(""))||(txtTelp.getText().equals(""))||txtAlamat.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Data belum lengkap, silahkan lengkapi data terlebih dahulu","Perhatian",JOptionPane.WARNING_MESSAGE);
                txtIDPel.requestFocus();
            } 
            else{
                String sql = "UPDATE pelanggan SET nmPel=?, telpPel=?, almtPel=? WHERE idPel=?";
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, txtNamaPel.getText());
                    ps.setString(2, txtTelp.getText());
                    ps.setString(3, txtAlamat.getText());
                    ps.setString(4, tblDataPel.getValueAt(tblDataPel.getSelectedRow(),0).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil diubah","Informasi",JOptionPane.INFORMATION_MESSAGE);
                    tampilPelanggan();
                } 
                catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Data gagal diubah",null,JOptionPane.ERROR_MESSAGE);
                }
                reset();
            }
        }
    }
    
    private void hapusPelanggan(){
        if (tblDataPel.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih data pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            int ok=JOptionPane.showConfirmDialog(null,"Apakah yakin untuk menghapus data?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            String sql = "DELETE FROM pelanggan WHERE idPel=?";
            if(ok==0) {
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, tblDataPel.getValueAt(tblDataPel.getSelectedRow(),0).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
                    tampilPelanggan();
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

        lblIDPel = new javax.swing.JLabel();
        txtIDPel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblNamaPel = new javax.swing.JLabel();
        txtNamaPel = new javax.swing.JTextField();
        lblTelp = new javax.swing.JLabel();
        txtTelp = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataPel = new javax.swing.JTable();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblAlamat = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lblIDPel.setText("ID Pelanggan");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Data Pelanggan");

        lblNamaPel.setText("Nama");

        lblTelp.setText("No.Telp/HP");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        tblDataPel.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDataPel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataPelMouseClicked(evt);
            }
        });
        tblDataPel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDataPelKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataPel);

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

        lblAlamat.setText("Alamat");

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane2.setViewportView(txtAlamat);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNamaPel)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIDPel)
                                    .addComponent(lblAlamat)
                                    .addComponent(lblTelp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIDPel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNamaPel)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIDPel)
                            .addComponent(txtIDPel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNamaPel)
                            .addComponent(txtNamaPel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelp))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAlamat)))
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
        tambahPelanggan();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahPelanggan();
        
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusPelanggan();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblDataPelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDataPelKeyReleased
        // TODO add your handling code here:
        txtIDPel.setText(tblDataPel.getValueAt(tblDataPel.getSelectedRow(),0).toString());
        txtNamaPel.setText(tblDataPel.getValueAt(tblDataPel.getSelectedRow(),1).toString());
        txtTelp.setText(tblDataPel.getValueAt(tblDataPel.getSelectedRow(),2).toString());
        txtAlamat.setText(tblDataPel.getValueAt(tblDataPel.getSelectedRow(),3).toString());
    }//GEN-LAST:event_tblDataPelKeyReleased

    private void tblDataPelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataPelMouseClicked
        // TODO add your handling code here:
        txtIDPel.setText(tblDataPel.getValueAt(tblDataPel.getSelectedRow(),0).toString());
        txtNamaPel.setText(tblDataPel.getValueAt(tblDataPel.getSelectedRow(),1).toString());
        txtTelp.setText(tblDataPel.getValueAt(tblDataPel.getSelectedRow(),2).toString());
        txtAlamat.setText(tblDataPel.getValueAt(tblDataPel.getSelectedRow(),3).toString());
    }//GEN-LAST:event_tblDataPelMouseClicked

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
            java.util.logging.Logger.getLogger(pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                pelanggan dialog = new pelanggan(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblIDPel;
    private javax.swing.JLabel lblNamaPel;
    private javax.swing.JLabel lblTelp;
    private javax.swing.JTable tblDataPel;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtIDPel;
    private javax.swing.JTextField txtNamaPel;
    private javax.swing.JTextField txtTelp;
    // End of variables declaration//GEN-END:variables
}
