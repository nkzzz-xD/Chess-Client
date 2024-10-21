package nanakwame.ChessGame.Pieces.RuleSets;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.Piece;

public class PawnRules {

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
                //prevent from trying to take pieces straight on
                if(potentialMove.getOwner() != pieceOwner && Math.min(Math.abs(dx),Math.abs(dy)) != 0) {
                    setToUpdate.add(new Coordinate(x, y));
                    return;
                }
                else return;
            }
            //to make sure diagonal empty positions aren't added
            else if (Math.min(Math.abs(dx),Math.abs(dy)) == 0) {
                setToUpdate.add(new Coordinate(x, y));
            }
            //didnt want to hardcode to 1
            if (currentRange++ >= maxRange) {
                return;
            }
            x += dx;
            y += dy;
        }
    }

    public static HashSet<Coordinate> getDiagonalMoves(Piece currentPiece, Piece[][] board){
        return applyRule(currentPiece, board, 1, currentPiece.getOwner() == Owner.PLAYER_1 ? 
        new int[][]{{1,-1},{-1,-1}}: new int[][]{{-1,1},{1,1}});
    }

    public static HashSet<Coordinate> getStraightMoves(Piece currentPiece, Piece[][] board, int maxRange){
        return applyRule(currentPiece, board, maxRange, currentPiece.getOwner() == Owner.PLAYER_1 ? 
        new int[][]{{0,-1}}: new int[][]{{0,1}});
    }
}

