package cloud.ciky.controller.inventory;

import cloud.ciky.dao.StoreInventoryDao;
import cloud.ciky.module.StoreInventory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 通过门店id查询门店库存Servlet
 * @DateTime: 2024/11/21 21:39
 **/
@WebServlet("/inventory/list")
public class InventoryListServlet extends HttpServlet {
    private StoreInventoryDao inventoryDao = new StoreInventoryDao();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 获取请求参数
            Integer storeId = Integer.parseInt(request.getParameter("storeId"));
            int page = Integer.parseInt(request.getParameter("page"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));

            // 获取库存列表和总数
            List<StoreInventory> inventories = inventoryDao.findByStore(storeId, page, pageSize);
            int total = inventoryDao.countByStore(storeId);

            // 构建响应数据
            JsonObject result = new JsonObject();
            result.add("data", gson.toJsonTree(inventories));
            result.addProperty("total", total);

            // 设置响应类型
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 发送响应
            response.getWriter().write(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"获取库存列表失败\"}");
        }
    }
}
