import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay1 {public static void main(String [] args) throws FileNotFoundException {
    int total = 0;
    int lineLength;
    String thisLine, lineStringVal;


    try{
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day1input.txt"));
        while((thisLine = br.readLine()) != null){
            lineLength = thisLine.length();
            char fc = '!';
            char sc = '!';
            for(int a = 0; a < lineLength; a ++){
                int c1 = thisLine.charAt(a);
                if(c1 > 47 && c1 < 58 && fc == '!'){
                    fc = (char)c1;
                }
                int c2 = thisLine.charAt(lineLength - a - 1);
                if(c2 > 47 && c2 < 58 && sc == '!'){
                    sc = (char)c2;
                }
            }
            lineStringVal = Character.toString(fc) + Character.toString(sc);
            total += Integer.parseInt(lineStringVal);
        }
        System.out.println("Total is " + total);


    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}
}
