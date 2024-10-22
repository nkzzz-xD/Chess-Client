package nanakwame.IO;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import nanakwame.GUI.Images;

public class AppData {
    public static String appDataPath;

    private static final String TEXTURE_FOLDER_NAME = "textures";
    //TODO Load default textures if the texture pack fails to load
    /**
     * Load textures from the provided ZIP file.
     * @param zipFile The path to the ZIP file containing the textures.
     * @throws IOException If there is an error reading the ZIP or image files.
     */
    public static void loadTexturesFromZip(HashMap<String,BufferedImage> textureMap, HashSet<String> expectedTextureNames, String zipFile, boolean loadDefaults){
        System.out.println("Loading textures...");
        InputStream is = null;
        if (loadDefaults) {
            is = Images.class.getResourceAsStream("/" + TEXTURE_FOLDER_NAME + "/" + zipFile);
        } else
            try {
                //TODO Fix exception handling
                is = new FileInputStream(Paths.get(TEXTURE_FOLDER_NAME, zipFile).toFile());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //try try catchong within loop
        try (ZipInputStream zis = new ZipInputStream(is)) {
            System.out.println(zis.available());
            ZipEntry entry;
            // Loop through each entry in the ZIP file
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                // Check if the file matches one of the expected textures
                if(expectedTextureNames.contains(fileName)) {
                    // Read the image and store it in the map
                    BufferedImage image = ImageIO.read(zis);
                    if (image != null) {
                        textureMap.put(fileName, image);
                        System.out.println("Loaded texture: " + fileName);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading textures from zip: " + e.getMessage());
        }
    }
}
