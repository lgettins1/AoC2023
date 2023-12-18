import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay10 {
    public static char[][] map = new char[140][140];
    public static int[][] visited = new int [2][1000000];
    public static int coordinateCount = 0;
    public static int maxDim = 140;

    public static void main(String [] args) {
        String thisLine;
        int answer = 1;
        int answer2;
        String[]pipes = new String[141];
        int row = 0;
        int sy = 0;
        int sx = 0;
        int maxX = 4;
        try {
            for(int initY = 0; initY < maxDim; initY ++){
                for(int initX = 0; initX < maxDim; initX ++){
                    map[initX][initY] = '.';
                }
            }
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day10input.txt"));
            while ((thisLine = br.readLine()) != null) {
                pipes[row] = thisLine;
                maxX = thisLine.length() - 1;
                if(thisLine.contains("S")){
                    sy = row;
                    for(int a = 0 ; a < thisLine.length(); a ++){
                        if(thisLine.charAt(a) == 'S') sx = a;
                    }
                }
                row ++;
             }
            char c1 = 'a';
            char c2 = 'a';
            char c3 = 'a';
            int dir = 0;
            if(sy > 0)  c1 = pipes[sy - 1].charAt(sx);
            if(sx < maxX) c2 = pipes[sy].charAt(sx + 1);
            if(sy < row) c3 = pipes[sy + 1].charAt(sx);
            int nx = sx;
            int ny = sy;
            if(c1 == '|' || c1 == 'F' || c1 == '7') {
                dir = 1;
                ny = sy - 1;
            } else {
                if(c2 == 'J' || c2 == '-' || c2 == '7'){
                    dir = 2;
                    nx = sx + 1;

                } else {
                    if(c3 =='|' || c3 == 'J'|| c3 =='L'){
                        dir = 3;
                        ny = sy + 1;
                    }
                }
            }
            map[sx][sy] = 'S';
            while(pipes[ny].charAt(nx) !='S'){
                dir = newDir(dir, pipes[ny].charAt(nx));
                visited[0][coordinateCount] = nx;
                visited[1][coordinateCount] = ny;
                coordinateCount ++;
                map[nx][ny] = pipes[ny].charAt(nx);
                ny = ny + (dir % 2) * (dir - 2);
                nx = nx + ((dir + 1) % 2) * (3 - dir);
                answer ++;
            }
            for(int start = 0; start < maxDim; start ++){
                flood(start, 0);
                flood(start, maxDim - 1);
                flood(0, start);
                flood(maxDim - 1, start);
            }
            for(int xl = 0; xl < maxDim; xl ++){
                for(int yl = 0; yl < maxDim; yl ++){
                    if(map[xl][yl] == '.'){
                        double barriers = 0;
                        if(yl == sy && xl < sx){
                            for(int walk = xl; walk >= 0; walk --){
                                switch (map[walk][yl]) {
                                    case '|' -> barriers ++;
                                    case 'F', 'J' -> barriers += 0.5;
                                    case '7', 'L' -> barriers -= 0.5;
                                }
                            }
                        } else {
                            for(int walk = xl; walk < maxDim; walk ++){
                                switch (map[walk][yl]) {
                                    case '|' -> barriers ++;
                                    case 'F', 'J' -> barriers += 0.5;
                                    case '7', 'L' -> barriers -= 0.5;
                                }
                            }
                        }
                        if(barriers % 2 == 0){
                            flood(xl, yl);
                        }
                    }
                }
            }



            for(int initY = 0; initY < maxDim; initY ++){
                for(int initX = 0; initX < maxDim; initX ++){
                    System.out.print(map[initX][initY]);
                }
                System.out.println();
            }
            answer2 = (maxDim * maxDim) - (coordinateCount + 1);

            System.out.println("The answer to part 1 is " + answer / 2);
            System.out.println("The answer to part 2 is " + answer2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int newDir(int dir, char x){
        switch(x){
            case '|':
            case '-':
            case 'S':
                break;
            case'L':
                if(dir == 4){
                    dir = 1;
                } else {
                    dir = 2;
                }
                break;
            case 'J':
                if(dir == 2){
                    dir = 1;
                } else {
                    dir = 4;
                }
                break;
            case '7':
                if (dir == 2){
                    dir = 3;
                } else {
                    dir = 4;
                }
                break;
            case 'F':
                if (dir == 4){
                    dir = 3;
                } else {
                    dir = 2;
                }
        }
        return dir;
    }
    public static void flood(int x, int y){
        if(map[x][y] == '.'){
            map[x][y] = 'O';
            visited[0][coordinateCount] = x;
            visited[1][coordinateCount] = y;
            coordinateCount ++;
            for(int xs = x - 1; xs <= x + 1; xs ++){
                for(int ys = y - 1; ys <= y + 1;  ys ++){
                    if(xs >= 0 && xs < maxDim && ys >= 0 && ys < maxDim){
                        boolean original = true;
                        for(int a = 0; a < coordinateCount; a ++){
                            if (visited[0][a] == xs && visited[1][a] == ys) {
                                original = false;
                                break;
                            }
                        }
                        if(original) {
                             flood(xs, ys);
                        }
                    }
                }
            }
        }
    }
}
