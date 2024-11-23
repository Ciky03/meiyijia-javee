package cloud.ciky.controller.employee;

import cloud.ciky.dao.EmployeeDao;
import cloud.ciky.module.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
 * @Description: 根据id获取员工信息Servlet
 * @DateTime: 2024/11/23 13:08
 **/
@WebServlet("/employee/get/*")
public class EmployeeGetServlet extends HttpServlet {
    private EmployeeDao employeeDao = new EmployeeDao();
    private Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try {
            // 从路径中获取员工ID
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.length() <= 1) {
                throw new ServletException("未指定员工ID");
            }

            int employeeId = Integer.parseInt(pathInfo.substring(1));
            Employee employee = employeeDao.getEmployeeById(employeeId);

            if (employee != null) {
                response.getWriter().write(gson.toJson(employee));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                Map<String, String> error = new HashMap<>();
                error.put("error", "员工不存在");
                response.getWriter().write(gson.toJson(error));
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> error = new HashMap<>();
            error.put("error", "无效的员工ID格式");
            response.getWriter().write(gson.toJson(error));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", "获取员工信息失败: " + e.getMessage());
            response.getWriter().write(gson.toJson(error));
        }
    }

}
