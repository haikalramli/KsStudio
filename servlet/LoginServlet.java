package servlet;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Photographer;
import dao.PhotographerDAO;
import dao.PhotographerDAOImpl;
import util.ValidationUtil;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PhotographerDAO photographerDAO = new PhotographerDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = ValidationUtil.sanitizeInput(request.getParameter("email"));
        String password = request.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMsg", "Email dan Password tidak boleh kosong!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        Photographer photographer = photographerDAO.loginPhotographer(email, password);

        if (photographer != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("photographerId", photographer.getPhotographerId());
            session.setAttribute("name", photographer.getName());
            session.setAttribute("email", photographer.getEmail());
            session.setAttribute("phone", photographer.getPhone());

            response.sendRedirect("activity-calendar.jsp");
        } else {
            request.setAttribute("errorMsg", "Email atau Password salah!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}