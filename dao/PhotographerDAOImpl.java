package dao;

import model.Photographer;
import util.DatabaseConnection;
import java.sql.*;

public class PhotographerDAOImpl implements PhotographerDAO {

    @Override
    public boolean registerPhotographer(Photographer photographer) {
        String sql = "INSERT INTO PHOTOGRAPHER (NAME, EMAIL, PHONE, PASSWORD, STATUS) " +
                     "VALUES (?, ?, ?, ?, 'ACTIVE')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, photographer.getName());
            pstmt.setString(2, photographer.getEmail());
            pstmt.setString(3, photographer.getPhone());
            pstmt.setString(4, photographer.getPassword());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Photographer loginPhotographer(String email, String password) {
        String sql = "SELECT * FROM PHOTOGRAPHER WHERE EMAIL = ? AND PASSWORD = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Photographer(
                    rs.getInt("PHOTOGRAPHER_ID"),
                    rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("PHONE"),
                    rs.getString("PASSWORD"),
                    rs.getTimestamp("CREATED_DATE").toLocalDateTime(),
                    rs.getString("STATUS")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean photographerExists(String email) {
        String sql = "SELECT COUNT(*) FROM PHOTOGRAPHER WHERE EMAIL = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Photographer getPhotographerById(int photographerId) {
        String sql = "SELECT * FROM PHOTOGRAPHER WHERE PHOTOGRAPHER_ID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, photographerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Photographer(
                    rs.getInt("PHOTOGRAPHER_ID"),
                    rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("PHONE"),
                    rs.getString("PASSWORD"),
                    rs.getTimestamp("CREATED_DATE").toLocalDateTime(),
                    rs.getString("STATUS")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updatePhotographer(Photographer photographer) {
        String sql = "UPDATE PHOTOGRAPHER SET NAME = ?, EMAIL = ?, PHONE = ? WHERE PHOTOGRAPHER_ID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, photographer.getName());
            pstmt.setString(2, photographer.getEmail());
            pstmt.setString(3, photographer.getPhone());
            pstmt.setInt(4, photographer.getPhotographerId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePhotographer(int photographerId) {
        String sql = "DELETE FROM PHOTOGRAPHER WHERE PHOTOGRAPHER_ID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, photographerId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}