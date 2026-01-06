<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Photographer Studio</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <nav class="navbar">
            <div class="nav-brand">📸 Photographer Studio</div>
            <div class="nav-links">
                <a href="index.html" class="btn btn-secondary">Home</a>
            </div>
        </nav>

        <div class="form-container">
            <div class="form-card">
                <h2>Daftar Akun Photographer</h2>

                <%
                    String errorMsg = (String) request.getAttribute("errorMsg");
                    String successMsg = (String) request.getAttribute("successMsg");
                    if (errorMsg != null && !errorMsg.isEmpty()) {
                %>
                    <div class="alert alert-danger"><%= errorMsg %></div>
                <% }
                    if (successMsg != null && !successMsg.isEmpty()) {
                %>
                    <div class="alert alert-success"><%= successMsg %></div>
                <% } %>

                <form method="POST" action="RegisterServlet">
                    <div class="form-group">
                        <label for="name">Nama Lengkap:</label>
                        <input type="text" id="name" name="name" required
                               placeholder="Min 3 karakter" autofocus>
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required
                               placeholder="Masukkan email">
                    </div>

                    <div class="form-group">
                        <label for="phone">Nomor Telepon:</label>
                        <input type="tel" id="phone" name="phone" required
                               placeholder="10-20 digit">
                    </div>

                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" required
                               placeholder="Min 6 karakter">
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Konfirmasi Password:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required
                               placeholder="Ulangi password">
                    </div>

                    <button type="submit" class="btn btn-primary btn-block">Daftar</button>
                </form>

                <p class="form-footer">
                    Sudah punya akun? <a href="login.jsp">Login di sini</a>
                </p>
            </div>
        </div>

        <footer class="footer">
            <p>&copy; 2024 Photographer Management System.</p>
        </footer>
    </div>
</body>
</html>