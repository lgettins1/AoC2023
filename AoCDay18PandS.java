import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//Combined Shoelace and Pick algorithms
public class AoCDay18PandS {

    public static void main(String[] args) throws IOException {
        String thisLine;
        int x = 0;
        int y = 0;
        int minx = 999;
        int miny = 999;

        ArrayList<int[]> digPlan = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day18input.txt"));
            while ((thisLine = br.readLine()) != null) {
                String[] p = thisLine.split(" ");
                int dir = 0;
                int dist = Integer.parseInt(p[1]);
                switch (p[0]) {
                    case "D" -> {
                        y += dist;
                        dir = 2;
                    }
                    case "L" -> {
                        x -= dist;
                        if (x < minx) minx = x;
                        dir = 3;
                    }
                    case "U" -> {
                        y -= dist;
                        if (y < miny) miny = y;
                        dir = 4;
                    }
                    case "R" -> {
                        x += dist;
                        dir = 1;
                    }
                }
                int[] b = {dir, dist};
                digPlan.add(b);
            }
            ArrayList<int[]>coords = new ArrayList<>();
            x = -minx;
            y = -miny;
            int perimeter = 0;
            for (int[] ints : digPlan) {
                int[] b = {x, y};
                coords.add(b);
                x = x + ((2 - ints[0]) % 2) * ints[1];
                y = y + ((3 - ints[0]) % 2) * ints[1];
                perimeter += ints[1];
            }
            long area = 0;
            for(int a = 0; a < coords.size() - 1; a ++){
                long p1 = (long) coords.get(a)[0] * coords.get(a + 1)[1];
                long p2 = (long) coords.get(a)[1] * coords.get(a + 1)[0];
                area += p1 - p2;
            }
            long p1 = (long) coords.get(coords.size() - 1)[0] * coords.get(0)[1];
            long p2 = (long) coords.get(coords.size() - 1)[1] * coords.get(0)[0];
            area += p1 - p2;
            area = Math.abs(area) / 2;
            long internal = 1 + area - (perimeter /2);
            long answer = internal + perimeter;

            System.out.println("The answer is " + answer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
