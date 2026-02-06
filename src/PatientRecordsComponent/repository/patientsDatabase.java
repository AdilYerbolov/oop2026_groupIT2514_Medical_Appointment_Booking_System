package PatientRecordsComponent.repository;
import Interfaces.Repository;
import edu.aitu.oop3.db.DatabaseConnection;
import PatientRecordsComponent.model.Patient;

import java.sql.*;

public class patientsDatabase implements Repository<Patient> {
    public void main(String[] args) {
        System.out.println("connecting...");
        try (Connection connection = DatabaseConnection.getConnection()) {
            createTableIfNeeded(connection);
        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
    private void createTableIfNeeded(Connection connection) throws SQLException {
        String sql = """
 create table if not exists patients (
 id serial primary key,
 name varchar(100) not null,
 email varchar(100) not null,
phone varchar(100) not null,
password varchar(100) not null
 );
 """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Table patients is ready.");
        }
    }
    @Override
    public void insertUser(Patient user) throws SQLException {
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = "insert into patients (name, email, phone, password) values (?, ?, ?, ?) ";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPhone());
                stmt.setString(4, user.getPassword());
                int rows = stmt.executeUpdate();
                System.out.println("Inserted rows: " + rows);
            }
        }
        catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
    public String getUserPasswordByEmail(String email) throws SQLException{
        String sql = "select password from patients where email = ?";
        ResultSet rs = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, email);
                rs = stmt.executeQuery();
                if(rs.next()){
                    return rs.getString("password");
                }
                else{
                    System.out.println("User not found");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
        return null;
    }
    public int getId(String email) throws SQLException{
        String sql = "select id from patients where email = ?";
        ResultSet rs = null;
        try (Connection connection = DatabaseConnection.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, email);
                rs = stmt.executeQuery();
                if (rs.next()){
                    return rs.getInt("id");
                }
                else{
                    System.out.println("User not found");
                    return -1;
                }
            }
        } catch (SQLException e){
            System.out.println("Database error:");
            e.printStackTrace();
        }
        return -1;
    }
}
