package cloud.ciky.module;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: ciky
 * @Description: 员工实体类
 * @DateTime: 2024/11/23 11:42
 **/
public class Employee {
     private Integer id;
    private String employeeNo;
    private String name;
    private String phone;
    private Integer storeId;
    private String storeName;
    private Date hireDate;
    private Integer status;

     // 添加一个获取格式化日期的方法，用于前端显示
//    public String getDate() {
//        if (hireDate != null) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            return sdf.format(hireDate);
//        }
//        return null;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
