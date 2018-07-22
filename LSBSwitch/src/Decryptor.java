import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decryptor {
    private static Bit2[] resizeBuff (Bit2[] buf){
        Bit2[] tmp = new Bit2[buf.length*2];
        for (int i=0; i<buf.length; i++){
            tmp[i] = buf[i];
        }
        return tmp;
    }
    public static void decrypt(byte[] buf){
        try (FileOutputStream fos = new FileOutputStream(globals.DecryptedPDF)) {
            fos.write(buf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        PhotoManager pm = new PhotoManager(globals.DESTDIRECTORY);
        Bit2[] buf = new Bit2[1024];
        int ptr = 0;

        BufferedImage img = pm.getNextPhoto();
        while (img != null){
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int p = img.getRGB(i, j);
                    for (int k = 2; k >= 0; k--) {
                        if (ptr >= buf.length) {
                            buf = resizeBuff(buf);
                        }
                        buf[ptr] = new Bit2((p >> (8 * k)));
                        ptr++;
                    }
                }
            }
            img = pm.getNextPhoto();
        }

        System.out.println("Deconstruction of photos completed");
        byte[] osArr = new byte[buf.length%4 == 0 ? buf.length/4 : buf.length/4 + 1];
        for (int i = 0; i< ptr; i++){
            osArr[i/4] += (byte) (buf[i].get() << (6-2*(i%4)));
        }
        System.out.println("Creation of byte stream completed");
        decrypt(osArr);
    }
}