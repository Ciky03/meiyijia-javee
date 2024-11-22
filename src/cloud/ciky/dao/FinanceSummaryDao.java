package cloud.ciky.dao;

import cloud.ciky.module.FinanceSummary;
import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @Author: ciky
 * @Description: 财务概览Dao
 * @DateTime: 2024/11/22 21:52
 **/
public class FinanceSummaryDao {
    public FinanceSummary getCurrentMonthSummary(int storeId) throws SQLException {
        FinanceSummary summary = new FinanceSummary();

        // 获取当前月份的起始日期和结束日期
        LocalDate now = LocalDate.now();
        String startDate = now.withDayOfMonth(1).toString();
        String endDate = now.withDayOfMonth(now.lengthOfMonth()).toString();

        String sql = "SELECT " +
                    "SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) as total_income, " +
                    "SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) as total_expense " +
                    "FROM finance_record " +
                    "WHERE store_id = ? " +
                    "AND record_date BETWEEN ? AND ?";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, storeId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    summary.setMonthlyIncome(rs.getBigDecimal("total_income"));
                    summary.setMonthlyExpense(rs.getBigDecimal("total_expense"));
                }
            }
        }

        // 如果没有数据，设置为0
        if (summary.getMonthlyIncome() == null) {
            summary.setMonthlyIncome(java.math.BigDecimal.ZERO);
        }
        if (summary.getMonthlyExpense() == null) {
            summary.setMonthlyExpense(java.math.BigDecimal.ZERO);
        }

        return summary;
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        String startDate = now.withDayOfMonth(1).toString();
        String endDate = now.withDayOfMonth(now.lengthOfMonth()).toString();
        System.out.println(startDate);
        System.out.println(endDate);
    }
}
