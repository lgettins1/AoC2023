import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay8 {

    public static void main(String [] args) {
        String thisLine;
        String leftRight;
        String[][] map = new String[800][3];
        int mapNodes = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day8input.txt"));
            leftRight = br.readLine();
            br.readLine();
            while ((thisLine = br.readLine()) != null) {
                map[mapNodes][0] = thisLine.substring(0, 3);
                map[mapNodes][1] = thisLine.substring(7, 10);
                map[mapNodes][2] = thisLine.substring(12, 15);
                mapNodes ++;
            }
            int leftRightLen = leftRight.length();
            int steps = 0;
            int lrSteps = 0;
            String currNode = "AAA";
            boolean foundZZZ = false;
            while(!foundZZZ){
                if(lrSteps == leftRightLen) lrSteps = 0;
                int mapLine = -1;
                for(int a = 0; a < mapNodes; a ++){
                    if(map[a][0].equals(currNode)) mapLine = a;
                }
                int nextStep = 0;
                switch (leftRight.charAt(lrSteps)) {
                    case 'L' -> nextStep = 1;
                    case 'R' -> nextStep = 2;
                }
                lrSteps ++;
                steps ++;
                currNode = map[mapLine][nextStep];
                if(currNode.equals("ZZZ")){
                    foundZZZ = true;
                }
            }
            System.out.println("The answer to part 1 is " + steps);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
