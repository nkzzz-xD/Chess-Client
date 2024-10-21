package nanakwame.ChessGame.ChessBoard.Exceptions;

public class BoardDeserializeException extends RuntimeException{
     // Default constructor
     public BoardDeserializeException() {
        super();
    }

    // Constructor that accepts a custom message
    public BoardDeserializeException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public BoardDeserializeException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public BoardDeserializeException(Throwable cause) {
        super(cause);
    }
}
