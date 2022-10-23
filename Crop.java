public class Crop {
    private String name;
    private String type;
    private int harvestTime;
    private int waterMin;
    private int waterMax;
    private int fertilizerMin;
    private int fertilizerMax;
    private int productMin;
    private int productMax;
    private int cost;
    private int price;
    private int exp;
    private int waterCount;
    private int fertilizerCount;

    public Crop(String name, String type, int harvestTime, int waterMin, int waterMax, int fertilizerMin, int fertilizerMax, int productMin, int productMax, int cost, int price, int exp) {
        this.name = name;
        this.type = type;
        this.harvestTime = harvestTime;
        this.waterMin = waterMin;
        this.waterMax = waterMax;
        this.fertilizerMin = fertilizerMin;
        this.fertilizerMax = fertilizerMax;
        this.productMin = productMin;
        this.productMax = productMax;
        this.cost = cost;
        this.price = price;
        this.exp = exp;
    }

    public String getType(){
        return type;
    }


}
