package nanakwame.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import javax.swing.JPanel;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.ChessBoard.Board;
import nanakwame.ChessGame.Pieces.Piece;

public class GameBoard extends JPanel{

    private static GameBoard instance;
    private static final int DEFAULT_SIDE_LENGTH = 800;
    private static final Color PLAYER_1_COLOR = new Color(235,236,208);
    private static final int DEFAULT_SQUARE_PADDING = 10;
    private static final Color PLAYER_2_COLOR = new Color(116,79,64);

    private int squareLength;
    private int squarePadding;
    private Board logicalBoard;

    private int hoveredCellX,hoveredCellY = -1;
    private int selectedCellX,selectedCellY = -1;
    //Represents selction mode(true) or deselecting mode (false)
    private boolean selecting;

    private HashSet<Coordinate> availableMoves;

    public GameBoard() {
        setPreferredSize(new Dimension(DEFAULT_SIDE_LENGTH, DEFAULT_SIDE_LENGTH));
        setBackground(Color.RED);
        setMaximumSize(new Dimension(DEFAULT_SIDE_LENGTH, DEFAULT_SIDE_LENGTH));
        //TODO Make sure that when the window is resized the gameboard is always a multiple of 8
        squareLength = DEFAULT_SIDE_LENGTH / 8;
        logicalBoard = new Board();
        squarePadding = DEFAULT_SQUARE_PADDING;
        selecting = true;
        availableMoves = new HashSet<Coordinate>();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int columnIndex = (e.getX() - 1) / squareLength;
                int rowIndex = (e.getY() - 1) / squareLength;
                if (rowIndex < 0) {
                    rowIndex = 0;
                }
                if (columnIndex < 0) {
                    columnIndex = 0;
                }

                if (hoveredCellX == columnIndex && hoveredCellY == rowIndex) {
                    // if they moved within the same cell, there is no need to carry out any update
                    return;
                }
                else if (logicalBoard.getPiece(columnIndex, rowIndex) != null && 
                    logicalBoard.getPiece(columnIndex, rowIndex).getOwner()  == logicalBoard.getCurrentPlayer()){
                    hoveredCellX = columnIndex;
                    hoveredCellY = rowIndex;
                }
                else {
                    hoveredCellX = -1;
                    hoveredCellY = -1;
                }
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredCellX = -1;
                hoveredCellY = -1;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int columnIndex = (e.getX() - 1) / squareLength;
                int rowIndex = (e.getY() - 1) / squareLength;
                if (rowIndex < 0) {
                    rowIndex = 0;
                }
                if (columnIndex < 0) {
                    columnIndex = 0;
                }
                if(columnIndex == selectedCellX && rowIndex == selectedCellY) {
                    selecting = false;
                }
                if (logicalBoard.getPiece(columnIndex, rowIndex) != null && 
                    logicalBoard.getPiece(columnIndex, rowIndex).getOwner() == logicalBoard.getCurrentPlayer()) {
                    selectedCellX = columnIndex;
                    selectedCellY = rowIndex; 
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int columnIndex = (e.getX() - 1) / squareLength;
                int rowIndex = (e.getY() - 1) / squareLength;
                if (rowIndex < 0) {
                    rowIndex = 0;
                }
                if (columnIndex < 0) {
                    columnIndex = 0;
                }
                if (columnIndex == selectedCellX && rowIndex == selectedCellY) {
                    if(!selecting) {
                        selectedCellX = -1;
                        selectedCellY = -1;
                        selecting = true;
                        availableMoves = new HashSet<Coordinate>();
                    }
                    else {
                        updateAvailableMoves();
                    }
                }
                else {
                    selectedCellX = -1;
                    selectedCellY = -1;
                    availableMoves = new HashSet<Coordinate>();
                }
                repaint();
            }
        });
    }

    public static GameBoard getInstance() {
        return instance == null ? instance = new GameBoard() : instance;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("Repainting");
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Coordinate thisCoord = new Coordinate(x, y);
                // System.out.println("This coord " + thisCoord);
                Piece currentPiece = logicalBoard.getPiece(x,y);
                //TODO Change the opacity instead of the colour
                if (availableMoves.contains(thisCoord)) {
                    System.out.println("Contains");
                    g.setColor(Color.GREEN);
                }
                else if (x == selectedCellX && y == selectedCellY && currentPiece != null) {
                    g.setColor(Color.RED);
                } else if (x == hoveredCellX && y == hoveredCellY && currentPiece != null) {
                    //TODO Store a hover colour
                    g.setColor(Color.CYAN);
                }
                else if (y % 2 != x % 2 ) {
                    g.setColor(PLAYER_2_COLOR);
                }
                else g.setColor(PLAYER_1_COLOR);
                g.fillRect(x * squareLength, y * squareLength, squareLength, squareLength);
                if (currentPiece == null) continue;
                // System.out.println(currentPiece.getOwner().name() + "_" + currentPiece.getClass().getSimpleName());
                BufferedImage img = Textures.getTexture(currentPiece.getOwner().name() + "_" + currentPiece.getClass().getSimpleName());
                if (img != null) {
                    // System.out.println("Image drawn");
                    g.drawImage(img, x * squareLength + squarePadding, y * squareLength + squarePadding, 
                        squareLength - 2 * squarePadding, squareLength - 2 * squarePadding, this);
                }
            }
        }
    }

    public void updateAvailableMoves() {
        availableMoves = logicalBoard.getMoves(selectedCellX, selectedCellY);
        System.out.println("Available moves updated. " + availableMoves.size() + " available moves");
        for (Coordinate coordinate : availableMoves) {
            System.out.println(coordinate);
        }
        System.out.println("Current piece " + logicalBoard.getPiece(selectedCellX, selectedCellY));
    }
}
