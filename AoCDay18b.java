import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Combined Shoelace and Pick algorithms
public class AoCDay18b {
   public static  void main(String[] args) throws IOException {
        String thisLine;
        int x= 0;
        int y = 0;
        int minx = 999;
        int miny = 999;

        ArrayList<int[]> digPlan = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day18input.txt"));
            while ((thisLine = br.readLine()) != null) {
                String[] p = thisLine.split(" ");
                String distance = p[2].substring(2, p[2].length() - 2);
                int dist = Integer.parseInt(convertNumberToNewBase(distance, 16, 10));
                String direction = p[2].substring(p[2].length() - 2, p[2].length() - 1);
                int dir = Integer.parseInt(direction) + 1;
                x += ((2 - dir) % 2) * dist;
                y += ((3 - dir) % 2) * dist;
                if (x < minx) minx = x;
                if (y < miny) miny = y;
                int[] b = {dir, dist};
                digPlan.add(b);
            }

            ArrayList<int[]> coordinates = new ArrayList<>();
            x = -minx;
            y = -miny;
            int perimeter = 0;
            for (int[] ints : digPlan) {
                int[] b = {x, y};
                coordinates.add(b);
                x = x + ((2 - ints[0]) % 2) * ints[1];
                y = y + ((3 - ints[0]) % 2) * ints[1];
                perimeter += ints[1];
            }
            long area = 0;
            for(int a = 0; a < coordinates.size() - 1; a ++){
                long p1 = (long) coordinates.get(a)[0] * coordinates.get(a + 1)[1];
                long p2 = (long) coordinates.get(a)[1] * coordinates.get(a + 1)[0];
                area += p1 - p2;
            }
            long p1 = (long) coordinates.get(coordinates.size() - 1)[0] * coordinates.get(0)[1];
            long p2 = (long) coordinates.get(coordinates.size() - 1)[1] * coordinates.get(0)[0];
            area += p1 - p2;
            area = Math.abs(area) / 2;
            long internal = 1 + area - (perimeter /2);
            long answer = internal + perimeter;

            System.out.println("The answer is " + answer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertNumberToNewBase (String number,int base, int newBase){
        return Integer.toString(Integer.parseInt(number, base), newBase);
    }

}
