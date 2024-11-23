<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>员工管理 - 美宜佳管理系统</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
   <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
        }

        .main-content {
            margin-left: 250px;
            padding: 20px;
            transition: margin-left 0.3s ease;
        }

        body.nav-collapsed .main-content {
            margin-left: 70px;
        }

        .card {
            background: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .btn-group {
            display: flex;
            gap: 10px;
        }

        .search-bar {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }

        .search-bar input {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 200px;
        }

        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .btn-primary {
            background: #2196F3;
            color: white;
        }

        .btn-warning {
            background: #ff9800;
            color: white;
        }

        .btn-danger {
            background: #f44336;
            color: white;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background: #f8f9fa;
            font-weight: 600;
        }

        .actions {
            display: flex;
            gap: 5px;
        }

        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            z-index: 1000;
        }

        .modal-content {
            position: relative;
            background: white;
            width: 500px;
            margin: 50px auto;
            padding: 20px;
            border-radius: 8px;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .close {
            cursor: pointer;
            font-size: 24px;
            color: #666;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .buttons {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 20px;
        }
        /* 添加分页相关样式 */
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
            gap: 10px;
        }

        .pagination button {
            padding: 8px 16px;
            border: 1px solid #ddd;
            background: white;
            cursor: pointer;
            border-radius: 4px;
        }

        .pagination button.active {
            background: #2196F3;
            color: white;
            border-color: #2196F3;
        }

        .pagination button:disabled {
            background: #f5f5f5;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
    <!-- 保持原有HTML结构,修改数据加载逻辑 -->
    <jsp:include page="../common/navbar.jsp" />
    <div class="main-content">
        <div class="card">
            <div class="header">
                <h2>员工管理</h2>
                <div class="btn-group">
                    <button class="btn btn-primary" onclick="showAddModal()">
                        <i class="fas fa-plus"></i>
                        添加员工
                    </button>
                    <button class="btn btn-primary" onclick="location.href='schedule.jsp'">
                        <i class="fas fa-calendar-alt"></i>
                        排班管理
                    </button>
                </div>
            </div>

            <div class="search-bar">
                <input type="text" placeholder="搜索员工..." id="searchInput">
                <button class="btn btn-primary" onclick="searchEmployees()">
                    <i class="fas fa-search"></i>
                    搜索
                </button>
            </div>
            
            <table>
                <thead>
                    <tr>
                        <th>工号</th>
                        <th>姓名</th>
                        <th>联系方式</th>
                        <th>所属门店</th>
                        <th>入职日期</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="employeeTableBody">
                    <!-- 数据将通过JavaScript动态加载 -->
                </tbody>
            </table>

            <!-- 添加分页控件 -->
            <div class="pagination" id="pagination">
                <button onclick="changePage('prev')" id="prevBtn">上一页</button>
                <span id="pageInfo">第 1 页</span>
                <button onclick="changePage('next')" id="nextBtn">下一页</button>
            </div>
        </div>
    </div>

    <div class="modal" id="employeeModal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="formTitle">添加员工</h3>
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <form id="employeeForm" onsubmit="saveEmployee(event)">
                <input type="hidden" id="employeeId">
                <div class="form-group">
                    <label for="employeeName">姓名</label>
                    <input type="text" id="employeeName" required>
                </div>
                <div class="form-group">
                    <label for="employeePhone">联系方式</label>
                    <input type="tel" id="employeePhone" required>
                </div>
                <div class="form-group">
                    <label for="employeeStore">所属门店</label>
                    <select id="employeeStore" required>
                        <!-- 门店选项将通过JavaScript动态加载 -->
                    </select>
                </div>
                <div class="form-group">
                    <label for="employeeDate">入职日期</label>
                    <input type="date" id="employeeDate" required>
                </div>
                <div class="buttons">
                    <button type="button" class="btn btn-secondary" onclick="closeModal()">
                        <i class="fas fa-times"></i>
                        取消
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i>
                        保存
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        // 分页相关变量
        let currentPage = 1;
        const pageSize = 10;
        let totalPages = 1;

        $(document).ready(function() {
            $("#navbar").load("../common/navbar.html", function() {
                const isCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';
                if (isCollapsed) {
                    document.body.classList.add('nav-collapsed');
                    const sidebar = document.getElementById('sidebar');
                    if (sidebar) {
                        sidebar.classList.add('collapsed');
                    }
                }
            });

            loadStoreOptions();
            loadEmployees(currentPage);
        });

        function loadStoreOptions() {
            // 替换为Ajax调用
            $.ajax({
                url: '/api/stores',
                method: 'GET',
                success: function(response) {
                    const storeSelect = document.getElementById('employeeStore');
                    storeSelect.innerHTML = '<option value="">请选择门店</option>';
                    
                    response.forEach(store => {
                        storeSelect.innerHTML += `<option value="${store.id}">${store.name}</option>`;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载门店失败:', error);
                }
            });
        }

        function loadEmployees(page) {
            const searchTerm = document.getElementById('searchInput').value;
            
            // 替换为Ajax调用
            $.ajax({
                url: '/api/employees',
                method: 'GET',
                data: {
                    page: page,
                    pageSize: pageSize,
                    search: searchTerm
                },
                success: function(response) {
                    const tbody = document.getElementById('employeeTableBody');
                    tbody.innerHTML = '';
                    
                    response.data.forEach(employee => {
                        addTableRow(employee);
                    });

                    // 更新分页信息
                    totalPages = Math.ceil(response.total / pageSize);
                    updatePagination();
                },
                error: function(xhr, status, error) {
                    console.error('加载员工数据失败:', error);
                }
            });
        }

        function updatePagination() {
            document.getElementById('pageInfo').textContent = `第 ${currentPage} 页 / 共 ${totalPages} 页`;
            document.getElementById('prevBtn').disabled = currentPage === 1;
            document.getElementById('nextBtn').disabled = currentPage === totalPages;
        }

        function changePage(direction) {
            if (direction === 'prev' && currentPage > 1) {
                currentPage--;
            } else if (direction === 'next' && currentPage < totalPages) {
                currentPage++;
            }
            loadEmployees(currentPage);
        }

        function saveEmployee(event) {
            event.preventDefault();
            
            const employeeId = document.getElementById('employeeId').value;
            const formData = {
                name: document.getElementById('employeeName').value,
                phone: document.getElementById('employeePhone').value,
                storeId: document.getElementById('employeeStore').value,
                date: document.getElementById('employeeDate').value
            };

            // 替换为Ajax调用
            $.ajax({
                url: '/api/employees' + (employeeId ? `/${employeeId}` : ''),
                method: employeeId ? 'PUT' : 'POST',
                data: JSON.stringify(formData),
                contentType: 'application/json',
                success: function(response) {
                    closeModal();
                    loadEmployees(currentPage);
                },
                error: function(xhr, status, error) {
                    console.error('保存员工数据失败:', error);
                }
            });
        }

        function deleteEmployee(employeeId) {
            if (confirm('确定要删除这个员工吗？')) {
                // 替换为Ajax调用
                $.ajax({
                    url: `/api/employees/${employeeId}`,
                    method: 'DELETE',
                    success: function(response) {
                        loadEmployees(currentPage);
                    },
                    error: function(xhr, status, error) {
                        console.error('删除员工失败:', error);
                    }
                });
            }
        }

        // 其他函数保持不变
        // ...

    </script>
</body>
</html> 