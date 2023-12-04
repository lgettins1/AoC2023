import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay1b {
    public static void main(String [] args) {
        int total = 0;
        int lineLength;
        String thisLine;

        try{
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day1input.txt"));
            while((thisLine = br.readLine()) != null){
                lineLength = thisLine.length();
                int fc = 99;
                int sc = 99;
                for(int a = 0; a < lineLength; a ++){
                   if(fc == 99) fc = findNum(thisLine.substring(a));
                   if(sc == 99) sc = findNum(thisLine.substring(lineLength - a - 1));
                }

                total += (fc * 10) + sc;
            }
            System.out.println("Total is " + total);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int findNum(String extra){
        int a = 99;
        String[] numList = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        int c0 = extra.charAt(0);
        if(c0 > 47 && c0 < 58){
            a = c0 - 48;
        } else {
            for(int n = 0; n < 9; n ++){
                if(extra.startsWith(numList[n])){
                    a = n + 1;
                }
            }
        }
        return a;
    }
}
