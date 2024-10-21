package nanakwame.ChessGame.Pieces;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.RuleSets.FixedDirectionalRules;

public class Bishop extends Piece {
    
    public Bishop(int x, int y, Owner owner) {
        super(x, y, owner);
    }

    @Override
    public HashSet<Coordinate> getMoves(Piece[][] board) {
        HashSet<Coordinate> validMoves = new HashSet<Coordinate>();
        validMoves.addAll(FixedDirectionalRules.getDiagonalMoves(this, board, FixedDirectionalRules.INFINITE_RANGE));
        return validMoves;
    }
}
