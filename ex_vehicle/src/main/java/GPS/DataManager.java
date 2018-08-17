package GPS;

import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {
    /* instance */
    private static DataManager ourInstance;

    /* gps info*/
    private int gps_idx = 0;
    private ArrayList<gps> gpsdata;

    /* Start and End coord */
    private double startX, startY, endX, endY;
    private boolean ready_to_start = false;

    private DataManager() {
        gpsdata = new ArrayList<>();
    }

    public void updateGPS(){
        this.gps_idx++;
    }

    public gps getCurrentGPS() {
        if(gps_idx >= gpsdata.size()) return this.gpsdata.get(gpsdata.size()-1);
        gps gps = this.gpsdata.get(gps_idx);
        return gps;
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            synchronized (DataManager.class) {
                if (ourInstance == null) {
                    ourInstance = new DataManager();
                }
            }
        }
        return ourInstance;
    }

    public boolean isReady_to_start() {
        return ready_to_start;
    }

    public void setReady_to_start(boolean ready_to_start) {
        this.ready_to_start = ready_to_start;
    }

    public ArrayList<gps> getGpsdata() {
        return gpsdata;
    }

    public void setGpsdata(ArrayList<gps> gpsdata) {
        this.gpsdata = gpsdata;
    }

    public int getGps_idx() {
        return gps_idx;
    }

    public void setGps_idx(int gps_idx) {
        this.gps_idx = gps_idx;
    }

    public static DataManager getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(DataManager ourInstance) {
        DataManager.ourInstance = ourInstance;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }
}
