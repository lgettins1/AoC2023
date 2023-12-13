import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay5b {
    public static void main(String [] args) {
        long answer = 9999999999L;
        String thisLine;
        int section = 0;
        int seedCount = 0;
        int ruleNum = 0;
        long[][][] rules = new long[8][50][3];
        long[] map = new long[21];

        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day5input.txt"));
            thisLine = br.readLine();
            String[] a = thisLine.split(" ");
            for(int b = 0; b < a.length - 1; b ++){
                map[b] = Long.parseLong(a[b + 1]);
                seedCount ++;
            }
            while ((thisLine = br.readLine()) != null) {
                if(thisLine.trim().equals("")){
                    section ++;
                    ruleNum = 0;
                    br.readLine();
                } else {
                    String[] sp = thisLine.split((" "));
                    for(int th = 0; th < 3; th ++){
                        rules[section][ruleNum][th] = Long.parseLong(sp[th]);
                    }
                    ruleNum ++;
                }
            }
            long startVal;
            long loopVal;
            for (int c = 0; c < seedCount; c += 2) {
                for (startVal = map[c]; startVal < map[c] + map[c + 1]; startVal ++){
                    loopVal = startVal;
                    long newVal = 0;
                    for (int rc = 1; rc < rules.length; rc ++) {
                        boolean found = false;
                        for (int rc2 = 0; rc2 < rules[rc].length; rc2 ++) {
                            if (loopVal >= rules[rc][rc2][1] && loopVal <= (rules[rc][rc2][1] + rules[rc][rc2][2]) && !found) {
                                found = true;
                                newVal = loopVal - rules[rc][rc2][1] + rules[rc][rc2][0];
                            }
                        }
                        if (!found) newVal = loopVal;
                        loopVal = newVal;
                    }
                    if (newVal < answer) {
                        answer = newVal;
                        System.out.println(answer);
                    }
                }
            }
            System.out.println("The answer to part 2 is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
