import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay9 {

    public static void main(String [] args) {
        String thisLine;
        int answer = 0;
        int answer2 = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day9input.txt"));
            while ((thisLine = br.readLine()) != null) {
                String[] o = thisLine.split(" ");
                int[][]oasis = new int[o.length][o.length - 1];
                for(int a = 0; a < o.length; a ++){
                    oasis[a][0] = Integer.parseInt(o[a]);
                }
                int depth = 0;
                boolean done = false;
                while(!done){
                    boolean allZero = true;
                    depth ++;
                    for(int a = 0 ; a < oasis.length - depth; a ++){
                        oasis[a][depth] = oasis[a + 1][depth - 1] - oasis[a][depth - 1];
                        if(oasis[a][depth] != 0) allZero = false;
                    }
                    if(allZero) done = true;
                }
                int cv = 0;
                int pv = 0;
                for(int climb = depth; climb > 0; climb --){
                    cv = cv + oasis[oasis.length - climb][climb - 1];
                    pv = oasis[0][climb - 1] - pv;
                }
                answer += cv;
                answer2 += pv;
            }
            System.out.println("The answer to part 1 is " + answer);
            System.out.println("The answer to part 2 is " + answer2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
