package cloud.ciky.dao;

import cloud.ciky.module.Employee;
import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 员工Dao
 * @DateTime: 2024/11/23 11:45
 **/
public class EmployeeDao {
public List<Employee> getEmployees(int offset, int pageSize, String searchTerm) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.*, s.name as store_name FROM employee e " +
                    "LEFT JOIN store s ON e.store_id = s.id " +
                    "WHERE e.status = 1 ";

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql += "AND (e.name LIKE ? OR e.employee_no LIKE ? OR e.phone LIKE ?) ";
        }

        sql += "ORDER BY e.id LIMIT ? OFFSET ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String searchPattern = "%" + searchTerm + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }

            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setEmployeeNo(rs.getString("employee_no"));
                employee.setName(rs.getString("name"));
                employee.setPhone(rs.getString("phone"));
                employee.setStoreId(rs.getInt("store_id"));
                employee.setStoreName(rs.getString("store_name"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setStatus(rs.getInt("status"));
                employees.add(employee);
            }
        }

        return employees;
    }

    public int getTotalEmployees(String searchTerm) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employee WHERE status = 1 ";

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql += "AND (name LIKE ? OR employee_no LIKE ? OR phone LIKE ?)";
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String searchPattern = "%" + searchTerm + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    public Employee getEmployeeById(int id) throws SQLException {
        String sql = "SELECT e.*, s.name as store_name FROM employee e " +
                    "LEFT JOIN store s ON e.store_id = s.id " +
                    "WHERE e.id = ? AND e.status = 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setEmployeeNo(rs.getString("employee_no"));
                employee.setName(rs.getString("name"));
                employee.setPhone(rs.getString("phone"));
                employee.setStoreId(rs.getInt("store_id"));
                employee.setStoreName(rs.getString("store_name"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setStatus(rs.getInt("status"));
                return employee;
            }
        }

        return null;
    }

}
