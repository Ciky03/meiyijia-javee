package cloud.ciky.dao;

import cloud.ciky.module.InventoryWarning;
import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 库存警告Dao
 * @DateTime: 2024/11/22 13:56
 **/
public class InventoryWarningDao {
    public List<InventoryWarning> getWarnings(int storeId) throws SQLException {
        List<InventoryWarning> warnings = new ArrayList<>();

        String sql = "SELECT i.id, i.name, si.quantity, si.warning_threshold " +
                    "FROM store_inventory si " +
                    "JOIN item i ON si.item_id = i.id " +
                    "WHERE si.store_id = ? " +
                    "AND si.quantity <= si.warning_threshold " +
                    "ORDER BY si.quantity / si.warning_threshold ASC";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, storeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InventoryWarning item = new InventoryWarning();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setThreshold(rs.getInt("warning_threshold"));
                    warnings.add(item);
                }
            }
        }

        return warnings;
    }
}
