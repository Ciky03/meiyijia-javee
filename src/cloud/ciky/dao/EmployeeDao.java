package cloud.ciky.dao;

import cloud.ciky.module.Employee;
import cloud.ciky.utils.DBUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
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

     public boolean insertEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee (employee_no, name, phone, store_id, hire_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, 1)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getEmployeeNo());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getPhone());
            stmt.setInt(4, employee.getStoreId());
            stmt.setDate(5, (Date) employee.getHireDate());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name = ?, phone = ?, store_id = ?, " +
                    "hire_date = ? WHERE id = ? AND status = 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getPhone());
            stmt.setInt(3, employee.getStoreId());
            stmt.setDate(4, (Date) employee.getHireDate());
            stmt.setInt(5, employee.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public int getNextEmployeeSequence() {
        String sql = "SELECT COUNT(*) + 1 FROM employee WHERE employee_no LIKE ?";
        String today = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, today + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

     public boolean deleteEmployee(int id) throws SQLException {
        String sql = "UPDATE employee SET status = 0 WHERE id = ? AND status = 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean hasActiveSchedule(int employeeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM schedule WHERE employee_id = ? " +
                    "AND week_number >= WEEK(CURRENT_DATE)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
 public List<Employee> getEmployeesByStore(int storeId) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id, employee_no, name, phone, store_id, hire_date, status " +
                    "FROM employee WHERE store_id = ? AND status = 1 " +
                    "ORDER BY name";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setEmployeeNo(rs.getString("employee_no"));
                employee.setName(rs.getString("name"));
                employee.setPhone(rs.getString("phone"));
                employee.setStoreId(rs.getInt("store_id"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setStatus(rs.getInt("status"));
                employees.add(employee);
            }
        }

        return employees;
    }
}
