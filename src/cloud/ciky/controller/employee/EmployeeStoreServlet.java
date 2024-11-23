package cloud.ciky.controller.employee;

import cloud.ciky.dao.EmployeeDao;
import cloud.ciky.module.Employee;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 根据门店id查询员工Servlet
 * @DateTime: 2024/11/23 13:48
 **/
@WebServlet("/employee/store")
public class EmployeeStoreServlet extends HttpServlet {
    private EmployeeDao employeeDao = new EmployeeDao();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try {
            // 获取门店ID参数
            String storeIdStr = request.getParameter("storeId");
            if (storeIdStr == null || storeIdStr.trim().isEmpty()) {
                throw new ServletException("未指定门店ID");
            }

            int storeId = Integer.parseInt(storeIdStr);

            // 获取该门店的所有在职员工
            List<Employee> employees = employeeDao.getEmployeesByStore(storeId);

            // 返回JSON数据
            response.getWriter().write(gson.toJson(employees));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("无效的门店ID格式");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("获取门店员工列表失败: " + e.getMessage());
        }
    }
}
