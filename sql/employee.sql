-- 员工表
CREATE TABLE employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employee_no VARCHAR(20) NOT NULL,     -- 工号
    name VARCHAR(50) NOT NULL,            -- 姓名
    phone VARCHAR(20) NOT NULL,           -- 联系方式
    store_id INT NOT NULL,                -- 所属门店ID
    hire_date DATE NOT NULL,              -- 入职日期
    status TINYINT DEFAULT 1,             -- 状态(1:在职 0:离职)
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (store_id) REFERENCES store(id)
);

-- 排班表
CREATE TABLE schedule (
    id INT PRIMARY KEY AUTO_INCREMENT,
    store_id INT NOT NULL,                -- 门店ID
    employee_id INT NOT NULL,             -- 员工ID
    week_number INT NOT NULL,             -- 第几周
    day_of_week TINYINT NOT NULL,        -- 星期几(1-7)
    shift_type VARCHAR(20) NOT NULL,      -- 班次类型(morning/afternoon/evening)
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (store_id) REFERENCES store(id),
    FOREIGN KEY (employee_id) REFERENCES employee(id),
    -- 确保同一个时间段只能安排一次
    UNIQUE KEY unique_schedule (store_id, employee_id, week_number, day_of_week, shift_type)
);

-- 插入员工数据
INSERT INTO employee (employee_no, name, phone, store_id, hire_date) VALUES
-- 北京路店员工
('E001', '张三', '13800138001', 1, '2023-01-01'),
('E002', '李四', '13800138002', 1, '2023-02-15'),
('E003', '王五', '13800138003', 1, '2023-03-20'),

-- 天河路店员工
('E004', '赵六', '13800138004', 2, '2023-01-10'),
('E005', '钱七', '13800138005', 2, '2023-02-20'),
('E006', '孙八', '13800138006', 2, '2023-04-01'),

-- 体育西路店员工
('E007', '周九', '13800138007', 3, '2023-01-15'),
('E008', '吴十', '13800138008', 3, '2023-03-01'),
('E009', '郑十一', '13800138009', 3, '2023-04-15'),

-- 江南西店员工
('E010', '王小明', '13800138010', 4, '2023-01-05'),
('E011', '李小红', '13800138011', 4, '2023-02-25'),
('E012', '张小华', '13800138012', 4, '2023-03-10'),

-- 上下九店员工
('E013', '刘晓峰', '13800138013', 5, '2023-01-20'),
('E014', '陈美玲', '13800138014', 5, '2023-02-10'),
('E015', '黄志强', '13800138015', 5, '2023-04-05');

-- 插入排班数据 (以第20周为例)
INSERT INTO schedule (store_id, employee_id, week_number, day_of_week, shift_type) VALUES
-- 北京路店第20周排班
-- 周一
(1, 1, 20, 1, 'morning'),
(1, 2, 20, 1, 'afternoon'),
(1, 3, 20, 1, 'evening'),
-- 周二
(1, 2, 20, 2, 'morning'),
(1, 3, 20, 2, 'afternoon'),
(1, 1, 20, 2, 'evening'),
-- 周三
(1, 3, 20, 3, 'morning'),
(1, 1, 20, 3, 'afternoon'),
(1, 2, 20, 3, 'evening'),

-- 天河路店第20周排班
-- 周一
(2, 4, 20, 1, 'morning'),
(2, 5, 20, 1, 'afternoon'),
(2, 6, 20, 1, 'evening'),
-- 周二
(2, 5, 20, 2, 'morning'),
(2, 6, 20, 2, 'afternoon'),
(2, 4, 20, 2, 'evening'),
-- 周三
(2, 6, 20, 3, 'morning'),
(2, 4, 20, 3, 'afternoon'),
(2, 5, 20, 3, 'evening'),

-- 体育西路店第20周排班
-- 周一
(3, 7, 20, 1, 'morning'),
(3, 8, 20, 1, 'afternoon'),
(3, 9, 20, 1, 'evening'),
-- 周二
(3, 8, 20, 2, 'morning'),
(3, 9, 20, 2, 'afternoon'),
(3, 7, 20, 2, 'evening'),
-- 周三
(3, 9, 20, 3, 'morning'),
(3, 7, 20, 3, 'afternoon'),
(3, 8, 20, 3, 'evening'),

-- 继续添加其他门店的排班...
-- 江南西店第20周排班
(4, 10, 20, 1, 'morning'),
(4, 11, 20, 1, 'afternoon'),
(4, 12, 20, 1, 'evening'),

-- 上下九店第20周排班
(5, 13, 20, 1, 'morning'),
(5, 14, 20, 1, 'afternoon'),
(5, 15, 20, 1, 'evening'),

-- 第21周的一些排班数据
-- 北京路店
(1, 1, 21, 1, 'morning'),
(1, 2, 21, 1, 'afternoon'),
(1, 3, 21, 1, 'evening'),

-- 天河路店
(2, 4, 21, 1, 'morning'),
(2, 5, 21, 1, 'afternoon'),
(2, 6, 21, 1, 'evening');