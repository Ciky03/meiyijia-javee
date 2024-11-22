<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>库存管理 - 美宜佳管理系统</title>
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
            margin-bottom: 20px;
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

        .btn-success {
            background: #4caf50;
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

        .status-normal {
            color: #4caf50;
        }

        .status-warning {
            color: #ff9800;
        }

        .status-danger {
            color: #f44336;
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

        .tabs {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }

        .tab {
            padding: 8px 16px;
            cursor: pointer;
            border-bottom: 2px solid transparent;
        }

        .tab.active {
            border-bottom-color: #2196F3;
            color: #2196F3;
        }

        .history-item {
            padding: 10px;
            border-bottom: 1px solid #eee;
        }

        .history-item:last-child {
            border-bottom: none;
        }

        .history-date {
            color: #666;
            font-size: 12px;
        }

        .store-select-card {
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
            transition: all 0.3s ease;
        }

        .store-select-wrapper select:hover {
            border-color: #2196F3;
        }

        .store-select-wrapper select:focus {
            outline: none;
            border-color: #2196F3;
            box-shadow: 0 0 0 3px rgba(33, 150, 243, 0.1);
        }

        .select-arrow {
            position: absolute;
            right: 16px;
            top: 50%;
            transform: translateY(-50%);
            color: #666;
            pointer-events: none;
            transition: transform 0.3s ease;
        }

        .store-select-wrapper select:focus + .select-arrow {
            transform: translateY(-50%) rotate(180deg);
            color: #2196F3;
        }

        /* 美化选项样式 */
        .store-select-wrapper select option {
            padding: 12px;
            font-size: 14px;
        }

        /* 禁用选项的样式 */
        .store-select-wrapper select option[value=""] {
            color: #999;
        }

        .pagination {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 15px;
            margin-top: 20px;
        }

        .pagination button {
            padding: 8px 16px;
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 4px;
            cursor: pointer;
        }

        .pagination button:hover {
            background: #f5f5f5;
        }

        .pagination button:disabled {
            background: #f5f5f5;
            cursor: not-allowed;
            opacity: 0.6;
        }

        .pagination select {
            padding: 6px 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        #pageInfo {
            font-size: 14px;
            color: #666;
        }

        .actions {
            display: flex;
            gap: 5px;  /* 按钮之间的间隔 */
            justify-content: flex-start;  /* 按钮靠左对齐 */
        }

        .actions .btn {
            padding: 6px 10px;  /* 稍微调整按钮大小 */
        }


    </style>
</head>
<body>
    <!-- 保持原有的所有HTML结构不变 -->
    <jsp:include page="../common/navbar.jsp" />
    <div class="main-content">
        <div class="card store-select-card">
            <div class="header">
                <h2>选择门店</h2>
            </div>
            <div class="store-select-wrapper">
                <select id="storeSelect" onchange="loadStoreInventory()">
                    <option value="">请选择门店</option>
                </select>
                <i class="fas fa-chevron-down select-arrow"></i>
            </div>
        </div>

        <div class="card" id="warningCard" style="display: none;">
            <div class="header">
                <h3><i class="fas fa-exclamation-triangle" style="color: #ff9800;"></i> 库存预警</h3>
            </div>
            <div id="warningList"></div>
        </div>

        <div class="card" id="mainContent" style="display: none;">
            <div class="header">
                <h2>库存管理 - <span id="storeName"></span></h2>
                <div class="btn-group">
                    <button class="btn btn-success" onclick="showModal('in')">
                        <i class="fas fa-plus"></i>
                        入库
                    </button>
                    <button class="btn btn-danger" onclick="showModal('out')">
                        <i class="fas fa-minus"></i>
                        出库
                    </button>
                    <button class="btn btn-warning" onclick="showModal('return')">
                        <i class="fas fa-undo"></i>
                        退货
                    </button>
                </div>
            </div>

            <div class="tabs">
                <div class="tab active" onclick="switchTab('inventory')">库存列表</div>
                <div class="tab" onclick="switchTab('history')">操作记录</div>
            </div>

            <div id="inventoryTab">
                <div class="search-bar">
                    <input type="text" placeholder="搜索商品..." id="searchInput">
                    <button class="btn btn-primary" onclick="searchItems()">
                        <i class="fas fa-search"></i>
                        搜索
                    </button>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>商品编号</th>
                            <th>商品名称</th>
                            <th>当前库存</th>
                            <th>预警阈值</th>
                            <th>状态</th>
                            <th>最后更新</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="inventoryTableBody">
                        <!-- 数据将通过JavaScript动态加载 -->
                    </tbody>
                </table>

                <div class="pagination">
                    <button class="btn" onclick="changePage(-1)">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <span id="pageInfo">第 <span id="currentPage">1</span>/<span id="totalPages">1</span> 页</span>
                    <button class="btn" onclick="changePage(1)">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                    <select id="pageSizeSelect" onchange="changePageSize()">
                        <option value="10">10条/页</option>
                        <option value="20">20条/页</option>
                        <option value="50">50条/页</option>
                    </select>
                </div>
            </div>

            <div id="historyTab" style="display: none;">
                <div id="historyList">
                    <!-- 历史记录通过JavaScript动态加载 -->
                </div>
            </div>
        </div>
    </div>

    <div class="modal" id="operationModal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="modalTitle">库</h3>
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <form id="operationForm" onsubmit="saveOperation(event)">
                <input type="hidden" id="operationType">
                <div class="form-group">
                    <label for="itemSelect">选择商品</label>
                    <select id="itemSelect" required>
                        <option value="">请选择商品</option>
                        <option value="new">+ 添加新商品</option>
                    </select>
                </div>
                <div id="newItemFields" style="display: none;">
                    <div class="form-group">
                        <label for="itemName">商品名称</label>
                        <input type="text" id="itemName">
                    </div>
                    <div class="form-group">
                        <label for="warningThreshold">预警阈值</label>
                        <input type="number" id="warningThreshold" min="0">
                    </div>
                </div>
                <div class="form-group">
                    <label for="quantity">数量</label>
                    <input type="number" id="quantity" required min="1">
                </div>
                <div class="form-group">
                    <label for="remark">备注</label>
                    <input type="text" id="remark">
                </div>
                <div class="buttons">
                    <button type="button" class="btn btn-secondary" onclick="closeModal()">取消</button>
                    <button type="submit" class="btn btn-primary">确定</button>
                </div>
            </form>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>

        let currentPage = 1;
        let pageSize = 10;
        let totalPages = 1;

        // 修改数据操作相关的函数,改用Ajax调用后端API
        $(document).ready(function() {

            // 初始化数据
            loadStoreOptions();
            loadItemOptions();
        });

        // 加载库存数据
        function loadInventoryData(storeId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/inventory/list',
                method: 'GET',
                data: {
                    storeId: storeId,
                    page: currentPage,
                    pageSize: pageSize
                },
                success: function(response) {
                    const tbody = document.getElementById('inventoryTableBody');
                    tbody.innerHTML = '';
                    console.log(response)

                    totalItems = response.total;
                    const totalPages = Math.ceil(totalItems / pageSize);
                    document.getElementById('totalPages').textContent = totalPages;
                    document.getElementById('currentPage').textContent = currentPage;

                    response.data.forEach(item => {
                        const row = tbody.insertRow();
                        row.innerHTML = `
                            <td>\${item.itemId}</td>
                            <td>\${item.itemName}</td>
                            <td>\${item.quantity}</td>
                            <td>\${item.warningThreshold}</td>
                            <td><span class="status-\${getStatusClass(item)}">\${getStatusText(item)}</span></td>
                            <td>\${formatDate(item.lastUpdate)}</td>
                            <td class="actions">
                                <button class="btn btn-success" onclick="showModal('in', '\${item.itemId}')">
                                    <i class="fas fa-plus"></i>
                                </button>
                                <button class="btn btn-danger" onclick="showModal('out', '\${item.itemId}')">
                                    <i class="fas fa-minus"></i>
                                </button>
                                <button class="btn btn-warning" onclick="showModal('return', '\${item.itemId}')">
                                    <i class="fas fa-undo"></i>
                                </button>
                            </td>
                        `;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载库存数据失败:', error);
                    alert('加载库存数据失败');
                }
            });
        }

        // 加载历史记录
        function loadHistoryData(storeId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/inventory/history',
                method: 'GET',
                data: { storeId: storeId },
                success: function(response) {
                    const historyList = document.getElementById('historyList');
                    historyList.innerHTML = '';
                    console.log(response)
                    response.forEach(record => {
                        const typeText = {
                            'in': '入库',
                            'out': '出库',
                            'return': '退货'
                        }[record.type];

                        historyList.innerHTML += `
                            <div class="history-item">
                                <div class="history-date">\${formatDate(record.createTime)}</div>
                                <div>\${record.itemName} - \${typeText} \${record.quantity}件</div>
                                \${record.remark ? `<div>备注：\${record.remark}</div>` : ''}
                            </div>
                        `;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('加载历史记录失败:', error);
                    alert('加载历史记录失败');
                }
            });
        }

        // 检查库存预警
        function checkWarnings(storeId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/inventory/warning',
                method: 'GET',
                data: { storeId: storeId },
                success: function(response) {
                    const warningList = document.getElementById('warningList');
                    const warningCard = document.getElementById('warningCard');

                    if (response.length > 0) {
                        warningCard.style.display = 'block';
                        warningList.innerHTML = response.map(item => `
                            <div class="history-item">
                                <strong>\${item.name}</strong> - 当前库存: \${item.quantity}
                                (低于预警值: \${item.threshold})
                            </div>
                        `).join('');
                    } else {
                        warningCard.style.display = 'none';
                    }
                },
                error: function(xhr, status, error) {
                    console.error('检查库存预警失败:', error);
                }
            });
        }

        // 加载商品选项
        function loadItemOptions() {
            $.ajax({
                url: '${pageContext.request.contextPath}/item/list',
                method: 'GET',
                success: function(response) {
                    const itemSelect = document.getElementById('itemSelect');
                    itemSelect.innerHTML = '<option value="">请选择商品</option>';

                    response.forEach(item => {
                        itemSelect.innerHTML += `<option value="\${item.id}">\${item.name}</option>`;
                    });

                    itemSelect.innerHTML += '<option value="new">+ 添加新商品</option>';

                    itemSelect.onchange = function() {
                        document.getElementById('newItemFields').style.display =
                            this.value === 'new' ? 'block' : 'none';
                    };
                },
                error: function(xhr, status, error) {
                    console.error('加载商品选项失败:', error);
                    alert('加载商品选项失败');
                }
            });
        }

        // 保存操作
        function saveOperation(event) {
            event.preventDefault();

            const storeId = document.getElementById('storeSelect').value;
            if (!storeId) {
                alert('请先选择门店');
                return;
            }

            const type = document.getElementById('operationType').value;
            const itemSelect = document.getElementById('itemSelect');
            const quantity = parseInt(document.getElementById('quantity').value);
            const remark = document.getElementById('remark').value;


            let itemId = itemSelect.value;
            // 处理新商品
            if (itemId === 'new') {
                const itemName = document.getElementById('itemName').value;
                const warningThreshold = parseInt(document.getElementById('warningThreshold').value);


                if (!itemName || !warningThreshold) {
                    alert('请填写完整的商品信息');
                    return;
                }

                // 创建新商品
                $.ajax({
                    url: '${pageContext.request.contextPath}/item/create',
                    method: 'POST',
                    data: {
                        name: itemName,
                        warningThreshold: warningThreshold,
                        storeId: storeId
                    },
                    success: function(response) {
                        itemId = response.itemId;
                        updateInventory(storeId, itemId, type,quantity,remark);
                    },
                    error: function(xhr, status, error) {
                        console.error('创建商品失败:', error);
                        alert('创建商品失败');
                        return;
                    }
                });
            } else {
                updateInventory(storeId, itemId, type,null,null);
            }

            closeModal();
        }

        // 更新库存
        function updateInventory(storeId, itemId, type,quantity,remark) {
            if(quantity == null){
                quantity = parseInt(document.getElementById('quantity').value);
            }
            if(remark == null){
                remark = document.getElementById('remark').value;
            }

            $.ajax({
                url: '${pageContext.request.contextPath}/inventory/update',
                method: 'POST',
                data: {
                    storeId: storeId,
                    itemId: itemId,
                    type: type,
                    quantity: quantity,
                    remark: remark
                },
                success: function(response) {
                    loadInventoryData(storeId);
                    loadHistoryData(storeId);
                    checkWarnings(storeId);
                },
                error: function(xhr, status, error) {
                    console.error('更新库存失败:', error);
                    alert('更新库存失败');
                }
            });
        }

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

        // 搜索商品
        function searchItems() {
            const storeId = document.getElementById('storeSelect').value;
            if (!storeId) {
                alert('请先选择门店');
                return;
            }

            const searchTerm = document.getElementById('searchInput').value;

            $.ajax({
                url: '${pageContext.request.contextPath}/inventory/search',
                method: 'GET',
                data: {
                    storeId: storeId,
                    searchTerm: searchTerm,
                    page: currentPage,
                    pageSize: pageSize
                },
                success: function(response) {
                    const tbody = document.getElementById('inventoryTableBody');
                    tbody.innerHTML = '';

                    totalItems = response.total;
                    const totalPages = Math.ceil(totalItems / pageSize);
                    currentPage = 1;

                    document.getElementById('totalPages').textContent = totalPages;
                    document.getElementById('currentPage').textContent = currentPage;

                    response.data.forEach(item => {
                        const row = tbody.insertRow();
                        row.innerHTML = `
                            <td>\${item.itemId}</td>
                            <td>\${item.itemName}</td>
                            <td>\${item.quantity}</td>
                            <td>\${item.warningThreshold}</td>
                            <td><span class="status-\${getStatusClass(item)}">\${getStatusText(item)}</span></td>
                            <td>\${formatDate(item.lastUpdate)}</td>
                            <td class="actions">
                                <button class="btn btn-success" onclick="showModal('in', '\${item.itemId}')">
                                    <i class="fas fa-plus"></i>
                                </button>
                                <button class="btn btn-danger" onclick="showModal('out', '\${item.itemId}')">
                                    <i class="fas fa-minus"></i>
                                </button>
                                <button class="btn btn-warning" onclick="showModal('return', '\${item.id}')">
                                    <i class="fas fa-undo"></i>
                                </button>
                            </td>
                        `;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('搜索商品失败:', error);
                    alert('搜索商品失败');
                }
            });
        }

        // 保留原有的工具函数
        function getStatusClass(item) {
            if (item.quantity <= item.warningThreshold) return 'danger';
            if (item.quantity <= item.warningThreshold * 1.5) return 'warning';
            return 'normal';
        }

        function getStatusText(item) {
            if (item.quantity <= item.warningThreshold) return '库存不足';
            if (item.quantity <= item.warningThreshold * 1.5) return '库存偏低';
            return '正常';
        }

        function formatDate(dateString) {
            const date = new Date(dateString);
            return `\${date.getFullYear()}-\${String(date.getMonth() + 1).padStart(2, '0')}-\${String(date.getDate()).padStart(2, '0')} \${String(date.getHours()).padStart(2, '0')}:\${String(date.getMinutes()).padStart(2, '0')}`;
        }

        // 保留其他原有的UI相关函数
        function closeModal() {
            document.getElementById('operationModal').style.display = 'none';
            document.getElementById('operationForm').reset();
            document.getElementById('newItemFields').style.display = 'none';
        }

        window.onclick = function(event) {
            const modal = document.getElementById('operationModal');
            if (event.target == modal) {
                closeModal();
            }
        }

        function showModal(type, itemId = '') {
            const storeId = document.getElementById('storeSelect').value;
            if (!storeId) {
                alert('请先选择门店');
                return;
            }

            document.getElementById('operationType').value = type;
            document.getElementById('modalTitle').textContent = {
                'in': '入库',
                'out': '出库',
                'return': '退货'
            }[type];

            if (itemId) {
                document.getElementById('itemSelect').value = itemId;
                document.getElementById('itemSelect').disabled = true;
                document.getElementById('newItemFields').style.display = 'none';
            } else {
                document.getElementById('itemSelect').disabled = false;
                document.getElementById('itemSelect').value = '';
            }

            document.getElementById('operationModal').style.display = 'block';
        }

        function switchTab(tabName) {
            document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
            document.querySelector(`.tab[onclick="switchTab('\${tabName}')"]`).classList.add('active');

            document.getElementById('inventoryTab').style.display = tabName === 'inventory' ? 'block' : 'none';
            document.getElementById('historyTab').style.display = tabName === 'history' ? 'block' : 'none';
        }

        function changePage(delta) {
            const newPage = currentPage + delta;
            const totalPages = Math.ceil(totalItems / pageSize);

            if (newPage >= 1 && newPage <= totalPages) {
                currentPage = newPage;
                loadStoreInventory();
            }
        }

        function changePageSize() {
            pageSize = parseInt(document.getElementById('pageSizeSelect').value);
            currentPage = 1;
            loadStoreInventory();
        }

        function loadStoreInventory() {
            const storeId = document.getElementById('storeSelect').value;
            const mainContent = document.getElementById('mainContent');
            const storeName = document.getElementById('storeSelect').options[document.getElementById('storeSelect').selectedIndex].text;

            if (!storeId) {
                mainContent.style.display = 'none';
                return;
            }

            document.getElementById('storeName').textContent = storeName;
            mainContent.style.display = 'block';
            loadInventoryData(storeId);
            loadHistoryData(storeId);
            checkWarnings(storeId);
        }
    </script>
</body>
</html>