//package controller;
//
//import player.*;
////import view.ChessLoginFrame;
////import view.Chessboard;
//import view.ChessboardComponent;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class PlayerController {
//    private final ChessboardComponent chessboard;
//    private Player Player1;
//    private Player Player2;
//    private List<Player> players = new ArrayList<>();
//    private int roundType;
//    private int now;
//
//    public PlayerController(ChessboardComponent chessboard) {
//        this.chessboard = chessboard;
//        this.now = 0;
//        try {
//            List<String> playersData = Files.readAllLines(Path.of("players.json"));
//            for(String s : playersData) {
//                String[] data = s.split(" ");
//                Player player = new HumanPlayer(data[0], data[1]);
//                player.setWinCount(Integer.parseInt(data[2]));
//                player.setLoseCount(Integer.parseInt(data[3]));
//                players.add(player);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setRoundType(int roundType) {
//        this.roundType = roundType;
//        if(roundType != 0) Player1 = ChessLoginFrame.player;
//        if(roundType == 1) Player2 = new EasyAI(chessboard);
//        if(roundType == 2) Player2 = new NormalAI(chessboard);
//        if(roundType == 3) Player2 = new DifficultAI(chessboard);
//    }
//
//    public void setPlayer1(String name) {
//        for(Player player : players) {
//            if(player.getName().equals(name)) {
//                Player1 = player;
//            }
//        }
//    }
//
//    public void setPlayer2(String name) {
//        for(Player player : players) {
//            if(player.getName().equals(name)) {
//                Player2 = player;
//            }
//        }
//    }
//
//    public Player getPlayer1() {
//        return Player1;
//    }
//
//    public Player getPlayer2() {
//        return Player2;
//    }
//
//    public Player getNowPlayer() {
//        if(now == 0) return Player1;
//        else return Player2;
//    }
//
//    public int getNow() {
//        return now;
//    }
//
//    public void setNow(int now) {
//        this.now = now;
//    }
//
//    public void savePlayers() {
//        List<String> playersData = new ArrayList<>();
//        for(Player player : players) {
//            if(player.getName().equals("admin")) continue;
//            playersData.add(String.format("%s %s %d %d", player.getName(), player.getPassword(), player.getWinCount(), player.getLoseCount()));
//        }
//        try{
//            File file = new File("players.json");
//            Writer write = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
//            for (String s : playersData) {
//                s += "\n";
//                write.write(s);
//            }
//            write.flush();
//            write.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
