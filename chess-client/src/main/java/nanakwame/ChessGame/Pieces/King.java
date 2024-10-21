package nanakwame.ChessGame.Pieces;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.RuleSets.FixedDirectionalRules;

public class King extends Piece {

    public King(int x, int y, Owner owner) {
        super(x, y, owner);
    }

    @Override
    public HashSet<Coordinate> getMoves(Piece[][] board) {
        HashSet<Coordinate> validMoves = new HashSet<Coordinate>();
        validMoves.addAll(FixedDirectionalRules.getDiagonalMoves(this, board, 1));
        validMoves.addAll(FixedDirectionalRules.getHorizontalMoves(this, board, 1));
        return validMoves;
    }

    
    
}

