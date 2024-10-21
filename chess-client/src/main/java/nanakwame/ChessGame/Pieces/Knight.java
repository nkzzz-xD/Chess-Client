package nanakwame.ChessGame.Pieces;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.RuleSets.KnightRules;

public class Knight extends Piece {
    
    public Knight(int x, int y, Owner owner) {
        super(x, y, owner);
    }

    @Override
    public HashSet<Coordinate> getMoves(Piece[][] board) {
        return KnightRules.applyRule(this, board);
    }

}
