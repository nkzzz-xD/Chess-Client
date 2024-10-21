package nanakwame.ChessGame.ChessBoard;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.Piece;

public class Board {
    protected static final int BOARD_SIZE = 8;
    private Piece[][] piecePostions;
    private Owner currentPlayer;

    public Board(){
        piecePostions = new Piece[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = Owner.PLAYER_1;
        BoardDeserializer.configureBoard(piecePostions, BoardDeserializer.DEFAULT_BOARD_CONFIG);
        System.out.println(piecePostions[0][2].getMoves(piecePostions));
        // for (Piece[] pieces : piecePostions) {
        //     for (Piece piece : pieces) {
        //         System.out.println(piece);
        //     }
        // }
    }

    public String boardToText(){
        return "";
    }

    public Piece getPiece(int x, int y) {
        return piecePostions[y][x];
    }

    public HashSet<Coordinate> getMoves(int x, int y) {
        Piece currentPiece = getPiece(x, y);
        return currentPiece == null ? new HashSet<Coordinate>() : currentPiece.getMoves(piecePostions);
    }

    public Owner getCurrentPlayer() {
        return currentPlayer;
    }

    // private void incrementCount(int x , int y){
    //     if((x + 1) >= 8)
    // }
}
