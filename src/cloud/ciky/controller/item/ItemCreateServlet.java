package cloud.ciky.controller.item;

import cloud.ciky.dao.ItemDao;
import cloud.ciky.module.Item;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ciky
 * @Description: 新增商品Servlet
 * @DateTime: 2024/11/22 11:24
 **/
@WebServlet("/item/create")
public class ItemCreateServlet extends HttpServlet {

    private ItemDao itemDao = new ItemDao();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应类型
        response.setContentType("application/json;charset=UTF-8");

        // 获取请求参数
        String name = request.getParameter("name");
        int warningThreshold = Integer.parseInt(request.getParameter("warningThreshold"));
        int storeId = Integer.parseInt(request.getParameter("storeId"));

        Map<String, Object> result = new HashMap<>();

        // 创建商品
        Item item = new Item();
        item.setName(name);

        int itemId = 0;
        try {
            // 插入商品并获取生成的ID
            itemId = itemDao.insert(item);
            if (itemId == 0) {
                throw new SQLException();
            }
            // 创建商品-门店关联记录（包含预警阈值）
            itemDao.createStoreItemRelation(itemId, storeId, warningThreshold);
            // 返回成功响应
            result.put("success", true);
            result.put("itemId", itemId);
            result.put("message", "商品创建成功");

        } catch (SQLException e) {
            // 记录错误日志
            e.printStackTrace();

            // 返回错误响应
            result.put("success", false);
            result.put("message", "商品创建失败：" + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        // 设置响应类型和编码
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // 写入响应
        response.getWriter().write(gson.toJson(result));
    }
}
