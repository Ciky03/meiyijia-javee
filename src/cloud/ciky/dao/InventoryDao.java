package cloud.ciky.dao;

import cloud.ciky.module.StoreInventory;
import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: TODO
 * @DateTime: 2024/11/22 13:02
 **/
public class InventoryDao {

    // 检查库存是否足够
    public boolean checkInventory(int storeId, int itemId, int quantity) throws SQLException {
        String sql = "SELECT quantity FROM store_inventory WHERE store_id = ? AND item_id = ?";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, storeId);
            stmt.setInt(2, itemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int currentQuantity = rs.getInt("quantity");
                    return currentQuantity >= quantity;
                }
                return false;
            }
        }
    }

    // 增加库存
    public void increaseInventory(int storeId, int itemId, int quantity) throws SQLException {
        String sql = "INSERT INTO store_inventory (store_id, item_id, quantity) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, storeId);
            stmt.setInt(2, itemId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        }
    }

    // 减少库存
    public void decreaseInventory(int storeId, int itemId, int quantity) throws SQLException {
        String sql = "UPDATE store_inventory SET quantity = quantity - ? " +
                    "WHERE store_id = ? AND item_id = ?";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setInt(2, storeId);
            stmt.setInt(3, itemId);
            stmt.executeUpdate();
        }
    }

    public int getTotalCount(int storeId, String searchTerm) throws SQLException {
        String sql = "SELECT COUNT(*) FROM store_inventory si " +
                    "JOIN item i ON si.item_id = i.id " +
                    "WHERE si.store_id = ? " +
                    (searchTerm != null && !searchTerm.trim().isEmpty()
                        ? "AND i.name LIKE ?" : "");

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, storeId);
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                stmt.setString(2, "%" + searchTerm.trim() + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    public List<StoreInventory> searchItems(int storeId, String searchTerm, int page, int pageSize)
            throws SQLException {
        List<StoreInventory> items = new ArrayList<>();

        String sql = "SELECT i.id, i.name, si.quantity, si.warning_threshold, si.last_update " +
                    "FROM store_inventory si " +
                    "JOIN item i ON si.item_id = i.id " +
                    "WHERE si.store_id = ? " +
                    (searchTerm != null && !searchTerm.trim().isEmpty()
                        ? "AND i.name LIKE ? " : "") +
                    "ORDER BY i.name " +
                    "LIMIT ? OFFSET ?";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            int paramIndex = 1;
            stmt.setInt(paramIndex++, storeId);

            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchTerm.trim() + "%");
            }

            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex, (page - 1) * pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StoreInventory item = new StoreInventory();
                    item.setItemId(rs.getInt("id"));
                    item.setItemName(rs.getString("name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setWarningThreshold(rs.getInt("warning_threshold"));
                    item.setLastUpdate(rs.getTimestamp("last_update"));
                    items.add(item);
                }
            }
        }

        return items;
    }

}
