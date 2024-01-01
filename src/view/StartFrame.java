package view;

import controller.GameController;
import player.HumanPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class StartFrame extends JFrame {
    private final int WIDTH ;
    private final int HEIGHT;
    private JButton btn = new JButton();
    private JButton btn1 = new JButton();
    private JButton btn2 = new JButton();
    private JButton btn3 = new JButton();
    private JButton btn4 = new JButton();
    private JButton loadButton = new JButton();
    private boolean flag1;//是否进入选择关卡界面
    private  JLabel label = new JLabel("        关卡选择");

    private JButton btn001 = new JButton();
    private JButton btn002 = new JButton();
    private JButton btn003 = new JButton();
    private JButton btn004 = new JButton();
    private JButton btn005 = new JButton();
    private JButton btn006 = new JButton();
    private JButton btn007 = new JButton();
    private JButton btn008 = new JButton();
    private JButton btn009 = new JButton();
    private JButton btn010 = new JButton();

    private final JPanel root = new JPanel();
    private final ImageIcon image1=new ImageIcon("images/077.png");
    private final ImageIcon image5=new ImageIcon("images/066.png");
    private final ImageIcon image6=new ImageIcon("images/014.jpg");
    private final ImageIcon image01=new ImageIcon("images/1.png");
    private final ImageIcon image02=new ImageIcon("images/2.png");
    private final ImageIcon image03=new ImageIcon("images/3.png");
    private final ImageIcon image04=new ImageIcon("images/4.png");
    private final ImageIcon image05=new ImageIcon("images/5.png");
    private final ImageIcon image06=new ImageIcon("images/6.png");
    private final ImageIcon image07=new ImageIcon("images/7.png");
    private final ImageIcon image08=new ImageIcon("images/8.png");
    private final ImageIcon image09=new ImageIcon("images/9.png");
    private final ImageIcon image10=new ImageIcon("images/10.png");

    public StartFrame(int width, int height) {
        setTitle("Start"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setVisible(false);
        addSelectModeButton(image1);
        addNewGameButton();
        addLevel1Button(image01);
        addLevel2Button(image02);
        addLevel3Button(image03);
        addLevel4Button(image04);
        addLevel5Button(image05);
        addLevel6Button(image06);
        addLevel7Button(image07);
        addLevel8Button(image08);
        addLevel9Button(image09);
        addLevel10Button(image10);
        addTabeLable();
        addBackgroundLabel();
    }
    private void addBackgroundLabel() {
        ImageIcon image=new ImageIcon("images/0243.png");
        image.setImage(image.getImage().getScaledInstance(1100,810,Image.SCALE_DEFAULT));
        JLabel label1=new JLabel(image);
        add(label1);
        label1.setBounds(0,0,1100,810);
//        playMusic();
    }
    private void addSelectModeButton(ImageIcon image) {
        btn.setIcon(image);
        image.setImage(image.getImage().getScaledInstance(328,67,Image.SCALE_DEFAULT));
        btn.setBorderPainted(false);
        btn.setBounds(380,202,328,67);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            btn.setVisible(false);
//            btn1.setVisible(false);
//            loadButton.setVisible(false);
            btn3.setVisible(false);
//            btn4.setVisible(true);
            label.setVisible(true);
            btn001.setVisible(true);
            btn002.setVisible(true);
            btn003.setVisible(true);
            btn004.setVisible(true);
            btn005.setVisible(true);
            btn006.setVisible(true);
            btn007.setVisible(true);
            btn008.setVisible(true);
            btn009.setVisible(true);
            btn010.setVisible(true);
            flag1 = true;
        });
        add(btn);
    }
    private void addLoadButton(ImageIcon image) {
        loadButton.setIcon(image);
        image.setImage(image.getImage().getScaledInstance(328,71,Image.SCALE_DEFAULT));
        loadButton.setBorderPainted(false);
        loadButton.setBounds(380,293,328,71);

        loadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        //loadButton.addActionListener(e -> {
//            player.over();//音乐播放
          //  dispose();
        //    ChessGameFrame.modeLabel = new JLabel("双人模式");
   //         ChessGameFrame mainFrame = new ChessGameFrame(1024, 768);
        //    mainFrame.setVisible(true);
//            ChessGameFrame.getGameController().setPlayerController(0);
      //      String path = JOptionPane.showInputDialog(this, "Input Path here");
//            getGameController().loadGameFromFile(path);
//        });
        add(loadButton);
    }
    private void addNewGameButton() {
        btn3.setIcon(image5);
        btn3.setPressedIcon(image6);
        btn3.setBorderPainted(false);
        btn3.setBounds(380,400,350,120);
        btn3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        root.add(btn3);
        // 添加按钮点击事件监听器
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                mainFrame.setVisible(true);
                dispose();
            }
        });
        add(btn3);
    }
    private void addLevel2Button(ImageIcon image) {
        btn002.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn002.setBorderPainted(false);
        btn002.setBounds(350,300,80,80);
        btn002.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn002.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level2.getNum1());
            mainFrame.gameController.setStep(Level.Level2.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn002.setVisible(false);
        add(btn002);
    }
    private void addLevel3Button(ImageIcon image) {
        btn003.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn003.setBorderPainted(false);
        btn003.setBounds(500,300,80,80);
        btn003.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn003.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level3.getNum1());
            mainFrame.gameController.setStep(Level.Level3.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn003.setVisible(false);
        add(btn003);
    }
    private void addLevel4Button(ImageIcon image) {
        btn004.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn004.setBorderPainted(false);
        btn004.setBounds(650,300,80,80);
        btn004.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn004.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level4.getNum1());
            mainFrame.gameController.setStep(Level.Level4.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn004.setVisible(false);
        add(btn004);
    }
    private void addLevel5Button(ImageIcon image) {
        btn005.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn005.setBorderPainted(false);
        btn005.setBounds(800,300,80,80);
        btn005.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn005.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level5.getNum1());
            mainFrame.gameController.setStep(Level.Level5.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn005.setVisible(false);
        add(btn005);
    }
    private void addLevel6Button(ImageIcon image) {
        btn006.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn006.setBorderPainted(false);
        btn006.setBounds(200,500,80,80);
        btn006.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn006.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level6.getNum1());
            mainFrame.gameController.setStep(Level.Level6.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn006.setVisible(false);
        add(btn006);
    }
    private void addLevel7Button(ImageIcon image) {
        btn007.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn007.setBorderPainted(false);
        btn007.setBounds(350,500,80,80);
        btn007.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn007.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level7.getNum1());
            mainFrame.gameController.setStep(Level.Level7.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn007.setVisible(false);
        add(btn007);
    }
    private void addLevel8Button(ImageIcon image) {
        btn008.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn008.setBorderPainted(false);
        btn008.setBounds(500,500,80,80);
        btn008.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn008.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level8.getNum1());
            mainFrame.gameController.setStep(Level.Level8.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn008.setVisible(false);
        add(btn008);
    }
    private void addLevel9Button(ImageIcon image) {
        btn009.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn009.setBorderPainted(false);
        btn009.setBounds(650,500,80,80);
        btn009.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn009.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level9.getNum1());
            mainFrame.gameController.setStep(Level.Level9.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn009.setVisible(false);
        add(btn009);
    }
    private void addLevel10Button(ImageIcon image) {
        btn010.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn010.setBorderPainted(false);
        btn010.setBounds(800,500,80,80);
        btn010.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn010.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level10.getNum1());
            mainFrame.gameController.setStep(Level.Level10.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn010.setVisible(false);
        add(btn010);
    }
    private void addLevel1Button(ImageIcon image) {
        btn001.setIcon( image);
        image.setImage(image.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        btn001.setBorderPainted(false);
        btn001.setBounds(200,300,80,80);
        btn001.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn001.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            mainFrame.gameController.setScoretarget(Level.Level1.getNum1());
            mainFrame.gameController.setStep(Level.Level1.getNum2());
            mainFrame.setVisible(true);
            dispose();
        });
        btn001.setVisible(false);
        add(btn001);
    }
    public void addTabeLable() {
        label.setBounds(350, 100, 380, 67);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(new Color(92, 165, 172)));
        label.setBackground(new Color(139, 216, 236));
        setLabel(label, 40, "隶书");
    }
    public void setLabel(JLabel label,int size,String name){
        label.setFont(new Font(name, Font.BOLD, size));
        label.setForeground(Color.WHITE);
        label.setVisible(false);
        add(label);
    }
}
