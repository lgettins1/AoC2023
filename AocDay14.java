import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AocDay14 {

    public static void main(String [] args) {
        String thisLine;
       int answer = 0;

        String[] thisPattern = new String[100];
        int height = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day14input.txt"));
            while ((thisLine = br.readLine()) != null) {
                thisPattern[height] = thisLine;
                height ++;
            }
            int moves = 1;
            while(moves > 0) {
                moves = 0;
                for (int rp = 1; rp < height; rp ++) {
                    for (int sc = 0; sc < thisPattern[0].length(); sc++) {
                        if(thisPattern[rp].charAt(sc) == 'O' && thisPattern[rp - 1].charAt(sc) == '.'){
                            moves ++;
                            String endBit1 = "";
                            String endBit2 = "";
                            if(sc < thisPattern[rp - 1].length() - 1){
                                endBit1 = thisPattern[rp - 1].substring(sc + 1);
                                endBit2 = thisPattern[rp].substring(sc + 1);
                            }
                            thisPattern[rp - 1] = thisPattern[rp - 1].substring(0, sc) + "O" + endBit1;
                            thisPattern[rp] = thisPattern[rp].substring(0, sc) + "." + endBit2;
                        }
                    }
                }
            }
            for (int rpa = 0; rpa < height; rpa++) {
                int roundRocks = thisPattern[rpa].length() - thisPattern[rpa].replace("O", "").length();
                answer += roundRocks * (height - rpa);
            }

            System.out.println("The answer is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
