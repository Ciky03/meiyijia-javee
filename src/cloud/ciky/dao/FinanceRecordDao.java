package cloud.ciky.dao;

import cloud.ciky.module.FinanceRecord;
import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 收支记录表Dao
 * @DateTime: 2024/11/22 19:41
 **/
public class FinanceRecordDao {
    private Connection conn;

    public FinanceRecordDao() {
    }

    public FinanceRecordDao(Connection conn) {
        this.conn = conn;
    }

    public int getTotalCount(int storeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM finance_record WHERE store_id = ?";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, storeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    public List<FinanceRecord> getRecordsByStore(int storeId, int page, int pageSize) throws SQLException {
        List<FinanceRecord> records = new ArrayList<>();

        String sql = "SELECT fr.id, fr.store_id, fr.type, fr.amount, " +
                "fc.name as category_name, fr.record_date, fr.remark, " +
                "fr.create_time, fr.update_time " +
                "FROM finance_record fr " +
                "JOIN finance_category fc ON fr.category_id = fc.id " +
                "WHERE fr.store_id = ? " +
                "ORDER BY fr.record_date DESC, fr.create_time DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, storeId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FinanceRecord record = new FinanceRecord();
                    record.setId(rs.getInt("id"));
                    record.setStoreId(rs.getInt("store_id"));
                    record.setType(rs.getString("type"));
                    record.setAmount(rs.getBigDecimal("amount"));
                    record.setCategory(rs.getString("category_name"));
                    record.setDate(rs.getDate("record_date"));
                    record.setRemark(rs.getString("remark"));
                    record.setCreateTime(rs.getTimestamp("create_time"));
                    record.setUpdateTime(rs.getTimestamp("update_time"));
                    records.add(record);
                }
            }
        }

        return records;
    }

    public int insert(FinanceRecord record) throws SQLException {
        String sql = "INSERT INTO finance_record (store_id, type, amount, category_id, record_date, remark) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, record.getStoreId());
            stmt.setString(2, record.getType());
            stmt.setBigDecimal(3, record.getAmount());
            stmt.setInt(4, record.getCategoryId());
            stmt.setDate(5, new java.sql.Date(record.getDate().getTime()));
            stmt.setString(6, record.getRemark());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("创建记录失败，未获取到ID");
            }
        }
    }
}
