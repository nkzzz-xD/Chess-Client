package nanakwame.ChessGame.Pieces;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.RuleSets.PawnRules;

public class Pawn extends Piece {
    //TODO Remember to set moved to true
    private boolean moved;

    public Pawn(int x, int y, Owner owner) {
        super(x, y, owner);
        moved = false;
    }

    @Override
    public HashSet<Coordinate> getMoves(Piece[][] board) {
        HashSet<Coordinate> validMoves = new HashSet<Coordinate>();
        validMoves.addAll(PawnRules.getDiagonalMoves(this, board));
        int maxRangeForForwardMovement = 1;
        if (!moved) {
            //TODO Remember to set moved to true when it is moved
            maxRangeForForwardMovement = 2;
        }
        validMoves.addAll(PawnRules.getStraightMoves(this, board, maxRangeForForwardMovement));
        return validMoves;
    }
    
}
