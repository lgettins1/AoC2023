import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay16 {
    public static String[] grid = new String[110];
    public static int[][] visited = new int [30000][3];
    public static int moveCount;
    public static int gridSize = 0;

    public static void main(String[] args) throws IOException {
        String thisLine;
        int maxAnswer = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day16input.txt"));
            while ((thisLine = br.readLine()) != null) {
                grid[gridSize] = thisLine;
                gridSize ++;
            }
            for(int try1 = 0; try1 < gridSize; try1 ++){
                moveCount = 0;
                move(0,try1,1);
                int answer = countUnique();
                if(answer > maxAnswer) maxAnswer = answer;
            }
            for(int try1 = 0; try1 < gridSize; try1 ++){
                moveCount = 0;
                move(grid[0].length() - 1,try1,3);
                int answer = countUnique();
                if(answer > maxAnswer) maxAnswer = answer;
            }
            for(int try1 = 0; try1 < grid[0].length(); try1 ++){
                moveCount = 0;
                move(try1,0,2);
                int answer = countUnique();
                if(answer > maxAnswer) maxAnswer = answer;
            }
            for(int try1 = 0; try1 < grid[0].length(); try1 ++){
                moveCount = 0;
                move(try1,gridSize - 1,4);
                int answer = countUnique();
                if(answer > maxAnswer) maxAnswer = answer;
            }
            System.out.println("The answer is " + maxAnswer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void move(int x, int y, int dir){
        if(x >= 0 && y >= 0 && x < grid[0].length() && y < gridSize){
            boolean dup = false;
            for(int a = 0; a <= moveCount; a ++){
                if (x == visited[a][0] && y == visited[a][1] && dir == visited[a][2]) {
                    dup = true;
                    break;
                }
            }
            if(!dup){
                visited[moveCount][0] = x;
                visited[moveCount][1] = y;
                visited[moveCount][2] = dir;
                moveCount ++;
 //               System.out.println( moveCount + " " + x + "," + y + " " + dir);
                switch(grid[y].substring(x, x + 1)){
                    case ".":
                        move(x + (2 - dir) % 2, y + (3 - dir) % 2, dir);
                        break;
                    case "/":
                        move(x - (3 - dir) % 2, y - (2 - dir) % 2, 5 - dir);
                        break;
                    case "\\":
                        move(x + (3 - dir) % 2, y + (2 - dir) % 2, dir - (5 - dir) % 2 + (4 - dir) % 2);
                        break;
                    case "|":
                        if(dir == 2 || dir == 4){
                            move(x , y + (3 - dir) % 2, dir);
                        } else {
                            move(x, y - 1, 4);
                            move(x, y + 1, 2);
                        }
                        break;
                    case "-":
                        if(dir == 1|| dir == 3){
                            move(x + (2 - dir) % 2, y , dir);
                            break;
                        } else {
                            move (x - 1, y, 3);
                            move (x + 1, y, 1);
                        }
                }
            }
        }
    }
    public static int countUnique(){
        int answer = 0;
        for(int a = 0; a < moveCount; a ++) {
            boolean unique = true;
            for (int b = 0; b < a; b++) {
                if (visited[a][0] == visited[b][0] && visited[a][1] == visited[b][1]) {
                    unique = false;
                    break;
                }
            }
            if (unique) answer++;
        }
        return answer;
    }
}
