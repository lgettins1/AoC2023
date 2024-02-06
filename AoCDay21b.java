import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay21b {    public static int width;
    public static long maxSteps = 26501365;
    public static char[][] plotMap;


    public static void main(String[] args) {
    int y = 0;
    int evenTile = 0;
    int oddTile = 0;
    int hashCount = 0;
    int evenCorners = 0;
    int oddCorners = 0;
    String thisLine;
        try {
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day21input.txt"));
        thisLine = br.readLine();
            width = thisLine.length();
            plotMap = new char[width][width];

        while (thisLine != null) {
            int x = 0;

            while(x < thisLine.length()){
                plotMap[x][y] = thisLine.charAt(x);
                if((x + y) % 2 == 0 && thisLine.charAt(x) != '#'){
                    evenTile ++;
                    if(Math.abs(x - 65) + Math.abs(y - 65) > 65) evenCorners ++;
                }
                if((x + y + 1) % 2 == 0 && thisLine.charAt(x) != '#') {
                    oddTile ++;
                    if(Math.abs(x - 65) + Math.abs(y - 65) > 65) oddCorners ++;
                }
                if(thisLine.charAt(x) =='#') hashCount ++;
                x ++;
            }
            y ++;
            thisLine = br.readLine();
        }

        long tileWidth =  (2 * maxSteps + 1) / width;
        int leftOver = (int) (2 * maxSteps + 1) % width;
        System.out.println("Even " + evenTile + " Odd " + oddTile);
        System.out.println(hashCount + " hashes");
        System.out.println("131 x 131 = " + Math.pow(131,2));
        System.out.println(leftOver);
        System.out.println("Tiles across " + (tileWidth));
        System.out.println("Corners - even " + evenCorners + " odd " + oddCorners);
        long evenTileCount ;
        long oddTileCount;
        long oddOrEven = ((tileWidth - 1) / 2);
        if((oddOrEven + maxSteps) % 2 == 0){
            evenTileCount = (long) Math.pow(oddOrEven + 1, 2);
            oddTileCount = (long) Math.pow(oddOrEven, 2);
        } else {
            evenTileCount = (long) Math.pow(oddOrEven, 2);
            oddTileCount = (long) Math.pow(oddOrEven + 1, 2);
        }
        System.out.println("left over " + leftOver  + " per side");
        System.out.println("even tile count " + evenTileCount + " odd tile count " + oddTileCount);
        System.out.println("middle bit " + ((evenTileCount * evenTile) + (oddTileCount * oddTile)));


        long finalAnswer = ((202301L * 202301) * oddTile) + ((202300L * 202300)
                * evenTile) - (202301L * oddCorners) + (202300L * evenCorners);

         System.out.println("The final answer is " + finalAnswer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
