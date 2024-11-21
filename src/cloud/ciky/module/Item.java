package cloud.ciky.module;

import java.util.Date;

/**
 * @Author: ciky
 * @Description: 商品实体类
 * @DateTime: 2024/11/21 21:20
 **/
public class Item {
    private Integer id;
    private String name;
    private Date createTime;
    private Date updateTime;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
