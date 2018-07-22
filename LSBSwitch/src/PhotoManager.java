import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PhotoManager {

    private ArrayList<String> imageList;
    private int counter = 0;

    public PhotoManager(String directory){
        imageList = new ArrayList<>();
        Arrays.stream(new File(directory).listFiles()).forEach(f -> imageList.add(f.getAbsolutePath()));
    }
    public BufferedImage getNextPhoto() {
        if (imageList.isEmpty()) return null;
        try {
            BufferedImage image = ImageIO.read(new File(imageList.remove(0)));
            return image;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage clearLSB(BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int p = image.getRGB(i, j);
                p = p & 0xfffcfcfc;
                image.setRGB(i, j, p);
            }
        }
        return image;
    }

    public void createPhoto(BufferedImage photo){
        counter++;
        File outputFile = new File(globals.DESTDIRECTORY + "/"  + counter);
        try {
            ImageIO.write(photo, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
