import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AoCDay8b {

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

            int aNodeCount = 0;
            ArrayList<String> aList = new ArrayList<>();
            for(int a = 0; a < mapNodes; a ++){
                if(map[a][0].charAt(2) == 'A'){
                    aList.add(map[a][0]);
                    aNodeCount ++;
                }
            }
            ArrayList<Integer> stepList = new ArrayList<>();
            for(int ghosts = 0; ghosts < aNodeCount; ghosts ++) {
                int steps = 0;
                int lrSteps = 0;
                boolean foundZZZ = false;
                while (!foundZZZ) {
                    if (lrSteps == leftRightLen) lrSteps = 0;
                    int mapLine = -1;
                    for (int a = 0; a < mapNodes; a++) {
                        if (map[a][0].equals(aList.get(ghosts))) mapLine = a;
                    }
                    int nextStep = 0;
                    switch (leftRight.charAt(lrSteps)) {
                        case 'L' -> nextStep = 1;
                        case 'R' -> nextStep = 2;
                    }
                    aList.set(ghosts, map[mapLine][nextStep]);
                    lrSteps++;
                    steps++;
                    if (aList.get(ghosts).charAt(2) == 'Z') {
                        foundZZZ = true;
                        stepList.add(steps);
                    }
                }
            }
            long answer = lcm((long)stepList.get(0),(long) stepList.get(1));
            for(int a = 2; a < aNodeCount; a ++) {
                answer = lcm(answer, (long)stepList.get(a));
            }
            System.out.println("The answer to part 2 is " + answer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }
    public static long lcm(long... numbers) {
        return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }
}
