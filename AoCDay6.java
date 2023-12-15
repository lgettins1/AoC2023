import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay6 {
    public static void main(String [] args) {
    int raceCount;
    int answer = 1;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day6input.txt"));
            String[]timeLine = br.readLine().substring(11).trim().split("\\s+");
            String[]distanceLine = br.readLine().substring(11).trim().split("\\s+");
            raceCount = timeLine.length;
            StringBuilder time = new StringBuilder();
            StringBuilder distance = new StringBuilder();
            for(int a = 0; a < raceCount; a ++){
                time.append(timeLine[a].trim());
                distance.append(distanceLine[a].trim());
                int winCount = 0;
                int raceTime = Integer.parseInt(timeLine[a]);
                int raceData = Integer.parseInt(distanceLine[a]);
                for(int b = 0; b < raceTime; b ++){
                    if((raceTime - b) * b > raceData) winCount ++;
                }
                answer *= winCount;
            }
            System.out.println(time + " " + distance);
            long lDist = Long.parseLong(distance.toString());
            long lTime = Long.parseLong(time.toString());
            int winCount2 = 0;
            for(long c = 0; c < lTime; c++){
                if((lTime - c) * c > lDist) winCount2 ++;
            }
            System.out.println("The answer to part one is " + answer);
            System.out.println("The answer to part two is " + winCount2);


        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }
