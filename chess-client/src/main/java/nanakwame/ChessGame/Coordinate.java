package nanakwame.ChessGame;

import java.util.Objects;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        // System.out.println("Equals being run");
        return obj instanceof Coordinate && ((Coordinate)obj).x == x && ((Coordinate)obj).y == y;
    }

    @Override
    public String toString(){
        return getClass().getName() + " [x = " + x + ", y = "+ y +"]";
    }

     @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
