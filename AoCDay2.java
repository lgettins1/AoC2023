import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay2 {public static void main(String [] args) {
    int total = 0;
    int totalB = 0;
    String thisLine;
    int maxR = 12;
    int maxG = 13;
    int maxB = 14;

    try {
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day2input.txt"));
        while ((thisLine = br.readLine()) != null) {
            String[] t1 = thisLine.split(":");
            String[] t2 = t1[0].split(" ");
            int g = Integer.parseInt(t2[1]);
            String[] t3 = t1[1].split(";");
            boolean gameStatus = true;
            int minR = 0;
            int minG = 0;
            int minB = 0;
            for (String value : t3) {
                String[] t4 = value.split(",");
                for (String s : t4) {
                    String[] t5 = s.split(" ");
                    switch (t5[2]) {
                        case "red" -> {
                            if (Integer.parseInt(t5[1]) > maxR) gameStatus = false;
                            if (Integer.parseInt(t5[1]) > minR) minR = Integer.parseInt(t5[1]);
                        }
                        case "green" -> {
                            if (Integer.parseInt(t5[1]) > maxG) gameStatus = false;
                            if (Integer.parseInt(t5[1]) > minG) minG = Integer.parseInt(t5[1]);
                        }
                        case "blue" -> {
                            if (Integer.parseInt(t5[1]) > maxB) gameStatus = false;
                            if (Integer.parseInt(t5[1]) > minB) minB = Integer.parseInt(t5[1]);
                        }
                    }
                }
            }
            if (gameStatus) total += g;
            int gameTotal = minR * minG * minB;
            totalB += gameTotal;
        }
        System.out.println("Part 1 Total is " + total);
        System.out.println("Part 2 Total is " + totalB);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}
}
