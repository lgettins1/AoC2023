import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay12 {

    public static void main(String [] args) {
        String thisLine;
        int answer = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day12inputd.txt"));
            while ((thisLine = br.readLine()) != null) {
                int[] numList = new int[50];
                String[] line = thisLine.split(" ");
                String[] n = line[1].split(",");
                int numCount = n.length;
                int numSum = 0;
                for (int a = 0; a < numCount; a ++) {
                    numList[a] = Integer.parseInt(n[a]);
                    numSum += numList[a];
                }
                int maxDot = line[0].length() - numSum + 1;
                int possCount = (int) Math.pow(maxDot, (numCount + 1));
                for (int a = 0; a < possCount; a++) {
                    StringBuilder b = new StringBuilder(Integer.toString(a));
                    b = new StringBuilder(convertNumberToNewBase(b.toString(), 10, maxDot));
                    int d = b.length();
                    if(d < numCount + 1){
                        for(int c = 0; c < (numCount + 1 - d); c ++){
                            b.insert(0, "0");
                        }
                    }
                    boolean candidate = true;
                    int len = 0;
                    int dataLen = line[0].length();
                    int[] dots = new int[numCount + 1];
                    for(int e = 0; e < numCount + 1; e ++){
                        dots[e] = Integer.parseInt(convertNumberToNewBase(b.substring(e, e + 1),maxDot, 10));
                        len += dots[e];
                        if(e > 0 && e < numCount && b.charAt(e) == '0') candidate = false;
                    }
                    if(numSum + len != dataLen) candidate = false;
                    if(candidate) {
                        if ((line[0].charAt(0) == '#' && b.charAt(0) != '0') ||
                                (line[0].charAt(0) == '.' && b.charAt(0) == '0') ||
                                (line[0].charAt(dataLen - 1) == '#' && b.charAt(numCount) != '0') ||
                                (line[0].charAt(dataLen - 1) == '.' && b.charAt(numCount) == '0')){
                            candidate = false;
                        }
                    }
                    if(candidate){
                        StringBuilder ourString = new StringBuilder();
                        ourString.append(".".repeat(Math.max(0, dots[0])));
                        for (int f = 0; f < numCount; f ++) {
                            ourString.append("#".repeat(Math.max(0, numList[f])));
                            ourString.append(".".repeat(Math.max(0, dots[f + 1])));
                        }
                        for (int f = 0; f < ourString.length(); f++) {
                            if (ourString.charAt(f) != line[0].charAt(f) && line[0].charAt(f) != '?') {
                                candidate = false;
                                break;
                            }
                        }
                    }
                    if(candidate) {
                        answer ++;
                    }
                }
                System.out.println(line[0] + answer);

            }
            System.out.println("The answer is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        public static String convertNumberToNewBase (String number,int base, int newBase){
            return Integer.toString(Integer.parseInt(number, base), newBase);
        }
    }
