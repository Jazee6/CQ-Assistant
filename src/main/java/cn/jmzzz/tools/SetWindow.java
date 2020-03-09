package cn.jmzzz.tools;

import javax.swing.*;
import java.awt.*;

public class SetWindow {
    private int hour24;
    private int min;

    public SetWindow() {
        JFrame f = new JFrame("Zeka订阅助理");
        f.setSize(480, 320);
        f.setLocation(200, 200);
        f.setLayout(new FlowLayout());

        JLabel lName = new JLabel("每日推送时间：");
        // 输入框
        JTextField tfName = new JTextField("");
        tfName.setText("请输入账号");
        tfName.setPreferredSize(new Dimension(80, 30));

        f.add(lName);
        f.add(tfName);
        f.setVisible(true);
    }
}
