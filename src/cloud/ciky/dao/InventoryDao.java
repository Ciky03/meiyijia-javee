package cloud.ciky.dao;

import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
