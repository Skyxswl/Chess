package controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileWriter;

import gameState.GameState;
import listener.GameListener;
import model.*;
import player.MusicPlayer;
import view.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static view.ChessboardComponent.isProcessing;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController extends JFrame implements GameListener, Serializable {

    ChessGameFrame mainFrame;
    public Chessboard model;
    public ChessboardComponent view;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    private int score = 0;
    private int countnext = 0;
    private int step;
    private int scoretarget;
    private int stepnum = 0;
    private boolean ifgamecontinue = true;
    private int count = 0;
    private boolean checknext = true;
    private int magicnum = 2;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        if (!checkgame()) {
            if (!ifgamecontinue) {
                windows end = new windows(400, 200);
                end.gameoverwindows();
                end.setVisible(true);
            }
        } else if (checkgame()) {
            windows end = new windows(400, 200);
            end.Endwindows();
            end.setVisible(true);
            countnext = 2;
        }
        if (!isProcessing) {
            if (!findnull() && !ifswap()) {
                checknext = true;
            } else checknext = false;
        }
        if (!checknext) {
            windows end = new windows(400, 200);
            end.nextwindows();
            end.setVisible(true);
        } else {
            model.swapChessPiece(selectedPoint, selectedPoint2);
            view.initiateChessComponent(model);
            view.repaint();
            if (!ifswap()) {
                model.swapChessPiece(selectedPoint, selectedPoint2);
                Nowindow();
                view.initiateChessComponent(model);
                view.repaint();
                selectedPoint = null;
                selectedPoint2 = null;
            } else if (ifswap() && selectedPoint != null && selectedPoint2 != null) {
                removecombination();
                selectedPoint = null;
                selectedPoint2 = null;
                view.initiateChessComponent(model);
                view.repaint();
                stepnum++;
            }
        }
    }

    @Override
    public void onPlayerNextStep() {
        ChessGameFrame.refresh();
        if (!model.isAlive()) {
            windows alive = new windows(400, 200);
            alive.alivewindow();
            alive.setVisible(true);
        }

        if (countnext == 2) {
            countnext = 0;
            if (ifswap()) {
                removecombination();
                selectedPoint = null;
                selectedPoint2 = null;
                if (!checkgame()) {
                    if (!ifgamecontinue) {
                        windows end = new windows(400, 200);
                        end.gameoverwindows();
                        end.setVisible(true);
                    }
                } else if (checkgame()) {
                    windows end = new windows(400, 200);
                    end.Endwindows();
                    end.setVisible(true);
                    countnext = 2;
                }
                view.initiateChessComponent(model);
                view.repaint();
                return;
            }
        }
        if (countnext == 0) {
            boolean ifexchange = false;
            countnext++;
            for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
                for (int j = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; j > 0; j--) {
                    ChessboardPoint p1 = new ChessboardPoint(j, i);
                    if (model.getGridAt(p1).getPiece().getName() == "") {
                        for (int k = j; k >= 0; k--) {
                            ChessboardPoint p2 = new ChessboardPoint(k, i);
                            if (model.getGridAt(p2).getPiece().getName() != "") {
                                model.swapChessPiece(p1, p2);
                                j = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1;
                                ifexchange = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!ifexchange) {
                countnext = 2;
                for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
                    for (int j = 0; j < Constant.CHESSBOARD_ROW_SIZE.getNum(); j++) {
                        ChessboardPoint p1 = new ChessboardPoint(j, i);
                        if (model.getGridAt(p1).getPiece().getName() == "") {
                            model.getGridAt(p1).setPiece(new ChessPiece(Util.RandomPick(new String[]{"a", "b", "c", "d","e","f"})));
                            MusicPlayer player = new MusicPlayer();
                            player.play("Music/eliminate.wav");
                        }
                    }
                }
            }
            selectedPoint = null;
            selectedPoint2 = null;
            view.initiateChessComponent(model);
            view.repaint();
            return;
        }
        if (countnext == 1) {
            countnext++;
            for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_ROW_SIZE.getNum(); j++) {
                    ChessboardPoint p1 = new ChessboardPoint(j, i);
                    if (model.getGridAt(p1).getPiece().getName() == "") {
                        model.getGridAt(p1).setPiece(new ChessPiece(Util.RandomPick(new String[]{"a", "b", "c", "d","e","f"})));
                    }
                }
            }
            selectedPoint = null;
            selectedPoint2 = null;
            view.initiateChessComponent(model);
            view.repaint();
        }
    }

    public void onPlayerRearrange() {
        shuffleArray();
        while (ifswap()) {
            shuffleArray();
        }
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void onPlayerHint() {
        if (!model.isAlive()) {
            windows alive = new windows(400, 200);
            alive.alivewindow();
            alive.setVisible(true);
        }
        ChessboardPoint[] result = model.findPossibleMove();
        if (result != null) {
            windows hint = new windows(400, 200);
            hint.Hintwindow(result, 400, 200);
            hint.setVisible(true);
        }
    }

    public void onPlayerMagic() {
        if (magicnum > 0) {
            if (!findnull() && !ifswap()) {
                checknext = true;
            } else checknext = false;
            if (!checknext) {
                windows end = new windows(400, 200);
                end.nextwindows();
                end.setVisible(true);
            } else {
                Random random = new Random();
                int randomNumber = random.nextInt(8) + 1;
                if(randomNumber==0){
                    countnext = 1;
                }else countnext=0;
                for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
                    ChessboardPoint p1 = new ChessboardPoint(randomNumber, i);
                    model.setChessPiece(p1, new ChessPiece(""));
                    score += 10;
                    view.initiateChessComponent(model);
                    view.repaint();
                    ChessGameFrame.refresh();
                }
                if (!checkgame()) {
                    if (!ifgamecontinue) {
                        windows end = new windows(400, 200);
                        end.gameoverwindows();
                        end.setVisible(true);
                    }
                } else if (checkgame()) {
                    windows end = new windows(400, 200);
                    end.Endwindows();
                    end.setVisible(true);
                    countnext = 2;
                }
                if (isProcessing) {
                    if ((getSelectedPoint() != null && getSelectedPoint2() != null) || findnull()) {
                        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                        scheduler.scheduleAtFixedRate(() -> {
                            onPlayerNextStep();
                            if (!findnull() && !ifswap()) {
                                scheduler.shutdown();
                            }
                        }, 1, 1, TimeUnit.SECONDS);

                    }
                }

                magicnum--;
            }
        } else {
            windows magic = new windows(400, 200);
            magic.magicwindow();
            magic.setVisible(true);
        }
    }

    public void newgame() {
        Random random = new Random(0);
        Cell[][] grid = model.getGrid();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"a", "b", "c", "d", "e", "f"})));
            }
        }
        model.setGrid(grid);
        model.fixPieces();
        view.initiateChessComponent(model);
        view.repaint();
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }
    }

    //    public static void saveGameState(String filename) {
//        try {
//            GameState.saveGameState(filename);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void saveGame() {
        File file = new File( "save/save.txt");
        var home = System.getProperty("user.dir");        Formatter fm;
        try {
            var path = Paths.get(home, "save", "save.txt");
            fm = new Formatter(path.toAbsolutePath().toString());
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint p1 = new ChessboardPoint(i, j);
                    String name = model.getGridAt(p1).getPiece().getName();
                    fm.format("%s ", name);
                }
//            try (FileWriter writer = new FileWriter(file)) {
//                String s=System.lineSeparator();
//                writer.write(s);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            }
            fm.format("%d ", score);
            fm.format("%d ", step);
            fm.format("%d ", stepnum);
            fm.format("%d " ,scoretarget);
            fm.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //    public static void loadGameState(String filename) {
//        GameController state = null;
//        try {
//            FileInputStream fileIn = new FileInputStream(filename);
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            state = (GameController) in.readObject();
//            in.close();
//            fileIn.close();
//            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
//            GameController gameController=state;
//            mainFrame.setVisible(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void loadGameFromFile() {
        String path = System.getProperty("user.dir") + "/save/save.txt";
        if (!path.endsWith(".txt")) {
            JOptionPane.showMessageDialog(null, "Error:101", "错误", JOptionPane.INFORMATION_MESSAGE);
        }
        File file = new File("save/save.txt");
        Scanner in;
        Formatter fm;
        String names;
        try {
            in = new Scanner(file);
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    names = in.next();
                    ChessboardPoint p1 = new ChessboardPoint(i, j);
                    ChessPiece piece = new ChessPiece(names);
                    model.setChessPiece(p1, piece);
                    view.initiateChessComponent(model);
                    view.repaint();
                }
            }
            model.Print();
            score=in.nextInt();
            step=in.nextInt();
            stepnum=in.nextInt();
            scoretarget=in.nextInt();
            ChessGameFrame.refresh();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean ifswap() {
        boolean ifswap = false;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum() - 2; k++) {
                ChessboardPoint p1 = new ChessboardPoint(i, k);
                ChessboardPoint p2 = new ChessboardPoint(i, k + 1);
                ChessboardPoint p3 = new ChessboardPoint(i, k + 2);
                String name1 = model.getGridAt(p1).getPiece().getName();
                String name2 = model.getGridAt(p2).getPiece().getName();
                String name3 = model.getGridAt(p3).getPiece().getName();

                if (name1 == name2 && name2 == name3 && name1 != "") {
                    ifswap = true;
                }
            }
        }
        for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            for (int k = 0; k < Constant.CHESSBOARD_ROW_SIZE.getNum() - 2; k++) {
                ChessboardPoint p1 = new ChessboardPoint(k, j);
                ChessboardPoint p2 = new ChessboardPoint(k + 1, j);
                ChessboardPoint p3 = new ChessboardPoint(k + 2, j);

                String name1 = model.getGridAt(p1).getPiece().getName();
                String name2 = model.getGridAt(p2).getPiece().getName();
                String name3 = model.getGridAt(p3).getPiece().getName();

                if (name1 == name2 && name2 == name3 && name1 != "") {
                    ifswap = true;
                }
            }
        }
        if (ifswap) {
            System.out.println("Swap confirm");
        }
        return ifswap;
    }

    //create a window to imform player you can't swap here.
    public void Nowindow() {
        windows no = new windows(400, 200);
        no.Nowindows();
        no.setVisible(true);
    }

    //this method is used for remove the combination that can be removed;
    public void removecombination() {
        boolean[][] count = new boolean[8][8];
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum() - 2; k++) {
                ChessboardPoint p1 = new ChessboardPoint(i, k);
                ChessboardPoint p2 = new ChessboardPoint(i, k + 1);
                ChessboardPoint p3 = new ChessboardPoint(i, k + 2);
                String name1 = model.getGridAt(p1).getPiece().getName();
                String name2 = model.getGridAt(p2).getPiece().getName();
                String name3 = model.getGridAt(p3).getPiece().getName();

                if (name1 == name2 && name2 == name3) {
                    count[i][k] = true;
                    count[i][k + 1] = true;
                    count[i][k + 2] = true;
                }
            }
        }
        for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            for (int k = 0; k < Constant.CHESSBOARD_ROW_SIZE.getNum() - 2; k++) {
                ChessboardPoint p1 = new ChessboardPoint(k, j);
                ChessboardPoint p2 = new ChessboardPoint(k + 1, j);
                ChessboardPoint p3 = new ChessboardPoint(k + 2, j);

                String name1 = model.getGridAt(p1).getPiece().getName();
                String name2 = model.getGridAt(p2).getPiece().getName();
                String name3 = model.getGridAt(p3).getPiece().getName();

                if (name1 == name2 && name2 == name3) {
                    count[k][j] = true;
                    count[k + 1][j] = true;
                    count[k + 2][j] = true;
                }
            }
        }
        int num = 0;
        for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_ROW_SIZE.getNum(); j++) {
                if (count[i][j]) {
                    num++;
                    ChessboardPoint p1 = new ChessboardPoint(i, j);
                    model.setChessPiece(p1, new ChessPiece(""));
                    System.out.println("remove component at" + " (" + i + "," + j + ")");
                }
            }
        }
        MusicPlayer player = new MusicPlayer();
        player.play("Music/eliminate.wav");
        score = score + num * 10;
    }
//    public void setPlayerController(int roundType) {
//        view.playerController.setRoundType(roundType);
//    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setScoretarget(int scoretarget) {
        this.scoretarget = scoretarget;
    }

    public int getScore() {
        return score;
    }

    public int getCountnext() {
        return countnext;
    }

    public int getStep() {
        return step;
    }

    public int getScoretarget() {
        return scoretarget;
    }

    public int getStepnum() {
        return stepnum;
    }

    private boolean checkgame() {
        System.out.println(score);
        System.out.println(stepnum);
        if (score >= scoretarget && stepnum <= step) {
            return true;
        } else if (stepnum == step) {
            ifgamecontinue = false;
            return false;
        }
        return false;
    }

    public Chessboard getModel() {
        return model;
    }

    public String getlevel() {
        for (Level c : Level.values()) {
            if (c.getNum1() == scoretarget && c.getNum2() == step) {
                return c.name();
            }
        }
        return null;
    }

    public void refresh() {
        step = 0;
        stepnum = 0;
        scoretarget = 0;
        score = 0;
    }

    public void shuffleArray() {
        // 创建一个空的列表，用于存放所有的元素
        Cell[][] grid = model.getGrid();
        List list = new ArrayList<>();
        // 遍历二维数组的每个元素，添加到列表中
        for (Cell[] row : grid) {
            for (Cell num : row) {
                list.add(num);
            }
        }
        // 使用 Collections.shuffle 方法打乱列表中的元素
        Collections.shuffle(list);
        // 遍历二维数组的每个位置，从列表中弹出一个元素，放到二维数组中
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = (Cell) list.get(i * Constant.CHESSBOARD_ROW_SIZE.getNum() + j);
            }
        }
        model.setGrid(grid);
    }

    public boolean findnull() {
        Cell[][] grid = model.getGrid();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece().getName() == "") {
                    return true;
                }
            }
        }
        return false;
    }

    public ChessboardPoint getSelectedPoint() {
        return selectedPoint;
    }

    public ChessboardPoint getSelectedPoint2() {
        return selectedPoint2;
    }

    public void setMagicnum(int magicnum) {
        this.magicnum = magicnum;
    }
}
