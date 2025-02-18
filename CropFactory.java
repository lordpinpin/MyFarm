public class CropFactory {
    public static Crop createTurnip(int day) {
        return new Crop("Turnip", "Root", day, 2, 1, 2, 0, 1, 1, 2, 5, 6, 5);
    }

    public static Crop createCarrot(int day) {
        return new Crop("Carrot", "Root", day, 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5);
    }

    public static Crop createPotato(int day) {
        return new Crop("Potato", "Root", day, 5, 3, 4, 1, 2, 1, 10, 20, 3, 12.5);
    }

    public static Crop createRose(int day) {
        return new Crop("Rose", "Flower", day, 1, 1, 2, 0, 1, 1, 1, 5, 5, 2.5);
    }

    public static Crop createTurnips(int day) {
        return new Crop("Turnips", "Flower", day, 2, 2, 3, 0, 1, 1, 1, 10, 9, 5);
    }

    public static Crop createSunflower(int day) {
        return new Crop("Sunflower", "Flower", day, 3, 2, 3, 1, 2, 1, 1, 20, 19, 7.5);
    }

    public static Crop createMango(int day) {
        return new Crop("Mango", "Tree", day, 10, 7, 7, 4, 4, 5, 15, 100, 8, 25);
    }

    public static Crop createApple(int day) {
        return new Crop("Apple", "Tree", day, 10, 7, 7, 5, 5, 10, 15, 200, 5, 25);
    }
}
