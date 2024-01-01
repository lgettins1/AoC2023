import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay12SecondTry {
    public static void main(String [] args) {
        String thisLine;
        int answer = 0;
        try {
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day12input.txt"));
        while ((thisLine = br.readLine()) != null) {
            int[] numList = new int[50];
            String[] line = thisLine.split(" ");
            String[] n = line[1].split(",");
            int numCount = n.length;
            int numSum = 0;
            for (int a = 0; a < numCount; a++) {
                numList[a] = Integer.parseInt(n[a]);
                numSum += numList[a];
            }
            if(line[0].substring(0,numList[0] + 1).contains("#")) {
                int ld = -1;
                int fh = -1;
                int lh = -1;
                int fd = -1;
                int cp = 0;
                while (fd == -1) {
                    if (fh == -1) {
                        switch (line[0].charAt(cp)) {
                            case ('.') -> ld = cp;
                            case ('#') -> {
                                fh = cp;
                                lh = cp;
                           }
                        }
                    } else {
                        switch (line[0].charAt(cp)) {
                            case ('.') -> fd = cp;
                            case ('#') -> {
                                if(cp - fh < numList[0]) lh = cp;
                            }
                        }
                    }
                    cp ++;
                    if(cp == line[0].length()) fd = cp;
                }
                int lp = fh;
                if(lp > fd - numList[0]) lp = fd - numList[0];
                int fp = ld + 1;
                if(fp < lh + 1 - numList[0]) fp = lh + 1 - numList[0];
                System.out.println(line[0] + " " + fp + "," + lp + " - " + numList[0]);
            }

        }
        System.out.println("The answer is " + answer);
    } catch (
    IOException e) {
        throw new RuntimeException(e);
    }
}
    public static String convertNumberToNewBase (String number,int base, int newBase){
        return Integer.toString(Integer.parseInt(number, base), newBase);
    }
}
