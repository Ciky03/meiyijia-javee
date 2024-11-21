package cloud.ciky.dao;

import cloud.ciky.module.InventoryHistory;
import cloud.ciky.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 历史记录数据访问层
 * @DateTime: 2024/11/22 0:50
 **/
public class InventoryHistoryDao {
    public List<InventoryHistory> findByStore(Integer storeId) throws SQLException {
        List<InventoryHistory> histories = new ArrayList<>();
        String sql = "SELECT h.*, i.name as item_name " +
                    "FROM inventory_history h " +
                    "JOIN item i ON h.item_id = i.id " +
                    "WHERE h.store_id = ? " +
                    "ORDER BY h.create_time DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InventoryHistory history = new InventoryHistory();
                    history.setId(rs.getInt("id"));
                    history.setStoreId(rs.getInt("store_id"));
                    history.setItemId(rs.getInt("item_id"));
                    history.setItemName(rs.getString("item_name"));
                    history.setType(rs.getString("operation_type"));
                    history.setQuantity(rs.getInt("quantity"));
                    history.setRemark(rs.getString("remark"));
                    history.setCreateTime(rs.getTimestamp("create_time"));
                    histories.add(history);
                }
            }
        }
        return histories;
    }

    public void insert(InventoryHistory history) throws SQLException {
        String sql = "INSERT INTO inventory_history (store_id, item_id, operation_type, quantity, remark) " +
                    "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, history.getStoreId());
            stmt.setInt(2, history.getItemId());
            stmt.setString(3, history.getType());
            stmt.setInt(4, history.getQuantity());
            stmt.setString(5, history.getRemark());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    history.setId(rs.getInt(1));
                }
            }
        }
    }
}
