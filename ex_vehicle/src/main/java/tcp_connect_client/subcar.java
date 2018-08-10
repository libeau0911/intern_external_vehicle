package tcp_connect_client;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class subcar extends JFrame {

    public JComboBox comboBox1;
    public JComboBox comboBox2;
    public JButton button1;
    public JButton button2;

    public JLabel location_label;
    public JPanel message_panel;
    public JLabel message_icon;
    public JLabel message;
    public DataOutputStream dataOutputStream1, dataOutputStream2;

    public String event;
    public ImageIcon backgroundimage=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("panel.png")));
    public ImageIcon image1=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("yellow.png")));
    public ImageIcon image2=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("red.png")));
    public ImageIcon image3=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("message_default_panel.png")));
    public ImageIcon image4=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("message_yellow_panel.png")));
    public ImageIcon image5=new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("message_red_panel.png")));
    public ImageIcon image=image3;

    //road damage, luggage 수정
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
    public JPanel panel1;
    private JPanel mainPanel;
    public JLabel subcar_icon;
    public JPanel sudden_case_panel;
    public JPanel accident_panel;
    public Graphics g;

    public subcar(DataOutputStream out1, DataOutputStream out2) {
        dataOutputStream1 = out1;
        dataOutputStream2 = out2;

        //임의 외부 차량 ID
        carID="001";
        //임의 외부 차량 위치
        x=String.valueOf(126.378);
        y=String.valueOf(35.55);

        location_label.setText("<html>외부 차량 현재 위치<br/>" +
                "X: "+x+" Y: "+y);

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
                event="sudden_case";
                image=image4;
                if(sudden_case_info==null){
                    sudden_case_icon=sudden_case[0];
                    sudden_case_info="Sudden Stop";
                }
                message_icon.setIcon(sudden_case_icon);
                message.setText("<html> 전송한 이벤트 내용<br/><br/>" +
                        "돌발 이벤트 - "+sudden_case_info+
                        "<br/><br/>이벤트 발생 위치 X: "+x+" &nbsp;Y: "+y);
                try {
                    jsonObject.put("id", carID);
                    jsonObject.put("type", "sudden case");
                    jsonObject.put("x", x);
                    jsonObject.put("y", y);
                    jsonObject.put("info", sudden_case_info);

                    dataOutputStream1.writeUTF(jsonObject.toJSONString());
                    dataOutputStream2.writeUTF(jsonObject.toJSONString());
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
                event="accident";
                image=image5;
                if(accident_info==null){
                    accident_icon=accident[0];
                    accident_info="Car Crash";
                }
                message.setText("<html> 전송한 이벤트 내용<br/><br/>" +
                        "돌발 이벤트 - "+accident_info+
                        "<br/><br/>이벤트 발생 위치 X: "+x+" &nbsp;Y: "+y);
                message_icon.setIcon(accident_icon);
                try {
                    jsonObject.put("id", carID);
                    jsonObject.put("type", "accident");
                    jsonObject.put("x", x);
                    jsonObject.put("y", y);
                    jsonObject.put("info", accident_info);

                    dataOutputStream1.writeUTF(jsonObject.toJSONString());
                    dataOutputStream2.writeUTF(jsonObject.toJSONString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        createUIComponents();

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

        panel1=new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(backgroundimage.getImage(), 0, 0, null);
            }
        };

        location_label=new JLabel();
        location_label.setForeground(Color.WHITE);
    }


    public void setFrame() {
        JFrame frame=new JFrame("subcar");

        frame.setContentPane(new subcar(this.dataOutputStream1, this.dataOutputStream2).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }

}
