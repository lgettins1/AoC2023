import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay12 {

    public static void main(String [] args) {
        String thisLine;
        long answer = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day12input.txt"));
            while ((thisLine = br.readLine()) != null) {
                int[] numList = new int[50];
                String[] line = thisLine.split(" ");
                String[] nums = line[1].split(",");
                int numCount = nums.length;
                int numSum = 0;
                for (int a = 0; a < numCount; a++) {
                    numList[a] = Integer.parseInt(nums[a]);
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
                    d = b.length();
                    boolean candidate =true;
                    int len = 0;
                    for(int e = 0; e < d; e ++){
                        int mn = Integer.parseInt(convertNumberToNewBase(b.substring(e, e + 1),maxDot, 10));
                        len += mn;
                        if(e > 0 && e < d - 1 && b.charAt(e) == '0') candidate = false;
                    }
                    if(numSum + len != line[0].length()) candidate = false;
                    StringBuilder ourString = new StringBuilder();
                    if(candidate){
                        if(b.charAt(0) != '0'){
                            int mn = Integer.parseInt(convertNumberToNewBase(b.substring(0, 1),maxDot, 10));
                            ourString.append(".".repeat(Math.max(0, mn)));
                        }
                        for(int f = 0; f < numCount; f ++){
                            ourString.append("#".repeat(Math.max(0, numList[f])));
                            if(b.charAt(f + 1) != '0'){
                                int mn = Integer.parseInt(convertNumberToNewBase(b.substring(f + 1, f + 2),maxDot, 10));
                                ourString.append(".".repeat(Math.max(0, mn)));
                            }

                        }
                    }
                    for(int f = 0; f < ourString.length(); f ++){
                        if(ourString.charAt(f) != line[0].charAt(f) && line[0].charAt(f) != '?') candidate = false;
                    }
                    if(candidate) {
                        answer ++;
                    }
                }
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
