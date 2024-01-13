import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class AoCDay14b {

    public static void main(String [] args) {
        String thisLine;
        int answer = 0;

        String[] thisPattern = new String[100];
        String[][] lastPattern = new String[1000][100];
        int height = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day14input.txt"));
            while ((thisLine = br.readLine()) != null) {
                thisPattern[height] = thisLine;
                height ++;
            }
            int cycles = 1;
            int firstInstance = 0;
            boolean foundRepeat = false;
            lastPattern[0] = thisPattern.clone();

            while(!foundRepeat) {
                tilt(thisPattern, height, 'N');
                tilt(thisPattern, height, 'W');
                tilt(thisPattern, height, 'S');
                tilt(thisPattern, height, 'E');
                for(int a = 0; a < cycles; a ++){
                        if(Arrays.equals(thisPattern, lastPattern[a])) {
                            firstInstance = a;
                            foundRepeat = true;
                        }
                    }
                    if(!foundRepeat){
                        lastPattern[cycles] = thisPattern.clone();
                    }

                if(!foundRepeat) cycles ++;
            }
            int newTarget = ((1000000000 - firstInstance) % (cycles - firstInstance)) + firstInstance;
            for (int rpa = 0; rpa < height; rpa++) {
                int roundRocks = lastPattern[newTarget][rpa].length() - lastPattern[newTarget][rpa].replace("O", "").length();
                answer += roundRocks * (height - rpa);
            }
            System.out.println(" The answer is " + answer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] tilt(String[] pattern,int height, char dir){
        switch (dir) {
            case 'N' -> {
                int moves = 1;
                while(moves > 0) {
                    moves = 0;
                    for (int rp = 1; rp < height; rp ++) {
                        for (int sc = 0; sc < pattern[0].length(); sc++) {
                            if(pattern[rp].charAt(sc) == 'O' && pattern[rp - 1].charAt(sc) == '.'){
                                moves ++;
                                String endBit1 = "";
                                String endBit2 = "";
                                if(sc < pattern[rp - 1].length() - 1){
                                    endBit1 = pattern[rp - 1].substring(sc + 1);
                                    endBit2 = pattern[rp].substring(sc + 1);
                                }
                                pattern[rp - 1] = pattern[rp - 1].substring(0, sc) + "O" + endBit1;
                                pattern[rp] = pattern[rp].substring(0, sc) + "." + endBit2;
                            }
                        }
                    }
                }
            }
            case 'W' -> {
                int moves = 1;
                while(moves > 0) {
                    moves = 0;
                    for (int rp = 0; rp < height; rp ++) {
                        for (int sc = 1; sc < pattern[0].length(); sc++) {
                            if(pattern[rp].charAt(sc) == 'O' && pattern[rp].charAt(sc - 1) == '.'){
                                moves ++;
                                String endBit1 = "";
                                 if(sc < pattern[rp].length() - 1){
                                    endBit1 = pattern[rp].substring(sc + 1);
                                }
                                pattern[rp] = pattern[rp].substring(0, sc - 1 ) + "O." + endBit1;
                            }
                        }
                    }
                }
            }
            case 'S' -> {
                int moves = 1;
                while(moves > 0) {
                    moves = 0;
                    for (int rp = height - 2; rp >= 0; rp --) {
                        for (int sc = 0; sc < pattern[0].length(); sc++) {
                            if(pattern[rp].charAt(sc) == 'O' && pattern[rp + 1].charAt(sc) == '.'){
                                moves ++;
                                String endBit1 = "";
                                String endBit2 = "";
                                if(sc < pattern[rp + 1].length() - 1){
                                    endBit1 = pattern[rp + 1].substring(sc + 1);
                                    endBit2 = pattern[rp].substring(sc + 1);
                                }
                                pattern[rp + 1] = pattern[rp + 1].substring(0, sc) + "O" + endBit1;
                                pattern[rp] = pattern[rp].substring(0, sc) + "." + endBit2;
                            }
                        }
                    }
                }
            }
            case 'E' -> {
                int moves = 1;
                while(moves > 0) {
                    moves = 0;
                    for (int rp = 0; rp < height; rp ++) {
                        for (int sc = pattern[0].length() - 2; sc >= 0; sc --) {
                            if(pattern[rp].charAt(sc) == 'O' && pattern[rp].charAt(sc + 1) == '.'){
                                moves ++;
                                String endBit1 = "";
                                if(sc < pattern[rp].length() - 2){
                                    endBit1 = pattern[rp].substring(sc + 2);
                                }
                                pattern[rp] = pattern[rp].substring(0, sc) + ".O" + endBit1;
                            }
                        }
                    }
                }
            }
        }
        return pattern;
    }

}
