-- 商品表
CREATE TABLE item
(
    id          int auto_increment primary key comment '商品ID',
    name        VARCHAR(100) NOT NULL comment '商品名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'
) comment '商品表';

-- 门店库存表
CREATE TABLE store_inventory
(
    store_id          int comment '门店ID',
    item_id           int comment '商品ID',
    quantity          INT NOT NULL DEFAULT 0 comment '数量',
    warning_threshold INT NOT NULL DEFAULT 0 comment '预警阈值',
    last_update       DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '最后更新时间',
    PRIMARY KEY (store_id, item_id),
    FOREIGN KEY (store_id) REFERENCES store (id),
    FOREIGN KEY (item_id) REFERENCES item (id)
) comment '门店库存表';

-- 库存操作历史表
CREATE TABLE inventory_history
(
    id             int AUTO_INCREMENT PRIMARY KEY,
    store_id       int comment '门店ID',
    item_id        int comment '商品ID',
    operation_type ENUM ('in', 'out', 'return') NOT NULL comment '操作类型',
    quantity       INT                          NOT NULL comment '数量',
    remark         VARCHAR(200) comment '备注',
    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    FOREIGN KEY (store_id) REFERENCES store (id),
    FOREIGN KEY (item_id) REFERENCES item (id)
) comment '库存操作历史表';

-- 商品数据
INSERT INTO item (id, name) VALUES
(1, '可口可乐330ml'),
(2, '百事可乐330ml'),
(3, '康师傅方便面'),
(4, '统一冰红茶'),
(5, '乐事薯片原味'),
(6, '旺旺雪饼');

-- 门店库存数据
INSERT INTO store_inventory (store_id, item_id, quantity, warning_threshold) VALUES
-- 东湖店库存
(1, 1, 100, 30),
(1, 2, 85, 30),
(1, 3, 150, 50),
(1, 4, 120, 40),
(1, 5, 95, 25),
(1, 6, 80, 30),

-- 天河店库存
(2, 1, 90, 30),
(2, 2, 25, 30),  -- 低于预警阈值
(2, 3, 200, 50),
(2, 4, 150, 40),
(2, 5, 75, 25),

-- 荔湾店库存
(3, 1, 70, 30),
(3, 2, 65, 30),
(3, 3, 45, 50),  -- 低于预警阈值
(3, 4, 35, 40),  -- 低于预警阈值
(3, 5, 20, 25);  -- 低于预警阈值

-- 库存操作历史数据
INSERT INTO inventory_history (store_id, item_id, operation_type, quantity, remark) VALUES
-- 东湖店操作记录
(1, 1, 'in', 100, '月初进货'),
(1, 1, 'out', 20, '正常销售'),
(1, 2, 'in', 100, '月初进货'),
(1, 2, 'return', 5, '商品破损'),

-- 天河店操作记录
(2, 1, 'in', 150, '月初进货'),
(2, 1, 'out', 60, '促销活动'),
(2, 2, 'in', 50, '补货'),
(2, 2, 'out', 25, '正常销售'),

-- 荔湾店操作记录
(3, 1, 'in', 80, '月初进货'),
(3, 3, 'in', 100, '补货'),
(3, 3, 'out', 55, '团购订单'),
(3, 4, 'return', 10, '临期商品退货');