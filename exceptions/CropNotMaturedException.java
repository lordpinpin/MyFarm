package exceptions;

public class CropNotMaturedException extends Exception {
    public CropNotMaturedException() {
        super("Crop in plot has not matured yet.");
    }
}
