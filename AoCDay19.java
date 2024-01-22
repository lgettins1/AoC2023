import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AoCDay19 {
    public static HashMap<String, String[]> workflows = new HashMap<>();
    public static int[][] ratings = new int[4][1000];
    public static int ratingsCount = 0;

    public static void main(String[] args) {
        String thisLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day19input.txt"));
            while (!Objects.equals(thisLine = br.readLine(), "")) {
                String[] p1 = thisLine.split("\\{");
                String[] p2 = p1[1].substring(0, p1[1].length() - 1).split(",");
                workflows.put(p1[0],p2);
            }
            while((thisLine = br.readLine()) != null){
                String[] p1 = thisLine.split("[,=}]");
                for( int a = 0; a < 4; a ++){
                    ratings[a][ratingsCount] = Integer.parseInt(p1[(a * 2) + 1]);
                }
                ratingsCount ++;
            }
            int answer = 0;
            for(int a = 0; a < ratingsCount; a ++){
                int[] bb = {ratings[0][a], ratings[1][a] ,ratings[2][a], ratings[3][a]};
                boolean accepted = evaluate("in", bb);
                if(accepted){
                    for(int b = 0; b < 4; b ++){
                        answer += (ratings[b][a]);
                    }
                }
            }
            System.out.println("The answer is " + answer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean evaluate(String wf, int[]xmas){
        boolean answer = false;
        String[]wfList = workflows.get(wf);
        int rule = 0;
        boolean hit = false;
        while (!hit) {
           if (wfList[rule].charAt(0) < 90) {
                hit = true;
                if (wfList[rule].charAt(0) == 65) answer = true;
            } else {
                int colon = wfList[rule].length() - wfList[rule].replace(":", "").length();
                if(colon == 1) {
                    String[] a1 = wfList[rule].split(":");
                    int cval = Integer.parseInt(a1[0].substring(2));
                    int b = 0;
                    int op = a1[0].charAt(1) - 61;
                    switch (a1[0].charAt(0)) {
                        case 'x' -> b = op * (xmas[0] - cval);
                        case 'm' -> b = op * (xmas[1] - cval);
                        case 'a' -> b = op * (xmas[2] - cval);
                        case 's' -> b = op * (xmas[3] - cval);
                    }
                    if (b > 0) {
                        hit = true;
                        if(a1[1].equals("A")){
                            answer = true;
                        } else {
                            if (!(a1[1].equals("R"))) answer = evaluate(a1[1], xmas);
                        }
                    } else {
                            rule++;
                        }
                } else {
                    hit = true;
                    answer = evaluate(wfList[rule], xmas);
                }
            }
        }
        return answer;
    }
}
