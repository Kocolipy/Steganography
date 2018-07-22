import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Encryptor {
    public static Bit2[] convertPDFtoBytes() throws IOException
    {
        Path pdfPath = Paths.get(globals.PDF);
        byte[] pdf = Files.readAllBytes(pdfPath);
        Bit2[] arr = new Bit2[pdf.length*4];
        for (int i=0; i < pdf.length; i++){
            for (int j=0; j< 4; j++) {
                arr[4 * i + j] = new Bit2(pdf[i], j);
            }
        }
        for (int i=0; i<12;i++){
            System.out.println(arr[i].get());
        }
        return arr;
    }


    public static BufferedImage insertLSB (BufferedImage img, Bit2[] arr, int ptr) {
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int p = img.getRGB(i, j);
                for (int k = 2; k>=0; k--){
                    if (ptr >= arr.length) return img;
                    p += arr[ptr].get() << 8*k;
                    ptr++;
                }
                img.setRGB(i,j,p);
            }
        }
        return img;
    }

    public static void main (String[] args) {

        PhotoManager pm = new PhotoManager(globals.SOURCEDIRECTORY);

        Bit2[] arr = null;
        try {
            arr = convertPDFtoBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int ptr = 0;
        while (ptr < arr.length){
            BufferedImage img = pm.clearLSB(pm.getNextPhoto());
            img = insertLSB(img, arr, ptr);
            pm.createPhoto(img);
            ptr += img.getWidth()*img.getHeight()*3;

        }
    }
}
