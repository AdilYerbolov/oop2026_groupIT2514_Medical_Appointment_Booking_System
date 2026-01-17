package repository;

import edu.aitu.oop3.db.DatabaseConnection;
import entity.Patient;

import java.sql.*;

public class PatientRepository {

    public Patient findById(int id) {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM patients WHERE id=?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
