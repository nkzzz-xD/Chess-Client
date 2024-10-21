package nanakwame.ChessGame.Pieces;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.RuleSets.FixedDirectionalRules;

public class Queen extends Piece {

    public Queen(int x, int y, Owner owner) {
        super(x, y, owner);
    }

    @Override
    public HashSet<Coordinate> getMoves(Piece[][] board) {
        HashSet<Coordinate> validMoves = new HashSet<Coordinate>();
        validMoves.addAll(FixedDirectionalRules.getDiagonalMoves(this, board, FixedDirectionalRules.INFINITE_RANGE));
        validMoves.addAll(FixedDirectionalRules.getHorizontalMoves(this, board, FixedDirectionalRules.INFINITE_RANGE));
        return validMoves;
    }

}

