import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.ChessBoard.BoardDeserializer;
import nanakwame.ChessGame.ChessBoard.Exceptions.BoardDeserializeException;
import nanakwame.ChessGame.Pieces.Bishop;
import nanakwame.ChessGame.Pieces.King;
import nanakwame.ChessGame.Pieces.Knight;
import nanakwame.ChessGame.Pieces.Pawn;
import nanakwame.ChessGame.Pieces.Piece;
import nanakwame.ChessGame.Pieces.Queen;
import nanakwame.ChessGame.Pieces.Rook;

public class BoardDeserializeTests {

    private static Piece[][] correctBoard = new Piece[8][8];

    @BeforeEach
    public void initialiseComparisonBoard() {
        correctBoard = new Piece[8][8];
    }

    @Test
    public void testInvalidShapedBoards() {
        BoardDeserializeException exception = assertThrows(BoardDeserializeException.class, () -> BoardDeserializer.configureBoard(new Piece[2][8], "RRR"));
        assertEquals("Invalid board provided: Invalid number of rows. Expected 8 rows but: 2 present.",exception.getMessage());
        exception = assertThrows(BoardDeserializeException.class, () -> BoardDeserializer.configureBoard(new Piece[8][10], "RK"));
        assertEquals("Invalid board provided: Row 1 has invalid number of columns. Expected 8 columns but 10 present.", exception.getMessage());
        //message should be based on the size of array first and not invalid characters
        exception = assertThrows(BoardDeserializeException.class, () -> BoardDeserializer.configureBoard(new Piece[8][11], "jjldsf"));
        assertEquals("Invalid board provided: Row 1 has invalid number of columns. Expected 8 columns but 11 present.", exception.getMessage());
    }

    @Test
    public void testEmptyString() {
        BoardDeserializer.configureBoard(correctBoard, "");
        assertTrue(Arrays.deepEquals(correctBoard, new Piece[8][8]));
        //empty string should overwrite old board.
        correctBoard[0][0] = new Rook(0, 0, Owner.PLAYER_1);
        BoardDeserializer.configureBoard(correctBoard, "");
        for (Piece[] pieces : correctBoard) {
            for (Piece piece : pieces) {
                System.out.println(piece);
            }
        }
        assertTrue(Arrays.deepEquals(correctBoard, new Piece[8][8]));
    }

    //"RkBQKBkR(8P)(32.)1(8P)RkBQKBkR";
    @Test
    public void testDefaultConfig() {
        correctBoard[0][0] = new Rook(0, 0, Owner.PLAYER_2);
        correctBoard[0][1] = new Knight(1, 0, Owner.PLAYER_2);
        correctBoard[0][2] = new Bishop(2, 0, Owner.PLAYER_2);
        correctBoard[0][3] = new Queen(3, 0, Owner.PLAYER_2);
        correctBoard[0][4] = new King(4, 0, Owner.PLAYER_2);
        correctBoard[0][5] = new Bishop(5, 0, Owner.PLAYER_2);
        correctBoard[0][6] = new Knight(6, 0, Owner.PLAYER_2);
        correctBoard[0][7] = new Rook(7, 0, Owner.PLAYER_2);
        for (int i = 0; i <8; i++) {
            correctBoard[1][i] = new Pawn(i, 1, Owner.PLAYER_2);
            correctBoard[6][i] = new Pawn(i, 6, Owner.PLAYER_1);
        }
        correctBoard[7][0] = new Rook(0, 7, Owner.PLAYER_1);
        correctBoard[7][1] = new Knight(1, 7, Owner.PLAYER_1);
        correctBoard[7][2] = new Bishop(2, 7, Owner.PLAYER_1);
        correctBoard[7][3] = new Queen(3, 7, Owner.PLAYER_1);
        correctBoard[7][4] = new King(4, 7, Owner.PLAYER_1);
        correctBoard[7][5] = new Bishop(5, 7, Owner.PLAYER_1);
        correctBoard[7][6] = new Knight(6, 7, Owner.PLAYER_1);
        correctBoard[7][7] = new Rook(7, 7, Owner.PLAYER_1);
        Piece[][] newBoard = new Piece[8][8];
        BoardDeserializer.configureBoard(newBoard, BoardDeserializer.DEFAULT_BOARD_CONFIG);
        assertTrue(Arrays.deepEquals(correctBoard, newBoard));
        //Test with combined brackets for multiple entry mode
        BoardDeserializer.configureBoard(newBoard, "RkBQKBkR(8P32.)1(8P)RkBQKBkR");
        assertTrue(Arrays.deepEquals(correctBoard, newBoard));
    }

    @Test
    public void testMultiplePlaceMode() {
        for (int y = 0; y <8; y++) {
            for (int x = 0; x <8; x++) {
                correctBoard[y][x] = new Pawn(x, y, Owner.PLAYER_2);
            }
        }
        Piece[][] newBoard = new Piece[8][8];
        BoardDeserializer.configureBoard(newBoard, "(64P)");
        assertTrue(Arrays.deepEquals(correctBoard, newBoard));
        //board is then reset to empty
        BoardDeserializer.configureBoard(newBoard, "(64" + BoardDeserializer.EMPTY_SQUARE_CHARACTER + ")");
        assertTrue(Arrays.deepEquals(new Piece[8][8], newBoard));

        //test this config
        /*PPPPPPPP
        PPPPPPPP
        ........
        ........
        PPPPPPPP
        ........
        ........
        PPPPPPPP*/
        for (int y = 0; y <8; y++) {
            for (int x = 0; x <8; x++) {
                if (y == 0 || y == 1 || y== 4 || y == 7) {
                    correctBoard[y][x] = new Pawn(x, y, Owner.PLAYER_2);
                }
                else correctBoard[y][x] = null;
            }
        }

        BoardDeserializer.configureBoard(newBoard, "(16P16" + BoardDeserializer.EMPTY_SQUARE_CHARACTER + "8P16" + BoardDeserializer.EMPTY_SQUARE_CHARACTER + "8P)");
        assertTrue(Arrays.deepEquals(correctBoard, newBoard));
    }

    //TODO test all exceptions e.g. number outside of brackets 64e
}
