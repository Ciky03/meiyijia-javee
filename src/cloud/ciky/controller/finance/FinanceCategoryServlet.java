package cloud.ciky.controller.finance;

import cloud.ciky.dao.FinanceCategoryDao;
import cloud.ciky.module.FinanceCategory;
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
import java.util.stream.Collectors;

/**
 * @Author: ciky
 * @Description: 查询财务分类Servlet
 * @DateTime: 2024/11/22 19:31
 **/
@WebServlet("/finance/categories")
public class FinanceCategoryServlet extends HttpServlet {
    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String type = request.getParameter("type");
        if (type == null || (!type.equals("income") && !type.equals("expense"))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid type parameter");
            return;
        }

        try  {
            FinanceCategoryDao categoryDAO = new FinanceCategoryDao();
            List<FinanceCategory> categories = categoryDAO.getCategoriesByType(type);

            // 只返回分类名称的列表
            List<String> categoryNames = categories.stream()
                    .map(FinanceCategory::getName)
                    .collect(Collectors.toList());

            response.getWriter().write(gson.toJson(categoryNames));

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("加载分类失败：" + e.getMessage());
        }
    }
}
