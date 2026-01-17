package repository;

import edu.aitu.oop3.db.DatabaseConnection;
import entity.Doctor;

import java.sql.*;

public class DoctorRepository {

    public Doctor findById(int id) {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM doctors WHERE id=?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Doctor(
                        id,
                        rs.getTime("available_from").toLocalTime(),
                        rs.getTime("available_to").toLocalTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
