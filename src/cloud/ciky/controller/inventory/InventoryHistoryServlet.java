package cloud.ciky.controller.inventory;

import cloud.ciky.dao.InventoryHistoryDao;
import cloud.ciky.module.InventoryHistory;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 查询历史记录Servlet
 * @DateTime: 2024/11/22 0:51
 **/
@WebServlet("/inventory/history")
public class InventoryHistoryServlet extends HttpServlet {
    private InventoryHistoryDao historyDao = new InventoryHistoryDao();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 获取门店ID
            Integer storeId = Integer.parseInt(request.getParameter("storeId"));

            // 获取历史记录
            List<InventoryHistory> histories = historyDao.findByStore(storeId);

            // 设置响应类型
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 发送响应
            response.getWriter().write(gson.toJson(histories));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"获取历史记录失败\"}");
        }
    }
}
