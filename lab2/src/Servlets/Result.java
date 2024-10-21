package Servlets;

public class Result {
    private String hit;
    private double x;
    private double y;
    private double r;

    public Result(double x, double y, double r, String hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getR() {
        return r;
    }

    public String getHit() {
        return hit;
    }
}
