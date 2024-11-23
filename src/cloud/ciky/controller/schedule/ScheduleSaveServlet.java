package cloud.ciky.controller.schedule;

import cloud.ciky.dao.ScheduleDao;
import cloud.ciky.module.Schedule;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * @Author: ciky
 * @Description: 保存排班Servlet
 * @DateTime: 2024/11/23 14:35
 **/
@WebServlet("/schedule/save")
public class ScheduleSaveServlet extends HttpServlet {
    private ScheduleDao scheduleDao = new ScheduleDao();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        try {
            // 读取请求体中的JSON数据
            String jsonData = request.getReader().lines().collect(Collectors.joining());
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);

            // 解析JSON数据
            int storeId = jsonObject.get("storeId").getAsInt();
            int weekNum = jsonObject.get("weekNum").getAsInt();
            int dayOfWeek = jsonObject.get("dayOfWeek").getAsInt();
            String shiftType = jsonObject.get("shiftType").getAsString();
            JsonArray employeeIds = jsonObject.get("employeeIds").getAsJsonArray();

            // 为每个选中的员工创建排班记录
            for (int i = 0; i < employeeIds.size(); i++) {
                int employeeId = employeeIds.get(i).getAsInt();

                Schedule schedule = new Schedule();
                schedule.setStoreId(storeId);
                schedule.setEmployeeId(employeeId);
                schedule.setWeekNumber(weekNum);
                schedule.setDayOfWeek(dayOfWeek);
                schedule.setShiftType(shiftType);

                scheduleDao.saveSchedule(schedule);
            }

            // 返回成功响应
            response.getWriter().write("{\"success\": true, \"message\": \"排班保存成功\"}");

        } catch (SQLException e) {
            // 数据库操作失败
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                "{\"success\": false, \"message\": \"保存失败: " + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            // 其他错误（如JSON解析错误）
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(
                "{\"success\": false, \"message\": \"请求数据无效: " + e.getMessage() + "\"}"
            );
        }
    }
}
