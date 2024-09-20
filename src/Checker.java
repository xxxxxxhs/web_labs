public class Checker {

    public static boolean hit (double x, double y, double r) {
        if (x == 0 && y <= r && y >= -1 * r) {
            return true;
        } else if (y == 0 && x >= -1 * r && x <= r) {
            return true;
        } else {
            if (y >= 0 && x >= 0 && x <= r && y <= r) {
                return true;
            }
            if (x < 0 && y > 0) {
                return false;
            }
            if (x <= 0 && y <= 0 && x * x + y * y <= r * r) {
                return true;
            }
            if (x >= 0 && y <= 0 && x <= r && y >= -1 * r && y >= x - r) {
                return true;
            }
            return false;
        }
    }
}
