import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoCDay18 {
    public static char[][] digMap;
    public static  void main(String[] args) throws IOException {
        String thisLine;
        int x= 0;
        int y = 0;
        int minx = 999;
        int miny = 999;
        int maxx = 0;
        int maxy = 0;

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
                        if (y > maxy) maxy = y;
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
                        if (x > maxx) maxx = x;
                        dir = 1;
                    }
                }
                int[] b = {dir, dist};
                digPlan.add(b);
            }
            int height = maxy - miny;
            int width = maxx - minx;
            digMap = new char[width + 1][height + 1];
            for(int a = 0; a <= width; a ++){
                for(int b = 0; b <= height; b ++){
                    digMap[a][b] = '.';
                }
            }
            x = -minx;
            y = -miny;
            for (int[] ints : digPlan) {
                int dir = ints[0];
                for (int walk = 0; walk < ints[1]; walk++) {
                    x += (2 - dir) % 2;
                    y += (3 - dir) % 2;
                    digMap[x][y] = '#';
                }
            }
            int sx = 0;
            int sy = 0;
            while(digMap[sx][sy] != '#')sx += 1;
            fillIn(sx + 1, sy + 1);


            int answer = 0;
            for(int a = 0; a <= height; a ++){
                for(int b = 0; b <= width; b ++){
                    System.out.print(digMap[b][a]);
                    if(digMap[b][a] == '#') answer ++;
                }
                System.out.println();
            }
            System.out.println("The answer is " + answer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void fillIn(int x, int y){
        if(digMap[x][y] == '.'){
            digMap[x][y] = '#';
            fillIn(x + 1, y);
            fillIn(x - 1, y);
            fillIn(x , y + 1);
            fillIn(x , y - 1);
        }
    }
}
