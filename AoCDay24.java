import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoCDay24 {
    public static void main(String[] args) {
        String thisLine;
        long minX = 200000000000000L;
        long maxX = 400000000000000L;
        int answer = 0;
        ArrayList<long[]> hailstones = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day24input.txt"));
            while ((thisLine = br.readLine()) != null) {
                long[] numLine = new long[6];
                String[] line = thisLine.split("[,@]");
                for(int a = 0; a < line.length; a ++) {
                    numLine[a] = Long.parseLong(line[a].trim());
                }
                hailstones.add(numLine);
            }
            for(int s1 = 0; s1 < hailstones.size() - 1; s1 ++){
                double a1 = hailstones.get(s1)[0];
                double b1 = hailstones.get(s1)[1];
                double c1 = hailstones.get(s1)[2];
                double d1 = hailstones.get(s1)[3];
                double e1 = hailstones.get(s1)[4];
                double f1 = hailstones.get(s1)[5];

                for(int s2 = s1 + 1; s2 < hailstones.size(); s2 ++){
                    double a2 = hailstones.get(s2)[0];
                    double b2 = hailstones.get(s2)[1];
                    double c2 = hailstones.get(s2)[2];
                    double d2 = hailstones.get(s2)[3];
                    double e2 = hailstones.get(s2)[4];
                    double f2 = hailstones.get(s2)[5];
                    double sl1a = e1/d1; double sl1b = f1/d1;
                    double sl2a = e2/d2; double sl2b = f2/d2;
                    double t2a = ((d1 / e1) * (b2 - b1) + a1 - a2) / (d2 - (e2 * d1 / e1));
                    double t2b = ((d1 / f1) * (c2 - c1) + a1 - a2) / (d2 - (f2 * d1 / f1));
                    if(sl1a == sl2a  && sl1b == sl2b){
                        System.out.println(s1 + " " + s2 + " " + sl1a + " " + sl2a + " " + sl1b + " " + sl2b);
                    }
                    if(t2a == t2b){
                        System.out.println(s1 + " " + s2);
                    }

                    if(e1/d1 != e2/d2){
                        double xv = (b2 - b1 + (a1 * e1 / d1) - (a2 * e2 / d2)) / ((e1 / d1) - (e2 / d2));
                        double yv = (e1 * xv / d1) + (b1 - (a1 * e1 / d1));
                        if(xv >= minX && xv <= maxX && yv >= minX && yv <= maxX){
                            if((xv - a1) * d1 > 0 && (xv - a2) * d2 > 0) {
            //                    System.out.println(s1 + "," + s2 + " " + xv + "," + yv);
                                answer ++;
                            }
                        }
                    }

                }
            }
            System.out.println("The answer is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
