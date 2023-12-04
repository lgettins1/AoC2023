import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay3 {
    public static void main(String[] args) {
        int total = 0;
        int totalB = 0;
        String thisLine;
        int dimX;
        int dimY = 0;
        int minY, maxY, minX, maxX;
        String[] engine = new String[142];

        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day3input.txt"));
            while ((thisLine = br.readLine()) != null) {
                engine[dimY] = thisLine;
                dimY ++;
            }
            dimX = engine[0].length();
            System.out.println("Dimensions are " + dimX + " x " + dimY);
            for(int y = 0; y < dimY; y ++){
                int x = 0;
                while(x < dimX){
                    if(engine[y].charAt(x) > 47 && engine[y].charAt(x) < 58){
                        minX = x;
                        minY = y;
                        maxY = y;
                        int xx = x;
                        boolean foundSymbol = false;
                        boolean findEnd = false;
                        while(!findEnd){
                            xx ++;
                            if(xx == dimX) {
                                break;
                            }
                            if(engine[y].charAt(xx) < 48 || engine[y].charAt(xx) > 57) findEnd = true;
                        }
                        maxX = xx;
                        if(y > 0) minY = y - 1;
                        if(x > 0) minX = x - 1;
                        if(y < dimY - 1) maxY = y + 1;
                        if(xx < dimX - 1) maxX = xx + 1;
                        for(int ol = minY; ol <= maxY; ol ++){
                            for(int il = minX; il < maxX; il ++){
                                if (engine[ol].charAt(il) != '.' && (engine[ol].charAt(il) < 48 || engine[ol].charAt(il) > 57)) {
                                    foundSymbol = true;
                                    break;
                                }
                            }
                        }
                        int num = Integer.parseInt(engine[y].substring(x,xx));
                        if (foundSymbol) total += num;
                        x = xx;
                    }
                    if(x == dimX) x = dimX - 1;
                    if(engine[y].charAt(x) == '*'){
                        int numCount = 0;
                        int[] numArray = new int[9];
                        minX = x;
                        maxX = x;
                        minY = y;
                        maxY = y;
                        if(y > 0) minY = y - 1;
                        if(x > 0) minX = x - 1;
                        if(y < dimY - 1) maxY = y + 1;
                        if(x < dimX - 1) maxX = x + 1;
                        for(int yl = minY; yl <= maxY; yl ++){
                            int xp = minX;
                            while(xp <= maxX) {
                                if(engine[yl].charAt(xp) > 47 && engine[yl].charAt(xp) < 58){
                                    int startPos = xp;
                                    int endPos = xp;
                                    boolean foundStart = false;
                                    boolean foundEnd = false;
                                    for (int fs = xp; fs >= 0; fs --) {
                                        if (engine[yl].charAt(fs) < 48 || engine[yl].charAt(fs) > 57) {
                                            startPos = fs + 1;
                                            foundStart = true;
                                            break;
                                        }
                                    }
                                    if (!foundStart) startPos = 0;

                                    for (int fe = xp; fe < dimX; fe ++) {
                                        if (engine[yl].charAt(fe) < 48 || engine[yl].charAt(fe) > 57) {
                                            endPos = fe;
                                            foundEnd = true;
                                            break;
                                        }
                                    }
                                    if (!foundEnd) endPos = dimX;
                                    xp = endPos;
                                    numArray[numCount] = Integer.parseInt(engine[yl].substring(startPos,endPos));
                                    numCount ++;
                                }
                                xp ++;
                            }
                        }
                        if(numCount == 2){
                            totalB += (numArray[0] * numArray[1]);
                        }
                    }
                    x ++;
                }
            }
            System.out.println("The answer to part 1 is " + total);
            System.out.println("The answer to part 2 is " + totalB);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}