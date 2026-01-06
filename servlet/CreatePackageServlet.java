package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.IndoorPackage;
import model.OutdoorPackage;
import dao.PackageDAO;
import dao.PackageDAOImpl;
import util.ValidationUtil;

@WebServlet("/CreatePackageServlet")
public class CreatePackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PackageDAO packageDAO = new PackageDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer photographerId = (Integer) request.getSession().getAttribute("photographerId");
        
        if (photographerId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String packageType = request.getParameter("packageType");
            String packageName = ValidationUtil.sanitizeInput(request.getParameter("packageName"));
            String priceStr = request.getParameter("price");
            String category = ValidationUtil.sanitizeInput(request.getParameter("category"));
            String durationStr = request.getParameter("duration");
            String eventType = request.getParameter("eventType");
            String description = ValidationUtil.sanitizeInput(request.getParameter("description"));

            if (packageName == null || packageName.isEmpty() || packageName.length() > 150) {
                request.setAttribute("errorMsg", "Package Name tidak valid!");
                response.sendRedirect("PackageServlet");
                return;
            }

            double price;
            int duration;
            try {
                price = Double.parseDouble(priceStr);
                duration = Integer.parseInt(durationStr);
                
                if (price <= 0 || duration <= 0) {
                    request.setAttribute("errorMsg", "Price dan Duration harus lebih dari 0!");
                    response.sendRedirect("PackageServlet");
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMsg", "Price dan Duration harus angka!");
                response.sendRedirect("PackageServlet");
                return;
            }

            boolean success = false;

            if ("INDOOR".equals(packageType)) {
                String numberOfPaxStr = request.getParameter("numberOfPax");
                String backgroundType = ValidationUtil.sanitizeInput(request.getParameter("backgroundType"));

                int numberOfPax;
                try {
                    numberOfPax = Integer.parseInt(numberOfPaxStr);
                    if (numberOfPax <= 0) {
                        request.setAttribute("errorMsg", "Number of Pax harus lebih dari 0!");
                        response.sendRedirect("PackageServlet");
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMsg", "Number of Pax harus angka!");
                    response.sendRedirect("PackageServlet");
                    return;
                }

                if (backgroundType == null || backgroundType.isEmpty()) {
                    request.setAttribute("errorMsg", "Background Type tidak boleh kosong!");
                    response.sendRedirect("PackageServlet");
                    return;
                }

                IndoorPackage indoorPackage = new IndoorPackage(
                    photographerId, packageName, price, category, duration, eventType,
                    description, numberOfPax, backgroundType
                );

                success = packageDAO.createIndoorPackage(indoorPackage);

            } else if ("OUTDOOR".equals(packageType)) {
                String distanceStr = request.getParameter("distance");
                String distancePricePerKmStr = request.getParameter("distancePricePerKm");
                String location = ValidationUtil.sanitizeInput(request.getParameter("location"));

                double distance, distancePricePerKm;
                try {
                    distance = Double.parseDouble(distanceStr);
                    distancePricePerKm = Double.parseDouble(distancePricePerKmStr);
                    
                    if (distance < 0 || distancePricePerKm < 0) {
                        request.setAttribute("errorMsg", "Distance dan Price per KM tidak boleh negatif!");
                        response.sendRedirect("PackageServlet");
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMsg", "Distance dan Price per KM harus angka!");
                    response.sendRedirect("PackageServlet");
                    return;
                }

                if (location == null || location.isEmpty()) {
                    request.setAttribute("errorMsg", "Location tidak boleh kosong!");
                    response.sendRedirect("PackageServlet");
                    return;
                }

                OutdoorPackage outdoorPackage = new OutdoorPackage(
                    photographerId, packageName, price, category, duration, eventType,
                    description, distance, distancePricePerKm, location
                );

                success = packageDAO.createOutdoorPackage(outdoorPackage);
            }

            if (success) {
                request.setAttribute("successMsg", "Package berhasil dibuat!");
            } else {
                request.setAttribute("errorMsg", "Gagal membuat package!");
            }
            
            response.sendRedirect("PackageServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Error: " + e.getMessage());
            try {
                response.sendRedirect("PackageServlet");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}