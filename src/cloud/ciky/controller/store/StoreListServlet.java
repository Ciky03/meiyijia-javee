package cloud.ciky.controller.store;

import cloud.ciky.module.Store;
import cloud.ciky.utils.DBUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ciky
 * @Description: 门店查询servlet
 * @DateTime: 2024/11/20 19:16
 **/
@WebServlet("/store/list")
public class StoreListServlet extends HttpServlet {
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取分页参数
        int page = 1;
        int pageSize = 10;

        try {
            if(request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if(request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
        } catch(NumberFormatException e) {
            // 如果参数解析失败，使用默认值
        }

        // 计算偏移量
        int offset = (page - 1) * pageSize;

        // 获取搜索关键词参数
        String searchTerm = request.getParameter("search");
        List<Store> stores = new ArrayList<>();
        int totalRecords = 0;

        try (Connection conn = DBUtil.getConnection()) {
            // 首先获取总记录数
            String countSql = "SELECT COUNT(*) FROM store";
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                countSql += " WHERE name LIKE ? OR address LIKE ? OR manager LIKE ?";
            }

            try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    String searchPattern = "%" + searchTerm.trim() + "%";
                    countStmt.setString(1, searchPattern);
                    countStmt.setString(2, searchPattern);
                    countStmt.setString(3, searchPattern);
                }

                ResultSet countRs = countStmt.executeQuery();
                if (countRs.next()) {
                    totalRecords = countRs.getInt(1);
                }
            }

            // 构建分页查询SQL
            String sql = "SELECT * FROM store";
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                sql += " WHERE name LIKE ? OR address LIKE ? OR manager LIKE ?";
            }
            sql += " LIMIT ? OFFSET ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                int paramIndex = 1;
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    String searchPattern = "%" + searchTerm.trim() + "%";
                    stmt.setString(paramIndex++, searchPattern);
                    stmt.setString(paramIndex++, searchPattern);
                    stmt.setString(paramIndex++, searchPattern);
                }
                stmt.setInt(paramIndex++, pageSize);
                stmt.setInt(paramIndex, offset);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Store store = new Store();
                        store.setId(String.valueOf(rs.getInt("id")));
                        store.setName(rs.getString("name"));
                        store.setAddress(rs.getString("address"));
                        store.setManager(rs.getString("manager"));
                        store.setPhone(rs.getString("phone"));
                        store.setSales(rs.getDouble("sales"));
                        store.setInventory(rs.getString("inventory"));
                        stores.add(store);
                    }
                }
            }

            // 计算总页数
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

            // 构建响应数据
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("data", stores);
            responseData.put("currentPage", page);
            responseData.put("totalPages", totalPages);
            responseData.put("pageSize", pageSize);
            responseData.put("totalRecords", totalRecords);

            // 设置响应类型为JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 使用Gson将数据转换为JSON并写入响应
            Gson gson = new Gson();
            String json = gson.toJson(responseData);
            response.getWriter().write(json);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"获取门店数据失败\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // POST请求转发给doGet处理
        doGet(request, response);
    }
}
