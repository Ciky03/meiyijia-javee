package cloud.ciky.module;

/**
 * @Author: ciky
 * @Description: 门店实体类
 * @DateTime: 2024/11/20 17:54
 **/
public class Store {
    private String id;
    private String name;
    private String address;
    private String manager;
    private String phone;
    private double sales;
    private String inventory;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public double getSales() { return sales; }
    public void setSales(double sales) { this.sales = sales; }

    public String getInventory() { return inventory; }
    public void setInventory(String inventory) { this.inventory = inventory; }
}
