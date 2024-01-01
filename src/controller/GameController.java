package controller;

import java.io.FileWriter;

import gameState.GameState;
import listener.GameListener;
import model.*;
import view.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener, Serializable {

    private Chessboard model;
    private ChessboardComponent view;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    private int score = 0;
    private int countnext = 0;

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
        }
        System.out.println("OK");

    }

    @Override
    public void onPlayerNextStep() {
        if (countnext == 2) {
            countnext = 0;
            if (ifswap()) {
                removecombination();
                selectedPoint = null;
                selectedPoint2 = null;
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
                            model.getGridAt(p1).setPiece(new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
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
                        model.getGridAt(p1).setPiece(new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
                    }
                }
            }
            selectedPoint = null;
            selectedPoint2 = null;
            view.initiateChessComponent(model);
            view.repaint();
        }
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
        File file = new File("/Users/huyufei/Desktop/save.txt");
        var home = System.getProperty("user.home");
        Formatter fm;
        try {
            var path = Paths.get(home, "Desktop", "save.txt");
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
    public void loadGameFromFile(String path) {
//        path="/Users/huyufei/Desktop/save.txt";
        if (!path.endsWith(".txt")) {
            JOptionPane.showMessageDialog(null, "Error:101", "错误", JOptionPane.INFORMATION_MESSAGE);
        }
        File file = new File(path);
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
        score = score + num * 10;
    }
//    public void setPlayerController(int roundType) {
//        view.playerController.setRoundType(roundType);
//    }
}
