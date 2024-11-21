package cloud.ciky.module;

import java.util.Date;

/**
 * @Author: ciky
 * @Description: 门店库存实体类
 * @DateTime: 2024/11/21 21:34
 **/
public class StoreInventory {
    private Integer storeId;
    private Integer itemId;
    private String itemName;  // 用于显示商品名称
    private Integer quantity;
    private Integer warningThreshold;
    private Date lastUpdate;

    // Getters and Setters
    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getWarningThreshold() {
        return warningThreshold;
    }

    public void setWarningThreshold(Integer warningThreshold) {
        this.warningThreshold = warningThreshold;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
