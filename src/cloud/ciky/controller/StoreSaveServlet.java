package cloud.ciky.controller;

import cloud.ciky.module.Store;
import cloud.ciky.utils.DBUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ciky
 * @Description: 新增门店Servlet
 * @DateTime: 2024/11/21 0:12
 **/
@WebServlet("/store/save")
public class StoreSaveServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 读取请求体中的JSON数据
        BufferedReader reader = request.getReader();
        Store store = gson.fromJson(reader, Store.class);

        Map<String, Object> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();

            // 根据是否有ID判断是新增还是更新
            if (store.getId() == null || store.getId().isEmpty()) {
                // 插入新记录
                String sql = "INSERT INTO store (name, address, manager, phone, sales, inventory) VALUES (?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, store.getName());
                pstmt.setString(2, store.getAddress());
                pstmt.setString(3, store.getManager());
                pstmt.setString(4, store.getPhone());
                pstmt.setDouble(5, store.getSales());
                pstmt.setString(6, store.getInventory());
            } else {
                // 更新现有记录
                String sql = "UPDATE store SET name=?, address=?, manager=?, phone=?, sales=?, inventory=? WHERE id=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, store.getName());
                pstmt.setString(2, store.getAddress());
                pstmt.setString(3, store.getManager());
                pstmt.setString(4, store.getPhone());
                pstmt.setDouble(5, store.getSales());
                pstmt.setString(6, store.getInventory());
                pstmt.setString(7, store.getId());
            }

            int affectedRows = pstmt.executeUpdate();

            result.put("success", affectedRows > 0);
            result.put("message", affectedRows > 0 ? "保存成功" : "保存失败");

        } catch (SQLException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "数据库错误：" + e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }

        // 设置响应类型和编码
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 发送响应
        response.getWriter().write(gson.toJson(result));
    }
}