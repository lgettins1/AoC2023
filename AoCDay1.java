import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay1 {
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
                    int c1 = thisLine.charAt(a);
                    if(c1 > 47 && c1 < 58 && fc == 99){
                        fc = c1 - 48;
                    }
                    int c2 = thisLine.charAt(lineLength - a - 1);
                    if(c2 > 47 && c2 < 58 && sc == 99){
                        sc = c2 - 48;
                    }
                }
                total += (fc * 10 + sc);
            }
            System.out.println("Total is " + total);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
