public class MD4 {
    private int A;
    private int B;
    private int C;
    private int D;

    public int[][] adaptMessage(byte[] message){
        long size = message.length * 8;
        byte[] extended_message;
        if (size % 512 ==0)
            extended_message = new byte[message.length + 64];
        else if (size % 512 >= 448)
            extended_message = new byte[(message.length / 64)*64 + 128];
        else
            extended_message = new byte[(message.length / 64)*64 + 64];
        System.arraycopy(message, 0, extended_message, 0, message.length);
        extended_message[message.length] = (byte)0x80;
        int[][] res_message = new int[extended_message.length/64][16];
        for (int i = extended_message.length - 8; i < extended_message.length; i++){
            extended_message[i] = (byte)size;
            size >>= 8;
        }
        for (int i = 0; i < extended_message.length; i+=4) {
            res_message[i/64][(i/4)%16] = (extended_message[i+3]<<24)+(extended_message[i+2]<<16&0xFF0000)+
                                       (extended_message[i+1]<<8&0xFF00)+(extended_message[i]&0xFF);
        }
        return res_message;
    }

    private int cycleShift(int value,  int count){
        int temp = (value) >>> (32 - count);
        int show = ((value << count) + temp);
        return ((value << count) + temp);
    }

    public int FFunc(int x, int y, int z){
        return (x & y) | (~x & z);
    }

    private int GFunc(int x, int y, int z){
        return (x & y) | (x & z) | (y & z);
    }

    private int HFunc(int x, int y, int z){
        return x ^ y ^ z;
    }

    private void getHash(int[][] words){
        for (int i = 0; i < words.length; i++) {
            int AA = A;
            int BB = B;
            int CC = C;
            int DD = D;
            for (int j = 0; j < 4; j++) {
                A = cycleShift((A + FFunc(B, C, D) + words[i][j * 4]), 3);
                D = cycleShift((D + FFunc(A, B, C) + words[i][j * 4 + 1]), 7);
                C = cycleShift((C + FFunc(D, A, B) + words[i][j * 4 + 2]), 11);
                B = cycleShift((B + FFunc(C, D, A) + words[i][j * 4 + 3]), 19);
            }
            for (int j = 0; j < 4; j++) {
                A = cycleShift((A + GFunc(B, C, D) + words[i][j] + 0x5A827999), 3);
                D = cycleShift((D + GFunc(A, B, C) + words[i][j + 4] + 0x5A827999), 5);
                C = cycleShift((C + GFunc(D, A, B) + words[i][j + 8] + 0x5A827999), 9);
                B = cycleShift((B + GFunc(C, D, A) + words[i][j + 12] + 0x5A827999), 13);
            }
            A = cycleShift((A + HFunc(B, C, D) + words[i][0] + 0x6ED9EBA1), 3);
            D = cycleShift((D + HFunc(A, B, C) + words[i][8] + 0x6ED9EBA1), 9);
            C = cycleShift((C + HFunc(D, A, B) + words[i][4] + 0x6ED9EBA1), 11);
            B = cycleShift((B + HFunc(C, D, A) + words[i][12] + 0x6ED9EBA1), 15);
            A = cycleShift((A + HFunc(B, C, D) + words[i][2] + 0x6ED9EBA1), 3);
            D = cycleShift((D + HFunc(A, B, C) + words[i][10] + 0x6ED9EBA1), 9);
            C = cycleShift((C + HFunc(D, A, B) + words[i][6] + 0x6ED9EBA1), 11);
            B = cycleShift((B + HFunc(C, D, A) + words[i][14] + 0x6ED9EBA1), 15);
            A = cycleShift((A + HFunc(B, C, D) + words[i][1] + 0x6ED9EBA1), 3);
            D = cycleShift((D + HFunc(A, B, C) + words[i][9] + 0x6ED9EBA1), 9);
            C = cycleShift((C + HFunc(D, A, B) + words[i][5] + 0x6ED9EBA1), 11);
            B = cycleShift((B + HFunc(C, D, A) + words[i][13] + 0x6ED9EBA1), 15);
            A = cycleShift((A + HFunc(B, C, D) + words[i][3] + 0x6ED9EBA1), 3);
            D = cycleShift((D + HFunc(A, B, C) + words[i][11] + 0x6ED9EBA1), 9);
            C = cycleShift((C + HFunc(D, A, B) + words[i][7] + 0x6ED9EBA1), 11);
            B = cycleShift((B + HFunc(C, D, A) + words[i][15] + 0x6ED9EBA1), 15);
            A = A + AA;
            B = B + BB;
            C = C + CC;
            D = D + DD;
        }
    }

   /* public int[] Hash(String message){
        int[][] adapted_message = adaptMessage(message.getBytes());
        getHash(adapted_message);
        int[] res = {(A<<24)+((A<<8)&0xFF0000)+((A>>8)&0xFF00)+((A>>24)&0xFF),
                     (B<<24)+((B<<8)&0xFF0000)+((B>>8)&0xFF00)+((B>>24)&0xFF),
                     (C<<24)+((C<<8)&0xFF0000)+((C>>8)&0xFF00)+((C>>24)&0xFF),
                     (D<<24)+((D<<8)&0xFF0000)+((D>>8)&0xFF00)+((D>>24)&0xFF)};
        return res;
    } */

    public int[] Hash(byte[] message){
        A = 0x67452301;
        B = 0xefcdab89;
        C = 0x98badcfe;
        D = 0x10325476;
        int[][] adapted_message = adaptMessage(message);
        getHash(adapted_message);
        int[] res = {(A<<24)+((A<<8)&0xFF0000)+((A>>8)&0xFF00)+((A>>24)&0xFF),
                (B<<24)+((B<<8)&0xFF0000)+((B>>8)&0xFF00)+((B>>24)&0xFF),
                (C<<24)+((C<<8)&0xFF0000)+((C>>8)&0xFF00)+((C>>24)&0xFF),
                (D<<24)+((D<<8)&0xFF0000)+((D>>8)&0xFF00)+((D>>24)&0xFF)};
        return res;
    }
}
