package model;

import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard(int randomSeed) {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces(randomSeed);
    }

    public void Print() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                System.out.printf("%s ", grid[i][j].getPiece().getName());
            }
            System.out.printf("\n");
        }
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces(int randomSeed) {
        Random random = new Random(randomSeed);

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }
        fixPieces();
    }

    private String GetNextName(String ch) {
        String pieceIcons[] = {"💎", "⚪", "▲", "🔶"};
        for (int i = 0; i < pieceIcons.length; i++) {
            if (pieceIcons[i] == ch) {
                if (i == pieceIcons.length - 1)
                    return pieceIcons[0];
                else
                    return pieceIcons[i + 1];
            }
        }
        return pieceIcons[0];
    }

    private void fixPieces() {

        boolean bModified = false;
        do {
            bModified = false;

            Print();

            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                if (fixRow(i))
                    bModified = true;
            }

            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (fixColumn(j))
                    bModified = true;
            }
            //TODO：这里最好加一个次数控制，比如100次之后就强行跳出，避免出现意外情况时死循环
        } while (bModified);
    }

    public boolean fixRow(int row) {
        boolean isFixed = false;

        for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum() - 2; k++) {
            ChessboardPoint p1 = new ChessboardPoint(row, k);
            ChessboardPoint p2 = new ChessboardPoint(row, k + 1);
            ChessboardPoint p3 = new ChessboardPoint(row, k + 2);
            String name1 = getGridAt(p1).getPiece().getName();
            String name2 = getGridAt(p2).getPiece().getName();
            String name3 = getGridAt(p3).getPiece().getName();

//            ChessPiece pi1 = getGridAt(p1).getPiece();
//            ChessPiece pi2 = getGridAt(p2).getPiece();
//            ChessPiece pi3 = getGridAt(p3).getPiece();
//
//            if ( pi1 == pi2 && pi2 == pi3 ) {     //tried to override equals(), but didn't work :(

            if (name1 == name2 && name2 == name3) {
                String name = getGridAt(p3).getPiece().getName();
                String newName = GetNextName(name);
                getGridAt(p3).setPiece(new ChessPiece(newName));

                System.out.printf("fix row piece [%d, %d] from %s to %s\n", row, k + 2, name, newName);
                isFixed = true;
            }
        }
        return isFixed;
    }

    public boolean fixColumn(int col) {
        boolean isFixed = false;

        for (int k = 0; k < Constant.CHESSBOARD_ROW_SIZE.getNum() - 2; k++) {
            ChessboardPoint p1 = new ChessboardPoint(k, col);
            ChessboardPoint p2 = new ChessboardPoint(k + 1, col);
            ChessboardPoint p3 = new ChessboardPoint(k + 2, col);

            String name1 = getGridAt(p1).getPiece().getName();
            String name2 = getGridAt(p2).getPiece().getName();
            String name3 = getGridAt(p3).getPiece().getName();

            if (name1 == name2 && name2 == name3) {
                String name = getGridAt(p3).getPiece().getName();
                String newName = GetNextName(name);
                getGridAt(p3).setPiece(new ChessPiece(newName));
                System.out.printf("fix column piece [%d, %d] from %s to %s\n", k + 2, col, name, newName);
                isFixed = true;
            }
        }
        return isFixed;
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }


    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }
}