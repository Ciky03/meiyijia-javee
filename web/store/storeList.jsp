<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>门店管理 - 美宜佳管理系统</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- 保持原有的CSS样式 -->
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

        /* 新增样式 */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
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

        .btn-danger {
            background: #f44336;
            color: white;
        }

        .btn-warning {
            background: #ff9800;
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

        .status-good {
            color: #4caf50;
            font-weight: 500;
        }

        .status-danger {
            color: #f44336;
            font-weight: 500;
        }

        /* 弹窗样式 */
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

        .close:hover {
            color: #333;
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

        .input-group {
            position: relative;
        }

        .input-group-prefix {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: #666;
        }

        .input-group input {
            padding-left: 25px;
        }

        .buttons {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 20px;
        }

        /* 在现有style标签内添加分页样式 */
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 20px;
            gap: 15px;
        }

        .pagination button {
            background: #f0f0f0;
            color: #333;
            border: 1px solid #ddd;
        }

        .pagination button:disabled {
            background: #e0e0e0;
            color: #999;
            cursor: not-allowed;
        }

        #pageInfo {
            font-size: 14px;
            color: #666;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/navbar.jsp" />
    <div class="main-content">
        <div class="card">
            <div class="header">
                <h2>门店管理</h2>
                <button class="btn btn-primary" onclick="showAddModal()">
                    <i class="fas fa-plus"></i>
                    添加门店
                </button>
            </div>

            <div class="search-bar">
                <input type="text" placeholder="搜索门店..." id="searchInput">
                <button class="btn btn-primary" onclick="searchStores()">
                    <i class="fas fa-search"></i>
                    搜索
                </button>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>门店编号</th>
                        <th>门店名称</th>
                        <th>地址</th>
                        <th>店长</th>
                        <th>联系电话</th>
                        <th>月度销售额</th>
                        <th>库存状况</th>
                        <th>操作</th>
                    </tr>
                </thead>

                <tbody id="storeTableBody">
                </tbody>
            </table>

            <!-- 添加分页控件 -->
            <div class="pagination">
                <button class="btn" onclick="changePage(currentPage-1)" id="prevBtn">
                    <i class="fas fa-chevron-left"></i> 上一页
                </button>
                <span id="pageInfo">第 <span id="currentPage">1</span> 页，共 <span id="totalPages">1</span> 页</span>
                <button class="btn" onclick="changePage(currentPage+1)" id="nextBtn">
                    下一页 <i class="fas fa-chevron-right"></i>
                </button>
            </div>
        </div>
    </div>

    <!-- 添加/编辑门店弹窗 -->
    <div class="modal" id="storeModal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="formTitle">添加门店</h3>
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <form id="storeForm"  method="POST" onsubmit="saveStore(event)">
                <input type="hidden" id="storeId" name="id">
                <div class="form-group">
                    <label for="storeName">门店名称</label>
                    <input type="text" id="storeName" name="name" required>
                </div>
                <div class="form-group">
                    <label for="storeAddress">地址</label>
                    <input type="text" id="storeAddress" name="address" required>
                </div>
                <div class="form-group">
                    <label for="storeManager">店长</label>
                    <input type="text" id="storeManager" name="manager" required>
                </div>
                <div class="form-group">
                    <label for="storePhone">联系电话</label>
                    <input type="tel" id="storePhone" name="phone" required>
                </div>
                <div class="form-group">
                    <label for="storeSales">月度销售额</label>
                    <div class="input-group">
                        <span class="input-group-prefix">¥</span>
                        <input type="number" id="storeSales" name="sales" min="0" step="0.01" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="storeInventory">库存状况</label>
                    <select id="storeInventory" name="inventory" required>
                        <option value="正常">正常</option>
                        <option value="告急">告急</option>
                    </select>
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
        // 分页变量
        let currentPage = 1;
        let pageSize = 10;
        let totalPages = 1;

        // 保存
        function saveStore(event) {
            event.preventDefault(); // 阻止表单默认提交

            // 获取表单数据
            const formData = {
                id: document.getElementById('storeId').value,
                name: document.getElementById('storeName').value,
                address: document.getElementById('storeAddress').value,
                manager: document.getElementById('storeManager').value,
                phone: document.getElementById('storePhone').value,
                sales: document.getElementById('storeSales').value,
                inventory: document.getElementById('storeInventory').value
            };

            // 发送AJAX请求
            $.ajax({
                url: '${pageContext.request.contextPath}/store/save',
                type: 'POST',
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify(formData),
                success: function(response) {
                    if (response.success) {
                        alert('保存成功！');
                        closeModal();
                        loadStores(); // 重新加载门店列表
                    } else {
                        alert('保存失败：' + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    alert('保存失败，请重试');
                    console.error('保存门店数据失败:', error);
                }
            });

            return false;        }

        // 删除
        function deleteStore(storeId) {
            if (confirm('确定要删除这个门店吗？')) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '${pageContext.request.contextPath}/store/delete';

                const input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'id';
                input.value = storeId;

                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            }
        }

        // 编辑
        function editStore(storeId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/store/get?id=' + storeId,
                type: 'GET',
                success: function(store) {
                    document.getElementById('storeId').value = store.id;
                    document.getElementById('storeName').value = store.name;
                    document.getElementById('storeAddress').value = store.address;
                    document.getElementById('storeManager').value = store.manager;
                    document.getElementById('storePhone').value = store.phone;
                    document.getElementById('storeSales').value = store.sales;
                    document.getElementById('storeInventory').value = store.inventory;

                    document.getElementById('formTitle').textContent = '编辑门店';
                    document.getElementById('storeModal').style.display = 'block';
                }
            });
        }

        // 搜索
        function searchStores() {
            const searchTerm = document.getElementById('searchInput').value;
            loadStores(searchTerm, currentPage);
        }

        // 换页
        function changePage(page) {
            if (page >= 1 && page <= totalPages) {
                currentPage = page;
                const searchTerm = document.getElementById('searchInput').value;
                loadStores(searchTerm, currentPage);
            }
        }

        // 加载数据的通用方法
        function loadStores(searchTerm = '', page = 1) {
            $.ajax({
                url: '${pageContext.request.contextPath}/store/list',
                type: 'GET',
                data: {
                    search: searchTerm,
                    page: page,
                    pageSize: pageSize
                },
                dataType: 'json',
                success: function(response) {
                    const stores = response.data;
                    totalPages = response.totalPages;
                    currentPage = response.currentPage;

                    updatePagination();
                    updateTable(stores);
                },
                error: function(xhr, status, error) {
                    console.error('获取门店数据失败:', error);
                    alert('获取门店数据失败，请重试');
                }
            });
        }

        // 更新分页控件
        function updatePagination() {
            document.getElementById('currentPage').textContent = currentPage;
            document.getElementById('totalPages').textContent = totalPages;

            const prevBtn = document.getElementById('prevBtn');
            const nextBtn = document.getElementById('nextBtn');

            prevBtn.disabled = currentPage <= 1;
            nextBtn.disabled = currentPage >= totalPages;
        }

        // 更新表格
        function updateTable(stores) {
            const tbody = document.getElementById('storeTableBody');
            tbody.innerHTML = '';

            if (stores.length === 0) {
                const row = tbody.insertRow();
                row.innerHTML = '<td colspan="8" style="text-align: center;">没有找到匹配的门店信息</td>';
                return;
            }

            stores.forEach(store => {
                const row = tbody.insertRow();
                console.log(store)
                row.dataset.storeId = store.id;
                row.innerHTML = `
                    <td>\${store.id}</td>
                    <td>\${store.name}</td>
                    <td>\${store.address}</td>
                    <td>\${store.manager}</td>
                    <td>\${store.phone}</td>
                    <td>¥\${store.sales}</td>
                    <td><span class="status-\${store.inventory === '正常' ? 'good' : 'danger'}">\${store.inventory}</span></td>
                    <td class="actions">
                        <button class="btn btn-warning" onclick="editStore('\${store.id}')">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-danger" onclick="deleteStore('\${store.id}')">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                `;
            });
        }

        // 其他辅助方法保持不变
        function showAddModal() {
            document.getElementById('formTitle').textContent = '添加门店';
            document.getElementById('storeForm').reset();
            document.getElementById('storeId').value = '';
            document.getElementById('storeModal').style.display = 'block';
        }

        function closeModal() {
            document.getElementById('storeModal').style.display = 'none';
            document.getElementById('storeForm').reset();
        }

        window.onclick = function(event) {
            const modal = document.getElementById('storeModal');
            if (event.target == modal) {
                closeModal();
            }
        }

        // 页面加载时的初始化
        document.addEventListener('DOMContentLoaded', function() {
            loadStores();
        });
    </script>
</body>
</html>