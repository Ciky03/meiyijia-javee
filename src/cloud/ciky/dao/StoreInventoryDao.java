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
 * @DateTime: 2024/11/21 21:34
 **/
public class StoreInventoryDao {
    public List<StoreInventory> findByStore(Integer storeId, int page, int pageSize) throws SQLException {
        List<StoreInventory> inventories = new ArrayList<>();
        String sql = "SELECT si.*, i.name as item_name FROM store_inventory si " +
                    "JOIN item i ON si.item_id = i.id " +
                    "WHERE si.store_id = ? ORDER BY si.item_id LIMIT ? OFFSET ?";
//        String sql = "SELECT * from store_inventory where store_id = ? order by item_id LIMIT ? OFFSET ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StoreInventory inventory = new StoreInventory();
                    inventory.setStoreId(rs.getInt("store_id"));
                    inventory.setItemId(rs.getInt("item_id"));
                    inventory.setItemName(rs.getString("item_name"));
                    inventory.setQuantity(rs.getInt("quantity"));
                    inventory.setWarningThreshold(rs.getInt("warning_threshold"));
                    inventory.setLastUpdate(rs.getTimestamp("last_update"));
                    inventories.add(inventory);
                }
            }
        }
        return inventories;
    }

    public int countByStore(Integer storeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM store_inventory WHERE store_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
