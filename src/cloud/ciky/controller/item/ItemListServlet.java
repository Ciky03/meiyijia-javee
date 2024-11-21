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
import java.util.List;

/**
 * @Author: ciky
 * @Description: 查看所有商品Servlet
 * @DateTime: 2024/11/21 21:21
 **/
@WebServlet("/item/list")
public class ItemListServlet extends HttpServlet {
    private ItemDao itemDao = new ItemDao();

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 获取商品列表
            List<Item> items = itemDao.findAll();

            // 设置响应类型
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 将商品列表转换为JSON并发送响应
            String json = gson.toJson(items);
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"获取商品列表失败\"}");
        }
    }
}
