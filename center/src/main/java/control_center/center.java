package control_center;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.awt.geom.Point2D.distance;
import static jdk.nashorn.internal.objects.NativeNumber.valueOf;

public class center {

    static{
        System.setProperty("java.awt.headless", "false");
    }

    private JLabel label=new JLabel("전송된 정보 목록");
    private JTable table;
    private JScrollPane scrollPane;
    public DataOutputStream dataOutputStream;
    public DefaultTableModel model;
    private String columns[]={"종류", "유형", "X", "Y", "중복된 정보"};
    //    public Object information[][]=new Object[100][];
    public ArrayList<Object[]> information=new ArrayList<Object[]>();
    Object x, y, type, info;
    Timer timer=new Timer(0, null);
    public int flag, row, cnt=1, tmp=0;
    public ArrayList<Integer> count=new ArrayList<Integer>();

    public center(){

//        model=new DefaultTableModel(columns, 0);
//        table=new JTable(model);
//
//        scrollPane=new JScrollPane(table);
//        scrollPane.setViewportView(table);
//
//        JFrame frame=new JFrame("Control Center");
//        label.setHorizontalAlignment(JLabel.CENTER);
//        frame.add(label, BorderLayout.NORTH);
//        frame.add(scrollPane);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);

    }

    public void updateTable(String msg){

        if(msg!=null) {
            JSONParser jsonParser = new JSONParser();
            JSONObject obj = null;
            try {
                obj = (JSONObject) jsonParser.parse(msg);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            System.out.println(obj.get("x"));
//            System.out.println(obj.get("y"));
//            System.out.println(obj.get("type"));
//            System.out.println(obj.get("info"));
            x = obj.get("x");
            y = obj.get("y");
            type = obj.get("type");
            info = obj.get("info");

//            information[length]=new Object[]{x, y, type, info};
            information.add(new Object[]{type, info, x, y, cnt});

            timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(type.equals("accident")){
                        //model.addRow(new Object[]{type, info, x, y, cnt});
                        if(information.size()==1){ model.addRow(information.get(0)); }
                        else{
                            for(int i=0; i<information.size()-1; i++){
                                if(getDistance(x, y, information.get(i)[2], information.get(i)[3])){ //x, y 비교
                                    if(!info.equals(information.get(i)[1])){ flag=0; }
                                    else{ //info.equals(information.get(i)[3])==TRUE
                                        flag=1; cnt++;
                                        System.out.println(model.getRowCount());
                                        if(i==0){ model.setValueAt(cnt, 0, 4); }
                                        else {
                                            for(int j=0; j<model.getRowCount(); j++){
                                                if(info.equals(model.getValueAt(j, 1))){ row=j; }
                                            }
                                            model.setValueAt(cnt, row, 4);
                                        }
                                        break;
                                    }
                                }
                                else{
                                    model.addRow(new Object[]{type, info, x, y, cnt});
//                                    count.set(tmp++, cnt);
                                    break;
                                }
                            }
                            if(flag==0){
                                cnt=1; model.addRow(new Object[]{type, info, x, y, cnt});
//                                count.set(tmp++, cnt);
                            }
                        }
                    }
                }
            });
        }
//
//        for(int i=0; i<columns.length; i++){
//            model.addColumn(columns[i]);
//        }
//
//        table.setAutoCreateRowSorter(true);
//        table.setFillsViewportHeight(true);
//        table.setPreferredScrollableViewportSize(new Dimension(550, 200));

        timer.setDelay(300000);
        timer.start();
    }

    public boolean getDistance(Object x1, Object y1, Object x2, Object y2){
        String x_1=x1.toString();
        String y_1=x1.toString();
        String x_2=x1.toString();
        String y_2=x1.toString();

        double dis=distance(Double.valueOf(x_1).doubleValue(), Double.valueOf(y_1).doubleValue(), Double.valueOf(x_2).doubleValue(), Double.valueOf(y_2).doubleValue());

        if(dis<100){ return true; }

        return false;
    }

    public void setFrame(){

        model=new DefaultTableModel(columns, 0);
        table=new JTable(model);

        scrollPane=new JScrollPane(table);
        scrollPane.setViewportView(table);

        JFrame frame=new JFrame("Control Center");
        label.setHorizontalAlignment(JLabel.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


//    public void setFrame() {
//
//        JFrame frame=new JFrame("control_center");
//
//        frame.setContentPane(new control_center().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//
//        frame.setVisible(true);
//    }

}