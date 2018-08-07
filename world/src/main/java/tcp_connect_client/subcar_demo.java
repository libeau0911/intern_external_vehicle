package tcp_connect_client;

import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.*;

public class subcar_demo extends JFrame {

    public JComboBox comboBox1;
    public JComboBox comboBox2;
    public JButton button1;
    public JButton button2;
    public JPanel panelMain;

    public JLabel location_label;
    public JPanel message_panel;
    public JLabel message_icon;
    public JLabel message;
    public DataOutputStream dataOutputStream;

    //road damage, luggage 수정
    public Icon[] sudden_case={
            new ImageIcon(this.getClass().getClassLoader().getResource("sudden_stop.png")),
            new ImageIcon(this.getClass().getClassLoader().getResource("animal.png")),
            new ImageIcon(this.getClass().getClassLoader().getResource("pedestrian.png")),
            new ImageIcon(this.getClass().getClassLoader().getResource("car-object.png"))
    };

    public Icon[] accident={
            new ImageIcon(this.getClass().getClassLoader().getResource("car-crash.png")),
            new ImageIcon(this.getClass().getClassLoader().getResource("luggage.png")),
            new ImageIcon(this.getClass().getClassLoader().getResource("car-breakdown.png")),
            new ImageIcon(this.getClass().getClassLoader().getResource("car-overturn.png")),
            new ImageIcon(this.getClass().getClassLoader().getResource("car-on-fire.png")),
            new ImageIcon(this.getClass().getClassLoader().getResource("road-damage.png")),
    };

    public String sudden_case_info, accident_info;
    Icon sudden_case_icon, accident_icon;
    String x, y, carID;
    public JPanel panel1;
    public JLabel subcar_icon;
    private JPanel sudden_case_panel;
    private JPanel accident_panel;

    public subcar_demo(DataOutputStream out) {
        dataOutputStream = out;

        //임의 외부 차량 ID
        carID="001";
        //임의 외부 차량 위치
        x=String.valueOf(126.378);
        y=String.valueOf(35.55);

        location_label.setText("<html>외부 차량 현재 위치<br/>" +
                "X: "+x+" Y: "+y);

        comboBox1.setMaximumRowCount(4);
        comboBox2.setMaximumRowCount(4);

//        message_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        sudden_case_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        accident_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //돌발상황
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange()==ItemEvent.SELECTED) {
                    sudden_case_icon    = sudden_case[comboBox1.getSelectedIndex()];
                    sudden_case_info=comboBox1.getSelectedItem().toString();
                    //System.out.printf("%s \n", s_accidents);
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
                message_panel.setBackground(Color.getHSBColor(56, 24, 98));
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

                    dataOutputStream.writeUTF(jsonObject.toJSONString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });

        //사고
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message_panel.setBackground(Color.getHSBColor(13, 17, 94));
                if(accident_info==null){
                    accident_icon=accident[0];
                    accident_info="Car Crash";
                }
                message.setText("<html> 전송한 이벤트 내용<br/><br/>" +
                        "돌발 이벤트 - "+accident_info+
                        "<br/><br/>이벤트 발생 위치 X: "+x+" &nbsp;Y: "+y);
                message_icon.setIcon(accident_icon);
                try {
                    jsonObject.put("ID", carID);
                    jsonObject.put("type", "accident");
                    jsonObject.put("X", x);
                    jsonObject.put("Y", y);
                    jsonObject.put("info", accident_info);

                    dataOutputStream.writeUTF(jsonObject.toJSONString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void setFrame() {
        JFrame frame=new JFrame("subcar_demo");

        frame.setContentPane(new subcar_demo(this.dataOutputStream).panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }

}
