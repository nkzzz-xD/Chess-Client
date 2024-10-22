package nanakwame.GUI;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import nanakwame.IO.AppData;

public class Images {
    
    //TODO Add texture packs
    private static HashMap<String, BufferedImage> textures = new HashMap<>();

    private static final HashSet<String> EXPECTED_TEXTURES = 
    new HashSet<String>(Arrays.asList("PLAYER_1_Rook.png", "PLAYER_1_Bishop.png", "PLAYER_1_Knight.png","PLAYER_1_Queen.png","PLAYER_1_King.png","PLAYER_1_Pawn.png",
        "PLAYER_2_Rook.png", "PLAYER_2_Bishop.png", "PLAYER_2_Knight.png","PLAYER_2_Queen.png","PLAYER_2_King.png","PLAYER_2_Pawn.png","title-bg.png"));

    //Pawn -<a href="https://www.flaticon.com/free-icons/chess" title="chess icons">Chess icons created by Andrejs Kirma - Flaticon</a>
    //Rook -<a href="https://www.flaticon.com/free-icons/chess" title="chess icons">Chess icons created by deemakdaksina - Flaticon</a>
            //newest : <a href="https://www.flaticon.com/free-icons/rook" title="rook icons">Rook icons created by riajulislam - Flaticon</a>
    //Knight <a href="https://www.flaticon.com/free-icons/knight" title="knight icons">Knight icons created by rizal2109 - Flaticon</a>
    //Queen <a href="https://www.flaticon.com/free-icons/chess-piece" title="chess piece icons">Chess piece icons created by riajulislam - Flaticon</a>
    //King <a href="https://www.flaticon.com/free-icons/crown" title="crown icons">Crown icons created by Freepik - Flaticon</a>
    public static void loadTexturesFromZip(String zipFile, boolean loadDefaults){
        AppData.loadTexturesFromZip(textures, EXPECTED_TEXTURES, zipFile, loadDefaults);
    }

    /**
     * Get a texture by its piece name.
     * @param pieceName The name of the piece (e.g., "rook", "bishop").
     * @return The BufferedImage of the texture, or null if not found.
     */
    public static BufferedImage getTexture(String pieceName) {
        return textures.get(pieceName + ".png");  //all textures are pngs.
    }

}
