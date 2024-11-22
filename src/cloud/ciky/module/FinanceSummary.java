package cloud.ciky.module;

import java.math.BigDecimal;

/**
 * @Author: ciky
 * @Description: 财务概览实体类
 * @DateTime: 2024/11/22 21:52
 **/
public class FinanceSummary {
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;

    // Getters and Setters
    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(BigDecimal monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }
}
