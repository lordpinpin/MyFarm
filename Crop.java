import java.util.Objects;

import exceptions.PlotUnoccupiedException;

/**
 * <p>
 * This class represents the crops that will be planted and harvested from plots.
 * <p>
 * Crops can be harvested when enough days have passed. A formula is used to calculate
 * how much money a harvested crop yields.
 * <p>
 * Crops will wither if the harvest date passes, or that a crop is not watered and fertilized
 * enough when harvesting time arrives.
 */

public class Crop {
    private final String name;
    private final String type;
    private final int date;
    private final int harvestTime;
    private final int waterMin;
    private final int waterMax;
    private final int fertilizerMin;
    private final int fertilizerMax;
    private final int productMin;
    private final int productMax;
    private final int cost;
    private final int price;
    private final double exp;
    private int waterAmount = 0;
    private int fertilizerAmount = 0;

    /**
     * Constructor for Crop with all variables.
     * @param name the name of crop.
     * @param type the type of crop.
     * @param date the day when crop was planted.
     * @param harvestTime the amount of days to pass before crop becomes harvestable.
     * @param waterMin the minimum amount of water needed.
     * @param waterMax the maximum amount of water counted for calculation.
     * @param fertilizerMin the minimum amount of fertilizer needed.
     * @param fertilizerMax the maximum amount of fertilizer counted for calculation.
     * @param productMin the minimum amount of product/s crop can produce
     * @param productMax the maximum amount of product/s crop can produce
     * @param cost the cost of buying crop.
     * @param price the base selling price of crop.
     * @param exp the EXP gain from harvesting crop.
     */
    public Crop(String name, String type, int date, int harvestTime, int waterMin, int waterMax, int fertilizerMin, int fertilizerMax, int productMin, int productMax, int cost, int price, double exp) {
        this.name = name;
        this.type = type;
        this.date = date;
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

    /**
     * Getter of name.
     * @return the String of name.
     */
    public String getName(){
        return name;
    }

    /**
     * Getter of crop cost.
     * @return the cost of crop.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Getter of the EXP gain from crop.
     * @return the EXP gain from crop.
     */
    public double getExp(){
        return exp;
    }

    /**
     * Calculates if crop has matured based on date planted compared to current date.
     * @param day the current day in the Game.
     * @return true if crop has matured, false if not.
     */
    public boolean getHarvestStatus(int day){
        return day - date == harvestTime;
    }

    /**
     * Increments water amount of Crop by 1.
     * @throws PlotUnoccupiedException 
     */
    public void addWater() throws PlotUnoccupiedException{
        waterAmount += 1;
    }

    /**
     * Increments fertilizer amount of Crop by 1.
     * @throws PlotUnoccupiedException 
     */
    public void addFertilizer() throws PlotUnoccupiedException{
        fertilizerAmount += 1;
    }

    /**
     * Checks if crop has withered based on date planted compared to current date.
     * A crop has withered if it has not been watered or fertilized enough at harvest time
     * or when the harvest time has passed.
     * @param day the current day in the Game.
     * @return true if crop has withered, false if not.
     */
    public boolean isWithered(int day){
        if (day - date == harvestTime){
            return waterMin > waterAmount || fertilizerMin > fertilizerAmount;
        }
        else return day - date > harvestTime;
    }

    /**
     * Calculates the total objectCoins earned from harvesting and selling crop.
     * <p>
     * The following formula is used to calculate the price:
     * <ul>
     *     <li>HarvestTotal = ProductsProduced x (BaseSellingPricePerPiece + FarmerTypeEarningBonus)</li>
     *     <li>WaterBonus = HarvestTotal x 0.2 x (TimesCropWasWatered – 1)</li>
     *     <li>FertilizerBonus = HarvestTotal x 0.5 x TimesCropAddedFertilizer</li>
     *     <li>FinalHarvestPrice = HarvestTotal + WaterBonus + FertilizerBonus</li>
     * </ul>
     * If the plant is also a flower, the final total is also multiplied by 1.1.
     * @param waterMaxBonus the Farmer stat for an additional bonus in calculating additional profit from watering.
     * @param fertilizerMaxBonus the Farmer stat for an additional bonus in calculating additional profit from fertilizing.
     * @param bonusEarnings the Farmer stat for additional bonus to base price when calculating profit.
     * @return the total amount of objectCoins gained from harvesting the crop based on the formula.
     */

    public int harvestCalculate(int waterMaxBonus, int fertilizerMaxBonus, int bonusEarnings){

        int productsProduced = (int)Math.floor(Math.random()*(productMax - productMin + 1) + productMin);
        int harvestTotal = productsProduced * (price + bonusEarnings);
        int waterBonus = Math.round(harvestTotal * (float)0.2 * (Math.min(waterAmount, waterMax + waterMaxBonus) - 1));
        int fertilizerBonus = Math.round(harvestTotal * (float)0.5 * (Math.min(fertilizerAmount, fertilizerMax + fertilizerMaxBonus)));
        int finalHarvestPrice = harvestTotal + waterBonus + fertilizerBonus;
        System.out.println();
        System.out.println("  Amount of " + name + " made: " + productsProduced);
        System.out.println("  Harvest price total: " + harvestTotal);
        System.out.println("  Water bonus: " + waterBonus);
        System.out.println("  Fertilizer bonus: " + fertilizerBonus);
        if (Objects.equals(type, "Flower")){
            return Math.round(finalHarvestPrice * (float)1.1);
        }
        return finalHarvestPrice;
    }



}
