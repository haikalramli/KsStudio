package dao;


import model.Package;
import model.IndoorPackage;
import model.OutdoorPackage;
import java.util.List;

public interface PackageDAO {
    // General Package Operations
    boolean createIndoorPackage(IndoorPackage indoorPackage);
    boolean createOutdoorPackage(OutdoorPackage outdoorPackage);
    
    Package getPackageById(int packageId);
    IndoorPackage getIndoorPackageById(int packageId);
    OutdoorPackage getOutdoorPackageById(int packageId);
    
    List<Package> getAllPackagesByPhotographer(int photographerId);
    List<IndoorPackage> getAllIndoorPackagesByPhotographer(int photographerId);
    List<OutdoorPackage> getAllOutdoorPackagesByPhotographer(int photographerId);
    
    boolean updateIndoorPackage(IndoorPackage indoorPackage);
    boolean updateOutdoorPackage(OutdoorPackage outdoorPackage);
    
    boolean deletePackage(int packageId);
}
