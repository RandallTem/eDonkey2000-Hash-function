import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class ED2K {

    private void intArrToByteArr(int[] int_arr, byte[] byte_arr, int pos){
        for (int i = 3; i >= 0; i--){
            for (int j = 3; j >= 0; j--){
                byte_arr[pos+i*4+j] = (byte)int_arr[i];
                int_arr[i]>>>=8;
            }
        }
    }

    public byte[] getHash(String path){
        try {
            MD4 md4hash = new MD4();
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(path), 9728000);
            int pos = 0;
            int[] hash;
            byte[] hash_array = reader.available()%9728000 == 0 ? new byte[16 * (reader.available() / 9728000)] :
                                                                  new byte[16 * (reader.available() / 9728000 + 1)];
            byte[] bytes;
            while (reader.available() > 0){
                bytes = reader.available()/9728000 >= 1 ?  new byte[9728000] : new byte[reader.available()%9728000];
                reader.read(bytes);
                hash = md4hash.Hash(bytes);
                intArrToByteArr(hash, hash_array, pos);
                pos += 16;
            }
            if (hash_array.length > 16){
                hash = md4hash.Hash(hash_array);
                hash_array = new byte[16];
                pos = 0;
                intArrToByteArr(hash, hash_array, pos);
            }
            reader.close();
            return hash_array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new byte[0];
    }
}
