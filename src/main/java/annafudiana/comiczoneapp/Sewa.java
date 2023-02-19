package annafudiana.comiczoneapp;

import java.sql.Date;

public class Sewa {
    private String Id_sewa;
    private String Id_customer;
    private String Id_karyawan;
    private Date Tanggal_pinjam;
    private Date Tanggal_kembali;
    private int Total_harga;
    private int Bayar;
    private int Kembalian;

    public Sewa(String Id_sewa, String Id_customer, String Id_karyawan,
                Date Tanggal_pinjam,Date Tanggal_kembali,
                int Total_harga, int Bayar, int Kembalian  )
    {
        this.Id_sewa = Id_sewa;
        this.Id_customer = Id_customer;
        this.Id_karyawan = Id_karyawan;
        this.Tanggal_pinjam = Tanggal_pinjam;
        this.Tanggal_kembali = Tanggal_kembali;
        this.Total_harga = Total_harga;
        this.Bayar = Bayar;
        this.Kembalian = Kembalian;
    }

    public String getId_sewa(){
        return Id_sewa;
    }

    public void setId_sewa(String Id_sewa) {
        this.Id_sewa = Id_sewa;
    }

    public String getId_customer(){
        return Id_customer;
    }

    public void setId_customer(String Id_customer) {
        this.Id_customer = Id_customer;
    }

    public String getId_karyawan(){
        return Id_karyawan;
    }

    public void setId_karyawan(String Id_karyawan) {
        this.Id_karyawan = Id_karyawan;
    }

    public Date getTanggal_pinjam(){
        return Tanggal_pinjam;
    }

    public void setTanggal_pinjam(Date Tanggal_pinjam) {
        this.Tanggal_pinjam = Tanggal_pinjam;
    }

    public Date getTanggal_kembali(){
        return Tanggal_kembali;
    }

    public void setTanggal_kembali(Date Tanggal_kembali) {
        this.Tanggal_kembali = Tanggal_kembali;
    }

    public int getTotal_harga(){
        return Total_harga;
    }

    public void setTotal_harga(int Total_harga) {
        this.Total_harga = Total_harga;
    }

    public int getBayar(){
        return Bayar;
    }

    public void setBayar(int Bayar) {
        this.Bayar = Bayar;
    }

    public int getKembalian(){
        return Kembalian;
    }

    public void setKembalian(int Kembalian) {
        this.Kembalian = Kembalian;
    }
}
