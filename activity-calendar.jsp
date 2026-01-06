<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Integer photographerId = (Integer) session.getAttribute("photographerId");
    String name = (String) session.getAttribute("name");

    if (photographerId == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Activity Calendar - Photographer Studio</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- COMBINED CSS -->
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
        }

        .app {
            display: flex;
            min-height: 100vh;
        }

        /* SIDEBAR */
        .sidebar {
            width: 250px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 30px 20px;
            color: white;
            position: fixed;
            height: 100vh;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
        }

        .menu {
            list-style: none;
        }

        .menu li {
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 6px;
            cursor: pointer;
            transition: 0.3s;
        }

        .menu li:hover {
            background: rgba(255,255,255,0.15);
        }

        .menu li.active {
            background: rgba(255,255,255,0.3);
            font-weight: bold;
        }

        .logout {
            margin-top: 20px;
            padding: 15px;
            text-align: center;
            background: rgba(255, 59, 48, 0.4);
            border-radius: 6px;
            cursor: pointer;
        }

        /* CONTENT */
        .content {
            margin-left: 250px;
            padding: 40px;
            flex: 1;
        }

        .calendar-header h2 {
            font-size: 28px;
            color: #667eea;
        }

        .calendar-header p {
            color: #666;
            margin-top: 5px;
        }

        .calendar-container {
            background: white;
            padding: 30px;
            border-radius: 8px;
            margin-top: 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .calendar-nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .calendar-nav button {
            background: #667eea;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
        }

        .month-year {
            font-size: 20px;
            font-weight: bold;
            color: #667eea;
        }

        .calendar-grid {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            gap: 10px;
        }

        .day-header {
            text-align: center;
            font-weight: bold;
            color: #667eea;
        }

        .calendar-day {
            aspect-ratio: 1;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 10px;
            text-align: center;
            cursor: pointer;
        }

        .calendar-day:hover {
            background: #e3f2fd;
        }

        .calendar-day.other-month {
            color: #ccc;
            background: #f9f9f9;
        }

        .calendar-day.today {
            background: #667eea;
            color: white;
            font-weight: bold;
        }

        .calendar-day.event {
            border: 2px solid #ff6b6b;
            background: #ffe0e0;
        }

        .events-list {
            margin-top: 30px;
            background: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
        }

        .event-item {
            background: white;
            padding: 15px;
            margin-bottom: 10px;
            border-left: 4px solid #667eea;
        }

        @media (max-width: 768px) {
            .sidebar {
                position: relative;
                width: 100%;
                height: auto;
            }
            .content {
                margin-left: 0;
            }
        }
    </style>
</head>

<body>
<div class="app">

    <aside class="sidebar">
        <div class="logo">📸 PMS</div>
        <ul class="menu">
            <li class="active">📅 Activity Calendar</li>
            <li onclick="navigateTo('package-management.jsp')">📦 Package</li>
            <li>📷 Upload Photo</li>
            <li>👤 Account</li>
            <li>✅ Verify Payment</li>
        </ul>
        <div class="logout" onclick="logout()">🚪 Logout</div>
    </aside>

    <main class="content">
        <div class="calendar-header">
            <h2>📅 Activity Calendar - <%= name %></h2>
            <p>Photographer ID: <%= photographerId %></p>
        </div>

        <div class="calendar-container">
            <div class="calendar-nav">
                <button onclick="previousMonth()">← Previous</button>
                <div class="month-year" id="monthYear"></div>
                <button onclick="nextMonth()">Next →</button>
            </div>

            <div class="calendar-grid" id="calendarGrid"></div>

            <div class="events-list">
                <h3>Upcoming Events</h3>
                <div id="eventsList">
                    <p style="color:#999;">No events scheduled</p>
                </div>
            </div>
        </div>
    </main>
</div>

<script>
    let currentDate = new Date();
    let events = {
        '2024-01-15': [{ title: 'Wedding Photoshoot', time: '10:00 AM' }],
        '2024-01-20': [{ title: 'Portrait Session', time: '2:00 PM' }]
    };

    function renderCalendar() {
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();

        document.getElementById('monthYear').textContent =
            new Intl.DateTimeFormat('en-US', { month: 'long', year: 'numeric' }).format(currentDate);

        const firstDay = new Date(year, month, 1).getDay();
        const daysInMonth = new Date(year, month + 1, 0).getDate();

        const calendarGrid = document.getElementById('calendarGrid');
        calendarGrid.innerHTML = '';

        ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'].forEach(d => {
            let h = document.createElement('div');
            h.className = 'day-header';
            h.textContent = d;
            calendarGrid.appendChild(h);
        });

        for (let day = 1; day <= daysInMonth; day++) {
            let div = document.createElement('div');
            div.className = 'calendar-day';
            div.textContent = day;
            calendarGrid.appendChild(div);
        }
    }

    function previousMonth() {
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar();
    }

    function nextMonth() {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar();
    }

    function logout() {
        if (confirm('Logout?')) {
            window.location.href = 'LogoutServlet';
        }
    }

    renderCalendar();
    
    function navigateTo(page) {
        window.location.href = page;
    }
    
</script>
</body>
</html>
