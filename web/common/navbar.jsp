<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

<div class="sidebar" id="sidebar">
    <div class="toggle-btn" onclick="toggleSidebar()">
        <i class="fas fa-chevron-left"></i>
    </div>

    <div class="nav-content">
        <div class="logo">
            <i class="fas fa-store"></i>
            <span>美宜佳管理系统</span>
        </div>

        <ul class="nav-list">
            <li>
                <a href="${pageContext.request.contextPath}/store/storeList.jsp">
                    <i class="fas fa-store"></i>
                    <span>门店管理</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/inventory/inventoryList.jsp">
                    <i class="fas fa-boxes"></i>
                    <span>库存管理</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/finance/financeList.jsp">
                    <i class="fas fa-chart-line"></i>
                    <span>财务管理</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/employee/employeeList.jsp">
                    <i class="fas fa-users"></i>
                    <span>员工管理</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/employee/schedule.jsp">
                    <i class="fas fa-calendar-alt"></i>
                    <span>排班管理</span>
                </a>
            </li>

        </ul>
    </div>
</div>

<style>
/* 保持原有样式不变 */
.sidebar {
    position: fixed;
    top: 0;
    left: 0;
    height: 100vh;
    width: 250px;
    background: #2196F3;
    transition: width 0.3s ease;
    z-index: 1000;
}

.sidebar.collapsed {
    width: 70px;
}

.toggle-btn {
    position: absolute;
    top: 20px;
    right: -12px;
    width: 24px;
    height: 24px;
    background: white;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    z-index: 1;
}

.toggle-btn i {
    color: #2196F3;
    font-size: 12px;
    transition: transform 0.3s;
}

.sidebar.collapsed .toggle-btn i {
    transform: rotate(180deg);
}

.nav-content {
    height: 100%;
    padding: 20px;
    overflow-x: hidden;
}

.logo {
    display: flex;
    align-items: center;
    gap: 10px;
    color: white;
    margin-bottom: 40px;
}

.logo i {
    font-size: 24px;
}

.logo span {
    font-size: 18px;
    font-weight: 500;
    white-space: nowrap;
}

.nav-list {
    list-style: none;
    padding: 0;
}

.nav-list li {
    margin-bottom: 5px;
}

.nav-list a {
    display: flex;
    align-items: center;
    padding: 12px;
    color: white;
    text-decoration: none;
    border-radius: 6px;
    transition: all 0.3s;
    gap: 10px;
}

.nav-list a:hover {
    background: rgba(255,255,255,0.1);
}

.nav-list i {
    font-size: 18px;
    min-width: 24px;
}

.nav-list span {
    white-space: nowrap;
}

.sidebar.collapsed .logo span,
.sidebar.collapsed .nav-list span {
    display: none;
}

.sidebar.collapsed .nav-list a {
    justify-content: center;
}

.sidebar.collapsed .nav-list i {
    margin: 0;
}
</style>

<script>
function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const body = document.body;

    sidebar.classList.toggle('collapsed');
    body.classList.toggle('nav-collapsed');

    localStorage.setItem('sidebarCollapsed', sidebar.classList.contains('collapsed'));
}

// 页面加载时恢复状态
window.addEventListener('load', function() {
    const sidebar = document.getElementById('sidebar');
    const body = document.body;
    const isCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';

    if (isCollapsed) {
        sidebar.classList.add('collapsed');
        body.classList.add('nav-collapsed');
    }
});
</script>