/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliherbalpos;
import static deliherbalpos.login.hakakses;
import static deliherbalpos.login.idkar;
import static deliherbalpos.login.namakar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gen781
 */
public class barang extends javax.swing.JDialog {
    Connection conn = new koneksi().connect();
    /**
     * Creates new form barang
     */
    public barang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtKodeBar.setEditable(false);
        txtNama.requestFocus();
        tampilBarang();
        reset();
    }
    
    private void nomorAuto(){
        String sql= "SELECT MAX(right(kdBrg,5)) AS no FROM barang";
        try{ 
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.first() == false){
                    txtKodeBar.setText("hdh000001");
                }
                else{
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String nomor = String.valueOf(auto_id);
                    int pjgNomor = nomor.length();
                    for(int a=0;a<6-pjgNomor;a++){
                        nomor = "0" + nomor;
                    }
                    txtKodeBar.setText("hdh" + nomor);
                }       
            }
        }   
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),"Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    } 
    
    private void reset(){
        txtNama.setText("");
        cmbKemasan.setSelectedIndex(0);
        txtHargaBeli.setText("");
        txtHargaJual.setText("");
        txtStok.setText("");
        tblDataBarang.clearSelection();
        nomorAuto();
    }
    
    private void tampilBarang(){
        Object header[]={"Kode Barang","Nama","Kemasan","Harga Beli","Harga Jual","Stok"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tblDataBarang.setModel(data);
        
        String sql = "SELECT * FROM barang";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                String kolom5 = rs.getString(5);
                String kolom6 = rs.getString(6);
               
                String kolom[] = {kolom1, kolom2, kolom3, kolom4, kolom5, kolom6};
                data.addRow(kolom);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"error :"+e.getMessage());
        }
    }
    
    private void cariBarang(){
        Object header[]={"Kode Barang","Nama","Kemasan","Harga Beli","Harga Jual","Stok"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tblDataBarang.setModel(data);
        String sql = "SELECT * FROM barang WHERE kdBrg like '%" + txtCari.getText() + "%'" +
                         "or nmBrg like '%" + txtCari.getText() + "%'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                String kolom5 = rs.getString(5);
                String kolom6 = rs.getString(6);
               
                String kolom[] = {kolom1, kolom2, kolom3, kolom4, kolom5, kolom6};
                data.addRow(kolom);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"error :"+e.getMessage());
        }
    }
    
    private void tambahBarang(){
        if (txtKodeBar.getText().equals("")||(txtNama.getText().equals(""))||cmbKemasan.getSelectedItem().equals("------Pilih------")||(txtHargaBeli.getText().equals(""))||(txtHargaJual.getText().equals(""))||(txtStok.getText().equals(""))){
	    JOptionPane.showMessageDialog(this, "Data belum lengkap, silahkan lengkapi data terlebih dahulu","Perhatian",JOptionPane.WARNING_MESSAGE);
            txtNama.requestFocus();
        } 
        else {
            String kodebar =this.txtKodeBar.getText();
            String nama=this.txtNama.getText();
            String kemasan=(String)this.cmbKemasan.getSelectedItem();
            String hargabeli=this.txtHargaBeli.getText();
            String hargajual=this.txtHargaJual.getText();
            String stok=this.txtStok.getText();
            String sql = "INSERT INTO barang (kdBrg,nmBrg,kmsn,hrgBeli,hrgJual,stok)VALUES(?,?,?,?,?,?)";
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,kodebar);
                ps.setString(2,nama);
                ps.setString(3,kemasan);
                ps.setString(4,hargabeli);
                ps.setString(5,hargajual);
                ps.setString(6,stok);
                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null,"Data berhasil ditambahkan","Informasi",JOptionPane.INFORMATION_MESSAGE);
                tampilBarang();
            
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }
            reset();
        }
    }  
    
    
    private void ubahBarang(){
        if (tblDataBarang.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih data pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            if (txtKodeBar.getText().equals("")||(txtNama.getText().equals(""))||cmbKemasan.getSelectedItem().equals("------Pilih------")||(txtHargaBeli.getText().equals(""))||(txtHargaJual.getText().equals(""))||(txtStok.getText().equals(""))){
                JOptionPane.showMessageDialog(this, "Data belum lengkap, silahkan lengkapi data terlebih dahulu","Perhatian",JOptionPane.WARNING_MESSAGE);
                txtNama.requestFocus();
            } 
            else{
                String sql = "UPDATE barang SET nmBrg = ?, kmsn = ?, hrgBeli = ?, hrgJual = ?, stok = ? WHERE kdBrg=?";
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, txtNama.getText());
                    ps.setString(2, cmbKemasan.getSelectedItem().toString());
                    ps.setString(3, txtHargaBeli.getText());
                    ps.setString(4, txtHargaJual.getText());
                    ps.setString(5, txtStok.getText());
                    ps.setString(6, tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),0).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil diubah","Informasi",JOptionPane.INFORMATION_MESSAGE);
                    tampilBarang();
                } 
                catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Data gagal diubah",null,JOptionPane.ERROR_MESSAGE);
                }
                reset();
            }
        }
    }
    
    private void hapusBarang(){
        if (tblDataBarang.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih data pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            int ok=JOptionPane.showConfirmDialog(null,"Apakah yakin untuk menghapus data?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            String sql = "DELETE FROM barang WHERE kdBrg=?";
            if(ok==0) {
                try{
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),0).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
                    tampilBarang();
                } 
                catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Data gagal dihapus",null,JOptionPane.ERROR_MESSAGE);
                }
                reset();
            }
        }
    }
    
    private void cekKodeBrg(){
        String sql = "SELECT * FROM barang WHERE kdBrg=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtKodeBar.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                btnTambah.setEnabled(false);
            } 
            else {
                btnTambah.setEnabled(true);
            }
        } 
        catch (Exception e) {
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

        jLabel2 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataBarang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtKodeBar = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        cmbKemasan = new javax.swing.JComboBox();
        txtHargaBeli = new javax.swing.JTextField();
        txtHargaJual = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        btnBatal = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Data Barang");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

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

        tblDataBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDataBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataBarangMouseClicked(evt);
            }
        });
        tblDataBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDataBarangKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataBarang);

        jLabel1.setText("Kode Barang");

        jLabel3.setText("Nama Barang");

        jLabel4.setText("Kemasan");

        jLabel5.setText("Harga Beli");

        jLabel6.setText("Harga Jual");

        txtKodeBar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtKodeBarCaretUpdate(evt);
            }
        });
        txtKodeBar.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                txtKodeBarCaretPositionChanged(evt);
            }
        });
        txtKodeBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeBarActionPerformed(evt);
            }
        });

        cmbKemasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "------Pilih------", "Kotak", "Botol", "Sachet" }));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel7.setText("Stok");

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel8.setText("Cari data berdasarkan nama/kode barang");

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbKemasan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKodeBar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)))
                        .addGap(55, 55, 55)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCari)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtKodeBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbKemasan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(btnCari)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(jButton1)
                    .addComponent(btnTambah)
                    .addComponent(btnBatal))
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
        tambahBarang();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahBarang();
        
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusBarang();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblDataBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataBarangMouseClicked
        // TODO add your handling code here:
        txtKodeBar.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),0).toString());
        txtNama.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),1).toString());
        cmbKemasan.setSelectedItem(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),2).toString());
        txtHargaBeli.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),3).toString());
        txtHargaJual.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),4).toString());
        txtStok.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),5).toString());
    }//GEN-LAST:event_tblDataBarangMouseClicked

    private void txtKodeBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeBarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeBarActionPerformed

    private void tblDataBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDataBarangKeyReleased
        // TODO add your handling code here:
        txtKodeBar.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),0).toString());
        txtNama.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),1).toString());
        cmbKemasan.setSelectedItem(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),2).toString());
        txtHargaBeli.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),3).toString());
        txtHargaJual.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),4).toString());
        txtStok.setText(tblDataBarang.getValueAt(tblDataBarang.getSelectedRow(),5).toString());
    }//GEN-LAST:event_tblDataBarangKeyReleased

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtKodeBarCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtKodeBarCaretPositionChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeBarCaretPositionChanged

    private void txtKodeBarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtKodeBarCaretUpdate
        // TODO add your handling code here:
        cekKodeBrg();
    }//GEN-LAST:event_txtKodeBarCaretUpdate

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        cariBarang();
    }//GEN-LAST:event_btnCariActionPerformed

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
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                barang dialog = new barang(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbKemasan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblDataBarang;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtHargaJual;
    private javax.swing.JTextField txtKodeBar;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtStok;
    // End of variables declaration//GEN-END:variables
}
