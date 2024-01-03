package view;

import controller.GameController;
import model.Chessboard;
import player.MusicPlayer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    public static GameController gameController;
    private MusicPlayer player = new MusicPlayer();


    //    public static JLabel modeLabel;
    public static JLabel stepLabel, scoreLabel, remainingStepLable,targetScoreLabel,levelLabel;
    Chessboard chessboard = new Chessboard(0);
    JLabel label1 = new JLabel();
    private JButton btn2 = new JButton();
    private JButton btn666 = new JButton();
    private final ImageIcon image5 = new ImageIcon("images/quit.jpg");
    private final ImageIcon image666 = new ImageIcon("images/boom.png");
    boolean ifBackground1=true;
    boolean ifAuto=false;
    ImageIcon image;
    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
//        addLabel();
        GameController gameController =
                new GameController(this.getChessboardComponent(), chessboard);
        this.gameController = gameController;

//        addHelloButton();
//        addLabel();
        addLevelLabel();
        addSwitchButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addSaveButton();
        addQuitButton(image5);
        addSwitchModeButton();
        addMagicButton(image666);
        addRearrangeButton();
        addRemainingStepLabel(gameController.getStepnum());
        remainingStepLable.setText(String.format("Remaining Steps: %d", 30));
        addStepLabel(gameController.getStepnum());
        addTargetScoreLabel(gameController.getScoretarget());
//        targetScoreLabel.setText(String.format("Target Scores: %d", gameController.getScoretarget()));
        addScoreLabel(gameController.getScore());
        addHintButton();
        addBackgroundLabel();
        ActionListener test=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.play("Music/3177003356.wav"); // 播放音乐
            }
        };
        Timer timer= new Timer(0,test);
        timer.start();
        timer.setRepeats(false);
        Timer timer2= new Timer(93000,test);
        timer2.start();
    }
    private void addBackgroundLabel() {
        if(ifBackground1==false){
             image=new ImageIcon("images/0245.png");
             label1.setIcon(image);
             ifBackground1=true;
        }else {
             image=new ImageIcon("images/0243.png");
             label1.setIcon(image);
             ifBackground1=false;
        }
        image.setImage(image.getImage().getScaledInstance(1100,810,Image.SCALE_DEFAULT));
        add(label1);
        label1.setBounds(0,0,1100,810);
        System.out.println(ifBackground1);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);

    }

    /**
     * 在游戏面板中添加标签
     */
//    private void addLabel() {
//        JLabel statusLabel = new JLabel(gameController.getlevel());
//        statusLabel.setLocation(HEIGTH, HEIGTH / 10+120);
//        statusLabel.setSize(200, 60);
//        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
//        statusLabel.setForeground(Color.red);
//        add(statusLabel);
//    }
    public void addLevelLabel() {
        levelLabel = new JLabel("Level");
        levelLabel.setLocation(HEIGTH-380, HEIGTH / 10 -60);
        levelLabel.setSize(200, 60);
        levelLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        levelLabel.setForeground(Color.red);
        add(levelLabel);
    }
    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addSwitchButton() {
        JButton button = new JButton("Switch Background");
        button.addActionListener((e) -> {
            ;
            addBackgroundLabel();
            System.out.println("Switch the background");

        });
        button.setLocation(0, 0);
        button.setSize(300, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> chessboardComponent.swapChess());
        button.setLocation(HEIGTH, HEIGTH / 10 +100);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> {

            chessboardComponent.nextStep();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 260);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            //String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile();
//            this.importGame();
        });
    }
    public void importGame() {
        JFileChooser jf = new JFileChooser(".");
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jf.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return ".txt";
            }

            @Override
            public boolean accept(File f) {
                if (f.getName().endsWith("txt")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        int flag = jf.showOpenDialog(this);
        if (flag == JFileChooser.APPROVE_OPTION) {
            FileInputStream fileIn = null;
            try {
                fileIn = new FileInputStream(jf.getSelectedFile().getName());
                String fileName = jf.getSelectedFile().getName();
                String lastName = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (!lastName.equals("txt")) {
                    JOptionPane.showMessageDialog(gameController.view, "Please import a txt type file!\nerror: 101");
                    return;
                }
                ObjectInputStream in = new ObjectInputStream(fileIn);
                Chessboard temp = (Chessboard) in.readObject();
                gameController.model = temp;
                gameController.view.initiateChessComponent(gameController.model);
                gameController.view.repaint();
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }
    /*
        用于向界面中添加一个“Load”按钮，以实现从文件加载棋局的功能。
        这部分代码首先打印"Click load"到控制台，然后弹出一个对话框提示用户输入文件路径。
        获取到的路径存储在path变量中。最后，它调用gameController的loadGameFromFile方法，尝试从指定路径加载棋局。
         */

    private void addSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.setLocation(HEIGTH, HEIGTH / 10 + 340);
        saveButton.setSize(200, 60);
        saveButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(saveButton);

        saveButton.addActionListener(e -> {
            System.out.println("Click save");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
            this.gameController.saveGame();
//            this.saveGame();
        });
    }


    public void saveGame() {
        try {
                JFileChooser chooser = new JFileChooser(".");
                chooser.setMultiSelectionEnabled(false);
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filepath = chooser.getSelectedFile().getAbsolutePath();
                    FileOutputStream fileOut = new FileOutputStream(filepath);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(gameController.getModel());
                    out.close();
                    fileOut.close();
                }
            } catch (IOException ee) {
                ee.printStackTrace();
            }
    }

    private void addQuitButton(ImageIcon image) {
        btn2.setIcon(image);
        image.setImage(image.getImage().getScaledInstance(38, 32, Image.SCALE_DEFAULT));

        btn2.setBorderPainted(false);
        btn2.setBounds(100, 680, 38, 32);
        btn2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn2.addActionListener(e -> {
            player.stop();
            setVisible(false);
            dispose();
            StartFrame startFrame = new StartFrame(1100, 810);
            startFrame.setVisible(true);
        });
        add(btn2);
    }

    private void addRearrangeButton() {
        JButton rearrangeButton = new JButton("Rearrange");
        rearrangeButton.setLocation(HEIGTH, HEIGTH / 10 + 420);
        rearrangeButton.setSize(200, 60);
        rearrangeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(rearrangeButton);
        rearrangeButton.addActionListener(e -> {
            chessboardComponent.Rearrange();
        });
    }
    private void addHintButton() {
        JButton hintButton = new JButton("Hint");
        hintButton.setLocation(HEIGTH, HEIGTH / 10 + 500);
        hintButton.setSize(200, 60);
        hintButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(hintButton);
        hintButton.addActionListener(e -> {
            gameController.onPlayerHint();
        });
    }
    private void addSwitchModeButton() {
        JButton switchModeButton = new JButton("Switch Mode");
        switchModeButton.setLocation(HEIGTH, HEIGTH / 10 + 580);
        switchModeButton.setSize(200, 60);
        switchModeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(switchModeButton);
        switchModeButton.addActionListener(e ->  {
            if(ChessboardComponent.isProcessing){
                switchModeButton.setText("Hand Mode");
                System.out.println("switch to Auto Mode");
                ifAuto=true;
            }else {
                switchModeButton.setText("Auto Mode");
                System.out.println("switch to Hand Mode");
                ifAuto=false;
            }
            ChessboardComponent.onButtonClick();
        });
    }

    public void addRemainingStepLabel(int step) {
        String text = String.format("Remaining Steps: %d", gameController.getStep() - step);
        remainingStepLable = new JLabel(text);
        remainingStepLable.setLocation(HEIGTH, HEIGTH / 10 - 80);
        remainingStepLable.setSize(400, 60);
        remainingStepLable.setFont(new Font("Rockwell", Font.BOLD, 20));
        remainingStepLable.setForeground(Color.red);
        add(remainingStepLable);
    }

    public void addStepLabel(int step) {
        String text = String.format("Steps: %d", step);
        stepLabel = new JLabel(text);
        stepLabel.setLocation(HEIGTH, HEIGTH / 10 - 50);
        stepLabel.setSize(200, 60);
        stepLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        stepLabel.setForeground(Color.red);
        add(stepLabel);
    }

    public void addScoreLabel(int score) {
        String text = String.format("Scores: %d", score);
        scoreLabel = new JLabel(text);
        scoreLabel.setLocation(HEIGTH, HEIGTH / 10 + 10);
        scoreLabel.setSize(200, 60);
        scoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        scoreLabel.setForeground(Color.red);
        add(scoreLabel);
    }
    public void addTargetScoreLabel(int score) {
        String text = String.format("Target Scores: %d", score);
        targetScoreLabel = new JLabel(text);
        targetScoreLabel.setLocation(HEIGTH, HEIGTH / 10 - 20);
        targetScoreLabel.setSize(300, 60);
        targetScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        targetScoreLabel.setForeground(Color.red);
        add(targetScoreLabel);
    }
    public static GameController getGameController(){
        return gameController;
    }
    public static void refresh(){
        String text = String.format(gameController.getlevel());
        levelLabel.setText(text);
        stepLabel.setText(String.format("Steps: %d", gameController.getStepnum()));
        remainingStepLable.setText(String.format("Remaining Steps: %d",gameController.getStep()-gameController.getStepnum()));
        scoreLabel.setText(String.format("Scores: %d", gameController.getScore()));
        targetScoreLabel.setText(String.format("Target Scores: %d", gameController.getScoretarget()));
    }
    private void addMagicButton(ImageIcon image) {
        btn666.setIcon(image);
        image.setImage(image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

        btn666.setBorderPainted(false);
        btn666.setBounds(50, 480, 100, 100);
        btn666.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn666.addActionListener(e -> {
            gameController.onPlayerMagic();
        });
        add(btn666);
    }
}
