package nanakwame.ChessGame.ChessBoard;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import nanakwame.ChessGame.Owner;
import nanakwame.ChessGame.ChessBoard.Exceptions.BoardDeserializeException;
import nanakwame.ChessGame.Pieces.Bishop;
import nanakwame.ChessGame.Pieces.King;
import nanakwame.ChessGame.Pieces.Knight;
import nanakwame.ChessGame.Pieces.Pawn;
import nanakwame.ChessGame.Pieces.Piece;
import nanakwame.ChessGame.Pieces.Queen;
import nanakwame.ChessGame.Pieces.Rook;

public class BoardDeserializer {

    // Represent the number of pieces and the first letter of piece name. WHen a
    // number is seen out of brackets it is to switch which players piece is being
    // placed

    //TODO Fix this test : "RkBQKBkR(3P.4P)(32.)1(8P)RkBQKBkR"
    //TODO Maybe validate the number of each piece
    public static final String DEFAULT_BOARD_CONFIG = "RkBQKBkR(8P)(32.)1(8P)RkBQKBkR";
    public static final char EMPTY_SQUARE_CHARACTER = '.';
    //public for testing classes

    // Format (Can never be digits)
    private static HashMap<Character, Class<? extends Piece>> pieceMap = new HashMap<Character, Class<? extends Piece>>() {
        {
            put('R', Rook.class);
            put('k', Knight.class);
            put('B', Bishop.class);
            put('Q', Queen.class);
            put('K', King.class);
            put('P', Pawn.class);
        }
    };
    

    private static Owner currentPlayer = Owner.PLAYER_2;
    private static int currentX = 0;
    private static int currentY = 0;
    private static Piece[][] currentBoard;

    // will always start with player 2 if it isn't specified.
    // synchronized to prevent multiple method calls causing errors.
    public synchronized static void configureBoard(Piece[][] board, String boardString) {
        // find a way to make the variables local and not static
        if (board.length != 8){
            throw new BoardDeserializeException(
                    "Invalid board provided: Invalid number of rows. Expected 8 rows but: " + board.length + " present.");
        }
        for (int i = 0; i < board.length; i++) {
            if(board[i].length != 8) {
                //uses row number starting from 1.
                throw new BoardDeserializeException("Invalid board provided: Row " + (i + 1) + " has invalid number of columns. Expected 8 columns but " + board[i].length + " present.");
            }
        }
        currentPlayer = Owner.PLAYER_2;
        currentX = 0;
        currentY = 0;
        currentBoard = board;
        for (int i = 0; i < boardString.length(); i++) {
            char currentChar = boardString.charAt(i);
            switch (currentChar) {
                case '(':
                    i = placeMultiple(boardString, i + 1);
                    break;
                case '1':
                    currentPlayer = Owner.PLAYER_1;
                    break;
                case '2':
                    currentPlayer = Owner.PLAYER_2;
                    break;
                case EMPTY_SQUARE_CHARACTER:
                    incrementXandY();
                    continue;
                default:
                    if (pieceMap.keySet().contains(currentChar)) {
                        placePiece(currentChar, i);
                    } else {
                        String errorMessage = "Invalid board string: " + boardString + ". Token: '" + currentChar + "' at index " + i + (
                        (Character.isDigit(currentChar)) ? " not expected. Numbers other than 1 and 2 must be placed within brackets." : 
                        (currentChar == ')') ? " not expected. Closing brackets must have accompanying opening bracket." : " not recognised.");
                        throw new BoardDeserializeException(errorMessage);
                    }
                    break;
            }
        }
        System.out.println(currentX + " " + currentY);
        for (int y = currentY; y < 8; y++) {
            for (int x = currentX; x < 8; x++) {
                currentBoard[y][x] = null;
            }
        }
    }

    // return the new current index ( after the multiple placing of pieces has been
    // done)
    private static int placeMultiple(String boardString, int startIndex) {
        int pieceMultiplier = 0;
        char currentChar;
        for (int i = startIndex; i < boardString.length(); i++) {
            currentChar = boardString.charAt(i);
            if (pieceMap.keySet().contains(currentChar) || currentChar == EMPTY_SQUARE_CHARACTER) {
                // place all the pieces.
                for (int j = 0; j < pieceMultiplier; j++) {
                    placePiece(currentChar, i);
                }
            } else if (Character.isDigit(currentChar)){
                //this is to allow chaining of multiple placing of multiple pieces. E.g. (32.4P)
                if(i > 1 && !Character.isDigit(boardString.charAt(i - 1))) {
                    pieceMultiplier = 0;
                }
                pieceMultiplier = pieceMultiplier * 10 + currentChar - '0';
            }
            else if (currentChar == ')')
                return i;
            else {
                throw new BoardDeserializeException("Invalid board string: " + boardString + ". Token: '"
                                + currentChar + "' at index " + i + " not recognised.");
            }
        }
        // This means a closing brackets is missing we will assume they forgot and
        // ignore it
        return boardString.length() - 1;
        // throw new BoardDeserializeException("Invalid board string: Closing brackets
        // missing")
    }

    private static void incrementXandY() {
        currentX = (currentX + 1) % 8;
        if (currentX == 0)
            currentY++;
    }

    //TODO Validate that it doesn't let you place more than the allowed number of each piece.
    private static void placePiece(char pieceChar, int currentBoardStringIndex) throws BoardDeserializeException {
        if (pieceChar == EMPTY_SQUARE_CHARACTER) {
            try {
                currentBoard[currentY][currentX] = null;
                incrementXandY();
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new BoardDeserializeException(
                        "Invalid board string: Too many pieces for 8 x 8 grid. At board string Index: " + currentBoardStringIndex + ".");
            }
            return;
        }
        Class<? extends Piece> clazz = pieceMap.getOrDefault(pieceChar, null);
        if (clazz != null) {
            try {
                currentBoard[currentY][currentX] = clazz.getConstructor(int.class, int.class, Owner.class)
                        .newInstance(currentX, currentY,
                                currentPlayer);
                incrementXandY();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new BoardDeserializeException("Fatal error: Failed to place piece from class: " + clazz, e);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new BoardDeserializeException(
                        "Invalid board string: Too many pieces for 8 x 8 grid. At board string Index: " + currentBoardStringIndex + ".");
            }
        } else {
            throw new BoardDeserializeException("Invalid board string: Invalid character '" + pieceChar + "' at board string index " + currentBoardStringIndex
                        + ". Check if there are any unclosed brackets.");
        }
    }
}
