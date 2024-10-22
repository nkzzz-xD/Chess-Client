package nanakwame.GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import nanakwame.ChessGame.Coordinate;
import nanakwame.ChessGame.ChessBoard.Board;
import nanakwame.ChessGame.Pieces.Piece;

public class GameBoard extends JPanel{

    private static GameBoard instance;
    private static final int DEFAULT_SIDE_LENGTH = 720;
    private static final Color PLAYER_1_COLOR = new Color(235,236,208);
    private static final Color PLAYER_2_COLOR = new Color(116,79,64);
    private static final Color SELECTION_COLOR = new Color(204, 96, 121);
    private static final Color HIGHLIGHT_COLOR = new Color(121, 128, 166);
    private static final int DEFAULT_SQUARE_PADDING = 10;
    private static final Color POTENTIAL_MOVE_COLOR = new Color(98, 222, 104);
    

    private int squareLength;
    private int squarePadding;
    private Board logicalBoard;

    private Coordinate hoveredCellCoordinate = new Coordinate(-1, -1);
    private Coordinate selectedCellCoordinate = new Coordinate(-1, -1);

    //Represents selction mode(true) or deselecting mode (false)
    private boolean selecting;

    public GameBoard() {
        setPreferredSize(new Dimension(DEFAULT_SIDE_LENGTH, DEFAULT_SIDE_LENGTH));
        setBackground(POTENTIAL_MOVE_COLOR);
        setMaximumSize(new Dimension(DEFAULT_SIDE_LENGTH, DEFAULT_SIDE_LENGTH));
        //TODO Make sure that when the window is resized the gameboard is always a multiple of 8
        squareLength = DEFAULT_SIDE_LENGTH / 8;
        logicalBoard = new Board();
        squarePadding = DEFAULT_SQUARE_PADDING;
        selecting = true;
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int columnIndex = (e.getX() - 1) / squareLength;
                int rowIndex = (e.getY() - 1) / squareLength;
                columnIndex = placeIndexinBounds(columnIndex);
                rowIndex = placeIndexinBounds(rowIndex);

                if (hoveredCellCoordinate.getX() == columnIndex && hoveredCellCoordinate.getY() == rowIndex) {
                    // if they moved within the same cell, there is no need to carry out any update
                    return;
                }
                else if (logicalBoard.getPiece(columnIndex, rowIndex) != null && 
                    logicalBoard.getPiece(columnIndex, rowIndex).getOwner()  == logicalBoard.getCurrentPlayer() &&
                    !logicalBoard.getPiece(columnIndex, rowIndex).getMoves(logicalBoard.getPieces()).isEmpty()){
                        hoveredCellCoordinate = new Coordinate(columnIndex, rowIndex);
                }
                else {
                    hoveredCellCoordinate = new Coordinate(-1, -1);
                }
                // selecting = false;
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) return;
                int columnIndex = (e.getX() - 1) / squareLength;
                int rowIndex = (e.getY() - 1) / squareLength;
                columnIndex = placeIndexinBounds(columnIndex);
                rowIndex = placeIndexinBounds(rowIndex);
                if (columnIndex != selectedCellCoordinate.getX() || rowIndex != selectedCellCoordinate.getY()) {
                    hoveredCellCoordinate = new Coordinate(-1, -1);
                    selectedCellCoordinate = new Coordinate(-1, -1);
                    selecting = true;
                    logicalBoard.resetAvailableMoves();
                    repaint();
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredCellCoordinate = new Coordinate(-1, -1);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) return;
                int columnIndex = (e.getX() - 1) / squareLength;
                int rowIndex = (e.getY() - 1) / squareLength;
                columnIndex = placeIndexinBounds(columnIndex);
                rowIndex = placeIndexinBounds(rowIndex);
                //TODO make this bool a predicate so I can use it here and in the released method
                Piece piece = logicalBoard.getPiece(columnIndex, rowIndex);
                boolean bool = selecting && (piece != null && piece.getOwner() == logicalBoard.getCurrentPlayer() && !piece.getMoves(logicalBoard.getPieces()).isEmpty()) ||
                    logicalBoard.getAvailableMoves().contains(new Coordinate(columnIndex, rowIndex));
                if(columnIndex == selectedCellCoordinate.getX() && rowIndex == selectedCellCoordinate.getY()) {
                    selecting = false;
                }
                else if (bool) {
                    selectedCellCoordinate = new Coordinate(columnIndex, rowIndex);
                }
                // else selectedCellCoordinate = new Coordinate(-1, -1);
                // logicalBoard.updateAvailableMoves(selectedCellCoordinate.getX(), selectedCellCoordinate.getY());     
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    System.out.println("Mouse button not 3");
                    return;
                }
                int columnIndex = (e.getX() - 1) / squareLength;
                int rowIndex = (e.getY() - 1) / squareLength;
                columnIndex = placeIndexinBounds(columnIndex);
                rowIndex = placeIndexinBounds(rowIndex);
                if (columnIndex == selectedCellCoordinate.getX() && rowIndex == selectedCellCoordinate.getY()) {
                    Piece piece = logicalBoard.getPiece(columnIndex, rowIndex);
                    boolean bool = selecting && (piece != null && piece.getOwner() == logicalBoard.getCurrentPlayer() && !piece.getMoves(logicalBoard.getPieces()).isEmpty()) ||
                    logicalBoard.getAvailableMoves().contains(new Coordinate(columnIndex, rowIndex));
                    if (bool) {
                        System.out.println("Attempt to move");
                        if (logicalBoard.move(columnIndex, rowIndex)) {
                            System.out.println("Move executed");
                            selectedCellCoordinate = new Coordinate(-1, -1);
                            logicalBoard.resetAvailableMoves();
                        }
                        else logicalBoard.updateAvailableMoves(selectedCellCoordinate.getX(),selectedCellCoordinate.getY());
                        selecting = true;
                        repaint();
                        return;
                    }
                }
                selectedCellCoordinate = new Coordinate(-1, -1);
                // selecting = true;
                logicalBoard.resetAvailableMoves();
                selecting = true;
                repaint();
            }
        });
        instance = this;
    }

    private int placeIndexinBounds(int index) {
        if (index < 0) {
            index = 0;
        } else if (index >= 8) {
            index = 7;
        }
        return index;
    }

    public static GameBoard getInstance() {
        return instance == null ? new GameBoard() : instance;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;
        // Graphics graphicsOriginal = g;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Coordinate thisCoord = new Coordinate(x, y);
                // System.out.println("This coord " + thisCoord);
                Piece currentPiece = logicalBoard.getPiece(x,y);
                //TODO Change the opacity instead of the colour
                if (x == selectedCellCoordinate.getX() && y == selectedCellCoordinate.getY() && currentPiece != null) {
                    graphics.setColor(SELECTION_COLOR);
                } else if (x == hoveredCellCoordinate.getX() && y == hoveredCellCoordinate.getY() && currentPiece != null) {
                    graphics.setColor(HIGHLIGHT_COLOR);
                }
                else if (y % 2 != x % 2 ) {
                    graphics.setColor(PLAYER_2_COLOR);
                }
                else graphics.setColor(PLAYER_1_COLOR);
                if (logicalBoard.getAvailableMoves().contains(thisCoord)) {
                    // graphics.setColor(POTENTIAL_MOVE_COLOR);
                    graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% transparency 
                    System.out.println("Composite set");
                }
                graphics.fillRect(x * squareLength, y * squareLength, squareLength, squareLength);
                graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                
                if (currentPiece == null) continue;
                // System.out.println(currentPiece.getOwner().name() + "_" + currentPiece.getClass().getSimpleName());
                BufferedImage img = Images.getTexture(currentPiece.getOwner().name() + "_" + currentPiece.getClass().getSimpleName());
                if (img != null) {
                    // System.out.println("Image drawn");
                    g.drawImage(img, x * squareLength + squarePadding, y * squareLength + squarePadding, 
                        squareLength - 2 * squarePadding, squareLength - 2 * squarePadding, this);
                }
            }
        }
    }
}
