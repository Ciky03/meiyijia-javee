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

/**
 * @Author: ciky
 * @Description: 根据id获取门店信息Servlet
 * @DateTime: 2024/11/21 19:48
 **/
@WebServlet("/store/get")
public class StoreGetServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应类型
        response.setContentType("application/json;charset=UTF-8");

        try {
            // 获取门店ID
            String storeId = request.getParameter("id");

            // 查询门店信息
            Store store = getStoreById(storeId);

            if (store != null) {
                // 将门店对象转换为JSON并返回
                String jsonResponse = gson.toJson(store);
                response.getWriter().write(jsonResponse);
            } else {
                // 未找到门店
                response.getWriter().write("{\"success\":false,\"message\":\"未找到门店信息\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    public Store getStoreById(String id) throws SQLException {
        String sql = "SELECT * FROM store WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Store store = new Store();
                store.setId(rs.getString("id"));
                store.setName(rs.getString("name"));
                store.setAddress(rs.getString("address"));
                store.setManager(rs.getString("manager"));
                store.setPhone(rs.getString("phone"));
                store.setSales(rs.getDouble("sales"));
                store.setInventory(rs.getString("inventory"));
                return store;
            }
        }
        return null;
    }
}
