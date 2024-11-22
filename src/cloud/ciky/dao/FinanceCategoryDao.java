package cloud.ciky.dao;

import cloud.ciky.module.FinanceCategory;
import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 财务分类表Dao
 * @DateTime: 2024/11/22 19:33
 **/
public class FinanceCategoryDao {
    private Connection conn;

     public FinanceCategoryDao() {
    }

    public FinanceCategoryDao(Connection conn) {
        this.conn = conn;
    }

    public List<FinanceCategory> getCategoriesByType(String type) throws SQLException {
        List<FinanceCategory> categories = new ArrayList<>();

        String sql = "SELECT id, name, type, create_time, update_time " +
                    "FROM finance_category " +
                    "WHERE type = ? " +
                    "ORDER BY name";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FinanceCategory category = new FinanceCategory();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    category.setType(rs.getString("type"));
                    category.setCreateTime(rs.getTimestamp("create_time"));
                    category.setUpdateTime(rs.getTimestamp("update_time"));
                    categories.add(category);
                }
            }
        }

        return categories;
    }

    public int getCategoryIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM finance_category WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return 0;
            }
        }
    }
}
