package cloud.ciky.controller.inventory;

import cloud.ciky.dao.InventoryDao;
import cloud.ciky.dao.InventoryHistoryDao;
import cloud.ciky.module.InventoryHistory;
import cloud.ciky.utils.DBUtil;
import com.google.gson.Gson;

import javax.crypto.spec.IvParameterSpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ciky
 * @Description: 更新商品库存Servlet
 * @DateTime: 2024/11/22 13:01
 **/
@WebServlet("/inventory/update")
public class InventoryUpdateServlet extends HttpServlet {
    private InventoryDao inventoryDao = new InventoryDao();
    private InventoryHistoryDao inventoryHistoryDao = new InventoryHistoryDao();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // 获取请求参数
        int storeId = Integer.parseInt(request.getParameter("storeId"));
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        String type = request.getParameter("type");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String remark = request.getParameter("remark");

        Map<String, Object> result = new HashMap<>();

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false); // 开启事务

            try {
                // 更新库存
                switch (type) {
                    case "in":
                        inventoryDao.increaseInventory(storeId, itemId, quantity);
                        break;
                    case "out":
                    case "return":
                         if (!inventoryDao.checkInventory(storeId, itemId, quantity)) {
                            throw new SQLException("库存不足");
                        }
                        inventoryDao.decreaseInventory(storeId, itemId, quantity);
                        break;
                    default:
                        throw new SQLException("无效的操作类型");
                }

                // 记录操作历史
                InventoryHistory inventoryHistory = new InventoryHistory();
                inventoryHistory.setStoreId(storeId);
                inventoryHistory.setItemId(itemId);
                inventoryHistory.setType(type);
                inventoryHistory.setQuantity(quantity);
                inventoryHistory.setRemark(remark);
                inventoryHistoryDao.insert(inventoryHistory);

                conn.commit(); // 提交事务

                result.put("success", true);
                result.put("message", "操作成功");

            } catch (Exception e) {
                conn.rollback(); // 发生异常时回滚事务
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "操作失败：" + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        // 设置响应类型和编码
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // 写入响应
        response.getWriter().write(gson.toJson(result));
    }
}
