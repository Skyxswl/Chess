package gameState;
import model.Chessboard;
import view.ChessboardComponent;
import java.io.*;

public class GameState implements Serializable {
    private Chessboard model;
    private ChessboardComponent view; // 棋盘状态

    public Chessboard getModel() {
        return model;
    }

    public void setModel(Chessboard model) {
        this.model = model;
    }

    public ChessboardComponent getView() {
        return view;
    }

    public void setView(ChessboardComponent view) {
        this.view = view;
    }


    public GameState(Chessboard model, ChessboardComponent view) {
        this.model = model;
        this.view = view;
    }


    // 其他局内参数...

    // 构造函数、getter和setter...


    public static void saveGameState(GameState state, String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(state);
            out.close();
            fileOut.close();
            System.out.println("游戏状态已保存！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadGameState(String filename) {
        GameState state = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            state = (GameState) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
