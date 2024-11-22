package cloud.ciky.controller.finance;

import cloud.ciky.dao.FinanceReportDao;
import cloud.ciky.module.FinanceReport;
import cloud.ciky.utils.DBUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 查询月度报表Servlet
 * @DateTime: 2024/11/22 21:45
 **/
@WebServlet("/finance/report")
public class FinanceReportServlet extends HttpServlet {
    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        int storeId = Integer.parseInt(request.getParameter("storeId"));

        try {
            FinanceReportDao reportDAO = new FinanceReportDao();
            List<FinanceReport> reports = reportDAO.getMonthlyReports(storeId);

            response.getWriter().write(gson.toJson(reports));

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("加载月度报表失败：" + e.getMessage());
        }
    }
}
