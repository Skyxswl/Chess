package view;

import javax.swing.*;
import java.awt.*;

public class windows extends JFrame {
    int width;
    int height;
    public windows(int width, int height){
        this.width=width;
        this.height=height;
    }

    public void Nowindows(){
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelNo(height,width);
        addButtonConfirm();
    }
    private void addLabelNo(int height,int width) {
        JLabel statusLabel = new JLabel("You can't swap here");
        statusLabel.setLocation(100,30 );
        statusLabel.setSize(200,60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    private void addButtonConfirm(){
        JButton button = new JButton("Confirm");
        button.addActionListener((e) -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
            frame.dispose();
        });
        button.setLocation(150, 100);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(button);
    }

}
