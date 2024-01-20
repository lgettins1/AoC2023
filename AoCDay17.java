import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AoCDay17 {
    public static int height = 0;
    public static int width = 0;
    public static Map<String, Integer> visited = new HashMap<>();
    public static int bestCost = 99999999;
    public static int[][] heatLoss= new int [150][150];
    public static void main(String[] args) throws IOException {
        String thisLine;
        String[] grid = new String [150];
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day17input.txt"));
            while ((thisLine = br.readLine()) != null) {
                grid[height] = thisLine;
                height ++;
            }
            width = grid[0].length();
            for(int a = 0; a < height; a ++){
                for(int b = 0; b < width; b ++){
                    heatLoss[b][a] = Integer.parseInt(grid[a].substring(b, b + 1));
                }
            }

            visit(1,0,1,0, heatLoss[1][0],"");
            visit(0,1,2,0, heatLoss[0][1], "");
            System.out.println("The answer is " + bestCost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void visit(int x, int y, int dir, int soFar, int cost, String path) {
        String loc = x + " " + y + " " + dir + " " + soFar;
        if (x == width - 1 && y == height - 1) {
            System.out.println(path + " success " + cost);
            if(cost < bestCost) bestCost = cost;
        } else {
            Integer a = visited.get(loc);
            boolean newSquare = true;
            if(a != null) {
                if (cost >= a) newSquare = false;
            }
            if(newSquare){
                visited.put(loc, cost);
                for(int ch = 1; ch < 5; ch ++){
                    int newSoFar = soFar;
                    int nx = x + (2 - ch) % 2;
                    int ny = y + (3 - ch) % 2;
                    if(nx < width && nx >= 0 && ny < height && ny >= 0){
                        int op = ((4 - dir) % 2 *(4 - dir)) + ((5 - dir) % 2 * (6 - dir));
                        if(!(ch == dir && soFar == 2) && !(op == ch)){
                            if(dir == ch){
                                newSoFar ++;
                            } else {
                                newSoFar = 0;
                            }
                            if(cost + heatLoss[nx][ny] <= bestCost) visit(nx, ny, ch, newSoFar, cost + heatLoss[nx][ny],
                                    path + " (" + nx + "," + ny + ") " + heatLoss[nx][ny] + " " +
                                            (cost + heatLoss[nx][ny]) + " s:" + newSoFar);
                        }
                    }
                }
            }
        }
    }
}
