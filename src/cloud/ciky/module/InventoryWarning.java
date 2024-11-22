package cloud.ciky.module;

/**
 * @Author: ciky
 * @Description: 库存警告实体类
 * @DateTime: 2024/11/22 13:55
 **/
public class InventoryWarning {
    private int id;
    private String name;
    private int quantity;
    private int threshold;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
