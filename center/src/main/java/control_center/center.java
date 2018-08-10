package control_center;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import static java.time.zone.ZoneRulesProvider.refresh;

public class center {

    static{
        System.setProperty("java.awt.headless", "false");
    }

    private JLabel label=new JLabel("전송된 정보 목록");
    private JTable table;
    private JScrollPane scrollPane;
    public DataOutputStream dataOutputStream;
    public DefaultTableModel model;
    private String columns[]={"종류", "유형", "X", "Y"};
    private Object contents[][]={};

    Object x, y, type, info;
    Timer timer=new Timer(0, null);
    int row=0;
    String msg=null;

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
            System.out.println(obj.get("x"));
            System.out.println(obj.get("y"));
            System.out.println(obj.get("type"));
            System.out.println(obj.get("info"));
            x = obj.get("x");
            y = obj.get("y");
            type = obj.get("type");
            info = obj.get("info");

            timer = new Timer(200, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.addRow(new Object[]{type, info, x, y});
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

        timer.setDelay(30000);
        timer.start();
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
