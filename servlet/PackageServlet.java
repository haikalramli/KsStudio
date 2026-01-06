package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Package;
import model.IndoorPackage;
import model.OutdoorPackage;
import dao.PackageDAO;
import dao.PackageDAOImpl;

@WebServlet("/PackageServlet")
public class PackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PackageDAO packageDAO = new PackageDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer photographerId = (Integer) request.getSession().getAttribute("photographerId");
        
        if (photographerId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Get all packages (base packages)
            List<Package> allPackages = packageDAO.getAllPackagesByPhotographer(photographerId);
            
            // Convert to specific types (IndoorPackage or OutdoorPackage)
            List<Package> packageList = new ArrayList<>();
            
            if (allPackages != null) {
                for (Package pkg : allPackages) {
                    if ("INDOOR".equals(pkg.getPackageType())) {
                        // Get indoor-specific details
                        IndoorPackage indoor = packageDAO.getIndoorPackageById(pkg.getPackageId());
                        if (indoor != null) {
                            packageList.add(indoor);
                        } else {
                            packageList.add(pkg);
                        }
                    } else if ("OUTDOOR".equals(pkg.getPackageType())) {
                        // Get outdoor-specific details
                        OutdoorPackage outdoor = packageDAO.getOutdoorPackageById(pkg.getPackageId());
                        if (outdoor != null) {
                            packageList.add(outdoor);
                        } else {
                            packageList.add(pkg);
                        }
                    }
                }
            }
            
            request.setAttribute("packages", packageList);
            request.getRequestDispatcher("package-management.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Error loading packages: " + e.getMessage());
            request.getRequestDispatcher("package-management.jsp").forward(request, response);
        }
    }
}
