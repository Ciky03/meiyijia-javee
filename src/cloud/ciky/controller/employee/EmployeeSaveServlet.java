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
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ciky
 * @Description: 新增/编辑员工信息Servlet
 * @DateTime: 2024/11/23 13:32
 **/
@WebServlet("/employee/save/*")
public class EmployeeSaveServlet extends HttpServlet {
    private EmployeeDao employeeDao = new EmployeeDao();
    private Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .create();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleSave(request, response, null);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "未指定员工ID");
            return;
        }
        int employeeId = Integer.parseInt(pathInfo.substring(1));
        handleSave(request, response, employeeId);
    }

    private void handleSave(HttpServletRequest request, HttpServletResponse response, Integer employeeId)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try {
            // 读取请求体
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // 解析JSON数据
            Map<String, String> formData = gson.fromJson(sb.toString(), Map.class);

            // 创建员工对象
            Employee employee = new Employee();
            if (employeeId != null) {
                employee.setId(employeeId);
            }
            employee.setName(formData.get("name"));
            employee.setPhone(formData.get("phone"));
            employee.setStoreId(Integer.parseInt(formData.get("storeId")));

            // 解析日期
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(formData.get("date"));
            employee.setHireDate(new java.sql.Date(parsedDate.getTime()));

            // 如果是新员工，生成工号
            if (employeeId == null) {
                employee.setEmployeeNo(generateEmployeeNo());
            }

            // 保存到数据库
            boolean success;
            if (employeeId != null) {
                success = employeeDao.updateEmployee(employee);
            } else {
                success = employeeDao.insertEmployee(employee);
            }

            if (success) {
                Map<String, Object> result = new HashMap<>();
                result.put("success", true);
                result.put("message", employeeId == null ? "添加成功" : "更新成功");
                response.getWriter().write(gson.toJson(result));
            } else {
                sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "保存失败");
            }

        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "保存员工数据失败: " + e.getMessage());
        }
    }

    private void sendError(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        response.getWriter().write(gson.toJson(error));
    }

    private String generateEmployeeNo() {
        // 生成工号：年月日+4位序号，如：202312250001
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePrefix = dateFormat.format(new java.util.Date());
        String sequence = String.format("%04d", employeeDao.getNextEmployeeSequence());
        return "E"+datePrefix + sequence;
    }
}
