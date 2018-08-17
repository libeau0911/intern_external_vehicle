package tcp_connect_client;

import GPS.DataManager;
import GPS.gps;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class subcar extends JFrame {

    public JComboBox comboBox1;
    public JComboBox comboBox2;
    public JButton button1;
    public JButton button2;
    public JFrame frame;
    public JLabel location_label;
    public JPanel message_panel;
    public JLabel message_icon;
    public JLabel message;
    public DataOutputStream dataOutputStream1, dataOutputStream2;

    public String event;
    public ImageIcon image1=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("yellow.png")));
    public ImageIcon image2=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("red.png")));
    public ImageIcon image3=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("message_default_panel.png")));
    public ImageIcon image4=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("message_yellow_panel.png")));
    public ImageIcon image5=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("message_red_panel.png")));
    public ImageIcon image=image3;

    public Icon[] sudden_case={
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("sudden_stop.png"))),
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("animal.png"))),
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("pedestrian.png"))),
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("car-object.png")))
    };

    public Icon[] accident={
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("car-crash.png"))),
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("luggage.png"))),
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("car-breakdown.png"))),
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("car-overturn.png"))),
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("car-on-fire.png"))),
            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("road-damage.png")))
    };

    public String sudden_case_info, accident_info;
    Icon sudden_case_icon, accident_icon;
    String x, y, carID;
    String test;
    gps GPS;
    public JPanel panel1;
    private JPanel mainPanel;
    public JLabel subcar_icon;
    public JPanel sudden_case_panel;
    public JPanel accident_panel;
    private JPanel subPanel1;
    public Graphics g;
    public static int count = 10;
    Timer timer=new Timer(0, null);

    static ArrayList<gps> dataArrayList = new ArrayList<>();

    public subcar(DataOutputStream out1, DataOutputStream out2) {
        dataOutputStream1 = out1;
        dataOutputStream2 = out2;

        loadFile();
        GPS = DataManager.getInstance().getCurrentGPS();
        DataManager.getInstance().updateGPS();
        //임의 외부 차량 ID
        carID="001";
        location_label.setText(test);
        comboBox1.setMaximumRowCount(4);
        comboBox2.setMaximumRowCount(4);

        //돌발상황
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange()==ItemEvent.SELECTED) {
                    sudden_case_icon = sudden_case[comboBox1.getSelectedIndex()];
                    sudden_case_info=comboBox1.getSelectedItem().toString();
                }
            }
        });

        //사고
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    accident_icon = accident[comboBox2.getSelectedIndex()];
                    accident_info=comboBox2.getSelectedItem().toString();
                }
            }
        });

        JSONObject jsonObject = new JSONObject();

        //돌발 상황
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                message_panel.setBackground(Color.getHSBColor(56, 24, 98));
                mainPanel.updateUI();

                event="sudden_case";
                image=image4;

                if(sudden_case_info==null){
                    sudden_case_icon=sudden_case[0];
                    sudden_case_info="Sudden Stop";
                }
                message_icon.setIcon(sudden_case_icon);
                message.setText("<html> 전송한 이벤트 내용<br/><br/>" +
                        "돌발 이벤트 - "+sudden_case_info+
                        "<br/><br/>이벤트 발생 위치 X: "+GPS.getX()+" &nbsp;Y: "+ GPS.getY());

                try {
                    jsonObject.put("id", carID);
                    jsonObject.put("type", "sudden case");
                    jsonObject.put("x", GPS.getX());
                    jsonObject.put("y", GPS.getY());
                    jsonObject.put("info", sudden_case_info);

                    dataOutputStream1.writeUTF(jsonObject.toJSONString());
//                    dataOutputStream2.writeUTF(jsonObject.toJSONString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //사고
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                message_panel.setBackground(Color.getHSBColor(13, 17, 94));
                mainPanel.updateUI();

                event="accident";
                image=image5;
                if(accident_info==null){
                    accident_icon=accident[0];
                    accident_info="Car Crash";
                }

                message.setText("<html> 전송한 이벤트 내용<br/><br/>" +
                        "돌발 이벤트 - "+accident_info+
                        "<br/><br/>이벤트 발생 위치 X: "+GPS.getX()+" &nbsp;Y: "+GPS.getY());
                message_icon.setIcon(accident_icon);
                try {
                    jsonObject.put("id", carID);
                    jsonObject.put("type", "accident");
                    jsonObject.put("x", GPS.getX());
                    jsonObject.put("y", GPS.getY());
                    jsonObject.put("info", accident_info);

                    dataOutputStream1.writeUTF(jsonObject.toJSONString());
                    dataOutputStream2.writeUTF(jsonObject.toJSONString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        System.out.println("??");
        createUIComponents();
//        updateLabel();
        System.out.println(2);
    }

    public void updateLabel(){
        new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                subPanel1.updateUI();
                GPS = DataManager.getInstance().getCurrentGPS();
                DataManager.getInstance().updateGPS();
                System.out.println(GPS.getX()+" "+GPS.getY()+"\n");
                location_label.setText("X: "+GPS.getX()+" Y: "+GPS.getY());
            }
        }).start();
    }

    public static void loadFile() {

        try {
            File file = new File(".\\src\\main\\resources\\static\\ex1.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line == "") continue;
                dataArrayList.add(new gps(line));
            }
            DataManager.getInstance().setGpsdata(dataArrayList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here

        sudden_case_panel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image1.getImage(), 0, 0, null);
                setOpaque(false);
            }
        };

        accident_panel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image2.getImage(), 0, 0, null);
                setOpaque(false);
            }
        };

        message_panel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image.getImage(), 0, 0, null);

                setOpaque(false);
                // super.paintComponent(g);
            }
        };

//        location_label=new JLabel("a");

        System.out.println("1");
    }


    public void setFrame() {
        frame=new JFrame("subcar");

        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        System.out.println(3);
    }

}
