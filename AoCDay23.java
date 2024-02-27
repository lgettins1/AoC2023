import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoCDay23 {
    public static String[] maze;
    public static int answer = 0;
    public static int[][][] globalVisited;

    public static void main(String[] args) {
        String thisLine;
        int startX = 1;
        int startY = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day23input.txt"));
            thisLine = br.readLine();
            maze = new String[thisLine.length()];
            maze[0] = thisLine;
            int mazeSize = 1;
            while ((thisLine = br.readLine()) != null) {
                maze[mazeSize] = thisLine;
                mazeSize ++;
            }
            globalVisited = new int[mazeSize][mazeSize][4];
            int steps = 0;
            ArrayList<String> visited = new ArrayList<>();
            step(startX, startY, steps, visited, 1);
            System.out.println("The answer to part one is " + answer);
            answer = 0;
            step(startX, startY, steps, visited, 0);
            System.out.println("The answer to part two is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void step(int x, int y, int steps, ArrayList<String> visited,int slippy) {
        steps++;
        String step1 = x + "," + y;
        ArrayList<String> visitedCopy = (ArrayList<String>)visited.clone();
        visitedCopy.add(step1);
        if (y == maze.length - 1) {
           if(answer < steps - 1) answer = steps - 1;
           System.out.println(slippy + " " + x + "," + y + " " + steps + " " + answer);
        } else {
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int d = 0; d < 4; d++) {
                int nx = x + directions[d][0];
                int ny = y + directions[d][1];
                if (ny > 0 && maze[ny].charAt(nx) != '#') {
                    String step2 = nx + "," + ny;
                    if (!visited.contains(step2)) {
                        if(slippy == 1){
                           if (maze[ny].charAt(nx) == '.' || (maze[ny].charAt(nx) == '>' && d == 1) || (maze[ny].charAt(nx) == '<'
                                && d == 3) || (maze[ny].charAt(nx) == '^' && d == 2) || (maze[ny].charAt(nx) == 'v' && d == 0)) {
                           step(nx, ny, steps, visitedCopy, slippy);
                           }
                        } else {
                            if(globalVisited[nx][ny][d] <= steps) {
  //                              globalVisited[nx][ny][d] = steps;
                                step(nx, ny, steps, visitedCopy, slippy);
 //                             System.out.println(nx + "," + ny + " " + d + " " + globalVisited[nx][ny][d] +  " " + steps);
                            }
                        }
                    }
                }
            }
        }
    }
}
