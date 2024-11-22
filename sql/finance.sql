-- 财务分类表
CREATE TABLE finance_category (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    type ENUM('income', 'expense') NOT NULL COMMENT '类型：收入/支出',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_name` (`name`) COMMENT '分类名称唯一'
) COMMENT '财务分类表';

-- 财务记录表
CREATE TABLE finance_record (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    store_id INT NOT NULL COMMENT '门店ID',
    type ENUM('income', 'expense') NOT NULL COMMENT '类型：收入/支出',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    category_id INT NOT NULL COMMENT '分类ID',
    record_date DATE NOT NULL COMMENT '记录日期',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (store_id) REFERENCES store(id),
    FOREIGN KEY (category_id) REFERENCES finance_category(id)
) COMMENT '财务记录表';

-- 插入财务分类数据
INSERT INTO finance_category (name, type) VALUES
('销售收入', 'income'),
('其他收入', 'income'),
('进货支出', 'expense'),
('工资支出', 'expense'),
('水电费', 'expense'),
('房租', 'expense'),
('其他支出', 'expense');


-- 插入财务记录数据（使用实际的category_id）
INSERT INTO finance_record (store_id, type, amount, category_id, record_date, remark) VALUES
-- 3月收入记录 (store_id = 1)
(1, 'income', 5000.00, 1, '2024-03-01', '日常销售'),
(1, 'income', 6000.00, 1, '2024-03-05', '周末促销'),
(1, 'income', 5500.00, 1, '2024-03-10', '日常销售'),
(1, 'income', 300.00, 2, '2024-03-12', '废品回收'),
(1, 'income', 5800.00, 1, '2024-03-15', '日常销售'),
(1, 'income', 6500.00, 1, '2024-03-20', '节日促销'),

-- 3月支出记录 (store_id = 1)
(1, 'expense', 3000.00, 3, '2024-03-02', '日常进货'),
(1, 'expense', 4500.00, 4, '2024-03-05', '员工工资'),
(1, 'expense', 800.00, 5, '2024-03-10', '3月水电费'),
(1, 'expense', 2000.00, 6, '2024-03-15', '3月房租'),
(1, 'expense', 2500.00, 3, '2024-03-18', '补充库存'),
(1, 'expense', 300.00, 7, '2024-03-20', '清洁用品'),

-- 2月收入记录 (store_id = 1)
(1, 'income', 4800.00, 1, '2024-02-01', '日常销售'),
(1, 'income', 5200.00, 1, '2024-02-05', '日常销售'),
(1, 'income', 7000.00, 1, '2024-02-10', '春节促销'),
(1, 'income', 6500.00, 1, '2024-02-15', '春节促销'),
(1, 'income', 250.00, 2, '2024-02-20', '废品回收'),
(1, 'income', 4900.00, 1, '2024-02-25', '日常销售'),

-- 2月支出记录 (store_id = 1)
(1, 'expense', 2800.00, 3, '2024-02-02', '日常进货'),
(1, 'expense', 4500.00, 4, '2024-02-05', '员工工资'),
(1, 'expense', 750.00, 5, '2024-02-10', '2月水电费'),
(1, 'expense', 2000.00, 6, '2024-02-15', '2月房租'),
(1, 'expense', 3500.00, 3, '2024-02-08', '春节备货'),
(1, 'expense', 500.00, 7, '2024-02-20', '春节装饰'),

-- store_id = 2 的记录
(2, 'income', 4000.00, 1, '2024-03-01', '日常销售'),
(2, 'income', 4500.00, 1, '2024-03-05', '周末促销'),
(2, 'income', 200.00, 2, '2024-03-10', '废品回收'),
(2, 'income', 4200.00, 1, '2024-03-15', '日常销售'),

(2, 'expense', 2500.00, 3, '2024-03-02', '日常进货'),
(2, 'expense', 3500.00, 4, '2024-03-05', '员工工资'),
(2, 'expense', 600.00, 5, '2024-03-10', '3月水电费'),
(2, 'expense', 1500.00, 6, '2024-03-15', '3月房租'),

-- 1月收入记录 (store_id = 1)
(1, 'income', 4500.00, 1, '2024-01-01', '元旦促销'),
(1, 'income', 4800.00, 1, '2024-01-05', '日常销售'),
(1, 'income', 4600.00, 1, '2024-01-10', '日常销售'),
(1, 'income', 5000.00, 1, '2024-01-15', '日常销售'),
(1, 'income', 200.00, 2, '2024-01-20', '废品回收'),
(1, 'income', 4700.00, 1, '2024-01-25', '日常销售'),

-- 1月支出记录 (store_id = 1)
(1, 'expense', 2500.00, 3, '2024-01-02', '日常进货'),
(1, 'expense', 4500.00, 4, '2024-01-05', '员工工资'),
(1, 'expense', 700.00, 5, '2024-01-10', '1月水电费'),
(1, 'expense', 2000.00, 6, '2024-01-15', '1月房租'),
(1, 'expense', 2000.00, 3, '2024-01-20', '补充库存'),
(1, 'expense', 200.00, 7, '2024-01-25', '办公用品');