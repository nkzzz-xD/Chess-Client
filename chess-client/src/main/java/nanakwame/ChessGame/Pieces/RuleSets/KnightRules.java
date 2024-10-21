package nanakwame.ChessGame.Pieces.RuleSets;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.Piece;

public class KnightRules {
    
    public static HashSet<Coordinate> applyRule(Piece currentPiece, Piece[][] board) {
        HashSet<Coordinate> validMoves = new HashSet<Coordinate>();
        int[][] directions = {{1,2},{2,1},{1,-2},{-2,1},{-1,2},{2,-1},{-1,-2},{-2,-1}};
        for (int[] direction : directions){
            //TODO Handle index out of bounds exceptions.
            checkDirection(direction[0], direction[1], currentPiece,board, validMoves);
        }
        return validMoves;
    }

    private static void checkDirection(int dx, int dy, Piece currentPiece, Piece[][] board, HashSet<Coordinate> setToUpdate) {
        Owner pieceOwner = currentPiece.getOwner();
        int x = currentPiece.getPosition().getX() + dx;
        int y = currentPiece.getPosition().getY() + dy;
        if (x < 8 && x >= 0 && y < 8 && y >= 0) {
            Piece potentialMove = board[y][x];
            if (potentialMove != null) {
                if(potentialMove.getOwner() != pieceOwner) {
                    setToUpdate.add(new Coordinate(x, y));
                }
            }
            else setToUpdate.add(new Coordinate(x, y));
        }
    }
}
