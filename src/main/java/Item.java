public class Item {
    private String itemName;
    private Boolean status;

    public Item(){
        this("Default Item", false);
    }

    public Item(String itemName, Boolean status) {
        this.itemName = itemName;
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}