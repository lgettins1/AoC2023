import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class AoCDay21Try3or4 {

    public static int width;
    public static int maxSteps = 130;
    public static char[][] plotMap;
    public static String[][] visited;

    public static void main(String[] args) {
        long bigMaxSteps = 26501365;
        int y = 0;
        String thisLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day21input.txt"));
            thisLine = br.readLine();
            width = thisLine.length();
            plotMap = new char[width][width];
            visited = new String[width][width];
            while (thisLine  != null) {
                int x = 0;
                while(x < thisLine.length()){
                    plotMap[x][y] = thisLine.charAt(x);
                    x ++;
                }
                y ++;
                thisLine = br.readLine();
            }
            int evenTile = runScenario(65, 65, 130);
            int oddTile = runScenario(65, 65, 129);
            int evenDiamond =  runScenario(65, 65, 64);
            int oddDiamond =  runScenario(65, 65, 65);
            long tileWidth =  (2 * bigMaxSteps + 1) / width;
            long evenTileCount;
            long oddTileCount;
            long oddOrEven = ((tileWidth - 1) / 2);
            if((oddOrEven + maxSteps) % 2 == 0){
                evenTileCount = (long) Math.pow(oddOrEven + 1, 2);
                oddTileCount = (long) Math.pow(oddOrEven, 2);
            } else {
                evenTileCount = (long) Math.pow(oddOrEven, 2);
                oddTileCount = (long) Math.pow(oddOrEven + 1, 2);
            }
            long finalAnswer = (oddTileCount * oddTile) + (evenTileCount * evenTile) -
                    ((oddOrEven + 1) * (oddTile - oddDiamond)) + (oddOrEven * (evenTile - evenDiamond));
            System.out.println("The final answer is " + finalAnswer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int runScenario(int x, int y, int steps){
       String zeroes = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        for(int a = 0; a < width; a ++){
            for(int b = 0; b < width; b ++){
                visited[a][b] = zeroes;
            }
        }
        char[][] copyOfPlotMap = Arrays.stream(plotMap).map(char[]::clone).toArray(char[][]::new);
        maxSteps = steps;
        takeStep(x, y, 0);
        int answer = 0;
        for(int row = 0; row < width; row ++){
            for(int col = 0; col < width; col ++){
                if(plotMap[col][row] == 'O') answer ++;
            }
       }
        plotMap = Arrays.stream(copyOfPlotMap).map(char[]::clone).toArray(char[][]::new);
        return answer;
    }
    public static void takeStep(int x, int y , int step) {
        if (step == maxSteps) {
            plotMap[x][y] = 'O';
        } else {
            step++;
            int[][] dirs = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
            for (int loop = 0; loop < 4; loop++) {
                int nx = x + dirs[loop][0];
                int ny = y + dirs[loop][1];
                if (!(nx < 0 || nx == width || ny < 0 || ny == width)) {
                    if (plotMap[nx][ny] != '#') {
                        if (visited[nx][ny].charAt(step) == '0') {
                            String s1 = visited[nx][ny].substring(0, step);
                            String s2 = visited[nx][ny].substring(step + 1);
                            visited[nx][ny] = s1 + "1" + s2;
                            takeStep(nx, ny, step);
                        }
                    }
                }
            }
        }
    }
}
