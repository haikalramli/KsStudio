package dao;

import model.Package;
import model.IndoorPackage;
import model.OutdoorPackage;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDAOImpl implements PackageDAO {

	@Override
	public boolean createIndoorPackage(IndoorPackage indoorPackage) {
	    String sqlPackage = 
	        "INSERT INTO PACKAGE (PHOTOGRAPHER_ID, PACKAGE_NAME, PRICE, CATEGORY, " +
	        "DURATION, EVENT_TYPE, PACKAGE_TYPE, DESCRIPTION) " +
	        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    // Query untuk get last inserted PACKAGE_ID
	    String sqlGetLastId = "SELECT PACKAGE_ID FROM PACKAGE WHERE ROWID = (SELECT MAX(ROWID) FROM PACKAGE)";

	    String sqlIndoor =
	        "INSERT INTO INDOOR_PACKAGE (PACKAGE_ID, NUMBER_OF_PAX, BACKGROUND_TYPE) " +
	        "VALUES (?, ?, ?)";

	    Connection conn = null;
	    try {
	        conn = DatabaseConnection.getConnection();
	        conn.setAutoCommit(false);

	        // Insert ke PACKAGE table
	        PreparedStatement psPackage = conn.prepareStatement(sqlPackage);
	        
	        psPackage.setInt(1, indoorPackage.getPhotographerId());
	        psPackage.setString(2, indoorPackage.getPackageName());
	        psPackage.setDouble(3, indoorPackage.getPrice());
	        psPackage.setString(4, indoorPackage.getCategory());
	        psPackage.setInt(5, indoorPackage.getDuration());
	        psPackage.setString(6, indoorPackage.getEventType());
	        psPackage.setString(7, "INDOOR");
	        psPackage.setString(8, indoorPackage.getDescription());

	        int rowsInserted = psPackage.executeUpdate();
	        psPackage.close();
	        
	        if (rowsInserted <= 0) {
	            conn.rollback();
	            return false;
	        }

	        // Get last inserted PACKAGE_ID
	        int packageId = 0;
	        PreparedStatement psGetId = conn.prepareStatement(sqlGetLastId);
	        ResultSet rsId = psGetId.executeQuery();
	        if (rsId.next()) {
	            packageId = rsId.getInt(1);
	        } else {
	            conn.rollback();
	            psGetId.close();
	            return false;
	        }
	        psGetId.close();

	        // Insert ke INDOOR_PACKAGE table
	        PreparedStatement psIndoor = conn.prepareStatement(sqlIndoor);
	        psIndoor.setInt(1, packageId);
	        psIndoor.setInt(2, indoorPackage.getNumberOfPax());
	        psIndoor.setString(3, indoorPackage.getBackgroundType());
	        
	        int indoorRowsInserted = psIndoor.executeUpdate();
	        psIndoor.close();

	        if (indoorRowsInserted > 0) {
	            conn.commit();
	            return true;
	        } else {
	            conn.rollback();
	            return false;
	        }

	    } catch (Exception e) {
	        try {
	            if (conn != null) {
	                conn.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (conn != null) {
	                conn.setAutoCommit(true);
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public boolean createOutdoorPackage(OutdoorPackage outdoorPackage) {
	    String sqlPackage = 
	        "INSERT INTO PACKAGE (PHOTOGRAPHER_ID, PACKAGE_NAME, PRICE, CATEGORY, " +
	        "DURATION, EVENT_TYPE, PACKAGE_TYPE, DESCRIPTION) " +
	        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    // Query untuk get last inserted PACKAGE_ID
	    String sqlGetLastId = "SELECT PACKAGE_ID FROM PACKAGE WHERE ROWID = (SELECT MAX(ROWID) FROM PACKAGE)";

	    String sqlOutdoor =
	        "INSERT INTO OUTDOOR_PACKAGE (PACKAGE_ID, DISTANCE, DISTANCE_PRICE_PER_KM, LOCATION) " +
	        "VALUES (?, ?, ?, ?)";

	    Connection conn = null;
	    try {
	        conn = DatabaseConnection.getConnection();
	        conn.setAutoCommit(false);

	        // Insert ke PACKAGE table
	        PreparedStatement psPackage = conn.prepareStatement(sqlPackage);
	        
	        psPackage.setInt(1, outdoorPackage.getPhotographerId());
	        psPackage.setString(2, outdoorPackage.getPackageName());
	        psPackage.setDouble(3, outdoorPackage.getPrice());
	        psPackage.setString(4, outdoorPackage.getCategory());
	        psPackage.setInt(5, outdoorPackage.getDuration());
	        psPackage.setString(6, outdoorPackage.getEventType());
	        psPackage.setString(7, "OUTDOOR");
	        psPackage.setString(8, outdoorPackage.getDescription());

	        int rowsInserted = psPackage.executeUpdate();
	        psPackage.close();
	        
	        if (rowsInserted <= 0) {
	            conn.rollback();
	            return false;
	        }

	        // Get last inserted PACKAGE_ID
	        int packageId = 0;
	        PreparedStatement psGetId = conn.prepareStatement(sqlGetLastId);
	        ResultSet rsId = psGetId.executeQuery();
	        if (rsId.next()) {
	            packageId = rsId.getInt(1);
	        } else {
	            conn.rollback();
	            psGetId.close();
	            return false;
	        }
	        psGetId.close();

	        // Insert ke OUTDOOR_PACKAGE table
	        PreparedStatement psOutdoor = conn.prepareStatement(sqlOutdoor);
	        psOutdoor.setInt(1, packageId);
	        psOutdoor.setDouble(2, outdoorPackage.getDistance());
	        psOutdoor.setDouble(3, outdoorPackage.getDistancePricePerKm());
	        psOutdoor.setString(4, outdoorPackage.getLocation());
	        
	        int outdoorRowsInserted = psOutdoor.executeUpdate();
	        psOutdoor.close();

	        if (outdoorRowsInserted > 0) {
	            conn.commit();
	            return true;
	        } else {
	            conn.rollback();
	            return false;
	        }

	    } catch (Exception e) {
	        try {
	            if (conn != null) {
	                conn.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (conn != null) {
	                conn.setAutoCommit(true);
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

    @Override
    public Package getPackageById(int packageId) {
        String sql = "SELECT * FROM PACKAGE WHERE PACKAGE_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, packageId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Package(
                    rs.getInt("PACKAGE_ID"),
                    rs.getInt("PHOTOGRAPHER_ID"),
                    rs.getString("PACKAGE_NAME"),
                    rs.getDouble("PRICE"),
                    rs.getString("CATEGORY"),
                    rs.getInt("DURATION"),
                    rs.getString("EVENT_TYPE"),
                    rs.getString("PACKAGE_TYPE"),
                    rs.getString("DESCRIPTION"),
                    rs.getTimestamp("CREATED_DATE") != null ? rs.getTimestamp("CREATED_DATE").toLocalDateTime() : null,
                    rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null,
                    rs.getString("STATUS")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IndoorPackage getIndoorPackageById(int packageId) {
        String sql = "SELECT p.*, i.INDOOR_PACKAGE_ID, i.NUMBER_OF_PAX, i.BACKGROUND_TYPE " +
                    "FROM PACKAGE p " +
                    "LEFT JOIN INDOOR_PACKAGE i ON p.PACKAGE_ID = i.PACKAGE_ID " +
                    "WHERE p.PACKAGE_ID = ? AND p.PACKAGE_TYPE = 'INDOOR'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, packageId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                IndoorPackage indoorPkg = new IndoorPackage(
                    rs.getInt("PACKAGE_ID"),
                    rs.getInt("PHOTOGRAPHER_ID"),
                    rs.getString("PACKAGE_NAME"),
                    rs.getDouble("PRICE"),
                    rs.getString("CATEGORY"),
                    rs.getInt("DURATION"),
                    rs.getString("EVENT_TYPE"),
                    rs.getString("DESCRIPTION"),
                    rs.getInt("NUMBER_OF_PAX"),
                    rs.getString("BACKGROUND_TYPE"),
                    rs.getTimestamp("CREATED_DATE") != null ? rs.getTimestamp("CREATED_DATE").toLocalDateTime() : null,
                    rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null,
                    rs.getString("STATUS")
                );
                indoorPkg.setIndoorPackageId(rs.getInt("INDOOR_PACKAGE_ID"));
                return indoorPkg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OutdoorPackage getOutdoorPackageById(int packageId) {
        String sql = "SELECT p.*, o.OUTDOOR_PACKAGE_ID, o.DISTANCE, o.DISTANCE_PRICE_PER_KM, o.LOCATION " +
                    "FROM PACKAGE p " +
                    "LEFT JOIN OUTDOOR_PACKAGE o ON p.PACKAGE_ID = o.PACKAGE_ID " +
                    "WHERE p.PACKAGE_ID = ? AND p.PACKAGE_TYPE = 'OUTDOOR'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, packageId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                OutdoorPackage outdoorPkg = new OutdoorPackage(
                    rs.getInt("PACKAGE_ID"),
                    rs.getInt("PHOTOGRAPHER_ID"),
                    rs.getString("PACKAGE_NAME"),
                    rs.getDouble("PRICE"),
                    rs.getString("CATEGORY"),
                    rs.getInt("DURATION"),
                    rs.getString("EVENT_TYPE"),
                    rs.getString("DESCRIPTION"),
                    rs.getDouble("DISTANCE"),
                    rs.getDouble("DISTANCE_PRICE_PER_KM"),
                    rs.getString("LOCATION"),
                    rs.getTimestamp("CREATED_DATE") != null ? rs.getTimestamp("CREATED_DATE").toLocalDateTime() : null,
                    rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null,
                    rs.getString("STATUS")
                );
                outdoorPkg.setOutdoorPackageId(rs.getInt("OUTDOOR_PACKAGE_ID"));
                return outdoorPkg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Package> getAllPackagesByPhotographer(int photographerId) {
        List<Package> packages = new ArrayList<>();
        String sql = "SELECT * FROM PACKAGE WHERE PHOTOGRAPHER_ID = ? AND STATUS = 'ACTIVE' ORDER BY CREATED_DATE DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, photographerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                packages.add(new Package(
                    rs.getInt("PACKAGE_ID"),
                    rs.getInt("PHOTOGRAPHER_ID"),
                    rs.getString("PACKAGE_NAME"),
                    rs.getDouble("PRICE"),
                    rs.getString("CATEGORY"),
                    rs.getInt("DURATION"),
                    rs.getString("EVENT_TYPE"),
                    rs.getString("PACKAGE_TYPE"),
                    rs.getString("DESCRIPTION"),
                    rs.getTimestamp("CREATED_DATE") != null ? rs.getTimestamp("CREATED_DATE").toLocalDateTime() : null,
                    rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null,
                    rs.getString("STATUS")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    @Override
    public List<IndoorPackage> getAllIndoorPackagesByPhotographer(int photographerId) {
        List<IndoorPackage> packages = new ArrayList<>();
        String sql = "SELECT p.*, i.INDOOR_PACKAGE_ID, i.NUMBER_OF_PAX, i.BACKGROUND_TYPE " +
                    "FROM PACKAGE p " +
                    "LEFT JOIN INDOOR_PACKAGE i ON p.PACKAGE_ID = i.PACKAGE_ID " +
                    "WHERE p.PHOTOGRAPHER_ID = ? AND p.PACKAGE_TYPE = 'INDOOR' AND p.STATUS = 'ACTIVE' " +
                    "ORDER BY p.CREATED_DATE DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, photographerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                IndoorPackage pkg = new IndoorPackage(
                    rs.getInt("PACKAGE_ID"),
                    rs.getInt("PHOTOGRAPHER_ID"),
                    rs.getString("PACKAGE_NAME"),
                    rs.getDouble("PRICE"),
                    rs.getString("CATEGORY"),
                    rs.getInt("DURATION"),
                    rs.getString("EVENT_TYPE"),
                    rs.getString("DESCRIPTION"),
                    rs.getInt("NUMBER_OF_PAX"),
                    rs.getString("BACKGROUND_TYPE"),
                    rs.getTimestamp("CREATED_DATE") != null ? rs.getTimestamp("CREATED_DATE").toLocalDateTime() : null,
                    rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null,
                    rs.getString("STATUS")
                );
                pkg.setIndoorPackageId(rs.getInt("INDOOR_PACKAGE_ID"));
                packages.add(pkg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    @Override
    public List<OutdoorPackage> getAllOutdoorPackagesByPhotographer(int photographerId) {
        List<OutdoorPackage> packages = new ArrayList<>();
        String sql = "SELECT p.*, o.OUTDOOR_PACKAGE_ID, o.DISTANCE, o.DISTANCE_PRICE_PER_KM, o.LOCATION " +
                    "FROM PACKAGE p " +
                    "LEFT JOIN OUTDOOR_PACKAGE o ON p.PACKAGE_ID = o.PACKAGE_ID " +
                    "WHERE p.PHOTOGRAPHER_ID = ? AND p.PACKAGE_TYPE = 'OUTDOOR' AND p.STATUS = 'ACTIVE' " +
                    "ORDER BY p.CREATED_DATE DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, photographerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OutdoorPackage pkg = new OutdoorPackage(
                    rs.getInt("PACKAGE_ID"),
                    rs.getInt("PHOTOGRAPHER_ID"),
                    rs.getString("PACKAGE_NAME"),
                    rs.getDouble("PRICE"),
                    rs.getString("CATEGORY"),
                    rs.getInt("DURATION"),
                    rs.getString("EVENT_TYPE"),
                    rs.getString("DESCRIPTION"),
                    rs.getDouble("DISTANCE"),
                    rs.getDouble("DISTANCE_PRICE_PER_KM"),
                    rs.getString("LOCATION"),
                    rs.getTimestamp("CREATED_DATE") != null ? rs.getTimestamp("CREATED_DATE").toLocalDateTime() : null,
                    rs.getTimestamp("UPDATED_DATE") != null ? rs.getTimestamp("UPDATED_DATE").toLocalDateTime() : null,
                    rs.getString("STATUS")
                );
                pkg.setOutdoorPackageId(rs.getInt("OUTDOOR_PACKAGE_ID"));
                packages.add(pkg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    @Override
    public boolean updateIndoorPackage(IndoorPackage indoorPackage) {
        String sqlPackage = "UPDATE PACKAGE SET PACKAGE_NAME = ?, PRICE = ?, CATEGORY = ?, " +
                           "DURATION = ?, EVENT_TYPE = ?, DESCRIPTION = ?, UPDATED_DATE = SYSTIMESTAMP " +
                           "WHERE PACKAGE_ID = ?";
        
        String sqlIndoor = "UPDATE INDOOR_PACKAGE SET NUMBER_OF_PAX = ?, BACKGROUND_TYPE = ? " +
                          "WHERE PACKAGE_ID = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmtPackage = conn.prepareStatement(sqlPackage);
            pstmtPackage.setString(1, indoorPackage.getPackageName());
            pstmtPackage.setDouble(2, indoorPackage.getPrice());
            pstmtPackage.setString(3, indoorPackage.getCategory());
            pstmtPackage.setInt(4, indoorPackage.getDuration());
            pstmtPackage.setString(5, indoorPackage.getEventType());
            pstmtPackage.setString(6, indoorPackage.getDescription());
            pstmtPackage.setInt(7, indoorPackage.getPackageId());
            pstmtPackage.executeUpdate();
            pstmtPackage.close();

            PreparedStatement pstmtIndoor = conn.prepareStatement(sqlIndoor);
            pstmtIndoor.setInt(1, indoorPackage.getNumberOfPax());
            pstmtIndoor.setString(2, indoorPackage.getBackgroundType());
            pstmtIndoor.setInt(3, indoorPackage.getPackageId());
            pstmtIndoor.executeUpdate();
            pstmtIndoor.close();

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateOutdoorPackage(OutdoorPackage outdoorPackage) {
        String sqlPackage = "UPDATE PACKAGE SET PACKAGE_NAME = ?, PRICE = ?, CATEGORY = ?, " +
                           "DURATION = ?, EVENT_TYPE = ?, DESCRIPTION = ?, UPDATED_DATE = SYSTIMESTAMP " +
                           "WHERE PACKAGE_ID = ?";
        
        String sqlOutdoor = "UPDATE OUTDOOR_PACKAGE SET DISTANCE = ?, DISTANCE_PRICE_PER_KM = ?, LOCATION = ? " +
                           "WHERE PACKAGE_ID = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmtPackage = conn.prepareStatement(sqlPackage);
            pstmtPackage.setString(1, outdoorPackage.getPackageName());
            pstmtPackage.setDouble(2, outdoorPackage.getPrice());
            pstmtPackage.setString(3, outdoorPackage.getCategory());
            pstmtPackage.setInt(4, outdoorPackage.getDuration());
            pstmtPackage.setString(5, outdoorPackage.getEventType());
            pstmtPackage.setString(6, outdoorPackage.getDescription());
            pstmtPackage.setInt(7, outdoorPackage.getPackageId());
            pstmtPackage.executeUpdate();
            pstmtPackage.close();

            PreparedStatement pstmtOutdoor = conn.prepareStatement(sqlOutdoor);
            pstmtOutdoor.setDouble(1, outdoorPackage.getDistance());
            pstmtOutdoor.setDouble(2, outdoorPackage.getDistancePricePerKm());
            pstmtOutdoor.setString(3, outdoorPackage.getLocation());
            pstmtOutdoor.setInt(4, outdoorPackage.getPackageId());
            pstmtOutdoor.executeUpdate();
            pstmtOutdoor.close();

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean deletePackage(int packageId) {
        String sqlPackage = "DELETE FROM PACKAGE WHERE PACKAGE_ID = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            PreparedStatement pstmt = conn.prepareStatement(sqlPackage);
            pstmt.setInt(1, packageId);
            int rowsDeleted = pstmt.executeUpdate();
            pstmt.close();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}