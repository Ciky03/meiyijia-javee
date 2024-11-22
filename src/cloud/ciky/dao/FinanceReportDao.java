package cloud.ciky.dao;

import cloud.ciky.module.FinanceReport;
import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 月度报表Dao
 * @DateTime: 2024/11/22 21:46
 **/
public class FinanceReportDao {
    public List<FinanceReport> getMonthlyReports(int storeId) throws SQLException {
        List<FinanceReport> reports = new ArrayList<>();

        String sql = "SELECT DATE_FORMAT(record_date, '%Y-%m') as month, " +
                    "SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) as total_income, " +
                    "SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) as total_expense " +
                    "FROM finance_record " +
                    "WHERE store_id = ? " +
                    "GROUP BY DATE_FORMAT(record_date, '%Y-%m') " +
                    "ORDER BY month DESC";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, storeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FinanceReport report = new FinanceReport();
                    report.setMonth(rs.getString("month"));
                    report.setIncome(rs.getBigDecimal("total_income"));
                    report.setExpense(rs.getBigDecimal("total_expense"));
                    reports.add(report);
                }
            }
        }

        return reports;
    }
}
