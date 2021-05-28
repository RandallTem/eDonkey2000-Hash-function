import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class ED2K_Test {

    public void hashTestFiles(){
        ED2K test = new ED2K();
        byte[] hash;
        System.out.println("");
        hash = test.getHash("zero1");
        System.out.print("File 1 hash = ");
        for (int i = 0; i < hash.length; i++){
            System.out.print((hash[i]>>>4) == 0 ? "0"+Integer.toHexString(hash[i]&0xFF) :
                    Integer.toHexString(hash[i]&0xFF));
        }
        hash = test.getHash("zero2");
        System.out.print("\nFile 2 hash = ");
        for (int i = 0; i < hash.length; i++){
            System.out.print((hash[i]>>>4) == 0 ? "0"+Integer.toHexString(hash[i]&0xFF) :
                    Integer.toHexString(hash[i]&0xFF));
        }
        hash = test.getHash("zero3");
        System.out.print("\nFile 3 hash = ");
        for (int i = 0; i < hash.length; i++){
            System.out.print((hash[i]>>>4) == 0 ? "0"+Integer.toHexString(hash[i]&0xFF) :
                    Integer.toHexString(hash[i]&0xFF));
        }
    }

    public static void main(String[] args){
        ED2K_Test test = new ED2K_Test();
        test.hashTestFiles();
    }
}
