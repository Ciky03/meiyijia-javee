package cloud.ciky.module;

import java.util.List;

/**
 * @Author: ciky
 * @Description: 排班表展示实体类
 * @DateTime: 2024/11/23 14:16
 **/
public class ScheduleView {
    private int dayOfWeek;
    private String shiftType;
    private List<Employee> employees;


    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
