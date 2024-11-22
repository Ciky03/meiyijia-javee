package cloud.ciky.controller.finance;

import cloud.ciky.dao.FinanceRecordDao;
import cloud.ciky.module.FinanceRecord;
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
 * @Description: 查询收支记录Servlet
 * @DateTime: 2024/11/22 19:40
 **/
@WebServlet("/finance/records")
public class FinanceRecordServlet extends HttpServlet {
    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        int storeId = Integer.parseInt(request.getParameter("storeId"));

        try{
            FinanceRecordDao recordDAO = new FinanceRecordDao();
            List<FinanceRecord> records = recordDAO.getRecordsByStore(storeId);
            response.getWriter().write(gson.toJson(records));

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("加载收支记录失败：" + e.getMessage());
        }
    }

}
