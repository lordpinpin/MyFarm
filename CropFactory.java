public class CropFactory {
    public static Crop createCrop(String type, int day) {
        switch (type) {
            case "Turnip":
                return new Crop("Turnip", "Root", day, 2, 1, 2, 0, 1, 1, 2, 5, 6, 5);
            case "Carrot":
                return new Crop("Carrot", "Root", day, 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5);
            case "Potato":
                return new Crop("Potato", "Root", day, 5, 3, 4, 1, 2, 1, 10, 20, 3, 12.5);
            case "Rose":
                return new Crop("Rose", "Flower", day, 1, 1, 2, 0, 1, 1, 1, 5, 5, 2.5);
            case "Turnips":
                return new Crop("Turnips", "Flower", day, 2, 2, 3, 0, 1, 1, 1, 10, 9, 5);
            case "Sunflower":
                return new Crop("Sunflower", "Flower", day, 3, 2, 3, 1, 2, 1, 1, 20, 19, 7.5);
            case "Mango":
                return new Crop("Mango", "Tree", day, 10, 7, 7, 4, 4, 5, 15, 100, 8, 25);
            case "Apple":
                return new Crop("Apple", "Tree", day, 10, 7, 7, 5, 5, 10, 15, 200, 5, 25);
            default:
                throw new IllegalArgumentException("Unknown crop type");
        }
    }

}
