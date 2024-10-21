package nanakwame.ChessGame.Pieces.RuleSets;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.Piece;

public class FixedDirectionalRules {
    public static final int INFINITE_RANGE = -1;
    
    private static HashSet<Coordinate> applyRule(Piece currentPiece, Piece[][] board, int maxRange, int[][] directions) {
        HashSet<Coordinate> validMoves = new HashSet<Coordinate>();
        for (int[] direction : directions){
            //TODO Handle index out of bounds exceptions.
            checkDirection(direction[0], direction[1], currentPiece,board,maxRange, validMoves);
        }
        return validMoves;
    }

    private static void checkDirection(int dx, int dy, Piece currentPiece, Piece[][] board, int maxRange, HashSet<Coordinate> setToUpdate) {
        Owner pieceOwner = currentPiece.getOwner();
        int x = currentPiece.getPosition().getX();
        int y = currentPiece.getPosition().getY();
        x += dx;
        y += dy;
        int currentRange = 1;
        while (x < 8 && x >= 0 && y < 8 && y >= 0) {
            Piece potentialMove = board[y][x];
            if (potentialMove != null) {
                if(potentialMove.getOwner() != pieceOwner) {
                    setToUpdate.add(new Coordinate(x, y));
                    return;
                }
                else return;
            }
            else setToUpdate.add(new Coordinate(x, y));
            //didnt want to hardcode to 1
            if (maxRange != INFINITE_RANGE && currentRange++ >= maxRange) {
                return;
            }
            x += dx;
            y += dy;
        }
    }

    public static HashSet<Coordinate> getDiagonalMoves(Piece currentPiece, Piece[][] board, int maxRange){
        return applyRule(currentPiece, board, maxRange, new int[][]{
            {1,1},{1,-1},{-1,1},{-1,-1}
        });
    }

    public static HashSet<Coordinate> getHorizontalMoves(Piece currentPiece, Piece[][] board, int maxRange){
        return applyRule(currentPiece, board, maxRange, new int[][]{
            {1,0},{-1,0},{0,1},{0,-1}
        });
    }
}
