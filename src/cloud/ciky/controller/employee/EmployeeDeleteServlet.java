package cloud.ciky.controller.employee;

import cloud.ciky.dao.EmployeeDao;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ciky
 * @Description: 删除员工Servlet
 * @DateTime: 2024/11/23 13:41
 **/
@WebServlet("/employee/delete/*")
public class EmployeeDeleteServlet extends HttpServlet {
     private EmployeeDao employeeDao = new EmployeeDao();
    private Gson gson = new Gson();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try {
            // 从路径中获取员工ID
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.length() <= 1) {
                throw new ServletException("未指定员工ID");
            }

            int employeeId = Integer.parseInt(pathInfo.substring(1));

            // 检查是否存在未完成的排班
            if (employeeDao.hasActiveSchedule(employeeId)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("无法删除该员工，请先删除该员工的排班信息");
                return;
            }

            // 执行逻辑删除
            boolean success = employeeDao.deleteEmployee(employeeId);

            if (success) {
                Map<String, Object> result = new HashMap<>();
                result.put("success", true);
                result.put("message", "删除成功");
                response.getWriter().write(gson.toJson(result));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("员工不存在或已被删除");
            }

        } catch (NumberFormatException e) {
             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("无效的员工ID格式");
        } catch (Exception e) {
             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write( "删除员工失败: " + e.getMessage());
        }
    }
}
