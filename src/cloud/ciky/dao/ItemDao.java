package cloud.ciky.dao;

import cloud.ciky.module.Item;
import cloud.ciky.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 商品Dao
 * @DateTime: 2024/11/21 21:23
 **/
public class ItemDao {
    public List<Item> findAll() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, name, create_time, update_time FROM item ORDER BY id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCreateTime(rs.getTimestamp("create_time"));
                item.setUpdateTime(rs.getTimestamp("update_time"));
                items.add(item);
            }
        }

        return items;
    }

    public Item findById(Integer id) throws SQLException {
        String sql = "SELECT id, name, create_time, update_time FROM item WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setCreateTime(rs.getTimestamp("create_time"));
                    item.setUpdateTime(rs.getTimestamp("update_time"));
                    return item;
                }
            }
        }

        return null;
    }

    public void insert(Item item) throws SQLException {
        String sql = "INSERT INTO item (name) VALUES (?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getName());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getInt(1));
                }
            }
        }
    }

    public void update(Item item) throws SQLException {
        String sql = "UPDATE item SET name = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM item WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
