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
public class supplier extends javax.swing.JDialog {
    Connection conn = new koneksi().connect();
    /**
     * Creates new form supplier
     */
    public supplier(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtIdSup.setEditable(false);
        txtNamaSup.requestFocus();
        tampilSupplier();
        reset();
    }
    
    private void nomorAuto(){
        String sql= "SELECT MAX(right(idSup,2)) AS no FROM supplier";
        try{ 
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.first() == false){
                    txtIdSup.setText("sdh001");
                }
                else{
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String nomor = String.valueOf(auto_id);
                    int pjgNomor = nomor.length();
                    for(int a=0;a<3-pjgNomor;a++){
                        nomor = "0" + nomor;
                    }
                    txtIdSup.setText("sdh" + nomor);
                }       
            }
        }   
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),"Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void reset(){
        txtNamaSup.setText("");
        txtSalesman.setText("");
        txtTelp.setText("");
        txtAlamat.setText("");
        tblDataSup.clearSelection();
        nomorAuto();
    }
    
    private void tampilSupplier(){
        Object header[]={"ID Supplier","Nama","Salesman","No.Telp/HP","Alamat"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tblDataSup.setModel(data);
        
        String sql = "SELECT * FROM supplier";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                String kolom5 = rs.getString(5);
               
                String kolom[] = {kolom1, kolom2, kolom3, kolom4, kolom5};
                data.addRow(kolom);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"error :"+e.getMessage());
        }
    }
    
    private void tambahSupplier(){
        if (txtIdSup.getText().equals("")||(txtNamaSup.getText().equals(""))||(txtTelp.getText().equals(""))||txtAlamat.getText().equals("")){
	    JOptionPane.showMessageDialog(this, "Data belum lengkap, silahkan lengkapi data terlebih dahulu","Perhatian",JOptionPane.WARNING_MESSAGE);
            txtIdSup.requestFocus();
        } 
        else {
            String idsup =this.txtIdSup.getText();
            String nama=this.txtNamaSup.getText();
            String salesman=this.txtSalesman.getText();
            String telp=this.txtTelp.getText();
            String alamat=this.txtAlamat.getText();
            String sql = "INSERT INTO supplier (idSup,nmSup,salesman,telpSup,almtSup)VALUES(?,?,?,?,?)";
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,idsup);
                ps.setString(2,nama);
                ps.setString(3,salesman);
                ps.setString(4,telp);
                ps.setString(5,alamat);
                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null,"Data berhasil ditambahkan","Informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilSupplier();
            
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }
            reset();
        }
    }  
    
    
    private void ubahSupplier(){
        if (tblDataSup.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih data pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            if (txtIdSup.getText().equals("")||(txtNamaSup.getText().equals(""))||(txtTelp.getText().equals(""))||txtAlamat.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Data belum lengkap, silahkan lengkapi data terlebih dahulu","Perhatian",JOptionPane.WARNING_MESSAGE);
                txtIdSup.requestFocus();
            } 
            else{
                String sql = "UPDATE supplier SET nmSup=?, salesman=?, telpSup=?, almtSup=? WHERE idSup=?";
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, txtNamaSup.getText());
                    ps.setString(2, txtSalesman.getText());
                    ps.setString(3, txtTelp.getText());
                    ps.setString(4, txtAlamat.getText());
                    ps.setString(5, tblDataSup.getValueAt(tblDataSup.getSelectedRow(),0).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil diubah","Informasi",JOptionPane.INFORMATION_MESSAGE);
                    tampilSupplier();
                } 
                catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Data gagal diubah",null,JOptionPane.ERROR_MESSAGE);
                }
                reset();
            }
        }
    }
    
    private void hapusSupplier(){
        if (tblDataSup.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih data pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            int ok=JOptionPane.showConfirmDialog(null,"Apakah yakin untuk menghapus data?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            String sql = "DELETE FROM supplier WHERE idSup=?";
            if(ok==0) {
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, tblDataSup.getValueAt(tblDataSup.getSelectedRow(),0).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
                    tampilSupplier();
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

        lblIdSup = new javax.swing.JLabel();
        txtIdSup = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblNamaSup = new javax.swing.JLabel();
        txtNamaSup = new javax.swing.JTextField();
        lblTelp = new javax.swing.JLabel();
        txtTelp = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataSup = new javax.swing.JTable();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblAlamat = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        lblSalesman = new javax.swing.JLabel();
        txtSalesman = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lblIdSup.setText("ID Supplier");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Data Supplier");

        lblNamaSup.setText("Nama");

        lblTelp.setText("No.Telp/HP");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        tblDataSup.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDataSup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataSupMouseClicked(evt);
            }
        });
        tblDataSup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDataSupKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataSup);

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

        lblSalesman.setText("Salesman");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNamaSup)
                            .addComponent(lblSalesman)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblIdSup)
                                            .addComponent(lblAlamat))
                                        .addGap(2, 2, 2))
                                    .addComponent(lblTelp, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIdSup, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSalesman)
                                    .addComponent(txtNamaSup)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIdSup)
                            .addComponent(txtIdSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNamaSup)
                            .addComponent(txtNamaSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSalesman)
                            .addComponent(txtSalesman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelp))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAlamat)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(jButton1)
                    .addComponent(btnTambah))
                .addContainerGap())
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
        tambahSupplier();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahSupplier();
        
    }//GEN-LAST:event_btnUbahActionPerformed

    private void tblDataSupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataSupMouseClicked
        // TODO add your handling code here:
        txtIdSup.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),0).toString());
        txtNamaSup.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),1).toString());
        txtSalesman.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),2).toString());
        txtTelp.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),3).toString());
        txtAlamat.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),4).toString());
    }//GEN-LAST:event_tblDataSupMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusSupplier();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblDataSupKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDataSupKeyReleased
        // TODO add your handling code here:
        txtIdSup.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),0).toString());
        txtNamaSup.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),1).toString());
        txtSalesman.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),2).toString());
        txtTelp.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),3).toString());
        txtAlamat.setText(tblDataSup.getValueAt(tblDataSup.getSelectedRow(),4).toString());
    }//GEN-LAST:event_tblDataSupKeyReleased

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
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                supplier dialog = new supplier(new javax.swing.JFrame(), true);
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblIdSup;
    private javax.swing.JLabel lblNamaSup;
    private javax.swing.JLabel lblSalesman;
    private javax.swing.JLabel lblTelp;
    private javax.swing.JTable tblDataSup;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtIdSup;
    private javax.swing.JTextField txtNamaSup;
    private javax.swing.JTextField txtSalesman;
    private javax.swing.JTextField txtTelp;
    // End of variables declaration//GEN-END:variables
}
