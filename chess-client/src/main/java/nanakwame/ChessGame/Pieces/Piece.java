package nanakwame.ChessGame.Pieces;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;

public abstract class Piece {
    private Coordinate position;
    private Owner owner;

    public Piece(int x, int y, Owner owner){
        position = new Coordinate(x, y);
        this.owner = owner;
    }

    //TODO Check whether to use abstract class and also whether this is necessary
    public Coordinate getPosition(){
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public Owner getOwner(){
        return owner;
    }

    public abstract HashSet<Coordinate> getMoves(Piece[][] board);

    @Override
    public String toString() {
        return getClass().getName() + ": [x = " + position.getX() + ", y = " + position.getY() + ", Owner = " + owner + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && owner.equals(((Piece)obj).getOwner()) && position.equals(((Piece)obj).getPosition());
    }
}
