package cloud.ciky.controller.inventory;

import cloud.ciky.dao.InventoryWarningDao;
import cloud.ciky.module.InventoryWarning;
import cloud.ciky.utils.DBUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 库存预警Servlet
 * @DateTime: 2024/11/22 13:53
 **/
@WebServlet("/inventory/warning")
public class InventoryWarningServlet extends HttpServlet {
    private Gson gson = new Gson();
    private InventoryWarningDao inventoryWarningDao = new InventoryWarningDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        int storeId = Integer.parseInt(request.getParameter("storeId"));

        try (Connection conn = DBUtil.getConnection()) {
            List<InventoryWarning> warnings = inventoryWarningDao.getWarnings(storeId);
            // 写入响应
            response.getWriter().write(gson.toJson(warnings));
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("检查库存预警失败：" + e.getMessage());
        }
    }
}
