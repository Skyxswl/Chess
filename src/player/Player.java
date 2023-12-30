package player;

//import model.Action;
import view.ChessboardComponent;

public abstract class Player {
    private final String name;
    private final String password;
    private int winCount;
    private int loseCount;
    private final PlayerType playerType;
    protected ChessboardComponent chessboard;

    public Player(String name, String password, PlayerType playerType) {
        this.name = name;
        this.password = password;
        this.playerType = playerType;
    }

//    public Action calculateNextStep() {
//        return null;
//    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public void addWinCount() {
        this.winCount++;
    }

    public void addLoseCount() {
        this.loseCount++;
    }
}
