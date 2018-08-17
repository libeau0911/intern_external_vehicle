package GPS;

public class gps {
    double x,y;

    public gps() {
    }

    public gps(String str){
        String tmp[] = str.split(" ");
        this.y = Double.parseDouble(tmp[0]);
        this.x = Double.parseDouble(tmp[1]);
    }

    public gps(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
