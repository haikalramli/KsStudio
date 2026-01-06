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

@WebServlet("/UpdatePackageServlet")
public class UpdatePackageServlet extends HttpServlet {
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
            String packageIdStr = request.getParameter("packageId");
            String packageType = request.getParameter("packageType");
            String packageName = ValidationUtil.sanitizeInput(request.getParameter("packageName"));
            String priceStr = request.getParameter("price");
            String category = ValidationUtil.sanitizeInput(request.getParameter("category"));
            String durationStr = request.getParameter("duration");
            String eventType = request.getParameter("eventType");
            String description = ValidationUtil.sanitizeInput(request.getParameter("description"));

            int packageId;
            try {
                packageId = Integer.parseInt(packageIdStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("PackageServlet");
                return;
            }

            double price;
            int duration;
            try {
                price = Double.parseDouble(priceStr);
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
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
                } catch (NumberFormatException e) {
                    response.sendRedirect("PackageServlet");
                    return;
                }

                IndoorPackage indoorPackage = new IndoorPackage(
                    packageId, photographerId, packageName, price, category, duration, eventType,
                    description, numberOfPax, backgroundType, null, null, "ACTIVE"
                );
                indoorPackage.setPackageId(packageId);

                success = packageDAO.updateIndoorPackage(indoorPackage);

            } else if ("OUTDOOR".equals(packageType)) {
                String distanceStr = request.getParameter("distance");
                String distancePricePerKmStr = request.getParameter("distancePricePerKm");
                String location = ValidationUtil.sanitizeInput(request.getParameter("location"));

                double distance, distancePricePerKm;
                try {
                    distance = Double.parseDouble(distanceStr);
                    distancePricePerKm = Double.parseDouble(distancePricePerKmStr);
                } catch (NumberFormatException e) {
                    response.sendRedirect("PackageServlet");
                    return;
                }

                OutdoorPackage outdoorPackage = new OutdoorPackage(
                    packageId, photographerId, packageName, price, category, duration, eventType,
                    description, distance, distancePricePerKm, location, null, null, "ACTIVE"
                );
                outdoorPackage.setPackageId(packageId);

                success = packageDAO.updateOutdoorPackage(outdoorPackage);
            }

            response.sendRedirect("PackageServlet");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendRedirect("PackageServlet");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}