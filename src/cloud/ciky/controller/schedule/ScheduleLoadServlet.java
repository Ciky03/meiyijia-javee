package cloud.ciky.controller.schedule;

import cloud.ciky.dao.ScheduleDao;
import cloud.ciky.module.Schedule;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: ciky
 * @Description: 加载已存在的排班表Servlet
 * @DateTime: 2024/11/23 14:00
 **/
@WebServlet("/schedule/load")
public class ScheduleLoadServlet extends HttpServlet {
    private ScheduleDao scheduleDao = new ScheduleDao();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try {
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            int weekNumber = Integer.parseInt(request.getParameter("weekNum"));
            int dayOfWeek = Integer.parseInt(request.getParameter("dayOfWeek"));
            String shiftType = request.getParameter("shiftType");

            // 获取指定条件的排班数据
            List<Schedule> schedules = scheduleDao.getSchedules(storeId, weekNumber, dayOfWeek, shiftType);

            // 提取员工ID列表
            List<Integer> employeeIds = schedules.stream()
                .map(Schedule::getEmployeeId)
                .collect(Collectors.toList());

            response.getWriter().write(gson.toJson(employeeIds));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write( "加载排班数据失败: " + e.getMessage());
        }
    }
}
