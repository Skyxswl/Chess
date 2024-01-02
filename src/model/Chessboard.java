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
                grid[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶","a","b"})));
            }
        }
        fixPieces();
    }

    private String GetNextName(String ch) {
        String pieceIcons[] = {"💎", "⚪", "▲", "🔶","a","b"};
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

    public void fixPieces() {

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

    public boolean isAlive(){
        ChessboardPoint[] points = findPossibleMove();

        if (points != null)
            return true;
        else
            return false;
    }

    private ChessboardPoint[] findPossibleMoveFromRow(int row){
        ChessboardPoint[] result = new ChessboardPoint[2];

        for (int col=0; col < Constant.CHESSBOARD_COL_SIZE.getNum() - 2; col++) {
            ChessboardPoint p1 = new ChessboardPoint(row, col);
            ChessboardPoint p2 = new ChessboardPoint(row, col+1);
            ChessboardPoint p3 = new ChessboardPoint(row, col+2);

            String name1 = getGridAt(p1).getPiece().getName();
            String name2 = getGridAt(p2).getPiece().getName();
            String name3 = getGridAt(p3).getPiece().getName();


            if (name1 == name2) {
                /*
                    +-----------------------------------------------+
                    | col-2 | col -1 | col | col+1 | col+2 | col+3  |
                    +-------+--------+-----+-------+-------+--------+
                    |       |    ?   |     |       |   ?   |        |     row-1(if row >=1): leftUp, rightUp
                    +-------+--------+-----+-------+-------+--------+
                    |  ?    |        |  X  |   X   | [p3]  |   ?    |     row:  left, right
                    +-------+--------+-----+-------+-------+--------+
                    |       |   ?    |     |       |   ?   |        |     row+1(if row < CHESSBOARD_COL_SIZE-1): leftDown, rightDown
                    +-----------------------------------------------+
                 * */

                //to check (row-1)
                if (row >=1) {
                    if (col >= 1) {
                        ChessboardPoint leftUp = new ChessboardPoint(row - 1, col-1);
                        String nameLeftUp = getGridAt(leftUp).getPiece().getName();

                        if (nameLeftUp == name1) {
                            result[0] = leftUp;
                            result[1] = new ChessboardPoint(row, col - 1);
                            return result;
                        }
                    }
                    if (col <= Constant.CHESSBOARD_COL_SIZE.getNum() - 3) {
                        ChessboardPoint rightUp = new ChessboardPoint(row-1, col+2);
                        String nameRightUp = getGridAt(rightUp).getPiece().getName();

                        if (nameRightUp == name1) {
                            result[0] = rightUp;
                            result[1] = p3;
                            return result;
                        }
                    }
                }

                //to check (row)
                {
                    if (col >= 2) {
                        ChessboardPoint left = new ChessboardPoint(row, col - 2);
                        String nameLeft = getGridAt(left).getPiece().getName();

                        if (nameLeft == name1) {
                            result[0] = left;
                            result[1] = new ChessboardPoint(row, col - 1);
                            return result;
                        }
                    }
                    if (col <= Constant.CHESSBOARD_COL_SIZE.getNum() - 4) {
                        ChessboardPoint right = new ChessboardPoint(row, col + 3);
                        String nameRight = getGridAt(right).getPiece().getName();

                        if (nameRight == name1) {
                            result[0] = right;
                            result[1] = p3;
                            return result;
                        }
                    }
                }

                //to check (row+1)
                if (row <= Constant.CHESSBOARD_ROW_SIZE.getNum()-2) {
                    System.out.println("to check row " + row);
                    if (col >= 1) {
                        ChessboardPoint leftDown = new ChessboardPoint(row + 1, col-1);
                        String nameLeftDown = getGridAt(leftDown).getPiece().getName();

                        if (nameLeftDown == name1) {
                            result[0] = leftDown;
                            result[1] = new ChessboardPoint(row, col - 1);
                            return result;
                        }
                    }
                    if (col <= Constant.CHESSBOARD_COL_SIZE.getNum() - 3) {
                        ChessboardPoint rightDown = new ChessboardPoint(row+1, col+2);
                        String nameRightDown = getGridAt(rightDown).getPiece().getName();

                        if (nameRightDown == name1) {
                            result[0] = rightDown;
                            result[1] = p3;
                            return result;
                        }
                    }
                }
            }
//            else if (name2 == name3) {
//                //不需要考虑，因为下一轮循环col++之后就会检查到了
//            }
            else if (name1 == name3) {
                /*
                    +---------------------+
                    | col | col+1 | col+2 |
                    +-----+-------+-------+
                    |     |   ?   |       |     middleUp
                    +-----+-------+-------+
                    |  X  |   []  |   X   |
                    +-----+-------+-------+
                    |     |   ?   |       |     middleDown
                    +---------------------+
                 * */
                if (row >= 1) {
                    ChessboardPoint middleUp = new ChessboardPoint(row-1, col+1);
                    String nameMiddleUp = getGridAt(middleUp).getPiece().getName();

                    if (nameMiddleUp == name1) {
                        result[0] = middleUp;
                        result[1] = p2;
                        return result;
                    }
                }

                if (row <= Constant.CHESSBOARD_ROW_SIZE.getNum() -2) {
                    ChessboardPoint middleDown = new ChessboardPoint(row+1, col+1);
                    String nameMiddleDown = getGridAt(middleDown).getPiece().getName();

                    if (nameMiddleDown == name1) {
                        result[0] = middleDown;
                        result[1] = p2;
                        return result;
                    }
                }
            }
        }
        return null;
    }


    private ChessboardPoint[] findPossibleMoveFromCol(int col) {
        ChessboardPoint[] result = new ChessboardPoint[2];


        return null;
    }

    public ChessboardPoint[] findPossibleMove(){

        for (int i=0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            ChessboardPoint[] result = findPossibleMoveFromRow(i);
            if (result != null)
                return result;
        }

        for (int i=0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            ChessboardPoint[] result = findPossibleMoveFromCol(i);
            if (result != null)
                return result;
        }

        return null;
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