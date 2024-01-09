import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay13Part2 {
    public static void main(String [] args) {

    String thisLine;
    int patternHeight = 0;
    int answer = 0;

    String[] thisPattern = new String[50];
        try {
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day13input.txt"));
        while ((thisLine = br.readLine()) != null) {
            System.out.println(thisLine);
            if(thisLine.length() == 0){
                answer += eval(thisPattern, patternHeight);
                System.out.println("answer so far " + answer);
                patternHeight = 0;
            } else {
                thisPattern[patternHeight] = thisLine;
                patternHeight ++;
            }
        }
        answer += eval(thisPattern, patternHeight);
        System.out.println("The answer is " + answer);
    } catch (
    IOException e) {
        throw new RuntimeException(e);
    }
}
    public static int process(String[] pattern, int height){
        int reflection = 0;
        int currRow = 1;
        while(currRow < height){
            if(pattern[currRow].equals(pattern[currRow - 1])){
                int range = currRow;
                boolean ref = true;
                if(currRow > 1 && currRow < height - 1) {
                    if (currRow > height - currRow) range = height - currRow;
                    for (int a = 1; a < range; a++) {
                        if (!pattern[currRow + a].equals(pattern[currRow - 1 - a])) {
                            ref = false;
                            break;
                        }
                    }
                }
                if(ref) reflection = currRow;
            }
            currRow ++;
        }
        return reflection;
    }

    public static int eval(String[] pattern, int height){
        String[] transPattern;
        int patternResult;
        int vReflection = 0;

        int hReflection = process(pattern, height);
        if(hReflection == 0){
            int newHeight = pattern[0].length();
            transPattern = transpose(pattern, height);
            vReflection = process(transPattern, newHeight);
        }
        patternResult = 100 * hReflection + vReflection;
        return patternResult;
    }
    public static String[] transpose(String[] pattern, int height){
        String[] newPattern = new String[50];
        for(int b = 0; b < pattern[0].length(); b ++) {
            newPattern[b] = "";
        }
        for(int a = 0; a < height; a ++){
            for(int b = 0; b < pattern[0].length(); b ++){
                newPattern[b] += pattern[a].charAt(b);
            }
        }
        return newPattern;
    }

}
