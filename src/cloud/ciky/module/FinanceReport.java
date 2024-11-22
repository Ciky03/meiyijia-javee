package cloud.ciky.module;

import java.math.BigDecimal;

/**
 * @Author: ciky
 * @Description: 月度报表实体类
 * @DateTime: 2024/11/22 21:46
 **/
public class FinanceReport {
    private String month;
    private BigDecimal income;
    private BigDecimal expense;

    // Getters and Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }
}
