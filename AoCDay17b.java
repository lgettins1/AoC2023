import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AoCDay17b {

    public static int height = 0;
    public static int width = 0;
    public static Map<String, Integer> visited = new HashMap<>();
   public static int bestCost = 99999999;
    public static int[][] heatLoss = new int [150][150];
    public static void main(String[] args) throws IOException {
        long inst1 = System.currentTimeMillis();
        String thisLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day17input.txt"));
            while ((thisLine = br.readLine()) != null) {
                width = thisLine.length();
                for(int a = 0; a < width; a ++){
                    heatLoss[a][height] = Integer.parseInt(thisLine.substring(a, a + 1));
                }
                height ++;
            }

            visit(1,0,1,0, heatLoss[1][0]);
            visit(0,1,2,0, heatLoss[0][1]);
            System.out.println("The answer is " + bestCost);
            long inst2  = System.currentTimeMillis();
            System.out.println("Run time duration " + (inst2 - inst1) + " ms.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void visit(int x, int y, int dir, int soFar, int cost) {
        String loc = x + " " + y + " " + dir + " " + soFar;
        if (x == width - 1 && y == height - 1) {
            if(cost < bestCost) bestCost = cost;
        } else {
            Integer a = visited.get(loc);
            if(a == null || cost < a){
                visited.put(loc, cost);
                for(int ch = 1; ch < 5; ch ++){
                    int newSoFar = soFar + 1;
                    if(ch != dir && newSoFar < 4) continue;
                    int nx = x + (2 - ch) % 2;
                    int ny = y + (3 - ch) % 2;
                    if(nx < width && nx >= 0 && ny < height && ny >= 0){
                        if(!(dir != ch && ((ch == 3 && x < 4) || (ch == 1 && x > width - 5) || (ch == 2 && y > height - 5) ||
                                (ch == 4 && y < 4)))){
                            int op = ((4 - dir) % 2 * (4 - dir)) + ((5 - dir) % 2 * (6 - dir));
                            if(!(ch == dir && newSoFar == 10) && !(op == ch)) {
                                if (dir != ch) {
                                    newSoFar = 0;
                                }
                                if (cost + heatLoss[nx][ny] <= bestCost) visit(nx, ny, ch, newSoFar, cost + heatLoss[nx][ny]);
                            }
                        }
                    }
                }
            }
        }
    }
}
