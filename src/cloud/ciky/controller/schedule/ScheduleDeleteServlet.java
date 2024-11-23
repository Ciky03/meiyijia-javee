package cloud.ciky.controller.schedule;

import cloud.ciky.dao.ScheduleDao;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author: ciky
 * @Description: 清除排班Servlet
 * @DateTime: 2024/11/23 14:39
 **/
@WebServlet("/schedule/delete")
public class ScheduleDeleteServlet extends HttpServlet {
    private ScheduleDao scheduleDao = new ScheduleDao();
    private Gson gson = new Gson();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }

    // 为了支持DELETE请求，需要重写doPost方法并将请求转发给doDelete
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("application/json;charset=UTF-8");

        try {
            // 获取请求参数
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            int weekNum = Integer.parseInt(request.getParameter("weekNum"));
            int dayOfWeek = Integer.parseInt(request.getParameter("dayOfWeek"));
            String shiftType = request.getParameter("shiftType");

            // 删除排班记录
            scheduleDao.deleteSchedules(storeId, weekNum, dayOfWeek, shiftType);

            // 返回成功响应
            response.getWriter().write("{\"success\": true, \"message\": \"排班清除成功\"}");

        } catch (SQLException e) {
            // 数据库操作失败
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                "{\"success\": false, \"message\": \"清除失败: " + e.getMessage() + "\"}"
            );
        } catch (NumberFormatException e) {
            // 参数格式错误
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(
                "{\"success\": false, \"message\": \"无效的参数: " + e.getMessage() + "\"}"
            );
        }
    }
}
