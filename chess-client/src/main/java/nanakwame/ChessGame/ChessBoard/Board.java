package nanakwame.ChessGame.ChessBoard;

import java.util.HashSet;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.Pieces.Pawn;
import nanakwame.ChessGame.Pieces.Piece;

public class Board {
    protected static final int BOARD_SIZE = 8;
    private Piece[][] piecePostions;
    private Owner currentPlayer;

    private Piece selectedPiece;
    private HashSet<Coordinate> availableMoves;

    public Board(){
        piecePostions = new Piece[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = Owner.PLAYER_1;
        availableMoves = new HashSet<Coordinate>();
        BoardDeserializer.configureBoard(piecePostions, BoardDeserializer.DEFAULT_BOARD_CONFIG);
        System.out.println(piecePostions[0][2].getMoves(piecePostions));
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

    public Piece[][] getPieces() {
        return piecePostions;
    }

    public void resetAvailableMoves() {
        availableMoves = new HashSet<Coordinate>();
        selectedPiece = null;
    }
    // private void incrementCount(int x , int y){
    //     if((x + 1) >= 8)
    // }

    public HashSet<Coordinate> getAvailableMoves() {
        return availableMoves;
    }

    public void updateAvailableMoves(int x, int y) {
        try {
            availableMoves = getMoves(x, y);
            selectedPiece = getPiece(x, y);
            System.out.println("Available moves updated. " + availableMoves.size() + " available moves");
            System.out.println("Selected piece: " + selectedPiece);
            for (Coordinate coordinate : availableMoves) {
                System.out.println(coordinate);
            }
            System.out.println("Current piece " + getPiece(x, y));
        }
        catch (IndexOutOfBoundsException e){}
    }

    public boolean move(int x, int y) {
        System.out.println("Selected piece " + selectedPiece);
        System.out.println(availableMoves);
        Coordinate targetCoord = new Coordinate(x, y);
        if (availableMoves.contains(targetCoord) && selectedPiece != null) {
            System.out.println("Move is valid");
            piecePostions[y][x] = selectedPiece;
            piecePostions[selectedPiece.getPosition().getY()][selectedPiece.getPosition().getX()] = null;
            selectedPiece.setPosition(new Coordinate(x, y));
            if (selectedPiece instanceof Pawn) {
                ((Pawn)selectedPiece).setMoved();
            }
            selectedPiece = null;
            currentPlayer = currentPlayer == Owner.PLAYER_1 ? Owner.PLAYER_2 : Owner.PLAYER_1;
            resetAvailableMoves();
            return true;
        }
        return false;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }
}
