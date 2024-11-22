package cloud.ciky.controller.inventory;

import cloud.ciky.dao.InventoryDao;
import cloud.ciky.dao.StoreInventoryDao;
import cloud.ciky.module.StoreInventory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ciky
 * @Description: 门店库存搜索
 * @DateTime: 2024/11/22 13:39
 **/
@WebServlet("/inventory/search")
public class InventorySearchServlet extends HttpServlet {
    private Gson gson = new Gson();
    private InventoryDao inventoryDao = new InventoryDao();
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // 获取请求参数
        int storeId = Integer.parseInt(request.getParameter("storeId"));
        String searchTerm = request.getParameter("searchTerm");
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        Map<String, Object> result = new HashMap<>();

        try (Connection conn = DBUtil.getConnection()) {

            // 获取总记录数
            int total = inventoryDao.getTotalCount(storeId, searchTerm);

            // 获取分页数据
            List<StoreInventory> items = inventoryDao.searchItems(storeId, searchTerm, page, pageSize);

            result.put("success", true);
            result.put("total", total);
            result.put("data", items);

        } catch (SQLException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "搜索失败：" + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

         // 设置响应类型和编码
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // 写入响应
        response.getWriter().write(gson.toJson(result));
    }
}
