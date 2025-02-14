/**
 * <p>
 * This class represents the farmer who can perform actions in their farm. These various actions include:
 * Plowing, planting, watering, fertilizing, harvesting, registering, shoveling, and pickaxing. All of them
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
    public Farmer() {}

    /**
     * Gets the total amount of EXP earned.
     * @return the total amount of EXP the player has earned.
     */
    public double getTotalExp() {
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
     * @throws NotEnoughMoneyException if the Farmer does not have enough objectCoins.
     */
    public void checkCoins(int amount, boolean discount) throws NotEnoughMoneyException {
        int availableCoins = discount ? objectCoins - seedCostReduction : objectCoins;
        if (availableCoins < amount) {
            throw new NotEnoughMoneyException();
        }
    }

    /**
     * Checks if the player has 100 EXP to level up, and updates the level if so.
     * @return true if the player has leveled up, false otherwise.
     */
    public boolean levelCheck() {
        if (exp >= 100) {
            exp -= 100;
            level++;
            return true;
        }
        return false;
    }

    /**
     * Displays an update message if the player has leveled up.
     * @param leveledUp true if the player has leveled up, false otherwise.
     */
    public void levelUpdate(boolean leveledUp) {
        if (leveledUp) {
            System.out.println("\n  Level up! You are now level " + level + ".");
        }
    }

    /**
     * Checks if the player has the required level to register for the next title upgrade.
     * @return true if the next title can be registered, false otherwise.
     */
    public boolean registerCheck() {
        switch (type) {
            case "Farmer":
                return level >= 5;
            case "Registered Farmer":
                return level >= 10;
            case "Distinguished Farmer":
                return level >= 15;
            case "Legendary Farmer":
                return false; // Max title reached
            default:
                return false;
        }
    }


    /**
     * Displays the next available title upgrade the Farmer can register for based on their current title.
     */
    public void displayRegister() {
        switch (type) {
            case "Farmer" -> {
                System.out.println("  Upgrade to Registered Farmer:");
                System.out.println("  - Increases earnings by 1 per produce.");
                System.out.println("  - Reduces seed cost by 1.");
            }
            case "Registered Farmer" -> {
                System.out.println("  Upgrade to Distinguished Farmer:");
                System.out.println("  - Increases earnings by 1 per produce.");
                System.out.println("  - Reduces seed cost by another 1.");
                System.out.println("  - Increases maximum water bonus by 1.");
            }
            case "Distinguished Farmer" -> {
                System.out.println("  Final upgrade to Legendary Farmer:");
                System.out.println("  - Increases earnings by 2 per produce.");
                System.out.println("  - Reduces seed cost by another 1.");
                System.out.println("  - Increases max water & fertilizer bonuses by 1.");
            }
            case "Legendary Farmer" -> {
                System.out.println("  You have already reached the maximum title.");
            }
        }
    }


    /**
     * Checks if the player has the levels required for each title upgrade.
     * @throws InvalidInputException if the player cannot register for a new title.
     */
    public void checkRegisterEligibility() throws InvalidInputException {
        switch (type) {
            case "Farmer":
                if (level < 5) throw new InvalidInputException();
                break;
            case "Registered Farmer":
                if (level < 10) throw new InvalidInputException();
                break;
            case "Distinguished Farmer":
                if (level < 15) throw new InvalidInputException();
                break;
            case "Legendary Farmer":
                throw new InvalidInputException();
        }
    }

    /**
     * Plows a Plot if it can. The Farmer gains 0.5 EXP if they successfully plow a Plot.
     * @param plot the unplowed Plot that will be plowed.
     * @param day the current day in the Game.
     * @throws PlotAlreadyPlowedException if the Plot is already plowed.
     */
    public void plow(Plot plot, int day) throws PlotAlreadyPlowedException {
        plot.plow(day);
        exp += 0.5;
    }

    /**
     * Plants a Crop in a Plot. The Farmer uses a certain amount of objectCoins.
     * @param plot the Plot to be planted on.
     * @param crop the Crop to be planted in the Plot.
     * @throws NotEnoughMoneyException if the Farmer does not have enough coins.
     */
    public void plant(Plot plot, Crop crop) throws NotEnoughMoneyException {
        checkCoins(crop.getCost(), true);
        plot.setCrop(crop);
        objectCoins -= crop.getCost() - seedCostReduction;
    }

    /**
     * Waters a Plot with a Crop if it can. The Farmer gains 0.5 EXP if they successfully water a Plot.
     * @param plot the Plot with the Crop that will be watered.
     * @param day the current day in the Game.
     * @throws NoCropInPlotException if there is no crop to water.
     */
    public void water(Plot plot, int day) throws NoCropInPlotException {
        plot.water(day);
        exp += 0.5;
    }

    /**
     * Fertilizes a Plot with a Crop if it can. It requires 10 objectCoins and will net the Farmer 4
     * EXP if they successfully fertilize a Plot.
     * @param plot the Plot with the Crop that will be fertilized.
     * @param day the current day in the Game.
     * @throws NotEnoughMoneyException if the Farmer does not have enough objectCoins.
     * @throws NoCropInPlotException if there is no crop to fertilize.
     */
    public void fertilize(Plot plot, int day) throws NotEnoughMoneyException, NoCropInPlotException {
        checkCoins(10, false);
        plot.fertilize(day);
        objectCoins -= 10;
        exp += 4;
    }

    /**
     * Harvests a Plot with a mature Crop if it can. Successfully doing so will net the Farmer a
     * certain amount of objectCoins and EXP based on the Crop harvested.
     * @param plot the Plot with the mature Crop that will be harvested.
     * @param day the current day in the Game.
     * @throws CropNotMatureException if the crop is not mature yet.
     */
    public void harvest(Plot plot, int day) throws CropNotMatureException {
        plot.harvestCheck(day);
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
     * Gets the cost of upgrading to the next title for registering.
     * The following is a list of the costs of the upgrades:
     * <ul>
     *     <li>200 for upgrading to Registered Farmer.
     *     <li>300 for upgrading to Distinguished Farmer.
     *     <li>400 for upgrading to Legendary Farmer.
     * </ul>
     * @return the appropriate cost for the next title upgrade.
     */
    public int getRegisterCost() {
        switch(type) {
            case "Farmer":
                return 200;
            case "Registered Farmer":
                return 300;
            case "Distinguished Farmer":
                return 400;
            case "Legendary Farmer":
                System.out.println("  You have reached the maximum title available.");
                return 0;
            default:
                return 0;
        }
    }


    /**
     * Upgrades the Farmer's current title to the next tier if they have enough objectCoins.
     * @throws NotEnoughMoneyException if the Farmer does not have enough objectCoins.
     */
    public void register() throws NotEnoughMoneyException {
        checkCoins(getRegisterCost(), false);
        objectCoins -= getRegisterCost();

        switch (type) {
            case "Farmer":
                bonusEarnings = 1;
                seedCostReduction = 1;
                type = "Registered Farmer";
                break;
            case "Registered Farmer":
                bonusEarnings = 2;
                seedCostReduction = 2;
                waterMaxBonus = 1;
                type = "Distinguished Farmer";
                break;
            case "Distinguished Farmer":
                bonusEarnings = 4;
                seedCostReduction = 3;
                waterMaxBonus = 2;
                fertilizerMaxBonus = 1;
                type = "Legendary Farmer";
                break;
        }
    }

    /**
     * Shovels a Plot. Requires 7 objectCoins and will net the Farmer 2 EXP.
     * @param plot the Plot that will be shoveled.
     * @throws NotEnoughMoneyException if the Farmer does not have enough objectCoins.
     */
    public void shovel(Plot plot) throws NotEnoughMoneyException {
        checkCoins(7, false);
        objectCoins -= 7;
        exp += 2;
        plot.resetPlot();
    }

    /**
     * Pickaxes a rock on a Plot. Requires 50 objectCoins and will net the Farmer 15 EXP.
     * @param plot the Plot with a rock to be removed.
     * @throws NotEnoughMoneyException if the Farmer does not have enough objectCoins.
     * @throws NoRockInPlotException if the Plot does not have a rock.
     */
    public void pickaxe(Plot plot) throws NotEnoughMoneyException, NoRockInPlotException {
        checkCoins(50, false);
        plot.removeRock();
        objectCoins -= 50;
        exp += 15;
    }
}
