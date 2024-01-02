package view;

import controller.GameController;
import model.Chessboard;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;


public class windows extends JFrame {
    int width;
    int height;
    GameController gameController = ChessGameFrame.getGameController();

    public windows(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void Nowindows() {
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelNo(height, width);
        addButtonConfirm();
    }

    private void addLabelNo(int height, int width) {
        JLabel statusLabel = new JLabel("You can't swap here");
        statusLabel.setLocation(100, 30);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addButtonConfirm() {
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

    public void Endwindows() {
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelCongratulations(height, width);
        addButtonExit();
        addButtonNext();
        addLabelCongratulations(height, width);
    }

    private void addLabelCongratulations(int height, int width) {
        JLabel statusLabel = new JLabel("Congratulations");
        statusLabel.setLocation(100, 30);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);

    }

    private void addButtonExit() {
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

    private void addButtonNext() {
        JButton button = new JButton("Next level");
        button.addActionListener((e) -> {
            nextLevel();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
            frame.dispose();
        });
        button.setLocation(250, 100);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(button);
    }

    private void nextLevel() {
        String level = gameController.getlevel();
        switch (level) {
            case "Level1": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level2.getNum1());
                gameController.setStep(Level.Level2.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level2": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level3.getNum1());
                gameController.setStep(Level.Level3.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level3": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level4.getNum1());
                gameController.setStep(Level.Level4.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level4": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level5.getNum1());
                gameController.setStep(Level.Level5.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level5": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level6.getNum1());
                gameController.setStep(Level.Level6.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level6": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level7.getNum1());
                gameController.setStep(Level.Level7.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level7": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level8.getNum1());
                gameController.setStep(Level.Level8.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level8": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level9.getNum1());
                gameController.setStep(Level.Level9.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level9": {
                gameController.refresh();
                gameController.newgame();
                gameController.setScoretarget(Level.Level10.getNum1());
                gameController.setStep(Level.Level10.getNum2());
                gameController.setMagicnum(2);
                ChessGameFrame.refresh();
                break;
            }
            case "Level10": {
                Congretulationwindows();
                break;
            }
        }
    }

    public void Congretulationwindows() {
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelCongratulations(height, width);
        addButtonConfirm();
    }

    public void gameoverwindows() {
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelgameover(height, width);
        addButtonConfirm();
    }

    private void addLabelgameover(int height, int width) {
        JLabel statusLabel = new JLabel("Game Over");
        statusLabel.setLocation(100, 30);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    public void nextwindows() {
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelNonext(height, width);
        addButtonConfirm();
    }

    private void addLabelNonext(int height, int width) {
        JLabel statusLabel = new JLabel("Please click next step button");
        statusLabel.setLocation(66, 30);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    public void Hintwindow(ChessboardPoint[] result, int width, int height){
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelHint(result);
        addButtonConfirm();
    }
    private void addLabelHint(ChessboardPoint[] result){
        String point1="("+(result[0].getRow()+1)+","+(result[0].getCol()+1)+")";
        String point2="("+(result[1].getRow()+1)+","+(result[1].getCol()+1)+")";
        String text=point1+" and "+point2+" can swap";
        JLabel statusLabel = new JLabel(text);
        statusLabel.setLocation(66, 30);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    public void alivewindow(){
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelAlive();
        addButtonConfirm();
    }
    private void addLabelAlive(){
        JLabel statusLabel = new JLabel("<html>There is no match on chessboard,<br>please click rearrange button to continue</html>");
        statusLabel.setLocation(50, 30);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(statusLabel);
    }
    public void magicwindow(){
        setSize(width, height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabelmagic();
        addButtonConfirm();
    }
    private void addLabelmagic(){
        JLabel statusLabel = new JLabel("You  haven't magic now!");
        statusLabel.setLocation(80, 30);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
}
