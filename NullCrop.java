import exceptions.PlotUnoccupiedException;

public class NullCrop extends Crop {
    private static final NullCrop INSTANCE = new NullCrop();

    private NullCrop() {
        super("", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public static NullCrop getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean getHarvestStatus(int day) {
        return false;
    }

    @Override
    public void addWater() throws PlotUnoccupiedException {
        throw new PlotUnoccupiedException();
    }

    @Override
    public void addFertilizer() throws PlotUnoccupiedException {
        throw new PlotUnoccupiedException();
    }

    @Override
    public boolean witherCheck(int day) {
        return false;
    }

    @Override
    public int harvestCalculate(int waterMaxBonus, int fertilizerMaxBonus, int bonusEarnings) {
        return 0;
    }
}
