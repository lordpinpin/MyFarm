import exceptions.*;

/**
 * <p>
 * This class represents the farmer who can perform actions in their farm. These various actions include:
 * Plowing, planting, watering, fertilizing, harvesting, registering, shovelling and pickaxing. All of them
 * have a set cost and some have a set amount of EXP gain from doing these actions.
 * <p>
 * To pay for these, Farmers have coins that they can spend on certain actions. These actions will usually
 * affect a certain Plot or the Crop that has already been planted on said Plot.
 * <p>
 * Farmers also have a leveling system where every 100 EXP, they can gain a level. Every 5 levels up to 15,
 * they can gain an upgrade by registering for a new title with a cost, which will give them special
 * bonuses for doing so.
 */
public class Farmer {
    private double exp = 0;
    private int level = 5;
    private int objectCoins = 500;
    private String type = "Farmer";
    private int bonusEarnings = 0;
    private int seedCostReduction = 0;
    private int waterMaxBonus = 0;
    private int fertilizerMaxBonus = 0;

    /**
     * Constructor of Farmer.
     */
    public Farmer(){}

    /**
     * Gets the total amount of EXP earned.
     * @return the total amount of EXP the player has earned.
     */
    public double getTotalExp(){
        return ((level - 1) * 100) + exp;
    }

    /**
     * Gets the current level of the Farmer
     * @return the current level of the Farmer
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the current amount of objectCoins that the Farmer has
     * @return the current amount of objectCoins that the Farmer has
     */
    public int getObjectCoins() {
        return objectCoins;
    }

    /**
     * Gets the current title of the Farmer
     * @return the current title of the Farmer
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the seed cost reduction gained from registering for better titles.
     * @return the seed cost reduction the Farmer has for purchasing Crops.
     */
    public int getSeedCostReduction() {
        return seedCostReduction;
    }

    /**
     * Checks whether the Farmer has enough coins for an action. The seed cost reduction is considered
     * if buying Crops.
     * @param amount the amount of coins needed.
     * @param discount whether the seed cost reduction needs to be taken into account.
     * @return true if the Farmer has enough coins and false if not.
     */
    public boolean coinCheck(int amount, boolean discount){
        if (discount){
            return objectCoins - seedCostReduction >= amount;
        }
        else {
            return objectCoins >= amount;
        }
    }

    /**
     * Checks if the player has 100 EXP to level up, and updates the level if so.
     * @return true if the player has enough EXP, and false if not.
     */
    public boolean levelCheck(){
        if(exp >= 100){
            exp -= 100;
            level++;
            return true;
        }
        return false;
    }

    /**
     * Displays an update that the player has levelled up if activated.
     * @param update true if the player has levelled and false if not.
     */

    public void levelUpdate(boolean update){
        if (update) {
            System.out.println();
            System.out.println("  Level up! You are now level " + level + ".");
        }
    }

    /**
     * Checks if the player has the levels required for each title upgrade.
     * @return true if the next title can be registered and false if not.
     */
    public boolean registerCheck(){
        switch(type){
            case "Farmer":
                return level >= 5;
            case "Registered Farmer":
                return level >= 10;
            case "Distinguished Farmer":
                return level >= 15;
            case "Legendary Farmer":
                return false;
            default:
                System.out.println("  Error in registerCheck!");
                return false;
        }
    }

    /**
     * Plows a Plot if it can. The Farmer gains 0.5 EXP it they successfully plow a Plot.
     * @param plot the unplowed Plot that will be plowed.
     * @param day the current day in the Game.
     * @return the error code received from doing the action.
     */
    public void plow(Plot plot, int day) throws CropWitheredException, PlotAlreadyPlowedException, PlotAlreadyOccupiedException {
        plot.plow(day);
        exp += 0.5;
    }

    /**
     * Plants a Crop in a Plot. The Farmer uses a certain amount of objectCoins and gains a certain
     * @param plot the Plot to be planted on.
     * @param crop the Crop to be planted in the Plot.
     */
    public void plant(Plot plot, Crop crop){
        plot.setCrop(crop);
        objectCoins -= crop.getCost() - seedCostReduction;
    }

    /**
     * Waters a Plot with a Crop if it can. The Farmer gains 0.5 EXP it they successfully water a Plot.
     * @param plot the Plot with the Crop that will be watered.
     * @param day the current day in the Game.
     * @return the error code received from doing the action.
     */
    public void water(Plot plot, int day) throws CropWitheredException, PlotUnoccupiedException, PlotNotPlowedException, PlotAlreadyMaturedException {
        plot.water(day);
        exp += 0.5;
    }

    /**
     * Fertilizes a Plot with a Crop if it can. It requires 10 objectCoins and will net the Farmer 4
     * EXP if they successfully fertilize a Plot.
     * @param plot the Plot with the Crop that will be fertilized.
     * @param day the current day in the Game.
     * @return the error code received from doing the action.
     */
    public void fertilize(Plot plot, int day) throws CropWitheredException, PlotUnoccupiedException, PlotNotPlowedException, PlotAlreadyMaturedException {
        plot.fertilize(day);
        objectCoins -= 10;
        exp += 4;
    }

    /**
     * Harvests a Plot with a mature Crop if it can. Successfully doing so will net the Farmer a
     * certain amount of objectCoins and EXP based on the Crop harvested.
     * @param plot the Plot with the mature Crop that will be harvested.
     * @param day the current day in the Game.
     * @return the error code received from doing the action.
     */
    public void harvest(Plot plot, int day){
        try {
            plot.harvestCheck(day);
        } catch (PlotUnoccupiedException | CropWitheredException | CropNotMaturedException e) {
            System.err.println("Error: " + e.getMessage());
        }

        int profit = plot.getHarvestProfit(waterMaxBonus, fertilizerMaxBonus, bonusEarnings);
        double expGain = plot.getHarvestExp();
        objectCoins += profit;
        exp += expGain;
        System.out.println("  Amount of objectCoins gained: " + profit);
        System.out.println("  Amount of EXP gained: " + expGain);
        System.out.println();
        plot.resetPlot();
    }
    /**
     * Displays the next available title upgrade the Farmer can register for based on the Farmer's
     * current title.
     */
    public void displayRegister(){
        System.out.println("  The next upgrade costs " + getRegisterCost() + " objectCoins.");
        switch (type) {
            case "Farmer" -> {
                System.out.println();
                System.out.println("  The upgrade to become a Registered Farmer.");
                System.out.println("  It will increase earnings by 1 per produce and reduce seed cost by 1.");
            }
            case "Registered Farmer" -> {
                System.out.println();
                System.out.println("  The upgrade to become a Distinguished Farmer.");
                System.out.println("  It will increase earnings by 1 per produce and reduce seed cost by another 1.");
                System.out.println("  It will also increase maximum water bonus by 1.");
            }
            case "Distinguished Farmer" -> {
                System.out.println();
                System.out.println("  The final upgrade to become a Legendary Farmer.");
                System.out.println("  It will increase earnings by 2 per produce and reduce seed cost by another 1.");
                System.out.println("  It will also increase maximum water and fertilizer bonus by 1.");
            }
        }
    }
    /**
     * Upgrades the Farmer's current title to the next tier if the Farmer has enough objectCoins.
     * These can increase the Farmer's bonuses for watering/fertilizing, provide a discount in buying crops or
     * bring additional bonus earnings from each Crop produced.
     * @return the error code received from doing the action.
     */
    public void register() throws CannotAffordException {
        switch(type){
            case "Farmer":
                if(objectCoins >= 200){
                    objectCoins -= 200;
                    bonusEarnings = 1;
                    seedCostReduction = 1;
                    type = "Registered Farmer";
                }
                else throw new CannotAffordException();

            case "Registered Farmer":
                if(objectCoins >= 300){
                    objectCoins -= 300;
                    bonusEarnings = 2;
                    seedCostReduction = 2;
                    waterMaxBonus = 1;
                    type = "Distinguished Farmer";
                }
                else throw new CannotAffordException();

            case "Distinguished Farmer":
                if(objectCoins >= 400){
                    objectCoins -= 400;
                    bonusEarnings = 4;
                    seedCostReduction = 3;
                    waterMaxBonus = 2;
                    fertilizerMaxBonus = 1;
                    type = "Legendary Farmer";
                }
                else throw new CannotAffordException();

            default:
                System.out.println("  Error in register().");
                break;
        }
    }

    /**
     * Gets the cost of upgrading to the next title for registering.
     * The following is list of the costs of the upgrades:
     * <ul>
     *     <li>200 for upgrading to Registered Farmer.
     *     <li>300 for upgrading to Distinguished Farmer.
     *     <li>400 for upgrading to Legendary Farmer.
     * @return the appropriate cost for the next title upgrade.
     */
    public int getRegisterCost(){
        switch(type){
            case "Farmer":
                return 200;
            case "Registered Farmer":
                return 300;
            case "Distinguished Farmer":
                return 400;
            case "Legendary Farmer":
                System.out.println("  You have reached the maximum title available.");
        }
        return 0;
    }

    /**
     * Shovels a Plot. It will remove anything on the Plot both withered and unwithered Crops and removes
     * the plowing on the Plot. There are no restrictions to plowing, but it will not result in any change
     * if done on an empty unplowed Plot or on a Plot with a rock.
     * It requires 7 objectCoins and will net the Farmer 2 EXP doing so.
     * @param plot the Plot with the Crop that will be fertilized.
     * @return the error code received from doing the action.
     */
    public void shovel(Plot plot) throws CannotAffordException {
        if (objectCoins > 7) {
            objectCoins -= 7;
            exp += 2;
            plot.resetPlot();
        }
        else throw new CannotAffordException();
    }
    /**
     * Pickaxes a rock on a Plot with one. It requires 50 objectCoins and will net the Farmer 15
     * EXP if they successfully remove a rock from a Plot.
     * @param plot the Plot with the Crop that will be fertilized.
     * @return the error code received from doing the action.
     */
    public void pickaxe(Plot plot) throws NoRockException, CannotAffordException {
        if (objectCoins > 50) {
            plot.removeRock();
                objectCoins -= 50;
                exp += 15;
        }
        else {
            throw new CannotAffordException();
        }
    }
}
