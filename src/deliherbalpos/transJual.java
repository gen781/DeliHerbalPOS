/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliherbalpos;
import static deliherbalpos.login.hakakses;
import static deliherbalpos.login.idkar;
import static deliherbalpos.login.namakar;
import java.io.InputStream;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperPrintManager;


/**
 *
 * @author gen781
 */
public class transJual extends javax.swing.JDialog {
    Connection conn = new koneksi().connect();
    int baris = 0 ;
    static Object kolom[]={"Kode Barang","Nama","Harga","Diskon","Harga Diskon","Jumlah","Jumlah Harga"};
    DefaultTableModel modelTabel = new DefaultTableModel(kolom,baris);
    

    /**
     * Creates new form transJual
     */
    public transJual(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        waktu();
        lblNamaKar.setText(login.namakar);
        cmbPel.requestFocus();
        txtNoTrans.setEditable(false);
        tblDataTransJual.setModel(modelTabel);
        resetAll();
        tampilPelCombo();
        tampilHerbalCombo();
        cekItem();
        nonAktifDataBar();
        nonAktifRincBel();
    }
    
    private void resetAll(){
        cmbPel.setSelectedIndex(0);
        cmbNamaHerbal.setSelectedIndex(0);
        resetDataPel();
        resetDataBarang();
        resetRincBelanja();
        tblDataTransJual.clearSelection();
        nomorAuto();
    }
    
    private void resetDataPel(){
        txtIdPel.setText("");
        txtKet.setText("");
    }
    
    private void resetDataBarang(){
        cmbNamaHerbal.setSelectedIndex(0);
        txtKodeBar.setText("");
        txtStok.setText("0");
        txtSisaStok.setText("0");
        txtHargaBar.setText("0");
        txtDiskBrg.setText("0");
        txtHrgDisk.setText("0");
        txtJlhBrg.setText("1");
        txtJlhHarga.setText("0");
        btnTambahBrg.setEnabled(false);
    }
    
    private void resetRincBelanja(){
        txtSubTotal.setText("0");
        txtDiskTtl.setText("0");
        txtTotalHarga.setText("0");
        txtJlhUang.setText("0");
        txtUangKembali.setText("0");
        lblTtlBayar.setText("Rp.0");
    }
    
    private void hapusIsiTabel(){
        tblDataTransJual.setModel(modelTabel);
        int baris = modelTabel.getRowCount();
        for (int a=0;a<baris;a++){
            modelTabel.removeRow(0);
        }
            
    }
    
    private void nonAktifDataBar(){
        cmbNamaHerbal.setEnabled(false);
        txtKodeBar.setEnabled(false);
        txtStok.setEnabled(false);
        txtSisaStok.setEnabled(false);
        txtHargaBar.setEnabled(false);
        txtDiskBrg.setEnabled(false);
        txtHrgDisk.setEnabled(false);
        txtJlhBrg.setEnabled(false);
        txtJlhHarga.setEnabled(false);
        btnTambahBrg.setEnabled(false);
    }
    
    private void aktifDataBar(){
        cmbNamaHerbal.setEnabled(true);
        txtKodeBar.setEnabled(true);
        txtStok.setEnabled(true);
        txtSisaStok.setEnabled(true);
        txtHargaBar.setEnabled(true);
        txtDiskBrg.setEnabled(true);
        txtHrgDisk.setEnabled(true);
        txtJlhBrg.setEnabled(true);
        txtJlhHarga.setEnabled(true);
        btnTambahBrg.setEnabled(true);
    }
    
    private void aktifRincBel(){
        txtSubTotal.setEnabled(true);
        txtTotalHarga.setEnabled(true);
        txtDiskTtl.setEnabled(true);
        txtJlhUang.setEnabled(true);
        txtUangKembali.setEnabled(true);
    }
    
     private void nonAktifRincBel(){
        txtSubTotal.setEnabled(false);
        txtTotalHarga.setEnabled(false);
        txtDiskTtl.setEnabled(false);
        txtJlhUang.setEnabled(false);
        txtUangKembali.setEnabled(false);
    }
    
    private void nomorAuto(){
        String sql= "SELECT MAX(right(noTrans,4)) AS no FROM transjual";
        try{ 
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.first() == false){
                    txtNoTrans.setText("jdh1500001");
                }
                else{
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String nomor = String.valueOf(auto_id);
                    int pjgNomor = nomor.length();
                    for(int a=0;a<5-pjgNomor;a++){
                        nomor = "0" + nomor;
                    }
                    txtNoTrans.setText("jdh15" + nomor);
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
    
    public void tampilPelCombo(){  
        String sql = "SELECT * FROM pelanggan";  
        try {  
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                  
                cmbPel.addItem(rs.getString("nmPel"));  
            }  
            rs.last();  
            int jumlahData = rs.getRow();  
            rs.first();  
              
        } 
        catch (Exception e) {  
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),"Kesalahan", JOptionPane.WARNING_MESSAGE);
        }  
    }  
    
    public void tampilIdPel(){
        String sql = "SELECT idPel FROM pelanggan WHERE nmPel='"+cmbPel.getSelectedItem()+"'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {   
                txtIdPel.setText(rs.getString("idPel"));  
            }         
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
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
        String sql = "SELECT kdBrg,hrgJual,stok FROM barang WHERE nmBrg='"+cmbNamaHerbal.getSelectedItem()+"'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                   
                txtKodeBar.setText(rs.getString("kdBrg"));  
                txtHargaBar.setText(rs.getString("hrgJual")); 
                txtStok.setText(rs.getString("stok")); 
                txtSisaStok.setText(txtStok.getText());
            }         
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }              
    }
    
    public void hitungHargaDisk(){
        if (txtHargaBar.getText().equals("")||txtDiskBrg.getText().equals("")){
        }
        else{
            int harga = Integer.parseInt(txtHargaBar.getText());
            int diskbar = Integer.parseInt(txtDiskBrg.getText());
            int hargadisk= harga-harga*diskbar/100;
            txtHrgDisk.setText(String.valueOf(hargadisk));
        }
    }
    
    public void hitungJlhHarga(){
        if (txtHrgDisk.getText().equals("")||txtJlhBrg.getText().equals("")){
        }
        else{
            int jlhbar = Integer.parseInt(txtJlhBrg.getText());
            int hargadisk = Integer.parseInt(txtHrgDisk.getText());
            int jlhharga = jlhbar*hargadisk;
            txtJlhHarga.setText(String.valueOf(jlhharga));
        }
    }
    
    public void hitungStok(){
        if (txtStok.getText().equals("")||txtSisaStok.getText().equals("")){
        }
        else{
            int stokawal = Integer.parseInt(txtStok.getText());
            int stokout = Integer.parseInt(txtJlhBrg.getText());
            int sisastok = stokawal-stokout;
            txtSisaStok.setText(String.valueOf(sisastok));
        }
    }
    
    public void hitungDiskTotal(){
        if (txtSubTotal.getText().equals("")||txtDiskTtl.getText().equals("")){
        }
        else{
            int subtotal = Integer.parseInt(txtSubTotal.getText());
            int disktotal = Integer.parseInt(txtDiskTtl.getText());
            int ttlharga= subtotal-subtotal*disktotal/100;
            txtTotalHarga.setText(String.valueOf(ttlharga));
            lblTtlBayar.setText(String.valueOf("Rp."+ttlharga));
        }
    }
    
    public void hitungUangKembali(){
        if (txtTotalHarga.getText().equals("")||txtJlhUang.getText().equals("")){
        }
        else{
            int totalharga = Integer.parseInt(txtTotalHarga.getText());
            int jlhuang = Integer.parseInt(txtJlhUang.getText());
            int uangkembali= jlhuang-totalharga;
            txtUangKembali.setText(String.valueOf(uangkembali));
        }
    }
    
    
        public void tambahDetTrans(){
            String notrans =this.txtNoTrans.getText();
            String kodeBar=this.txtKodeBar.getText();
            String diskBar=this.txtDiskBrg.getText();
            String hrgDisk=this.txtHrgDisk.getText();
            String jumlah=this.txtJlhBrg.getText();
            String jlhHarga=this.txtJlhHarga.getText();
            String sql = "INSERT INTO dtltransjual (noTrans,kdBrg,diskon,hrgDiskon,jumlah,jlhHarga,laba)VALUES(?,?,?,?,?,?,?)";
            String sqllaba = "SELECT * FROM barang WHERE kdBrg=?";                      
            try{
                PreparedStatement pslaba = conn.prepareStatement(sqllaba);
                pslaba.setString(1, kodeBar);
                ResultSet rslaba = pslaba.executeQuery();
                if(rslaba.next()){
                    String hrgbeli=rslaba.getString(4);
                    int laba=(Integer.parseInt(hrgDisk)-Integer.parseInt(hrgbeli))*Integer.parseInt(jumlah);
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1,notrans);
                        ps.setString(2,kodeBar);
                        ps.setString(3,diskBar);
                        ps.setString(4,hrgDisk);
                        ps.setString(5,jumlah);
                        ps.setString(6,jlhHarga);
                        ps.setString(7,String.valueOf(laba));
                        ps.executeUpdate();
                        ps.close();
                }
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }
    }  
    
    public void simpanTrans(){
            String tampilan ="yyyy-MM-dd" ; 
            SimpleDateFormat fm = new SimpleDateFormat(tampilan); 
            String notrans =this.txtNoTrans.getText();
            String tgltrans=String.valueOf(fm.format(this.dtcTglTrans.getDate()));
            String idpel=this.txtIdPel.getText();
            String idkar=login.idkar;
            String subtotal=this.txtSubTotal.getText();
            String diskon=this.txtDiskTtl.getText();
            String ttlharga=this.txtTotalHarga.getText();
            String sql = "INSERT INTO transjual (noTrans,tglTrans,idPel,idKar,subTtl,disc,ttlHarga)VALUES(?,?,?,?,?,?,?)";
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,notrans);
                ps.setString(2,tgltrans);
                ps.setString(3,idpel);
                ps.setString(4,idkar);
                ps.setString(5,subtotal);
                ps.setString(6,diskon);
                ps.setString(7,ttlharga);
                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null,"Transaksi berhasil disimpan dan nota akan tercetak","Informasi",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }
    }  
    
    private void tampilDetTrans(){
        String kolom1 = txtKodeBar.getText();
        String kolom2 = (String)cmbNamaHerbal.getSelectedItem();
        String kolom3 = txtHargaBar.getText();
        String kolom4 = txtDiskBrg.getText();
        String kolom5 = txtHrgDisk.getText();
        String kolom6 = txtJlhBrg.getText();
        String kolom7 = txtJlhHarga.getText();
        modelTabel.addRow(new Object [] {kolom1,kolom2,kolom3, kolom4, kolom5, kolom6, kolom7} ) ;     
        tblDataTransJual.setModel(modelTabel);
    }
    
    private void hapusItem(){
        if (tblDataTransJual.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Mohon pilih item pada tabel","Perhatian",JOptionPane.WARNING_MESSAGE);
        } 
        else {
            String sql = "DELETE FROM dtltransjual WHERE noTrans=? AND kdBrg=?";
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtNoTrans.getText());
                ps.setString(2, tblDataTransJual.getValueAt(tblDataTransJual.getSelectedRow(),0).toString());
                ps.executeUpdate();
            } 
            catch (Exception e){
                JOptionPane.showMessageDialog(this, "Data gagal dihapus",null,JOptionPane.ERROR_MESSAGE);
            }
            
            String sqlcekstok = "SELECT * FROM barang WHERE kdBrg=?";
            String sqlupdate = "UPDATE barang SET stok=? WHERE kdBrg=?";
            try {
                PreparedStatement pscekstok = conn.prepareStatement(sqlcekstok);
                pscekstok.setString(1, tblDataTransJual.getValueAt(tblDataTransJual.getSelectedRow(),0).toString());
                ResultSet rscekstok = pscekstok.executeQuery();
                if(rscekstok.next()){
                    String kdBrg=rscekstok.getString(1);
                    String stok=rscekstok.getString(6);
                    String stokretur = tblDataTransJual.getValueAt(tblDataTransJual.getSelectedRow(),5).toString();
                    int stokbaru = Integer.parseInt(stok)+Integer.parseInt(stokretur);
                             
                    PreparedStatement psupdate = conn.prepareStatement(sqlupdate);
                    psupdate.setString(1, String.valueOf(stokbaru));
                    psupdate.setString(2, tblDataTransJual.getValueAt(tblDataTransJual.getSelectedRow(),0).toString());
                    psupdate.executeUpdate();
                    txtSisaStok.setText(String.valueOf(stokbaru));
                }    
            } 
            catch (Exception e) {
                JOptionPane.showMessageDialog(null,"error : "+e.getMessage());
            }   
            
            int selectedRow = tblDataTransJual.getSelectedRow();
            if(selectedRow != -1) {
                modelTabel.removeRow(selectedRow);
            } 
        }
    }
    
    public void cekData(){
        if (cmbPel.getSelectedIndex()==0||cmbNamaHerbal.getSelectedIndex()==0||tblDataTransJual.getRowCount()==0){
            btnSimpTrans.setEnabled(false);
        }
        else{
            btnSimpTrans.setEnabled(true);
        }
    }
    
    
    public void cekStok(){
        if (Integer.parseInt(txtJlhBrg.getText())>Integer.parseInt(txtStok.getText())){
            JOptionPane.showMessageDialog(null,"Jumlah pembelian lebih besar dari jumlah stok, silahkan kurangi jumlah pembelian!","Perhatian",JOptionPane.WARNING_MESSAGE);
            txtJlhBrg.requestFocus();
        }
        else{
            hitungStok();
            tambahDetTrans();
            hitungSubTotal();
            hitungDiskTotal();
            hitungUangKembali();
            tampilDetTrans();
            cekDetTrans();
            cekData();
            cekItem();
            updateStok();
        }
    }
    
    public void cekItem(){
        if (tblDataTransJual.getRowCount()==0){
            btnHpsItem.setEnabled(false);
        }
        else{
            btnHpsItem.setEnabled(true);
        }
    }
    
    public void cekDetTrans(){
        if(cmbNamaHerbal.getSelectedIndex()==0){
            btnTambahBrg.setEnabled(false);
        }
        else{
            String sql = "SELECT * FROM dtltransjual WHERE noTrans=? AND kdBrg=?";
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
    
    private void updateStok(){
        String sql = "UPDATE barang SET stok=? WHERE kdBrg=?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtSisaStok.getText());
            ps.setString(2, txtKodeBar.getText());
            ps.executeUpdate();
        } 
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"error :"+e.getMessage());
        }        
    }
                        
    public void hitungSubTotal(){
        String sql = "SELECT SUM(jlhHarga) FROM dtltransjual WHERE noTrans='" + txtNoTrans.getText() + "'";
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
    
    public void cetakNota() {
        try {
            Statement st = (Statement) conn.createStatement();
            InputStream path = getClass().getResourceAsStream("/laporan/nota.jasper");     
            HashMap parameter = new HashMap();
            parameter.put("noTrans",txtNoTrans.getText());   
            JasperPrint print = JasperFillManager.fillReport(path,parameter,st.getConnection());
            JasperPrintManager.printReport(print, false);
            //JasperViewer.viewReport(print, false);
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane,"Dokumen Tidak Ada "+ex);    
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
    
    public void tampilItemTerpilih(){
        cmbNamaHerbal.setSelectedItem(tblDataTransJual.getValueAt(tblDataTransJual.getSelectedRow(),1).toString());
        txtDiskBrg.setText(tblDataTransJual.getValueAt(tblDataTransJual.getSelectedRow(),3).toString());
        txtJlhBrg.setText(tblDataTransJual.getValueAt(tblDataTransJual.getSelectedRow(),5).toString());
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
        btnBaru = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtHargaBar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtKet = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtJlhBrg = new javax.swing.JTextField();
        btnTambahBrg = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataTransJual = new javax.swing.JTable();
        btnSimpTrans = new javax.swing.JButton();
        btnTutup = new javax.swing.JButton();
        btnHpsItem = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDiskTtl = new javax.swing.JTextField();
        txtTotalHarga = new javax.swing.JTextField();
        txtJlhUang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtUangKembali = new javax.swing.JTextField();
        lblTtlBayar = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDiskBrg = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtHrgDisk = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblNamaKar = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        dtcTglTrans = new com.toedter.calendar.JDateChooser();
        cmbPel = new javax.swing.JComboBox();
        txtIdPel = new javax.swing.JTextField();
        cmbNamaHerbal = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtKodeBar = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtJlhHarga = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtSisaStok = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();

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

        jLabel5.setText("Tgl. Transaksi");

        jLabel6.setText("Pelanggan");

        btnBaru.setText("Baru");
        btnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaruActionPerformed(evt);
            }
        });

        jLabel7.setText("Keterangan");

        jLabel8.setBackground(new java.awt.Color(190, 196, 188));
        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel8.setText("Data Barang");
        jLabel8.setOpaque(true);

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

        btnTambahBrg.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        btnTambahBrg.setText("Tambah");
        btnTambahBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBrgActionPerformed(evt);
            }
        });

        tblDataTransJual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama", "Harga", "Diskon", "Harga Diskon", "Jumlah", "Jumlah Harga"
            }
        ));
        tblDataTransJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataTransJualMouseClicked(evt);
            }
        });
        tblDataTransJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDataTransJualKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataTransJual);
        if (tblDataTransJual.getColumnModel().getColumnCount() > 0) {
            tblDataTransJual.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblDataTransJual.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblDataTransJual.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblDataTransJual.getColumnModel().getColumn(3).setPreferredWidth(10);
            tblDataTransJual.getColumnModel().getColumn(4).setPreferredWidth(30);
            tblDataTransJual.getColumnModel().getColumn(5).setPreferredWidth(10);
            tblDataTransJual.getColumnModel().getColumn(6).setPreferredWidth(40);
        }

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

        txtDiskTtl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiskTtlFocusLost(evt);
            }
        });
        txtDiskTtl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiskTtlKeyReleased(evt);
            }
        });

        txtTotalHarga.setEditable(false);

        txtJlhUang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJlhUangActionPerformed(evt);
            }
        });
        txtJlhUang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJlhUangKeyReleased(evt);
            }
        });

        jLabel17.setText("Uang Kembali");

        txtUangKembali.setEditable(false);
        txtUangKembali.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N

        lblTtlBayar.setBackground(new java.awt.Color(250, 182, 11));
        lblTtlBayar.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        lblTtlBayar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTtlBayar.setText("Rp.2000000");
        lblTtlBayar.setOpaque(true);

        jLabel20.setBackground(new java.awt.Color(190, 196, 188));
        jLabel20.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Total Bayar");
        jLabel20.setOpaque(true);

        jLabel18.setText("Diskon");

        jLabel21.setText("Total Harga");

        jLabel22.setText("Jumlah Uang");

        jLabel3.setText("%");

        jLabel14.setText("Diskon");

        txtDiskBrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiskBrgFocusLost(evt);
            }
        });
        txtDiskBrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiskBrgKeyReleased(evt);
            }
        });

        jLabel15.setText("%");

        jLabel16.setText("Harga Diskon");

        txtHrgDisk.setEditable(false);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setText("Nama User :");

        lblNamaKar.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblNamaKar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNamaKar.setText("nmKar");

        jLabel23.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel23.setText("Transaksi Penjualan");

        dtcTglTrans.setDateFormatString("dd-MM-yyyy");

        cmbPel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--------Pilih--------" }));
        cmbPel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPelActionPerformed(evt);
            }
        });

        txtIdPel.setEditable(false);

        cmbNamaHerbal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "------------------------Pilih------------------------" }));
        cmbNamaHerbal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNamaHerbalActionPerformed(evt);
            }
        });

        jLabel19.setText("ID");

        jLabel24.setText("Kode");

        txtKodeBar.setEditable(false);

        jLabel25.setText("Jumlah Harga");

        txtJlhHarga.setEditable(false);

        jLabel26.setText("Stok");

        txtStok.setEditable(false);

        jLabel27.setText("Sisa Stok");

        txtSisaStok.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblNamaKar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnHpsItem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimpTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTutup, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHargaBar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiskBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHrgDisk, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtJlhBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtJlhHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbNamaHerbal, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtKodeBar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSisaStok, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTambahBrg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(dtcTglTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(cmbPel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel19)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtIdPel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnBaru))
                                        .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(27, 27, 27)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtJlhUang, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                    .addComponent(txtSubTotal)
                                    .addComponent(txtUangKembali)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDiskTtl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel3))
                                    .addComponent(txtTotalHarga))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblTtlBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblNamaKar)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dtcTglTrans, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel18)
                                .addComponent(txtDiskTtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(btnBaru)
                            .addComponent(cmbPel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdPel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21)
                            .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(txtJlhUang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtUangKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)))))
                    .addComponent(lblTtlBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cmbNamaHerbal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(txtKodeBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)
                            .addComponent(txtSisaStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtHargaBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14)
                            .addComponent(txtDiskBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(txtHrgDisk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJlhBrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(txtJlhHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnTambahBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHpsItem)
                    .addComponent(btnTutup)
                    .addComponent(btnSimpTrans))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtJlhUangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJlhUangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJlhUangActionPerformed

    private void btnSimpTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpTransActionPerformed
        // TODO add your handling code here:
        simpanTrans();
        cetakNota();
        resetAll();
        hapusIsiTabel();
        cekItem();
        cekSimpan();
    }//GEN-LAST:event_btnSimpTransActionPerformed

    private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
        // TODO add your handling code here:
        new pelanggan(null,true).show();
        cmbPel.removeAllItems();
        cmbPel.addItem("--------Pilih--------");
        tampilPelCombo();
    }//GEN-LAST:event_btnBaruActionPerformed

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnTutupActionPerformed

    private void cmbPelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPelActionPerformed
        // TODO add your handling code here:
        int pelanggan= cmbPel.getSelectedIndex();
        if (pelanggan==0){
            resetDataPel();
            nonAktifDataBar();
            nonAktifRincBel();
        }
        else{
            tampilIdPel();
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
    }//GEN-LAST:event_cmbPelActionPerformed

    private void cmbNamaHerbalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNamaHerbalActionPerformed
        // TODO add your handling code here:
        int namaherbal= cmbNamaHerbal.getSelectedIndex();
        if (namaherbal==0){
            resetDataBarang();
            btnTambahBrg.setEnabled(false);
            nonAktifDataBar();
            nonAktifRincBel();
            cmbNamaHerbal.setEnabled(true);
        }
        else{
            tampilKdHrgHerbalCombo();
            hitungHargaDisk();
            hitungJlhHarga(); 
            btnTambahBrg.setEnabled(true);
            txtDiskBrg.setText("0");
            txtJlhBrg.setText("1");
            aktifDataBar();
        }
        cekDetTrans();
        cekData();
    }//GEN-LAST:event_cmbNamaHerbalActionPerformed

    private void btnTambahBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBrgActionPerformed
        // TODO add your handling code here:
        cekStok();
        cekSimpan();
        aktifRincBel();
    }//GEN-LAST:event_btnTambahBrgActionPerformed

    private void txtDiskBrgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiskBrgKeyReleased
        // TODO add your handling code here:
            hitungHargaDisk();
            hitungJlhHarga();
    }//GEN-LAST:event_txtDiskBrgKeyReleased

    private void txtDiskBrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiskBrgFocusLost
        // TODO add your handling code here:
        if (txtDiskBrg.getText().equals("")){
            txtDiskBrg.setText("0");
        }
        hitungHargaDisk();
        hitungJlhHarga();
    }//GEN-LAST:event_txtDiskBrgFocusLost

    private void txtJlhBrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJlhBrgFocusLost
        // TODO add your handling code here:
        if (txtJlhBrg.getText().equals("")||txtJlhBrg.getText().equals("0")){
            txtJlhBrg.setText("1");
        }
        hitungJlhHarga();
    }//GEN-LAST:event_txtJlhBrgFocusLost

    private void txtJlhBrgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJlhBrgKeyReleased
        // TODO add your handling code here:
        hitungJlhHarga();
    }//GEN-LAST:event_txtJlhBrgKeyReleased

    private void txtDiskTtlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiskTtlKeyReleased
        // TODO add your handling code here:
        hitungDiskTotal();
    }//GEN-LAST:event_txtDiskTtlKeyReleased

    private void txtDiskTtlFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiskTtlFocusLost
        // TODO add your handling code here:
        hitungDiskTotal();
    }//GEN-LAST:event_txtDiskTtlFocusLost

    private void txtJlhUangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJlhUangKeyReleased
        // TODO add your handling code here:
        hitungUangKembali();
    }//GEN-LAST:event_txtJlhUangKeyReleased

    private void btnHpsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHpsItemActionPerformed
        // TODO add your handling code here:
        hapusItem();
        cekItem();
        cekDetTrans();
        cekData();
        cekSimpan();
        hitungSubTotal();
        hitungDiskTotal();
    }//GEN-LAST:event_btnHpsItemActionPerformed

    private void txtSubTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubTotalActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (btnSimpTrans.isEnabled()){
            JOptionPane.showMessageDialog(null, "Transaksi belum disimpan, silahkan simpan transaksi terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
        else{
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void tblDataTransJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataTransJualMouseClicked
        // TODO add your handling code here:
        tampilItemTerpilih();
    }//GEN-LAST:event_tblDataTransJualMouseClicked

    private void tblDataTransJualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDataTransJualKeyReleased
        // TODO add your handling code here:
        tampilItemTerpilih();
    }//GEN-LAST:event_tblDataTransJualKeyReleased

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
            java.util.logging.Logger.getLogger(transJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                transJual dialog = new transJual(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnHpsItem;
    private javax.swing.JButton btnSimpTrans;
    private javax.swing.JButton btnTambahBrg;
    private javax.swing.JButton btnTutup;
    private javax.swing.JComboBox cmbNamaHerbal;
    private javax.swing.JComboBox cmbPel;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
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
    private javax.swing.JLabel lblTtlBayar;
    private javax.swing.JTable tblDataTransJual;
    private javax.swing.JTextField txtDiskBrg;
    private javax.swing.JTextField txtDiskTtl;
    private javax.swing.JTextField txtHargaBar;
    private javax.swing.JTextField txtHrgDisk;
    private javax.swing.JTextField txtIdPel;
    private javax.swing.JTextField txtJlhBrg;
    private javax.swing.JTextField txtJlhHarga;
    private javax.swing.JTextField txtJlhUang;
    private javax.swing.JTextField txtKet;
    private javax.swing.JTextField txtKodeBar;
    private javax.swing.JTextField txtNoTrans;
    private javax.swing.JTextField txtSisaStok;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotalHarga;
    private javax.swing.JTextField txtUangKembali;
    // End of variables declaration//GEN-END:variables
}
