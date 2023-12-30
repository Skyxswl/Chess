package player;

public class HumanPlayer extends Player{
    public HumanPlayer(String name, String password) {
        super(name, password, PlayerType.HUMAN);
    }
}
