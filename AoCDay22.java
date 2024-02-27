import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


public class AoCDay22 {
    public static ArrayList<Integer>[] supports;

    public static void main(String[] args) {
        ArrayList<int[]> brickPoint = new ArrayList<>();
        String thisLine;
        int minX = 100;
        int maxX = 0;
        int minY = 100;
        int maxY = 0;
        int minZ = 100;
        int maxZ = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day22input.txt"));
            while ((thisLine = br.readLine()) != null) {
                String[] brk = thisLine.split("[,~]");
                int[] brickVal = new int[brk.length];
                for (int a = 0; a < brk.length; a++) {
                    brickVal[a] = Integer.parseInt(brk[a]);
                    switch (a) {
                        case 0, 3 -> {
                            if (brickVal[a] < minX) minX = brickVal[a];
                            if (brickVal[a] > maxX) maxX = brickVal[a];
                        }
                        case 1, 4 -> {
                            if (brickVal[a] < minY) minY = brickVal[a];
                            if (brickVal[a] > maxY) maxY = brickVal[a];
                        }
                        case 2, 5 -> {
                            if (brickVal[a] < minZ) minZ = brickVal[a];
                            if (brickVal[a] > maxZ) maxZ = brickVal[a];
                        }
                    }
                }
                brickPoint.add(brickVal);
            }
            int[][][] tower = new int[maxX + 1][maxY + 1][maxZ + 1];
            HashSet<Integer> landed = new HashSet<>();
            for (int bricks = 0; bricks < brickPoint.size(); bricks++) {
                for (int x = brickPoint.get(bricks)[0]; x <= brickPoint.get(bricks)[3]; x++) {
                    for (int y = brickPoint.get(bricks)[1]; y <= brickPoint.get(bricks)[4]; y++) {
                        for (int z = brickPoint.get(bricks)[2]; z <= brickPoint.get(bricks)[5]; z++) {
                            tower[x][y][z] = bricks + 1;
                        }
                    }
                }
            }
            int falls = 1;
            supports = new ArrayList[brickPoint.size() + 1];
            for (int i = 0; i <= brickPoint.size(); i++) {
                supports[i] = new ArrayList<>();
            }

            while (falls != 0) {
                falls = 0;
                int sx;
                int sy;

                for (int layer = 2; layer <= maxZ; layer++) {
                    for (int x = 0; x <= maxX; x++) {
                        for (int y = 0; y <= maxY; y++) {
                            if (tower[x][y][layer] != 0) {
                                int brickVal = tower[x][y][layer];
                                if (!landed.contains(brickVal)) {
                                    sx = x;
                                    sy = y;
                                    while (sx <= maxX && tower[sx][y][layer] == brickVal) sx++;
                                    while (sy <= maxY && tower[x][sy][layer] == brickVal) sy++;
                                    boolean blocked = false;
                                    for (int lx = x; lx < sx; lx++) {
                                        for (int ly = y; ly < sy; ly++) {
                                            if (tower[lx][ly][layer - 1] != 0) {
                                                blocked = true;
                                                landed.add(brickVal);
                                                if (!supports[brickVal].contains(tower[lx][ly][layer - 1]) &&
                                                        tower[lx][ly][layer] != tower[lx][ly][layer - 1]) {
                                                    supports[brickVal].add(tower[lx][ly][layer - 1]);
                                                }
                                            }
                                        }
                                    }
                                    if (!blocked) {
                                        for (int lx = x; lx < sx; lx++) {
                                            for (int ly = y; ly < sy; ly++) {
                                                tower[lx][ly][layer - 1] = brickVal;
                                                tower[lx][ly][layer] = 0;
                                                falls++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ArrayList<Integer> uniqueSupports = new ArrayList<>();
            for (int a = 1; a <= brickPoint.size(); a++) {
                if (supports[a].size() == 1) {
                    int supportVal = supports[a].get(0);
                    if (!uniqueSupports.contains(supportVal)) {
                        uniqueSupports.add(supportVal);
                    }
                }
            }
            int answer2 = 0;
            for (Integer uniqueSupport : uniqueSupports) {
                ArrayList<Integer> disintegrated = new ArrayList<>();
                disintegrated.add(uniqueSupport);
                int oneSupport = countSupport(1, disintegrated);
                answer2 += oneSupport;
            }
            int answer = brickPoint.size() - uniqueSupports.size();
            System.out.println("The answer is part one is " + answer);
            System.out.println("The answer to part two is " + answer2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int countSupport(int number, ArrayList<Integer> disintegrated) {
        int answer = 0;
        for (int a = 1; a < supports.length; a++) {
            boolean fs = true;
            for (int b = 0; b < supports[a].size(); b++) {
                if (!disintegrated.contains(supports[a].get(b))) {
                    fs = false;
                    break;
                }
            }
            if(supports[a].size() == 0) fs = false;
            if (fs && !disintegrated.contains(a)) {
                answer++;
                number++;
                if (!disintegrated.contains(a)) {
                    disintegrated.add(a);
                    answer += countSupport(number, disintegrated);
                }
            }
        }
        return answer;
    }
}

