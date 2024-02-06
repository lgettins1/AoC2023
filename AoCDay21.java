import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AoCDay21 {
    public static int width = 11;
    public static int maxSteps = 64;
    public static char[][] plotMap = new char[width][width];
    public static String[][] visited = new String[width][width];

    public static void main(String[] args) {
        int startX = 0;
        int startY = 0;
        int y = 0;
        String thisLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day21inputb.txt"));
            while ((thisLine = br.readLine()) != null) {
                int x = 0;
                while(x < thisLine.length()){
                    plotMap[x][y] = thisLine.charAt(x);
                    if(thisLine.charAt(x) == 'S'){
                        startX = x;
                        startY = y;
                    }
                    x ++;
                }
                y ++;
            }
            String zeroes = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
            for(int a = 0; a < width; a ++){
                for(int b = 0; b < width; b ++){
                    visited[a][b] = zeroes;
                }
            }
            takeStep(startX, startY, 0);
            int answer = 0;
            for(int row = 0; row < width; row ++){
                for(int col = 0; col < width; col ++){
                    System.out.print(plotMap[col][row]);
                    if(plotMap[col][row] == 'O') answer ++;
                }
                System.out.println();
            }
            System.out.println("The answer is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
