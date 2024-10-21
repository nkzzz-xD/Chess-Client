package nanakwame.ChessGame.Pieces;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.RuleSets.FixedDirectionalRules;

public class Rook extends Piece {

    public Rook(int x, int y, Owner owner) {
        super(x, y, owner);
    }

    @Override
    public HashSet<Coordinate> getMoves(Piece[][] board) {
        HashSet<Coordinate> validMoves = new HashSet<Coordinate>();
        validMoves.addAll(FixedDirectionalRules.getHorizontalMoves(this, board, FixedDirectionalRules.INFINITE_RANGE));
        return validMoves;
    }
}
