import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class AoCDay5bSecondTry {

    public static void main(String [] args) {
        long answer = 9999999999L;
        String thisLine;
        int section = 0;
        int seedCount = 0;
        int ruleNum = 0;
        long[][][] rules = new long[8][50][3];
        long[] map = new long[100];

        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day5input.txt"));
            thisLine = br.readLine();
            String[] a = thisLine.split(" ");
            for(int b = 0; b < a.length - 1; b ++){
                map[b] = Long.parseLong(a[b + 1]);
                seedCount ++;
            }
            while ((thisLine = br.readLine()) != null) {
                if(thisLine.trim().equals("")){
                    section ++;
                    ruleNum = 0;
                    br.readLine();
                } else {
                    String[] sp = thisLine.split((" "));
                    for(int th = 0; th < 3; th ++){
                        rules[section][ruleNum][th] = Long.parseLong(sp[th]);
                    }
                    ruleNum ++;
                }
            }
            long[][] seedMap = new long [1000][2];
            int pairCount = 0;
            for(int seeds = 0; seeds < seedCount; seeds += 2){
                seedMap[seeds / 2][0] = map[seeds];
                seedMap[seeds / 2][1] = map[seeds] + map[seeds + 1] - 1;
                pairCount ++;
            }

            for (int rc =  1 ; rc < rules.length; rc ++) {
                long[][] mapDest = new long [2000][2];
                int mapPairs = 0;
                for(int scanMap = 0; scanMap < pairCount; scanMap ++){
                    long[][] mapSource = new long [2000][2];

                    for(int thisRule = 0; thisRule < rules[rc].length; thisRule ++ ){
                        long start = -1;
                        long end = -1;
                        long rs = rules[rc][thisRule][1];
                        long re = rules[rc][thisRule][1] + rules[rc][thisRule][2] - 1;
                        if(seedMap[scanMap][0] >= rs && seedMap[scanMap][0] <= re){
                            start = seedMap[scanMap][0];
                            end = Math.min(seedMap[scanMap][1], re);
                        } else {
                            if(seedMap[scanMap][0] < rs){
                                if(seedMap[scanMap][1] >= rs && seedMap[scanMap][1] <= re){
                                    start = rs;
                                    end = seedMap[scanMap][1];
                                }
                                if(seedMap[scanMap][1] > re){
                                    start = rs;
                                    end = re;
                                }
                            }
                        }
                        if(start > -1){
                            mapSource[mapPairs][0] = start;
                            mapSource[mapPairs][1] = end;
                            mapDest[mapPairs][0] = start - rules[rc][thisRule][1] + rules[rc][thisRule][0];
                            mapDest[mapPairs][1] = end - rules[rc][thisRule][1] + rules[rc][thisRule][0];
                            mapPairs ++;
                        }
                    }
                    Arrays.sort(mapSource, Comparator.comparingLong(x -> x[0]));
                    long mapGapsS = seedMap[scanMap][0];
                    for (long[] longs : mapSource) {
                        if (longs[1] > 0) {
                            if (longs[0] > mapGapsS) {
                                mapDest[mapPairs][0] = mapGapsS;
                                mapDest[mapPairs][1] = longs[0] - 1;
                                mapPairs++;
                            }
                            mapGapsS = longs[1] + 1;
                        }
                    }
                    if(mapGapsS < seedMap[scanMap][1]){
                        mapDest[mapPairs][0] = mapGapsS;
                        mapDest[mapPairs][1] = seedMap[scanMap][1];
                        mapPairs ++;
                    }
                }
                System.out.println(rc + "," + mapPairs);
                for(int ql = 0; ql < mapPairs; ql ++){
                    System.out.println("(" + mapDest[ql][0] + "," + mapDest[ql][1] + ")");
                }
                pairCount = mapPairs;
                seedMap = mapDest.clone();
            }
            for(int ql2 = 0; ql2 < pairCount; ql2 ++){
                if(seedMap[ql2][0] < answer) answer = seedMap[ql2][0];
            }
            System.out.println("The answer to part 2 is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
