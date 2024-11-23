package cloud.ciky.dao;

import cloud.ciky.module.Schedule;
import cloud.ciky.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ciky
 * @Description: 排班表Dao
 * @DateTime: 2024/11/23 14:01
 **/
public class ScheduleDao {
public List<Schedule> getSchedules(int storeId, int weekNumber, int dayOfWeek, String shiftType)
            throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedule WHERE store_id = ? AND week_number = ? " +
                    "AND day_of_week = ? AND shift_type = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, storeId);
            stmt.setInt(2, weekNumber);
            stmt.setInt(3, dayOfWeek);
            stmt.setString(4, shiftType);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(rs.getInt("id"));
                schedule.setStoreId(rs.getInt("store_id"));
                schedule.setEmployeeId(rs.getInt("employee_id"));
                schedule.setWeekNumber(rs.getInt("week_number"));
                schedule.setDayOfWeek(rs.getInt("day_of_week"));
                schedule.setShiftType(rs.getString("shift_type"));
                schedules.add(schedule);
            }
        }

        return schedules;
    }
}
