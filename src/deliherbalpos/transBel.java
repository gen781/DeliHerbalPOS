/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliherbalpos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gen781
 */
public class transBel extends javax.swing.JDialog {
    Connection conn = new koneksi().connect();
    int baris = 0 ;
    static Object kolom[]={"Kode Barang","Nama","Harga","Jumlah","Jumlah Harga"};
    DefaultTableModel modelTabel = new DefaultTableModel(kolom,baris);
    /**
     * Creates new form transBel
     */
    public transBel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        resetAll();
        nonAktifDataBar();
        nonAktifRincBar();
        nomorAuto();
        waktu();
        tampilSupCombo();
        tampilHerbalCombo();
        lblNamaKar.setText(login.namakar);
        tblDataTransBeli.setModel(modelTabel);
    }
    
    private void resetAll(){
        cmbSup.setSelectedIndex(0);
        cmbNamaHerbal.setSelectedIndex(0);
        resetDataSup();
        resetDataBarang();
        resetRincBelanja();
        tblDataTransBeli.clearSelection();
        nomorAuto();
    }
    
    private void resetDataSup(){
        txtIdSup.setText("");
        txtKet.setText("");
        txtSalesman.setText("");
        cmbSup.setSelectedIndex(0);
    }
    
    private void resetDataBarang(){
        cmbNamaHerbal.setSelectedIndex(0);
        txtKodeBar.setText("");
        txtStokAwal.setText("0");
        txtStokAkhir.setText("0");
        txtHargaBar.setText("0");
        txtJlhBrg.setText("1");
        txtJlhHarga.setText("0");
        btnTambahBrg.setEnabled(false);
    }
    
    private void resetRincBelanja(){
        txtSubTotal.setText("0");
        txtDisk.setText("0");
        txtTtlHarga.setText("0");
        btnHpsItem.setEnabled(false);
    }
    
    private void nomorAuto(){
        String sql= "SELECT MAX(right(noTrans,4)) AS no FROM transbeli";
        try{ 
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.first() == false){
                    txtNoTrans.setText("odh1500001");
                }
                else{
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String nomor = String.valueOf(auto_id);
                    int pjgNomor = nomor.length();
                    for(int a=0;a<5-pjgNomor;a++){
                        nomor = "0" + nomor;
                    }
                    txtNoTrans.setText("odh15" + nomor);
                }       
            }
        }   
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),"Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    } 
    
    public void waktu(){
        Date tgl = new Date();
        dtcTglTrans.setDate(tgl);
    }
    
    public void tampilSupCombo(){  
        String sql = "SELECT * FROM supplier";  
        try {  
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                  
                cmbSup.addItem(rs.getString("nmSup"));  
            }  
            rs.last();  
            int jumlahData = rs.getRow();  
            rs.first();  
              
        } 
        catch (Exception e) {  
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),"Kesalahan", JOptionPane.WARNING_MESSAGE);
        }  
    } 
    
    public void tampilHerbalCombo(){  
        String sql = "SELECT * FROM barang";  
        try {  
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                  
                cmbNamaHerbal.addItem(rs.getString("nmBrg"));  
            }  
            rs.last();  
            int jumlahData = rs.getRow();  
            rs.first();  
              
        } 
        catch (Exception e) {  
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),"Kesalahan", JOptionPane.WARNING_MESSAGE);
        }  
    }
    
    public void tampilKdHrgHerbalCombo(){
        String sql = "SELECT kdBrg,hrgBeli,stok FROM barang WHERE nmBrg='"+cmbNamaHerbal.getSelectedItem()+"'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                   
                txtKodeBar.setText(rs.getString("kdBrg"));  
                txtHargaBar.setText(rs.getString("hrgBeli")); 
                txtStokAwal.setText(rs.getString("stok")); 
                txtStokAkhir.setText(txtStokAwal.getText());
            }         
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }              
    }

    public void tampilIdSupCombo(){
        String sql = "SELECT idSup,salesman FROM supplier WHERE nmSup='"+cmbSup.getSelectedItem()+"'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                   
                txtIdSup.setText(rs.getString("idSup"));  
                txtSalesman.setText(rs.getString("salesman")); 
            }         
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }              
    }
    
    public void hitungJlhHarga(){
        if (txtHargaBar.getText().equals("")||txtJlhBrg.getText().equals("")){
        }
        else{
            int jlhbar = Integer.parseInt(txtJlhBrg.getText());
            int hargabar = Integer.parseInt(txtHargaBar.getText());
            int jlhharga = jlhbar*hargabar;
            txtJlhHarga.setText(String.valueOf(jlhharga));
        }
    }
    
    public void hitungTtlHarga(){
        if (txtSubTotal.getText().equals("")||txtDisk.getText().equals("")){
        }
        else{
            int subtotal = Integer.parseInt(txtSubTotal.getText());
            int disk = Integer.parseInt(txtDisk.getText());
            int ttlharga= subtotal-subtotal*disk/100;
            txtTtlHarga.setText(String.valueOf(ttlharga));
        }
    }
    
    public void cekData(){
        if (cmbSup.getSelectedIndex()>0&&cmbNamaHerbal.getSelectedIndex()>0&&tblDataTransBeli.getRowCount()>0){
            btnSimpTrans.setEnabled(true);
        }
        else {
            btnSimpTrans.setEnabled(false);
        }
    }
    
    private void nonAktifDataBar(){
        cmbNamaHerbal.setEnabled(false);
        txtKodeBar.setEnabled(false);
        txtStokAwal.setEnabled(false);
        txtStokAkhir.setEnabled(false);
        txtHargaBar.setEnabled(false);
        txtJlhBrg.setEnabled(false);
        txtJlhHarga.setEnabled(false);
        btnTambahBrg.setEnabled(false);
        btnBaruHerbal.setEnabled(false);
    }
    
    private void aktifDataBar(){
        cmbNamaHerbal.setEnabled(true);
        txtKodeBar.setEnabled(true);
        txtStokAwal.setEnabled(true);
        txtStokAkhir.setEnabled(true);
        txtHargaBar.setEnabled(true);
        txtJlhBrg.setEnabled(true);
        txtJlhHarga.setEnabled(true);
        btnTambahBrg.setEnabled(true);
        btnBaruHerbal.setEnabled(true);
    }
    
    private void aktifRincBar(){
        txtSubTotal.setEnabled(true);
        txtDisk.setEnabled(true);
        txtTtlHarga.setEnabled(true);
    }
    
    private void nonAktifRincBar(){
        txtSubTotal.setEnabled(false);
        txtDisk.setEnabled(false);
        txtTtlHarga.setEnabled(false);
    }
    
    private void tampilDetTrans(){
        String kolom1 = txtKodeBar.getText();
        String kolom2 = (String)cmbNamaHerbal.getSelectedItem();
        String kolom3 = txtHargaBar.getText();
        String kolom4 = txtJlhBrg.getText();
        String kolom5 = txtJlhHarga.getText();
        modelTabel.addRow(new Object [] {kolom1,kolom2,kolom3, kolom4, kolom5} ) ;     
        tblDataTransBeli.setModel(modelTabel);
    }
    
    public void cekDetTrans(){
        if(cmbNamaHerbal.getSelectedIndex()==0){
            btnTambahBrg.setEnabled(false);
        }
        else{
            String sql = "SELECT * FROM dtltransbeli WHERE noTrans=? AND kdBrg=?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtNoTrans.getText());
                ps.setString(2, txtKodeBar.getText());
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    btnTambahBrg.setEnabled(false);
                } 
                else {
                    btnTambahBrg.setEnabled(true);
                }
            } 
            catch (Exception e) {
            }
        }
    }
    
    public void tambahDetTrans(){
            String notrans =this.txtNoTrans.getText();
            String kodeBar=this.txtKodeBar.getText();
            String jumlah=this.txtJlhBrg.getText();
            String jlhHarga=this.txtJlhHarga.getText();
            String sql = "INSERT INTO dtltransbeli (noTrans,kdBrg,jumlah,jlhHarga)VALUES(?,?,?,?)";     
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,notrans);
                ps.setString(2,kodeBar);
                ps.setString(3,jumlah);
                ps.setString(4,jlhHarga);
                ps.executeUpdate();
                ps.close();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }
    }  
    
    public void hitungSubTotal(){
        String sql = "SELECT SUM(jlhHarga) FROM dtltransbeli WHERE noTrans='" + txtNoTrans.getText() + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                txtSubTotal.setText(rs.getString(1));
            }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"error :"+e.getMessage());
        }
        if (txtSubTotal.getText().equals("")){
            txtSubTotal.setText("0");
        }
    }
    
    public void simpanTrans(){
            String tampilan ="yyyy-MM-dd" ; 
            SimpleDateFormat fm = new SimpleDateFormat(tampilan); 
            String notrans =this.txtNoTrans.getText();
            String tgltrans=String.valueOf(fm.format(this.dtcTglTrans.getDate()));
            String idsup=this.txtIdSup.getText();
            String idkar=login.idkar;
            String subtotal=this.txtSubTotal.getText();
            String diskon=this.txtDisk.getText();
            String ttlharga=this.txtTtlHarga.getText();
            String sql = "INSERT INTO transbeli (noTrans,tglTrans,idSup,idKar,subTtl,disc,ttlHarga)VALUES(?,?,?,?,?,?,?)";
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,notrans);
                ps.setString(2,tgltrans);
                ps.setString(3,idsup);
                ps.setString(4,idkar);
                ps.setString(5,subtotal);
                ps.setString(6,diskon);
                ps.setString(7,ttlharga);
                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null,"Transaksi berhasil disimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }
    } 
    
    private void hapusIsiTabel(){
        tblDataTransBeli.setModel(modelTabel);
        int baris = modelTabel.getRowCount();
        for (int a=0;a<baris;a++){
            modelTabel.removeRow(0);
        }     
    }
    
    public void cekItem(){
        if (tblDataTransBeli.getRowCount()==0){
            btnHpsItem.setEnabled(false);
            nonAktifRincBar();
        }
        else{
            btnHpsItem.setEnabled(true);
            aktifRincBar();
        }
    }
    
    private void hapusItem(){
        if (tblDataTransBeli.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih item pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            String sql = "DELETE FROM dtltransbeli WHERE noTrans=? AND kdBrg=?";
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtNoTrans.getText());
                ps.setString(2, tblDataTransBeli.getValueAt(tblDataTransBeli.getSelectedRow(),0).toString());
                ps.executeUpdate();
            } 
            catch (Exception e){
                JOptionPane.showMessageDialog(this, "Data gagal dihapus",null,JOptionPane.ERROR_MESSAGE);
            }
            
            String sqlcekstok = "SELECT * FROM barang WHERE kdBrg=?";
            String sqlupdate = "UPDATE barang SET stok=? WHERE kdBrg=?";
            try {
                PreparedStatement pscekstok = conn.prepareStatement(sqlcekstok);
                pscekstok.setString(1, tblDataTransBeli.getValueAt(tblDataTransBeli.getSelectedRow(),0).toString());
                ResultSet rscekstok = pscekstok.executeQuery();
                if(rscekstok.next()){
                    String kdBrg=rscekstok.getString(1);
                    String stok=rscekstok.getString(6);
                    String stokretur = tblDataTransBeli.getValueAt(tblDataTransBeli.getSelectedRow(),3).toString();
                    int stokbaru = Integer.parseInt(stok)-Integer.parseInt(stokretur);
                             
                    PreparedStatement psupdate = conn.prepareStatement(sqlupdate);
                    psupdate.setString(1, String.valueOf(stokbaru));
                    psupdate.setString(2, tblDataTransBeli.getValueAt(tblDataTransBeli.getSelectedRow(),0).toString());
                    psupdate.executeUpdate();
                    txtStokAkhir.setText(String.valueOf(stokbaru));
                }    
            } 
            catch (Exception e) {
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }   
            
            int selectedRow = tblDataTransBeli.getSelectedRow();
            if(selectedRow != -1) {
                modelTabel.removeRow(selectedRow);
            } 
        }
    }
    
    public void cekSimpan(){
        if (btnSimpTrans.isEnabled()){
            btnTutup.setEnabled(false);
        }
        else {
            btnTutup.setEnabled(true);
        }
    }
    
    private void updateStok(){
        String sql = "UPDATE barang SET stok=? WHERE kdBrg=?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtStokAkhir.getText());
            ps.setString(2, txtKodeBar.getText());
            ps.executeUpdate();
        } 
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"error :"+e.getMessage());
        }        
    }
    
    public void hitungStok(){
        if (txtStokAwal.getText().equals("")||txtStokAkhir.getText().equals("")){
        }
        else{
            int stokawal = Integer.parseInt(txtStokAwal.getText());
            int stokin = Integer.parseInt(txtJlhBrg.getText());
            int stokakhir = stokawal+stokin;
            txtStokAkhir.setText(String.valueOf(stokakhir));
        }
    }
    
    public void tampilItemTerpilih(){
        cmbNamaHerbal.setSelectedItem(tblDataTransBeli.getValueAt(tblDataTransBeli.getSelectedRow(),1).toString());
        txtJlhBrg.setText(tblDataTransBeli.getValueAt(tblDataTransBeli.getSelectedRow(),3).toString());
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
        jLabel4 = new javax.swing.JLabel();
        txtNoTrans = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbBaruSup = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtHargaBar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtKet = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtJlhBrg = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataTransBeli = new javax.swing.JTable();
        btnSimpTrans = new javax.swing.JButton();
        btnTutup = new javax.swing.JButton();
        btnHpsItem = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDisk = new javax.swing.JTextField();
        txtTtlHarga = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblNamaKar = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        dtcTglTrans = new com.toedter.calendar.JDateChooser();
        cmbSup = new javax.swing.JComboBox();
        cmbNamaHerbal = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        txtIdSup = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtKodeBar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtStokAwal = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtStokAkhir = new javax.swing.JTextField();
        txtSalesman = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        btnBaruHerbal = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnTambahBrg = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtJlhHarga = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(190, 196, 188));
        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("Data Transaksi");
        jLabel2.setOpaque(true);

        jLabel4.setText("No. Transaksi");

        txtNoTrans.setEditable(false);

        jLabel5.setText("Tgl. Transaksi");

        jLabel6.setText("Nama Supplier");

        cmbBaruSup.setText("Baru");
        cmbBaruSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBaruSupActionPerformed(evt);
            }
        });

        jLabel7.setText("Keterangan");

        jLabel9.setText("Nama Herbal");

        txtHargaBar.setEditable(false);

        jLabel10.setText("Harga");

        jLabel11.setText("Jumlah");

        txtJlhBrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtJlhBrgFocusLost(evt);
            }
        });
        txtJlhBrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJlhBrgKeyReleased(evt);
            }
        });

        tblDataTransBeli.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDataTransBeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataTransBeliMouseClicked(evt);
            }
        });
        tblDataTransBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDataTransBeliKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataTransBeli);

        btnSimpTrans.setText("Simpan");
        btnSimpTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpTransActionPerformed(evt);
            }
        });

        btnTutup.setText("Tutup");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        btnHpsItem.setText("Hapus Item");
        btnHpsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHpsItemActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(190, 196, 188));
        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel12.setText("Rincian Belanja");
        jLabel12.setOpaque(true);

        txtSubTotal.setEditable(false);
        txtSubTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubTotalActionPerformed(evt);
            }
        });

        jLabel13.setText("Sub Total");

        txtDisk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiskFocusLost(evt);
            }
        });
        txtDisk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiskKeyReleased(evt);
            }
        });

        txtTtlHarga.setEditable(false);

        jLabel18.setText("Diskon");

        jLabel21.setText("Total Harga");

        jLabel3.setText("%");

        jLabel23.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel23.setText("Transaksi Pembelian");

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setText("Nama Karyawan :");

        lblNamaKar.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblNamaKar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNamaKar.setText("Putra");

        jLabel25.setText("Nama Salesman");

        dtcTglTrans.setDateFormatString("dd-MM-yyyy");

        cmbSup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--------Pilih--------" }));
        cmbSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSupActionPerformed(evt);
            }
        });

        cmbNamaHerbal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "----------Pilih----------" }));
        cmbNamaHerbal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNamaHerbalActionPerformed(evt);
            }
        });

        jLabel14.setText("ID");

        txtIdSup.setEditable(false);

        jLabel15.setText("Kode");

        txtKodeBar.setEditable(false);

        jLabel16.setText("Stok Awal");

        txtStokAwal.setEditable(false);

        jLabel17.setText("Stok Akhir");

        txtStokAkhir.setEditable(false);

        jLabel19.setBackground(new java.awt.Color(190, 196, 188));
        jLabel19.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel19.setText("Data Barang");
        jLabel19.setOpaque(true);

        btnBaruHerbal.setText("Baru");
        btnBaruHerbal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaruHerbalActionPerformed(evt);
            }
        });

        btnTambahBrg.setText("Tambah");
        btnTambahBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBrgActionPerformed(evt);
            }
        });

        jLabel8.setText("Jumlah Harga");

        txtJlhHarga.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnHpsItem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimpTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTutup, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel25)
                                            .addComponent(jLabel7))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtKet)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(dtcTglTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(txtSalesman, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cmbSup, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtIdSup, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cmbBaruSup))))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmbNamaHerbal, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtKodeBar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnBaruHerbal))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtJlhBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtJlhHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnTambahBrg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtHargaBar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel16)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtStokAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel17)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtStokAkhir))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDisk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel21))
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(392, 392, 392)
                                        .addComponent(txtTtlHarga))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNamaKar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel1)
                    .addComponent(lblNamaKar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cmbNamaHerbal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKodeBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBaruHerbal)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtHargaBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(txtStokAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(txtStokAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJlhBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(btnTambahBrg)
                            .addComponent(jLabel8)
                            .addComponent(txtJlhHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(txtDisk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel21)
                            .addComponent(txtTtlHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dtcTglTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(cmbSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtIdSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbBaruSup))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSalesman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTutup)
                    .addComponent(btnSimpTrans)
                    .addComponent(btnHpsItem))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpTransActionPerformed
        // TODO add your handling code here:
        simpanTrans();
        resetAll();
        hapusIsiTabel();
        cekItem();
        cekSimpan();
    }//GEN-LAST:event_btnSimpTransActionPerformed

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnTutupActionPerformed

    private void cmbBaruSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBaruSupActionPerformed
        // TODO add your handling code here:
        new supplier(null,true).show();
        cmbSup.removeAllItems();
        cmbSup.addItem("--------Pilih--------");
        tampilSupCombo();
    }//GEN-LAST:event_cmbBaruSupActionPerformed

    private void cmbSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSupActionPerformed
        // TODO add your handling code here:
        int supplier= cmbSup.getSelectedIndex();
        if (supplier==0){
            resetDataSup();
            nonAktifDataBar();
            nonAktifRincBar();
        }
        else{
            tampilIdSupCombo();
            txtSalesman.requestFocus();
            int namaherbal= cmbNamaHerbal.getSelectedIndex();
            if (namaherbal==0){
                cmbNamaHerbal.setEnabled(true);
            }
            else{
                aktifDataBar();
            }
        }
        cekData();   
        cekDetTrans();
    }//GEN-LAST:event_cmbSupActionPerformed

    private void txtSubTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubTotalActionPerformed

    private void txtJlhBrgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJlhBrgKeyReleased
        // TODO add your handling code here:
        hitungJlhHarga();
    }//GEN-LAST:event_txtJlhBrgKeyReleased

    private void txtJlhBrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJlhBrgFocusLost
        // TODO add your handling code here:
        hitungJlhHarga();
    }//GEN-LAST:event_txtJlhBrgFocusLost

    private void cmbNamaHerbalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNamaHerbalActionPerformed
        // TODO add your handling code here:
        int namaherbal= cmbNamaHerbal.getSelectedIndex();
        if (namaherbal==0){
            resetDataBarang();
            nonAktifDataBar();
            cmbNamaHerbal.setEnabled(true);
        }
        else{
            tampilKdHrgHerbalCombo();
            hitungJlhHarga();
            tampilKdHrgHerbalCombo();
            aktifDataBar();
            txtJlhBrg.setText("1");
        }
        cekDetTrans();
        cekData();
    }//GEN-LAST:event_cmbNamaHerbalActionPerformed

    private void txtDiskKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiskKeyReleased
        // TODO add your handling code here:
        hitungTtlHarga();
    }//GEN-LAST:event_txtDiskKeyReleased

    private void txtDiskFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiskFocusLost
        // TODO add your handling code here:
        hitungTtlHarga();
    }//GEN-LAST:event_txtDiskFocusLost

    private void btnBaruHerbalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruHerbalActionPerformed
        // TODO add your handling code here:
        new barang(null,true).show();
        cmbNamaHerbal.removeAllItems();
        cmbNamaHerbal.addItem("----------Pilih----------");
        tampilHerbalCombo();
    }//GEN-LAST:event_btnBaruHerbalActionPerformed

    private void btnTambahBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBrgActionPerformed
        // TODO add your handling code here:
        aktifRincBar();
        tampilDetTrans();
        tambahDetTrans();
        cekDetTrans();
        hitungSubTotal();
        hitungTtlHarga();
        hitungStok();
        updateStok();
        cekData();
        cekItem();
        cekSimpan();
    }//GEN-LAST:event_btnTambahBrgActionPerformed

    private void btnHpsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHpsItemActionPerformed
        // TODO add your handling code here:
        hapusItem();
        cekItem();
        cekDetTrans();
        cekData();
        cekSimpan();
        hitungSubTotal();
        hitungTtlHarga();
    }//GEN-LAST:event_btnHpsItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (btnSimpTrans.isEnabled()){
            JOptionPane.showMessageDialog(null, "Transaksi belum disimpan, silahkan simpan transaksi terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
        else{
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void tblDataTransBeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataTransBeliMouseClicked
        // TODO add your handling code here:
        tampilItemTerpilih();
    }//GEN-LAST:event_tblDataTransBeliMouseClicked

    private void tblDataTransBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDataTransBeliKeyReleased
        // TODO add your handling code here:
        tampilItemTerpilih();
    }//GEN-LAST:event_tblDataTransBeliKeyReleased

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
            java.util.logging.Logger.getLogger(transBel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transBel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transBel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transBel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                transBel dialog = new transBel(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBaruHerbal;
    private javax.swing.JButton btnHpsItem;
    private javax.swing.JButton btnSimpTrans;
    private javax.swing.JButton btnTambahBrg;
    private javax.swing.JButton btnTutup;
    private javax.swing.JButton cmbBaruSup;
    private javax.swing.JComboBox cmbNamaHerbal;
    private javax.swing.JComboBox cmbSup;
    private com.toedter.calendar.JDateChooser dtcTglTrans;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblNamaKar;
    private javax.swing.JTable tblDataTransBeli;
    private javax.swing.JTextField txtDisk;
    private javax.swing.JTextField txtHargaBar;
    private javax.swing.JTextField txtIdSup;
    private javax.swing.JTextField txtJlhBrg;
    private javax.swing.JTextField txtJlhHarga;
    private javax.swing.JTextField txtKet;
    private javax.swing.JTextField txtKodeBar;
    private javax.swing.JTextField txtNoTrans;
    private javax.swing.JTextField txtSalesman;
    private javax.swing.JTextField txtStokAkhir;
    private javax.swing.JTextField txtStokAwal;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTtlHarga;
    // End of variables declaration//GEN-END:variables
}
