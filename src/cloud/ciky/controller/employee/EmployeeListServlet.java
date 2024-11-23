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
import java.util.List;
import java.util.Map;

/**
 * @Author: ciky
 * @Description: 查询员工信息Servlet
 * @DateTime: 2024/11/23 11:41
 **/
@WebServlet("/employee/list")
public class EmployeeListServlet extends HttpServlet {
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    private EmployeeDao employeeDao = new EmployeeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应类型
        response.setContentType("application/json;charset=UTF-8");

        try {
            // 获取分页参数
            int page = Integer.parseInt(request.getParameter("page"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            String searchTerm = request.getParameter("search");

            // 计算偏移量
            int offset = (page - 1) * pageSize;

            // 查询数据
            List<Employee> employees = employeeDao.getEmployees(offset, pageSize, searchTerm);
            int total = employeeDao.getTotalEmployees(searchTerm);

            // 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("data", employees);
            result.put("total", total);

            // 返回JSON数据
            response.getWriter().write(gson.toJson(result));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", "获取员工列表失败: " + e.getMessage());
            response.getWriter().write(gson.toJson(error));
        }
    }

}
