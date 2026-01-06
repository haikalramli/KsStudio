<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Package" %>
<%@ page import="model.IndoorPackage" %>
<%@ page import="model.OutdoorPackage" %>

<%
    Integer photographerId = (Integer) session.getAttribute("photographerId");
    String name = (String) session.getAttribute("name");
    
    if (photographerId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Package> packages = (List<Package>) request.getAttribute("packages");
    if (packages == null) {
        packages = new ArrayList<Package>();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Package Management - Photographer Studio</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .package-management {
            margin-left: 250px;
            padding: 40px;
            background: #f5f5f5;
            min-height: 100vh;
        }

        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .header-section h2 {
            color: #667eea;
            font-size: 28px;
            margin: 0;
        }

        .btn-create {
            background: #667eea;
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-create:hover {
            background: #5568d3;
            transform: translateY(-2px);
        }

        .form-container {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
            display: none;
        }

        .form-container.show {
            display: block;
            animation: slideDown 0.3s ease;
        }

        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 600;
        }

        .form-group input,
        .form-group textarea,
        .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            font-family: inherit;
        }

        .form-group input:focus,
        .form-group textarea:focus,
        .form-group select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .form-group textarea {
            resize: vertical;
            min-height: 80px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .package-type-toggle {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
            padding: 15px;
            background: #f9f9f9;
            border-radius: 4px;
        }

        .type-option {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .type-option input[type="radio"] {
            cursor: pointer;
            width: 18px;
            height: 18px;
        }

        .type-option label {
            margin: 0;
            cursor: pointer;
            font-weight: normal;
        }

        .indoor-fields,
        .outdoor-fields {
            display: none;
            padding: 20px;
            background: #f9f9f9;
            border-radius: 4px;
            margin-top: 20px;
            border-left: 4px solid #667eea;
        }

        .indoor-fields.show,
        .outdoor-fields.show {
            display: block;
        }

        .indoor-fields h4,
        .outdoor-fields h4 {
            margin-top: 0;
            color: #667eea;
        }

        .form-buttons {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }

        .btn-submit,
        .btn-cancel {
            flex: 1;
            padding: 12px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-submit {
            background: #667eea;
            color: white;
        }

        .btn-submit:hover {
            background: #5568d3;
            transform: translateY(-2px);
        }

        .btn-cancel {
            background: #ec4899;
            color: white;
        }

        .btn-cancel:hover {
            background: #db2777;
            transform: translateY(-2px);
        }

        .packages-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 25px;
            margin-top: 30px;
        }

        .package-card {
            background: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            border-left: 5px solid #667eea;
            transition: all 0.3s ease;
        }

        .package-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
        }

        .package-card.indoor {
            border-left-color: #3b82f6;
        }

        .package-card.outdoor {
            border-left-color: #10b981;
        }

        .package-id {
            font-size: 12px;
            color: #999;
            margin-bottom: 5px;
        }

        .package-header {
            margin-bottom: 15px;
        }

        .package-type-badge {
            display: inline-block;
            background: #e3f2fd;
            color: #667eea;
            padding: 5px 10px;
            border-radius: 4px;
            font-size: 11px;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .package-name {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            margin: 10px 0;
        }

        .package-price {
            font-size: 24px;
            color: #10b981;
            font-weight: bold;
            margin: 10px 0;
        }

        .package-details {
            font-size: 13px;
            color: #666;
            margin: 15px 0;
            line-height: 1.8;
        }

        .package-details p {
            margin: 6px 0;
        }

        .package-details strong {
            color: #333;
        }

        .package-description {
            background: #f9f9f9;
            padding: 12px;
            border-radius: 4px;
            font-size: 13px;
            color: #666;
            margin: 15px 0;
            border-left: 3px solid #667eea;
            line-height: 1.5;
        }

        .package-actions {
            display: flex;
            gap: 8px;
            margin-top: 15px;
        }

        .btn-view,
        .btn-edit,
        .btn-delete {
            flex: 1;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 13px;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-view {
            background: #3b82f6;
            color: white;
        }

        .btn-view:hover {
            background: #2563eb;
        }

        .btn-edit {
            background: #10b981;
            color: white;
        }

        .btn-edit:hover {
            background: #059669;
        }

        .btn-delete {
            background: #ef4444;
            color: white;
        }

        .btn-delete:hover {
            background: #dc2626;
        }

        .alert {
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            animation: slideDown 0.3s ease;
        }

        .alert-danger {
            background: #fee;
            color: #c00;
            border-left: 4px solid #c00;
        }

        .alert-success {
            background: #efe;
            color: #060;
            border-left: 4px solid #060;
        }

        .no-packages {
            text-align: center;
            color: #999;
            padding: 60px 20px;
            background: white;
            border-radius: 8px;
            grid-column: 1 / -1;
        }

        .no-packages p {
            font-size: 16px;
            margin: 15px 0;
        }

        @media (max-width: 768px) {
            .package-management {
                margin-left: 0;
                padding: 20px;
            }

            .form-row {
                grid-template-columns: 1fr;
            }

            .packages-container {
                grid-template-columns: 1fr;
            }

            .header-section {
                flex-direction: column;
                gap: 15px;
            }

            .package-type-toggle {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="app">
        <!-- Sidebar -->
        <aside class="sidebar">
            <h2 class="logo">📸 PMS</h2>
            <ul class="menu">
                <li onclick="navigateTo('activity-calendar.jsp')">📅 Activity Calendar</li>
                <li class="active">📦 Package Management</li>
                <li onclick="navigateTo('upload-photo.jsp')">📷 Upload Photo</li>
                <li onclick="navigateTo('account.jsp')">👤 Account</li>
                <li onclick="navigateTo('verify-payment.jsp')">✅ Verify Payment</li>
            </ul>
            <div class="logout" onclick="logout()">🚪 Logout</div>
        </aside>

        <!-- Main Content -->
        <main class="package-management">
            <div class="header-section">
                <h2>📦 Package Management</h2>
                <button class="btn-create" onclick="toggleForm()">+ Create New Package</button>
            </div>

            <!-- Error/Success Messages -->
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

            <!-- Create/Edit Form -->
            <div class="form-container" id="formContainer">
                <h3>Create New Package</h3>
                
                <form method="POST" action="CreatePackageServlet" id="packageForm">
                    <input type="hidden" id="packageId" name="packageId">

                    <!-- Package Type Selection -->
                    <div class="package-type-toggle">
                        <div class="type-option">
                            <input type="radio" id="typeIndoor" name="packageType" value="INDOOR" 
                                   checked onchange="togglePackageType()">
                            <label for="typeIndoor">🏢 Indoor Package</label>
                        </div>
                        <div class="type-option">
                            <input type="radio" id="typeOutdoor" name="packageType" value="OUTDOOR" 
                                   onchange="togglePackageType()">
                            <label for="typeOutdoor">🌳 Outdoor Package</label>
                        </div>
                    </div>

                    <!-- Common Fields -->
                    <div class="form-row">
                        <div class="form-group">
                            <label for="packageName">Package Name *</label>
                            <input type="text" id="packageName" name="packageName" required 
                                   placeholder="e.g., Wedding Photography">
                        </div>
                        <div class="form-group">
                            <label for="price">Price ($) *</label>
                            <input type="number" id="price" name="price" required step="0.01" min="0"
                                   placeholder="0.00">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="category">Category *</label>
                            <input type="text" id="category" name="category" required 
                                   placeholder="e.g., Events, Portraits">
                        </div>
                        <div class="form-group">
                            <label for="duration">Duration (Hours) *</label>
                            <input type="number" id="duration" name="duration" required min="1"
                                   placeholder="e.g., 8">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="eventType">Event Type *</label>
                        <select id="eventType" name="eventType" required>
                            <option value="">Select Event Type</option>
                            <option value="wedding">Wedding</option>
                            <option value="birthday">Birthday</option>
                            <option value="corporate">Corporate</option>
                            <option value="portrait">Portrait</option>
                            <option value="other">Other</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="description">Description *</label>
                        <textarea id="description" name="description" required 
                                  placeholder="Describe your package..."></textarea>
                    </div>

                    <!-- INDOOR PACKAGE FIELDS -->
                    <div class="indoor-fields show" id="indoorFields">
                        <h4>🏢 Indoor Package Details</h4>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="numberOfPax">Number of Pax (Persons) *</label>
                                <input type="number" id="numberOfPax" name="numberOfPax" min="1"
                                       placeholder="e.g., 50">
                            </div>
                            <div class="form-group">
                                <label for="backgroundType">Background Type *</label>
                                <input type="text" id="backgroundType" name="backgroundType"
                                       placeholder="e.g., Backdrop, Studio Setup">
                            </div>
                        </div>
                    </div>

                    <!-- OUTDOOR PACKAGE FIELDS -->
                    <div class="outdoor-fields" id="outdoorFields">
                        <h4>🌳 Outdoor Package Details</h4>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="distance">Distance (KM) *</label>
                                <input type="number" id="distance" name="distance" step="0.1" min="0"
                                       placeholder="e.g., 50">
                            </div>
                            <div class="form-group">
                                <label for="distancePricePerKm">Price Per KM ($) *</label>
                                <input type="number" id="distancePricePerKm" name="distancePricePerKm" 
                                       step="0.01" min="0" placeholder="e.g., 5.00">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="location">Location *</label>
                            <input type="text" id="location" name="location"
                                   placeholder="e.g., Kuala Lumpur, Selangor">
                        </div>
                    </div>

                    <div class="form-buttons">
                        <button type="submit" class="btn-submit">Save Package</button>
                        <button type="button" class="btn-cancel" onclick="toggleForm()">Cancel</button>
                    </div>
                </form>
            </div>

            <!-- Packages Grid -->
            <%
                if (packages == null || packages.isEmpty()) {
            %>
                <div class="packages-container">
                    <div class="no-packages">
                        <p>📦 No packages yet</p>
                        <p>Click "Create New Package" to get started!</p>
                    </div>
                </div>
            <%
                } else {
            %>
                <div class="packages-container">
                <%
                    for (Package pkg : packages) {
                        String packageClass = "INDOOR".equals(pkg.getPackageType()) ? "indoor" : "outdoor";
                %>
                    <div class="package-card <%= packageClass %>">
                        <div class="package-id">#PKG<%= String.format("%03d", pkg.getPackageId()) %></div>
                        <div class="package-header">
                            <span class="package-type-badge">
                                <%= "INDOOR".equals(pkg.getPackageType()) ? "🏢 INDOOR" : "🌳 OUTDOOR" %>
                            </span>
                            <div class="package-name"><%= pkg.getPackageName() %></div>
                            <div class="package-price">$<%= String.format("%.2f", pkg.getPrice()) %></div>
                        </div>

                        <div class="package-details">
                            <p>⏱️ <strong>Duration:</strong> <%= pkg.getDuration() %> hours</p>
                            <p>🎭 <strong>Event:</strong> <%= pkg.getEventType() %></p>
                            <p>📂 <strong>Category:</strong> <%= pkg.getCategory() %></p>
                            
                            <%
                                if ("INDOOR".equals(pkg.getPackageType()) && pkg instanceof IndoorPackage) {
                                    IndoorPackage indoor = (IndoorPackage) pkg;
                            %>
                                <p>👥 <strong>Max Pax:</strong> <%= indoor.getNumberOfPax() %> persons</p>
                                <p>🎨 <strong>Background:</strong> <%= indoor.getBackgroundType() %></p>
                            <%
                                } else if ("OUTDOOR".equals(pkg.getPackageType()) && pkg instanceof OutdoorPackage) {
                                    OutdoorPackage outdoor = (OutdoorPackage) pkg;
                            %>
                                <p>📍 <strong>Distance:</strong> <%= outdoor.getDistance() %> KM</p>
                                <p>💰 <strong>Price/KM:</strong> $<%= String.format("%.2f", outdoor.getDistancePricePerKm()) %></p>
                                <p>📌 <strong>Location:</strong> <%= outdoor.getLocation() %></p>
                            <%
                                }
                            %>
                        </div>

                        <div class="package-description">
                            <%= pkg.getDescription() %>
                        </div>

                        <div class="package-actions">
                            <button class="btn-view" onclick="viewPackage(<%= pkg.getPackageId() %>)">
                                View
                            </button>
                            <button class="btn-edit" onclick="editPackage(<%= pkg.getPackageId() %>)">
                                Edit
                            </button>
                            <button class="btn-delete" onclick="deletePackage(<%= pkg.getPackageId() %>)">
                                Delete
                            </button>
                        </div>
                    </div>
                <%
                    }
                %>
                </div>
            <%
                }
            %>
        </main>
    </div>

    <script>
        function toggleForm() {
            const container = document.getElementById('formContainer');
            container.classList.toggle('show');
            if (container.classList.contains('show')) {
                document.getElementById('packageForm').reset();
                document.getElementById('typeIndoor').checked = true;
                togglePackageType();
            }
        }

        function togglePackageType() {
            const isIndoor = document.getElementById('typeIndoor').checked;
            const indoorFields = document.getElementById('indoorFields');
            const outdoorFields = document.getElementById('outdoorFields');
            
            if (isIndoor) {
                indoorFields.classList.add('show');
                outdoorFields.classList.remove('show');
            } else {
                indoorFields.classList.remove('show');
                outdoorFields.classList.add('show');
            }
        }

        function viewPackage(packageId) {
            alert('View package ' + packageId + ' - Coming soon!');
        }

        function editPackage(packageId) {
            alert('Edit package ' + packageId + ' - Coming soon!');
        }

        function deletePackage(packageId) {
            if (confirm('Are you sure you want to delete this package?')) {
                window.location.href = 'DeletePackageServlet?id=' + packageId;
            }
        }

        function navigateTo(page) {
            window.location.href = page;
        }

        function logout() {
            if (confirm('Are you sure you want to logout?')) {
                window.location.href = 'LogoutServlet';
            }
        }

        // Initialize on page load
        window.addEventListener('DOMContentLoaded', function() {
            togglePackageType();
        });
    </script>
</body>
</html>