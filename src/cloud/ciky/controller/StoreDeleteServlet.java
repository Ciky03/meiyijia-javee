package cloud.ciky.controller;

import cloud.ciky.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: ciky
 * @Description: 根据id删除门店信息Servlet
 * @DateTime: 2024/11/21 19:52
 **/
@WebServlet("/store/delete")
public class StoreDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应类型
        response.setContentType("application/json;charset=UTF-8");

        try {
            // 获取要删除的门店ID
            String storeId = request.getParameter("id");

            // 执行删除操作
            boolean success = deleteStore(storeId);

            if (success) {
                // 删除成功，重定向回列表页面
                response.sendRedirect(request.getContextPath() + "/store/storeList.jsp");
            } else {
                // 删除失败
                response.getWriter().write("{\"success\":false,\"message\":\"删除失败\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

     public boolean deleteStore(String id) throws SQLException {
        String sql = "DELETE FROM store WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
