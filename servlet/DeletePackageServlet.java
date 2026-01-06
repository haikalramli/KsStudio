package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.PackageDAO;
import dao.PackageDAOImpl;

@WebServlet("/DeletePackageServlet")
public class DeletePackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PackageDAO packageDAO = new PackageDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer photographerId = (Integer) request.getSession().getAttribute("photographerId");
        
        if (photographerId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String packageIdStr = request.getParameter("id");

        try {
            int packageId = Integer.parseInt(packageIdStr);
            packageDAO.deletePackage(packageId);
            response.sendRedirect("PackageServlet");
            
        } catch (NumberFormatException e) {
            response.sendRedirect("PackageServlet");
        }
    }
}