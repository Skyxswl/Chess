package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;


public class windows extends JFrame {
    int width;
    int height;
    GameController gameController=ChessGameFrame.getGameController();
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
    public void Endwindows(){
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelCongratulations(height,width);
        addButtonExit();
        addButtonNext();
        addLabelCongratulations(height,width);
    }
    private void addLabelCongratulations(int height,int width) {
        JLabel statusLabel = new JLabel("Congratulations");
        statusLabel.setLocation(100,30 );
        statusLabel.setSize(200,60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);

    }
    private void addButtonExit(){
        JButton button = new JButton("Exit");
        button.addActionListener((e) -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
            frame.dispose();
        });
        button.setLocation(50, 100);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(button);
    }
    private void addButtonNext(){
        JButton button = new JButton("Next level");
        button.addActionListener((e) -> {
            nextLevel();
        });
        button.setLocation(250, 100);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(button);
    }
    private void nextLevel(){
        String level=gameController.getlevel();
        switch (level){
            case "Level1":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level2.getNum1());
                gameController.setStep(Level.Level2.getNum2());
                break;
            }
            case "Level2":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level3.getNum1());
                gameController.setStep(Level.Level3.getNum2());
                break;
            }
            case "Level3":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level4.getNum1());
                gameController.setStep(Level.Level4.getNum2());
                break;
            }
            case "Level4":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level5.getNum1());
                gameController.setStep(Level.Level5.getNum2());
                break;
            }
            case "Level5":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level6.getNum1());
                gameController.setStep(Level.Level6.getNum2());
                break;
            }
            case "Level6":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level7.getNum1());
                gameController.setStep(Level.Level7.getNum2());
                break;
            }
            case "Level7":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level8.getNum1());
                gameController.setStep(Level.Level8.getNum2());
                break;
            }
            case "Level8":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level9.getNum1());
                gameController.setStep(Level.Level9.getNum2());
                break;
            }
            case "Level9":{
                gameController.refresh();
                gameController.onPlayerRearrange();
                gameController.setScoretarget(Level.Level10.getNum1());
                gameController.setStep(Level.Level10.getNum2());
                break;
            }
            case "Level10":{
                Congretulationwindows();
                break;
            }
        }
    }
    public void Congretulationwindows(){
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelCongratulations(height,width);
        addButtonConfirm();
    }
    public void gameoverwindows(){
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelgameover(height,width);
        addButtonConfirm();
    }
    private void addLabelgameover(int height,int width) {
        JLabel statusLabel = new JLabel("Game Over");
        statusLabel.setLocation(100,30 );
        statusLabel.setSize(200,60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
}
