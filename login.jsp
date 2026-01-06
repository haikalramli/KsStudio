<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Photographer Studio</title>
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
                <h2>Login</h2>

                <%
                    String errorMsg = (String) request.getAttribute("errorMsg");
                    if (errorMsg != null && !errorMsg.isEmpty()) {
                %>
                    <div class="alert alert-danger"><%= errorMsg %></div>
                <% } %>

                <form method="POST" action="LoginServlet">
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required
                               placeholder="Masukkan email" autofocus>
                    </div>

                    <div class="form-group">
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" required
                               placeholder="Masukkan password">
                    </div>

                    <button type="submit" class="btn btn-primary btn-block">Login</button>
                </form>

                <p class="form-footer">
                    Belum punya akun? <a href="register.jsp">Daftar di sini</a>
                </p>
            </div>
        </div>

        <footer class="footer">
            <p>&copy; 2024 Photographer Management System.</p>
        </footer>
    </div>
</body>
</html>