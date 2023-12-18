import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.abs;

public class AoCDay11 {

    public static void main(String [] args) {
        String thisLine;
        String[] universe = new String[200];
        long answer = 0;
        int rows = 0;
        int galaxyCount = 0;
        int rowLength = 0;
        int[][]galaxies = new int [2][1000];
        int[] blankColumns = new int[100];
        int bcCount = 0;
        int[]blankRows = new int[100];
        int brCount = 0;
        int expansion = 999999;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day11input.txt"));
            while ((thisLine = br.readLine()) != null) {
                rowLength = thisLine.length();
                universe[rows] = thisLine;
                if(!thisLine.contains("#")){
                    blankRows[brCount] = rows;
                    brCount ++;
                }
                rows ++;
            }
            for(int b = 0; b < rowLength; b ++) {
                int fg = 0;
                for (int a = 0; a < rows; a++) {
                    if (universe[a].charAt(b) == '#') {
                        fg ++;
                    }
                }
                if(fg == 0){
                    blankColumns[bcCount] = b;
                    bcCount ++;
                }
            }
            for (int a = 0; a < rows; a ++) {
                for(int b = 0; b < rowLength; b ++){
                    if(universe[a].charAt(b) == '#'){
                        galaxies[0][galaxyCount] = b;
                        galaxies[1][galaxyCount] = a;
                        galaxyCount ++;
                    }
                }
            }
            for(int a = 0; a < galaxyCount; a ++){
                for(int b = a; b < galaxyCount; b ++){
                    long xd = abs(galaxies[0][a] - galaxies[0][b]);
                    long yd = abs(galaxies[1][a] - galaxies[1][b]);
                    for(int c = 0; c < bcCount; c ++){
                        if((blankColumns[c] > galaxies[0][a] && blankColumns[c] < galaxies[0][b]) ||
                                (blankColumns[c] < galaxies[0][a] && blankColumns[c] > galaxies[0][b])) {
                            xd += expansion;
                        }
                    }
                    for(int c = 0; c < brCount; c ++){
                        if((blankRows[c] > galaxies[1][a] && blankRows[c] < galaxies[1][b]) ||
                                (blankRows[c] < galaxies[1][a] && blankRows[c] > galaxies[1][b])) {
                            yd += expansion;
                        }
                    }
                    answer += xd + yd;
                }
            }
            System.out.println("The answer is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
