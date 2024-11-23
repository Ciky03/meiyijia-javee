<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>排班管理 - 美宜佳管理系统</title>
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

        .controls {
            display: flex;
            gap: 15px;
            margin-bottom: 20px;
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

        select {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .schedule-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .schedule-table th,
        .schedule-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        .schedule-table th {
            background: #f8f9fa;
            font-weight: 600;
        }

        .schedule-cell {
            min-height: 80px;
            cursor: pointer;
            position: relative;
        }

        .schedule-cell:hover {
            background: #f5f5f5;
        }

        .shift {
            padding: 4px 8px;
            margin: 2px;
            border-radius: 4px;
            font-size: 12px;
            background: #e3f2fd;
        }

        .shift-morning {
            background: #e3f2fd;
        }

        .shift-afternoon {
            background: #fff3e0;
        }

        .shift-evening {
            background: #f3e5f5;
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
            width: 400px;
            margin: 100px auto;
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

        .form-group select {
            width: 100%;
        }

        .buttons {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/navbar.jsp" />
    <div class="main-content">
        <div class="card">
            <div class="header">
                <h2>排班管理</h2>
                <button class="btn btn-primary" onclick="location.href='employeeList.jsp'">
                    <i class="fas fa-arrow-left"></i>
                    返回员工列表
                </button>
            </div>

            <div class="controls">
                <select id="storeSelect" onchange="loadSchedule()">
                    <option value="">选择门店</option>
                </select>
                <select id="weekSelect" onchange="loadSchedule()">
                    <!-- 周数选项将通过JavaScript动态生成 -->
                </select>
            </div>

            <table class="schedule-table">
                <thead>
                    <tr>
                        <th>时间</th>
                        <th>周一</th>
                        <th>周二</th>
                        <th>周三</th>
                        <th>周四</th>
                        <th>周五</th>
                        <th>周六</th>
                        <th>周日</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>早班<br>(8:00-16:00)</th>
                        <td class="schedule-cell" onclick="showScheduleModal('1', 'morning')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('2', 'morning')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('3', 'morning')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('4', 'morning')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('5', 'morning')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('6', 'morning')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('7', 'morning')"></td>
                    </tr>
                    <tr>
                        <th>中班<br>(12:00-20:00)</th>
                        <td class="schedule-cell" onclick="showScheduleModal('1', 'afternoon')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('2', 'afternoon')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('3', 'afternoon')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('4', 'afternoon')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('5', 'afternoon')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('6', 'afternoon')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('7', 'afternoon')"></td>
                    </tr>
                    <tr>
                        <th>晚班<br>(16:00-24:00)</th>
                        <td class="schedule-cell" onclick="showScheduleModal('1', 'evening')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('2', 'evening')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('3', 'evening')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('4', 'evening')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('5', 'evening')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('6', 'evening')"></td>
                        <td class="schedule-cell" onclick="showScheduleModal('7', 'evening')"></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- 排班弹窗 -->
    <div class="modal" id="scheduleModal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>安排班次</h3>
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <form id="scheduleForm" onsubmit="saveSchedule(event)">
                <input type="hidden" id="dayOfWeek">
                <input type="hidden" id="shiftType">
                <div class="form-group">
                    <label>选择员工</label>
                    <select id="employeeSelect" multiple size="5" required>
                        <!-- 员工选项将通过JavaScript动态加载 -->
                    </select>
                </div>
                <div class="buttons">
                    <button type="button" class="btn btn-danger" onclick="clearSchedule()">
                        <i class="fas fa-trash"></i>
                        清除
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
            loadWeekOptions();
            loadSchedule();
        });

        function loadStoreOptions() {
            $.ajax({
                url: '${pageContext.request.contextPath}/store/list',
                method: 'GET',
                success: function(response) {
                    const storeSelect = document.getElementById('storeSelect');
                    storeSelect.innerHTML = '<option value="">选择门店</option>';

                    response.data.forEach(store => {
                        storeSelect.innerHTML += `<option value="\${store.id}">\${store.name}</option>`;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载门店失败:', error);
                }
            });
        }

        function loadWeekOptions() {
            const weekSelect = document.getElementById('weekSelect');
            const currentDate = new Date();
            const currentWeek = getWeekNumber(currentDate);

            weekSelect.innerHTML = '';
            for (let i = -4; i <= 4; i++) {
                const weekNum = currentWeek + i;
                const selected = i === 0 ? 'selected' : '';
                weekSelect.innerHTML += `<option value="\${weekNum}" \${selected}>第\${weekNum}周</option>`;
            }
        }

        // 加载排班表
        function loadSchedule() {
            const storeId = document.getElementById('storeSelect').value;
            const weekNum = document.getElementById('weekSelect').value;

            if (!storeId) return;

            $.ajax({
                url: '${pageContext.request.contextPath}/schedule/list',
                method: 'GET',
                data: {
                    storeId: storeId,
                    weekNum: weekNum
                },
                success: function(response) {
                    updateScheduleDisplay(response);
                },
                error: function(xhr, status, error) {
                    console.error('加载排班数据失败:', error);
                }
            });
        }

        function showScheduleModal(dayOfWeek, shiftType) {
            const storeId = document.getElementById('storeSelect').value;
            if (!storeId) {
                alert('请先选择门店');
                return;
            }

            document.getElementById('dayOfWeek').value = dayOfWeek;
            document.getElementById('shiftType').value = shiftType;

            loadEmployeeOptions(storeId);
            loadExistingSchedule(storeId, dayOfWeek, shiftType);

            document.getElementById('scheduleModal').style.display = 'block';
        }

        // 加载已存在的排班数据
       function loadExistingSchedule(storeId, dayOfWeek, shiftType) {
            const weekNum = document.getElementById('weekSelect').value;

            $.ajax({
                url: '${pageContext.request.contextPath}/schedule/load',
                method: 'GET',
                data: {
                    storeId: storeId,
                    weekNum: weekNum,
                    dayOfWeek: dayOfWeek,
                    shiftType: shiftType
                },
                success: function(employeeIds) {
                    const employeeSelect = document.getElementById('employeeSelect');

                    // 选中已排班的员工
                    Array.from(employeeSelect.options).forEach(option => {
                        option.selected = employeeIds.includes(parseInt(option.value));
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载排班数据失败:', error);
                    alert('加载排班数据失败: ' + error);
                }
            });
        }

        function loadEmployeeOptions(storeId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/employee/store',
                method: 'GET',
                data: { storeId: storeId },
                success: function(response) {
                    const employeeSelect = document.getElementById('employeeSelect');
                    employeeSelect.innerHTML = '';

                    response.forEach(employee => {
                        employeeSelect.innerHTML += `<option value="\${employee.id}">\${employee.name}</option>`;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载员工列表失败:', error);
                }
            });
        }

         // 保存排班
        function saveSchedule(event) {
            event.preventDefault();

            const storeId = document.getElementById('storeSelect').value;
            const weekNum = document.getElementById('weekSelect').value;
            const dayOfWeek = document.getElementById('dayOfWeek').value;
            const shiftType = document.getElementById('shiftType').value;

            const selectedEmployees = Array.from(document.getElementById('employeeSelect').selectedOptions)
                .map(option => option.value);

            $.ajax({
                url: '${pageContext.request.contextPath}/schedules',
                method: 'POST',
                data: JSON.stringify({
                    storeId: storeId,
                    weekNum: weekNum,
                    dayOfWeek: dayOfWeek,
                    shiftType: shiftType,
                    employeeIds: selectedEmployees
                }),
                contentType: 'application/json',
                success: function(response) {
                    closeModal();
                    loadSchedule();
                },
                error: function(xhr, status, error) {
                    console.error('保存排班失败:', error);
                }
            });
        }

        // 清除排班
        function clearSchedule() {
            const storeId = document.getElementById('storeSelect').value;
            const weekNum = document.getElementById('weekSelect').value;
            const dayOfWeek = document.getElementById('dayOfWeek').value;
            const shiftType = document.getElementById('shiftType').value;

            $.ajax({
                url: '${pageContext.request.contextPath}/schedules',
                method: 'DELETE',
                data: {
                    storeId: storeId,
                    weekNum: weekNum,
                    dayOfWeek: dayOfWeek,
                    shiftType: shiftType
                },
                success: function(response) {
                    closeModal();
                    loadSchedule();
                },
                error: function(xhr, status, error) {
                    console.error('清除排班失败:', error);
                }
            });
        }

        // 更新排班显示
        function updateScheduleDisplay(scheduleData) {
            // 清空所有单元格
            document.querySelectorAll('.schedule-cell').forEach(cell => cell.innerHTML = '');

            // 填充排班数据
            scheduleData.forEach(schedule => {
                const cell = document.querySelector(
                    `td[onclick="showScheduleModal('\${schedule.dayOfWeek}', '\${schedule.shiftType}')"]`
                );

                if (cell) {
                    cell.innerHTML = schedule.employees.map(employee =>
                        `<div class="shift shift-\${schedule.shiftType}">\${employee.name}</div>`
                    ).join('');
                }
            });
        }

        function getWeekNumber(date) {
            const firstDayOfYear = new Date(date.getFullYear(), 0, 1);
            const pastDaysOfYear = (date - firstDayOfYear) / 86400000;
            return Math.ceil((pastDaysOfYear + firstDayOfYear.getDay() + 1) / 7);
        }

        // 关闭弹窗
        function closeModal() {
            document.getElementById('scheduleModal').style.display = 'none';
            document.getElementById('scheduleForm').reset();
        }

        // 点击弹窗外部关闭弹窗
        window.onclick = function(event) {
            const modal = document.getElementById('scheduleModal');
            if (event.target == modal) {
                closeModal();
            }
        }
    </script>
</body>
</html>