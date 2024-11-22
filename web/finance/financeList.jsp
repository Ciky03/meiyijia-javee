<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>财务管理 - 美宜佳管理系统</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- 保持原有的所有CSS样式不变 -->
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
            margin-bottom: 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .store-select-wrapper {
            position: relative;
            width: 300px;
        }

        .store-select-wrapper select {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            background: white;
            font-size: 15px;
            color: #333;
            cursor: pointer;
            appearance: none;
            -webkit-appearance: none;
        }

        .select-arrow {
            position: absolute;
            right: 16px;
            top: 50%;
            transform: translateY(-50%);
            color: #666;
            pointer-events: none;
        }

        .summary-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .summary-card {
            padding: 20px;
            border-radius: 8px;
            color: white;
        }

        .summary-card.income {
            background: linear-gradient(45deg, #4CAF50, #81C784);
        }

        .summary-card.expense {
            background: linear-gradient(45deg, #F44336, #E57373);
        }

        .summary-card.profit {
            background: linear-gradient(45deg, #2196F3, #64B5F6);
        }

        .summary-card h3 {
            font-size: 16px;
            margin-bottom: 10px;
        }

        .summary-card .amount {
            font-size: 24px;
            font-weight: bold;
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

        .btn-success {
            background: #4CAF50;
            color: white;
            margin-bottom: 10px;
        }

        .btn-danger {
            background: #F44336;
            color: white;
        }

        .tabs {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
            border-bottom: 1px solid #ddd;
        }

        .tab {
            padding: 10px 20px;
            cursor: pointer;
            border-bottom: 2px solid transparent;
        }

        .tab.active {
            border-bottom-color: #2196F3;
            color: #2196F3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
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

        .amount-positive {
            color: #4CAF50;
        }

        .amount-negative {
            color: #F44336;
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
        .form-group select,
        .form-group textarea {
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
    </style>
</head>
<body>
    <jsp:include page="../common/navbar.jsp" />
   <div class="main-content">
        <div class="card">
            <div class="header">
                <h2>选择门店</h2>
            </div>
            <div class="store-select-wrapper">
                <select id="storeSelect" onchange="loadFinanceData()">
                    <option value="">请选择门店</option>
                </select>
                <i class="fas fa-chevron-down select-arrow"></i>
            </div>
        </div>

        <div id="financeContent" style="display: none;">
            <div class="summary-cards">
                <div class="summary-card income">
                    <h3>本月收入</h3>
                    <div class="amount" id="monthlyIncome">¥0.00</div>
                </div>
                <div class="summary-card expense">
                    <h3>本月支出</h3>
                    <div class="amount" id="monthlyExpense">¥0.00</div>
                </div>
                <div class="summary-card profit">
                    <h3>本月利润</h3>
                    <div class="amount" id="monthlyProfit">¥0.00</div>
                </div>
            </div>

            <div class="card">
                <div class="header">
                    <div class="tabs">
                        <div class="tab active" onclick="switchTab('records')">收支记录</div>
                        <div class="tab" onclick="switchTab('report')">月度报表</div>
                    </div>
                    <div class="btn-group">
                        <button class="btn btn-success" onclick="showModal('income')">
                            <i class="fas fa-plus"></i>
                            添加收入
                        </button>
                        <button class="btn btn-danger" onclick="showModal('expense')">
                            <i class="fas fa-minus"></i>
                            添加支出
                        </button>
                    </div>
                </div>

                <div id="recordsTab">
                    <table>
                        <thead>
                            <tr>
                                <th>日期</th>
                                <th>类型</th>
                                <th>金额</th>
                                <th>分类</th>
                                <th>备注</th>
                            </tr>
                        </thead>
                        <tbody id="recordsTableBody">
                        </tbody>
                    </table>
                </div>

                <div id="reportTab" style="display: none;">
                    <table>
                        <thead>
                            <tr>
                                <th>月份</th>
                                <th>总收入</th>
                                <th>总支出</th>
                                <th>净利润</th>
                            </tr>
                        </thead>
                        <tbody id="reportTableBody">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="modal" id="financeModal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="modalTitle">添加收入</h3>
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <form id="financeForm" onsubmit="saveFinanceRecord(event)">
                <input type="hidden" id="recordType">
                <div class="form-group">
                    <label for="amount">金额</label>
                    <input type="number" id="amount" required min="0" step="0.01">
                </div>
                <div class="form-group">
                    <label for="category">分类</label>
                    <select id="category" required>
                        <!-- 选项将通过JavaScript动态加载 -->
                    </select>
                </div>
                <div class="form-group">
                    <label for="date">日期</label>
                    <input type="date" id="date" required>
                </div>
                <div class="form-group">
                    <label for="remark">备注</label>
                    <textarea id="remark" rows="3"></textarea>
                </div>
                <div class="buttons">
                    <button type="button" class="btn btn-secondary" onclick="closeModal()">取消</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>


    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            // 初始化数据
            loadStoreOptions();
        });

        // 加载门店选项
        function loadStoreOptions() {
            $.ajax({
                url: '${pageContext.request.contextPath}/store/list',
                method: 'GET',
                success: function(response) {
                    const storeSelect = document.getElementById('storeSelect');
                    storeSelect.innerHTML = '<option value="">请选择门店</option>';

                    response.data.forEach(store => {
                        storeSelect.innerHTML += `<option value="\${store.id}">\${store.name}</option>`;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载门店选项失败:', error);
                    alert('加载门店选项失败');
                }
            });
        }

        // 加载财务数据
        function loadFinanceData() {
            const storeId = document.getElementById('storeSelect').value;
            const financeContent = document.getElementById('financeContent');

            if (!storeId) {
                financeContent.style.display = 'none';
                return;
            }

            financeContent.style.display = 'block';
            updateSummary(storeId);
            loadRecords(storeId);
            loadMonthlyReport(storeId);
        }

        // 更新财务概览
        function updateSummary(storeId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/finance/summary',
                method: 'GET',
                data: { storeId: storeId },
                success: function(response) {
                    document.getElementById('monthlyIncome').textContent =
                        `¥\${response.monthlyIncome.toFixed(2)}`;
                    document.getElementById('monthlyExpense').textContent =
                        `¥\${response.monthlyExpense.toFixed(2)}`;
                    document.getElementById('monthlyProfit').textContent =
                        `¥\${(response.monthlyIncome - response.monthlyExpense).toFixed(2)}`;
                },
                error: function(xhr, status, error) {
                    console.error('加载财务概览失败:', error);
                    alert('加载财务概览失败');
                }
            });
        }

        // 加载分类选项
        function loadCategories(type) {
            $.ajax({
                url: '${pageContext.request.contextPath}/finance/categories',
                method: 'GET',
                data: { type: type },
                success: function(response) {
                    const categorySelect = document.getElementById('category');
                    categorySelect.innerHTML = '<option value="">请选择分类</option>';

                    response.forEach(category => {
                        categorySelect.innerHTML += `<option value="\${category}">\${category}</option>`;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载分类选项失败:', error);
                    alert('加载分类选项失败');
                }
            });
        }

        // 保存财务记录
        function saveFinanceRecord(event) {
            event.preventDefault();

            const storeId = document.getElementById('storeSelect').value;
            const type = document.getElementById('recordType').value;
            const amount = parseFloat(document.getElementById('amount').value);
            const category = document.getElementById('category').value;
            const date = document.getElementById('date').value;
            const remark = document.getElementById('remark').value;

            $.ajax({
                url: '${pageContext.request.contextPath}/finance/save',
                method: 'POST',
                data: {
                    storeId: storeId,
                    type: type,
                    amount: amount,
                    category: category,
                    date: date,
                    remark: remark
                },
                success: function(response) {
                    loadFinanceData();
                    closeModal();
                },
                error: function(xhr, status, error) {
                    console.error('保存财务记录失败:', error);
                    alert('保存财务记录失败');
                }
            });
        }

        // 加载收支记录
        function loadRecords(storeId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/finance/records',
                method: 'GET',
                data: { storeId: storeId },
                success: function(response) {
                    const tbody = document.getElementById('recordsTableBody');
                    tbody.innerHTML = '';

                    response.forEach(record => {
                        const row = tbody.insertRow();
                        row.innerHTML = `
                            <td>\${record.date}</td>
                            <td>\${record.type === 'income' ? '收入' : '支出'}</td>
                            <td class="amount-\${record.type === 'income' ? 'positive' : 'negative'}">
                                \${record.type === 'income' ? '+' : '-'}¥\${record.amount.toFixed(2)}
                            </td>
                            <td>\${record.category}</td>
                            <td>\${record.remark || '-'}</td>
                        `;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载收支记录失败:', error);
                    alert('加载收支记录失败');
                }
            });
        }

        // 加载月度报表
        function loadMonthlyReport(storeId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/finance/report',
                method: 'GET',
                data: { storeId: storeId },
                success: function(response) {
                    const tbody = document.getElementById('reportTableBody');
                    tbody.innerHTML = '';

                    response.forEach(report => {
                        const profit = report.income - report.expense;
                        const row = tbody.insertRow();
                        row.innerHTML = `
                            <td>\${report.month}</td>
                            <td class="amount-positive">¥\${report.income.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2})}</td>
                            <td class="amount-negative">¥\${report.expense.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2})}</td>
                            <td class="amount-\${profit >= 0 ? 'positive' : 'negative'}">
                                \${profit >= 0 ? '+' : ''}¥\${profit.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2})}
                            </td>
                        `;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载月度报表失败:', error);
                    alert('加载月度报表失败');
                }
            });
        }

        // 保持原有的UI相关函数不变
        function showModal(type) {
            const storeId = document.getElementById('storeSelect').value;
            if (!storeId) {
                alert('请先选择门店');
                return;
            }

            document.getElementById('recordType').value = type;
            document.getElementById('modalTitle').textContent = type === 'income' ? '添加收入' : '添加支出';

            loadCategories(type);

            document.getElementById('financeModal').style.display = 'block';
        }

        function switchTab(tabName) {
            document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
            document.querySelector(`.tab[onclick="switchTab('\${tabName}')"]`).classList.add('active');

            document.getElementById('recordsTab').style.display = tabName === 'records' ? 'block' : 'none';
            document.getElementById('reportTab').style.display = tabName === 'report' ? 'block' : 'none';
        }

        function closeModal() {
            document.getElementById('financeModal').style.display = 'none';
            document.getElementById('financeForm').reset();
        }

        window.onclick = function(event) {
            const modal = document.getElementById('financeModal');
            if (event.target == modal) {
                closeModal();
            }
        }
    </script>
</body>
</html>