package cloud.ciky.controller.finance;

import cloud.ciky.dao.FinanceSummaryDao;
import cloud.ciky.module.FinanceSummary;
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

/**
 * @Author: ciky
 * @Description: 财务概览Servlet
 * @DateTime: 2024/11/22 21:51
 **/
@WebServlet("/finance/summary")
public class FinanceSummaryServlet extends HttpServlet {
    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        int storeId = Integer.parseInt(request.getParameter("storeId"));

        try (Connection conn = DBUtil.getConnection()) {
            FinanceSummaryDao summaryDAO = new FinanceSummaryDao();
            FinanceSummary summary = summaryDAO.getCurrentMonthSummary(storeId);

            response.getWriter().write(gson.toJson(summary));

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("加载财务概览失败：" + e.getMessage());
        }
    }
}
