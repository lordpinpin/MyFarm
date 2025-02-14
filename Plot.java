import exceptions.*;

/**
 * <p>
 * This class represents a plot that can have various actions done to it.
 * <p>
 * Plots can be plowed and then a Crop can be planted on them. Once a plot has a crop, it can be watered and fertilized
 * until it matures and becomes harvestable. After a Crop has been harvested, the plot reverts back
 * to being unplowed.
 * <p>
 * Plots can also be shovelled at any time, reverting it back to its unplowed state.
 * If a Crop withers, the plot will keep this withered plant until it is shovelled off.
 * <p>
 * Plots can also contain rocks, which cannot be plowed or be planted on. This can only be
 * removed by a pickaxe, and shovelling does not have an effect on it.
 */
public class Plot {
    private Crop crop = null;

    private boolean plowed = false;
    private boolean rock = false;
    private boolean wither = false;

    /**
     * Constructor for Plot.
     */
    public Plot(){
    }

    /**
     * Getter for Crop.
     * @return the Crop.
     */
    public Crop getCrop() {
        return crop;
    }

    /**
     * Getter for whether the plot has been plowed or not.
     * @return true if the plot is plowed, and false if not.
     */
    public boolean getPlow(){
        return plowed;
    }

    /**
     * Getter for whether the rock has been plowed or not.
     * @return true if the plot has a rock, and false if not.
     */
    public boolean getRock(){
        return rock;
    }

    /**
     * Setter for adding a Crop to the plot.
     * @param newCrop the Crop that will be added to the Plot.
     */
    public void setCrop(Crop newCrop){
        this.crop = newCrop;
    }

    /**
     * Adds water to the plot if and only if the plot is plowed and has a crop that is not mature yet.
     * @param day the current day in the Game
     * @return the appropriate error code: 6 if the plot is not plowed, 5 if the
     * plot has no crop in it, 11 if the crop is mature, 4 if the crop has withered and 0 if there is no error.
     */
    public void water(int day) throws PlotNotPlowedException, PlotUnoccupiedException, PlotAlreadyMaturedException, CropWitheredException {
        if (!plowed) throw new PlotNotPlowedException();
        if (crop == null) throw new PlotUnoccupiedException();
        if (crop.getHarvestStatus(day)) throw new PlotAlreadyMaturedException();
        if(witherCheck(day)) throw new CropWitheredException();

        crop.addWater();
    }
    /**
     * Adds fertilizer to the plot if and only if the plot is plowed and has a crop that is not mature yet.
     * @param day the current day in the Game
     * @return the appropriate error code: 6 if the plot is not plowed, 5 if the plot has no crop in it,
     * 11 if the crop is mature, 4 if the crop has withered and 0 if there is no error.
     */
    public void fertilize(int day) throws PlotNotPlowedException, PlotUnoccupiedException, PlotAlreadyMaturedException, CropWitheredException {
        if (!plowed) throw new PlotNotPlowedException();
        if (crop == null) throw new PlotUnoccupiedException();
        if (crop.getHarvestStatus(day)) throw new PlotAlreadyMaturedException();
        if (witherCheck(day)) throw new CropWitheredException();

        crop.addFertilizer();
    }

    /**
     * Plows the plot if and only if the plot is empty and has not been plowed.
     * @param day the current day of the Game
     * @return the appropriate error code: 4 if the plot has withered crop, 10 if the plot has a crop,
     * 12 if the plot has already been plowed without any plant, and 0 if there is no error.
     */
    public void plow(int day) throws CropWitheredException, PlotAlreadyOccupiedException, PlotAlreadyPlowedException {
        if (witherCheck(day)) throw new CropWitheredException();
        if (crop != null) throw new PlotAlreadyOccupiedException();
        if (plowed) throw new PlotAlreadyPlowedException();

        plowed = true;
    }

    /**
     * Removes a rock on the plot if there is one.
     * @return the appropriate error code: 0 if there is no error and 7 if the plot has no rock.
     */
    public void removeRock() throws NoRockException {
        if (rock) {
            rock = false;
        } else {
            throw new NoRockException();
        }
    }

    /**
     * Resets the plot. It becomes  unplowed, removes crops, removes wither status, and reverts water and
     * fertilizer amount to 0.
     */
    public void resetPlot(){
        plowed = false;
        crop = null;
        wither = false;
    }
    /**
     * Gets the profit from harvesting the crop.
     * @param waterMaxBonus the Farmer stat for an additional bonus in calculating additional profit from watering.
     * @param fertilizerMaxBonus the Farmer stat for an additional bonus in calculating additional profit from fertilizing.
     * @param bonusEarnings the Farmer stat for additional bonus to base price when calculating profit.
     * @return the profit gained from harvesting the crop in the plot.
     */

    public int getHarvestProfit(int waterMaxBonus, int fertilizerMaxBonus, int bonusEarnings){
        return crop.harvestCalculate(waterMaxBonus, fertilizerMaxBonus, bonusEarnings);
    }

    /**
     * Gets the amount of EXP that will be gained from harvesting the crop.
     * @return the amount of EXP that will be gained from harvesting the crop.
     */
    public double getHarvestExp(){
        return crop.getExp();
    }

    /**
     * Checks if the plot can be harvested.
     *
     * @param day the current day in the Game.
     * @return the appropriate error code: 5 if there is no crop, 4 if the crop has withered, 13 if
     * the crop cannot be harvested yet and 0 if there is no error.
     */
    public boolean harvestCheck(int day) throws PlotUnoccupiedException, CropWitheredException, CropNotMaturedException {
        if (crop == null) throw new PlotUnoccupiedException();
        if (witherCheck(day)) throw new CropWitheredException();
        if (!crop.getHarvestStatus(day)) throw new CropNotMaturedException();
        return true;
    }

    /**
     * Checks if the plot has a withered crop.
     * @param day the current day in the Game.
     * @return true if the plot has a wither crop, and false if not.
     */
    public boolean witherCheck(int day){
        if(wither){
            return true;
        }
        else if (crop != null && crop.witherCheck(day)){
            wither = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if the plot has a crop.
     * @return true if the plot has crop, and false if not.
     */

    public boolean cropCheck(){
        return crop != null;
    }

    /**
     * Checks if the plot can be planted on.
     *
     * @return the appropriate error code: 14 if there is a rock, 10 if the plot already has a crop,
     * 6 if the plot is not plowed and 0 if there is no error.
     */

    public boolean plantCheck() throws PlotHasRockException, PlotAlreadyOccupiedException, PlotNotPlowedException {
        if (rock) throw new PlotHasRockException();
        if (crop != null) throw new PlotAlreadyOccupiedException();
        if (!plowed) throw new PlotNotPlowedException();
        return true;
    }


    /**
     * Checks if the plot is empty (no rocks or no crops)
     * @return true if the plot is empty, and false if not.
     */
    public boolean emptyCheck(){
        return !(rock || crop != null);
    }

    /**
     * Finds appropriate character to represent status of the plot. Crops are represented as lowercase
     * if not mature yet and uppercase if the crop is harvestable.
     * <ul>
     *     <li> 0 - plot is not plowed
     *     <li> X - plot has rock
     *     <li> # - plot is plowed
     *     <li> @ - plot has withered crop
     *     <li> t or T - plot has turnip
     *     <li> c or C - plot has carrot
     *     <li> p or P - plot has potato
     *     <li> r or R - plot has rose
     *     <li> u or U - plot has turnips (flower)
     *     <li> s or S - plot has sunflower
     *     <li> m or M - plot has mango tree
     *     <li> a or A - plot has apple tree.
     * </ul>
     * @param day the current day in the Game
     * @return the character that represents the current status of the plot.
     */
    public char getCharStatus(int day){ // Text characters before GUI is implemented.
        char status;
        if(!plowed){
            return '0';
        }
        else if(rock){
            return 'X';
        }
        else if(crop == null){
            return '#';
        }
        if(!witherCheck(day)) {
            status = switch (crop.getName()) {
                case "Turnip" -> 't';
                case "Carrot" -> 'c';
                case "Potato" -> 'p';
                case "Rose" -> 'r';
                case "Sunflower" -> 's';
                case "Turnips" -> 'u';
                case "Mango" -> 'm';
                case "Apple" -> 'a';
                default -> 'e'; // ERROR
            };
            if(crop.getHarvestStatus(day)){
                status = Character.toUpperCase(status);
            }
            return status;
        }
        else {
            return '@';
        }
    }
}
