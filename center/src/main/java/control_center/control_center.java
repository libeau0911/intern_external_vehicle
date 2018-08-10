package control_center;

import jdk.nashorn.internal.parser.JSONParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class control_center {

    static{
        System.setProperty("java.awt.headless", "false");
    }

    private JPanel mainPanel;
    private JTable table;
    private JScrollPane scrollPane;
    public DataOutputStream dataOutputStream;
    public DefaultTableModel model;
    private String columns[]={"종류", "유형", "X", "Y"};
    private Object contents[][]={{"돌발", "Wild Animal Appearance", }};

    public control_center(){

        model=new DefaultTableModel();

        for(int i=0; i<columns.length; i++){
            model.addColumn(columns[i]);
        }

        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(550, 200));

        setRow((String) contents[0][0]);
        table.setModel(model);
        scrollPane.setViewportView(table);

    }

    //정보 추가
    public void setRow(String info){
        Vector row=new Vector();

        row.add("1");
        row.add("2");
        row.add("3");
        row.add("4");

        model.addRow(row);

    }

    public void setFrame() {

        JFrame frame=new JFrame("control_center");

        frame.setContentPane(new control_center().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
