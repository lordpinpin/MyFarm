/**
 * This represents the entire farm, which contains the various Plots that the Farmer will be acting on.
 */
public class Farm {

    private Plot[][] plots;

    /**
     * Constructor for Farm.
     */
    public Farm(){
        initializePlots();
    }

    /**
     * Gets the Plot at the coordinate (x, y) specified.
     * @param x the row of the Plot.
     * @param y the column of the Plot.
     * @return the Plot at the coordinate (x, y) specified.
     */
    public Plot getPlot(int x, int y){
        return plots[x][y];
    }

    /**
     * Checks if the coordinates match with a Plot in the farm.
     * @param x the row to be checked.
     * @param y the column to be checked.
     * @return true if it is a valid coordinate and false if not.
     */
    public boolean isValidPlot(int x, int y){
        return x >= 0 && y >= 0 && x <= plots.length && y <= plots[x].length;
    }

    /**
     * Initializes the needed Plots for the Farm.
     */
    public void initializePlots(){ // Will be expanded to 5 x 10 in final project
        plots = new Plot[1][1];
        plots[0][0] = new Plot();
    }



    /**
     * Checks if there is a single Plot in the entire farm that can be plowed.
     * @return true if it is a Plot to be plowed and false if not.
     */
    public boolean plowedFarmCheck(){
        for (Plot[] plot : plots) {
            for (Plot value : plot) {
                if (!value.getPlow()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if there is a rock on the farm.
     * @return true if there is a rock and false if not.
     */
    public boolean rockFarmCheck(){
        for (Plot[] plot : plots) {
            for (Plot value : plot) {
                if (value.getRock()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a plantable Plot on the farm.
     * @return true if there is a plantable Plot and false if not.
     */
    public boolean plantFarmCheck() {
        for (Plot[] plot : plots) {
            for (Plot value : plot) {
                if (value.plantCheck() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks the adjacent Plots of the given coordinates if they are all empty.
     * @param x the row to be checked.
     * @param y the column to be checked.
     * @return true if all adjacent Plots are empty and false if not.
     */
    public boolean adjacentPlotCheck(int x, int y){ // Does not apply in prototype as only one plot exists at the moment.
        if(x - 1 >= 0 && y - 1 >= 0 && plots[x - 1][y - 1].emptyCheck()) { // Top left
            return false;
        }
        if(y - 1 >= 0 && plots[x][y - 1].emptyCheck()){ // Top middle
            return false;
        }
        if(y - 1 >= 0 && x + 1 < plots[y - 1].length && plots[x + 1][y - 1].emptyCheck()){ // Top right
            return false;
        }
        if(x - 1 >= 0 && plots[x - 1][y].emptyCheck()){
            return false;
        }
        if(plots[x][y].emptyCheck()){
            return false;
        }
        if(x + 1 < plots[y].length && plots[x + 1][y].emptyCheck()){
            return false;
        }
        if(x - 1 >= 0 && y + 1 < plots.length && plots[x - 1][y + 1].emptyCheck()){
            return false;
        }
        if(y + 1 < plots.length && plots[x][y + 1].emptyCheck()){
            return false;
        }
        if(y + 1 < plots.length && x + 1 < plots[y + 1].length && plots[x + 1][y + 1].emptyCheck()){
            return true;
        }
        return true;
    }

    /**
     * Checks if there is a Crop on the farm that has not withered or matured yet.
     * @param day the current day in the Game.
     * @return true if there is a Crop that is neither withered nor mature and false if not.
     */
    public boolean cropFarmCheck(int day){
        for (Plot[] plot : plots) {
            for (Plot value : plot) {
                if (value.cropCheck() && !value.witherCheck(day) && value.harvestCheck(day) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a Plot with a harvestable Crop.
     * @param day the current day in the Game.
     * @return true if there is a Plot with a Crop that can be harvested and false if not.
     */
    public boolean harvestFarmCheck(int day){
        for (Plot[] plot : plots) {
            for (Plot value : plot) {
                if (value.harvestCheck(day) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Provides update on harvestable crops in the Plots.
     * @param day the current day in the Game.
     */

    public void harvestUpdate(int day){
        for (int i = 0; i < plots.length; i++) {
            for (int j = 0; j < plots[i].length; j++) {
                if (plots[i][j].harvestCheck(day) == 0){
                    System.out.println("  A " + plots[i][j].getCrop().getName() + " can be harvested at (" + i + ", " + j + ").");
                }
            }
        }
    }

    /**
     * Checks if all Plots have withered Crops.
     * @param day the current day in the Game.
     * @return true if all Plots have withered Crops and false if not.
     */
    public boolean witherFarmCheck(int day){
        for (Plot[] plot : plots) {
            for (Plot value : plot) {
                if (!value.witherCheck(day)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Update on whether crops have withered in the Plots.
     * @param day the current day in the Game.
     */

    public void witherUpdate(int day){
        for (Plot[] plot : plots) {
            for (Plot value : plot) {
                value.witherCheck(day);
            }
        }
    }


}
