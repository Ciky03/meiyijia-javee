-- 门店信息表
create table store
(
    id        int auto_increment primary key,
    name      varchar(100)                  not null comment '门店名称',
    address   varchar(200)                  not null comment '门店地址',
    manager   varchar(50)                   not null comment '店长姓名',
    phone     varchar(20)                   not null comment '联系电话',
    sales     decimal(10, 2) default 0.00   not null comment '月度销售额',
    inventory varchar(10)    default '正常' not null comment '库存状况'
) comment '门店信息表';

-- 门店信息表数据
INSERT INTO javaeedb.store (id, name, address, manager, phone, sales, inventory) VALUES (1, '美宜佳北京路店', '广州市越秀区北京路步行街122号', '张三', '13800138001', 156789.50, '正常');
INSERT INTO javaeedb.store (id, name, address, manager, phone, sales, inventory) VALUES (2, '美宜佳天河城店', '广州市天河区天河路208号', '李四', '13800138002', 198567.25, '正常');
INSERT INTO javaeedb.store (id, name, address, manager, phone, sales, inventory) VALUES (3, '美宜佳体育西店', '广州市天河区体育西路101号', '王五', '13800138003', 145678.75, '告急');
INSERT INTO javaeedb.store (id, name, address, manager, phone, sales, inventory) VALUES (4, '美宜佳江南西店', '广州市海珠区江南西路89号', '赵六', '13800138004', 167890.00, '正常');
INSERT INTO javaeedb.store (id, name, address, manager, phone, sales, inventory) VALUES (5, '美宜佳上下九店', '广州市荔湾区上下九路238号', '钱七', '13800138005', 178234.50, '告急');

