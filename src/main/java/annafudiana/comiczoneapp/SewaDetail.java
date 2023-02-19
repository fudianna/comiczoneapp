package annafudiana.comiczoneapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SewaDetail {
    private int Id_sewadetail;
    private String Id_sewa;
    private String Id_buku;


    public SewaDetail(int Id_sewadetail, String Id_sewa, String Id_buku) {
        this.Id_sewadetail = Id_sewadetail;
        this.Id_sewa = Id_sewa;
        this.Id_buku = Id_buku;

    }

    public int getId_sewadetail() {
        int id = 0;
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT MAX(Id_sewa_detail) AS max_id FROM Sewa_detail";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt("max_id") + 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public void setId_sewadetail(int Id_sewadetail) {
        this.Id_sewadetail =Id_sewadetail;
    }

    public String getId_sewa() {
        return Id_sewa;
    }
    public void setId_sewa(String Id_sewa) {
        this.Id_sewa = Id_sewa;
    }

    public String getId_buku() {
        return Id_buku;
    }
    public void setId_buku(String Id_buku) {
        this.Id_buku = Id_buku;
    }


}
