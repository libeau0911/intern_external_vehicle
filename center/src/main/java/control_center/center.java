package control_center;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class center {

    static {
        System.setProperty("java.awt.headless", "false");
    }

    private JLabel label = new JLabel("전송된 정보 목록");
    private JTable table;
    private JScrollPane scrollPane;
    public DefaultTableModel model;
    private String columns[] = {"종류", "유형", "X", "Y", "중복된 정보"};
    Object x, y, type, info;

    public center() {
    }

    public synchronized void updateTable(String msg) {
        if (msg != null) {
            JSONParser jsonParser = new JSONParser();
            JSONObject obj = null;
            try {
                obj = (JSONObject) jsonParser.parse(msg);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            x = obj.get("x");
            y = obj.get("y");
            type = obj.get("type");
            info = obj.get("info");

            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{type, info, x, y, 1});
            } else {
                int i;
                int _size = model.getRowCount();
                for (i = 0; i < _size; i++) {
                    System.out.print(i + " ");
                    if (getDistance(x, y, model.getValueAt(i, 2), model.getValueAt(i, 3))) { //x, y 비교
                        if (info.equals(model.getValueAt(i, 1).toString())) {
                            int cnt = Integer.parseInt(model.getValueAt(i, 4).toString());
                            model.setValueAt(cnt + 1, i, 4);
                            break;
                        } else {
                            model.addRow(new Object[]{type, info, x, y, 1});
                            break;
                        }
                    }
                }
                if (i >= _size) {
                    model.addRow(new Object[]{type, info, x, y, 1});
                }
            }
        }
    }

    public boolean getDistance(Object x1, Object y1, Object x2, Object y2) {
        double dis = this.distance(Double.parseDouble(y1.toString()), Double.parseDouble(x1.toString()),
                Double.parseDouble(y2.toString()), Double.parseDouble(x2.toString()));

        if (dis < 100) return true;
        return false;
    }

    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @return
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1609.344;

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public void setFrame() {
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);

        JFrame frame = new JFrame("Control Center");
        label.setHorizontalAlignment(JLabel.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}