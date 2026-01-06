package servlet;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Photographer;
import dao.PhotographerDAO;
import dao.PhotographerDAOImpl;
import util.ValidationUtil;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PhotographerDAO photographerDAO = new PhotographerDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = ValidationUtil.sanitizeInput(request.getParameter("name"));
        String email = ValidationUtil.sanitizeInput(request.getParameter("email"));
        String phone = ValidationUtil.sanitizeInput(request.getParameter("phone"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validasi input
        if (!ValidationUtil.isValidName(name)) {
            request.setAttribute("errorMsg", "Nama harus 3-100 karakter!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("errorMsg", "Email tidak valid!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            request.setAttribute("errorMsg", "Nomor telepon harus 10-20 digit!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("errorMsg", "Password harus minimal 6 karakter!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMsg", "Password tidak cocok!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Cek email sudah ada
        if (photographerDAO.photographerExists(email)) {
            request.setAttribute("errorMsg", "Email sudah terdaftar!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Register
        Photographer photographer = new Photographer(name, email, phone, password);

        if (photographerDAO.registerPhotographer(photographer)) {
            request.setAttribute("successMsg", "Registrasi berhasil! Silakan login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMsg", "Registrasi gagal! Coba lagi.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}