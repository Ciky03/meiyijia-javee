package cloud.ciky.controller.schedule;

import cloud.ciky.dao.EmployeeDao;
import cloud.ciky.dao.ScheduleDao;
import cloud.ciky.module.Employee;
import cloud.ciky.module.Schedule;
import cloud.ciky.module.ScheduleView;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: ciky
 * @Description: 加载排班表Servlet
 * @DateTime: 2024/11/23 14:14
 **/
@WebServlet("/schedule/list")
public class ScheduleListServlet extends HttpServlet {
    private ScheduleDao scheduleDao = new ScheduleDao();
    private EmployeeDao employeeDao = new EmployeeDao();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        try {
            // 获取请求参数
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            int weekNum = Integer.parseInt(request.getParameter("weekNum"));

            // 存储所有班次的结果
            List<ScheduleView> scheduleViews = new ArrayList<>();

            // 获取所有班次类型的排班
            String[] shiftTypes = {"morning", "afternoon", "evening"};

            // 遍历每一天和每个班次
            for (int day = 1; day <= 7; day++) {
                for (String shiftType : shiftTypes) {
                    // 获取该时间段的所有排班记录
                    List<Schedule> schedules = scheduleDao.getSchedules(storeId, weekNum, day, shiftType);

                    if (!schedules.isEmpty()) {
                        // 获取所有员工信息
                        List<Employee> employees = schedules.stream()
                            .map(s -> {
                                try {
                                    return employeeDao.getEmployeeById(s.getEmployeeId());
                                } catch (SQLException e) {
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                        // 创建视图对象
                        ScheduleView view = new ScheduleView();
                        view.setDayOfWeek(day);
                        view.setShiftType(shiftType);
                        view.setEmployees(employees);

                        scheduleViews.add(view);
                    }
                }
            }

            // 将结果转换为JSON并返回
            String json = gson.toJson(scheduleViews);
            response.getWriter().write(json);

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"加载排班数据失败\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"无效的参数\"}");
        }
    }


}
