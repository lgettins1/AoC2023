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
                char fc = '!';
                char sc = '!';
                for(int a = 0; a < lineLength; a ++){
                   if(fc == '!') fc = findNum(thisLine.substring(a));
                   if(sc == '!') sc = findNum(thisLine.substring(lineLength - a - 1));
                }
                String lineVal = Character.toString(fc) + sc;
                total += Integer.parseInt(lineVal);
            }
            System.out.println("Total is " + total);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static char findNum(String extra){
        char a = '!';
        String[] numList = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        int c0 = extra.charAt(0);
        if(c0 > 47 && c0 < 58){
            a = (char) c0;
        } else {
            for(int n = 0; n < 9; n ++){
                if(extra.startsWith(numList[n])){
                    a = (char) (n + 49);
                }
            }
        }
        return a;
    }
}
