package cloud.ciky.controller.finance;

import cloud.ciky.dao.FinanceCategoryDao;
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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ciky
 * @Description:
 * @DateTime: 2024/11/22 22:04
 **/
@WebServlet("/finance/save")
public class FinanceSaveServlet extends HttpServlet {
    private Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false); // 开启事务

            try {
                // 获取请求参数
                int storeId = Integer.parseInt(request.getParameter("storeId"));
                String type = request.getParameter("type");
                BigDecimal amount = new BigDecimal(request.getParameter("amount"));
                String categoryName = request.getParameter("category");
                LocalDate recordDate = LocalDate.parse(request.getParameter("date"));
                String remark = request.getParameter("remark");

                // 根据分类名称获取分类ID
                FinanceCategoryDao categoryDAO = new FinanceCategoryDao(conn);
                int categoryId = categoryDAO.getCategoryIdByName(categoryName);

                if (categoryId == 0) {
                    throw new SQLException("无效的分类名称");
                }

                // 创建财务记录
                FinanceRecord record = new FinanceRecord();
                record.setStoreId(storeId);
                record.setType(type);
                record.setAmount(amount);
                record.setCategoryId(categoryId);
                record.setDate(java.sql.Date.valueOf(recordDate));
                record.setRemark(remark);

                // 保存记录
                FinanceRecordDao recordDAO = new FinanceRecordDao(conn);
                int recordId = recordDAO.insert(record);

                conn.commit(); // 提交事务

                result.put("success", true);
                result.put("message", "保存成功");
                result.put("recordId", recordId);

            } catch (Exception e) {
                conn.rollback(); // 发生异常时回滚事务
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "保存失败：" + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.getWriter().write(gson.toJson(result));
    }
}
