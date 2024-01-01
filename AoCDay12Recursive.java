import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay12Recursive {
    private static int globalAnswer;
    public static void main(String [] args) {
        String thisLine;
       try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day12inputb.txt"));
            while ((thisLine = br.readLine()) != null) {
                int[] numList = new int[50];
                String[] line = thisLine.split(" ");
                String a = line[0] + "?";
                String b = line[1] + ",";
                for(int c = 0; c < 4; c ++){
                    line[0] = a + line[0];
                    line[1] = b + line[1];
                }
                String[] n = line[1].split(",");
                int numCount = n.length;
                for (int d = 0; d < numCount; d ++) {
                    numList[d] = Integer.parseInt(n[d]);
                }
                System.out.println(line[0] + " " + line[1]);
                countArrangements(line[0], numList, numCount);
            }
            System.out.println("The answer is " + globalAnswer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String convertNumberToNewBase (String number,int base, int newBase){
        return Integer.toString(Integer.parseInt(number, base), newBase);
    }

    public static void countArrangements(String line, int[] numList, int numCount){
        int numSum = 0;
        for(int a = 0; a < numCount; a ++){
            numSum += numList[a];
        }

        if(numCount > 1 && line.substring(0,numList[0] + 1).contains("#")) {
            int ld = -1;
            int fh = -1;
            int fd = -1;
            int cp = 0;
            while (fd == -1) {
                if (fh == -1) {
                    switch (line.charAt(cp)) {
                        case ('.') -> ld = cp;
                        case ('#') -> fh = cp;
                    }
                } else {
                    if (line.charAt(cp) == '.') {
                        fd = cp;
                    }
                }
                cp ++;
                if(cp == line.length()) fd = cp;
            }
            int lp = fh;
            if(lp > fd - numList[0]) lp = fd - numList[0];
            int fp = ld + 1;
            if(fp < fh + 1 - numList[0]) fp = fh + 1 - numList[0];
            if( lp > line.length() - numSum - numCount + 1) lp = line.length() - numSum - numCount + 1;
            int[] newNumList = new int[numCount];
            System.arraycopy(numList, 1, newNumList, 0, numCount);

            for (int a = fp; a <= lp; a ++){
                 if(line.charAt(a + numList[0]) !='#') {
                     countArrangements(line.substring(a + numList[0] + 1), newNumList, numCount - 1);
                 }
            }
        } else {
            int maxDot = line.length() - numSum + 1;
            int possCount = (int) Math.pow(maxDot, (numCount + 1));
            for (int a = 0; a < possCount; a++) {
                StringBuilder b = new StringBuilder(Integer.toString(a));
                b = new StringBuilder(convertNumberToNewBase(b.toString(), 10, maxDot));
                int d = b.length();
                if (d < numCount + 1) {
                    for (int c = 0; c < (numCount + 1 - d); c++) {
                        b.insert(0, "0");
                    }
                }
                boolean candidate = true;
                int len = 0;
                int dataLen = line.length();
                int[] dots = new int[numCount + 1];
                for (int e = 0; e < numCount + 1; e++) {
                    if(b.charAt(e) == '0'){
                        dots[e] = 0;
                    } else {
                        dots[e] = Integer.parseInt(convertNumberToNewBase(b.substring(e, e + 1), maxDot, 10));
                    }
                    len += dots[e];
                    if (e > 0 && e < numCount && b.charAt(e) == '0') candidate = false;
                }
                if (numSum + len != dataLen) candidate = false;
                if (candidate) {
                    if ((line.charAt(0) == '#' && b.charAt(0) != '0') ||
                            (line.charAt(0) == '.' && b.charAt(0) == '0') ||
                            (line.charAt(dataLen - 1) == '#' && b.charAt(numCount) != '0') ||
                            (line.charAt(dataLen - 1) == '.' && b.charAt(numCount) == '0')) {
                        candidate = false;
                    }
                }
                if (candidate) {
                    StringBuilder ourString = new StringBuilder();
                    ourString.append(".".repeat(Math.max(0, dots[0])));
                    for (int f = 0; f < numCount; f++) {
                        ourString.append("#".repeat(Math.max(0, numList[f])));
                        ourString.append(".".repeat(Math.max(0, dots[f + 1])));
                    }
                    for (int f = 0; f < ourString.length(); f++) {
                        if (ourString.charAt(f) != line.charAt(f) && line.charAt(f) != '?') {
                            candidate = false;
                            break;
                        }
                    }
                }
                if (candidate) {
                    globalAnswer ++;
                }
            }
        }
    }
}
