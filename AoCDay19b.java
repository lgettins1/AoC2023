import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AoCDay19b {
    public static HashMap<String, String[]> workflows = new HashMap<>();
    public static long answer = 0;

    public static void main(String[] args) {
        String thisLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day19input.txt"));
            while (!Objects.equals(thisLine = br.readLine(), "")) {
                String[] p1 = thisLine.split("\\{");
                String[] p2 = p1[1].substring(0, p1[1].length() - 1).split(",");
                workflows.put(p1[0], p2);
            }

            int[] ranges = {1, 4000, 1, 4000, 1, 4000, 1, 4000};
            evaluate("in", ranges);

            System.out.println("The answer is " + answer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void evaluate(String wf, int[] xmas) {
        if ((!wf.equals("R"))) {
            if (wf.equals("A")) {
                updateScore(xmas);
            } else {
                int ruleNum = 0;
                boolean hit = false;
                while (!hit) {
                    String rule = workflows.get(wf)[ruleNum];
                    if (rule.equals("R")) {
                        hit = true;
                    } else {
                        if (rule.equals("A")) {
                            hit = true;
                            updateScore(xmas);
                        } else {
                            if (rule.charAt(1) > 65) {
                                hit = true;
                                evaluate(rule, xmas);
                            } else {
                                String[] rp = rule.split(":");
                                int cVal = Integer.parseInt(rp[0].substring(2));
                                int op = (rule.charAt(1) - 60) / 2;
                                int xVal = 0;
                                switch(rule.charAt(0)){
                                    case 'm' -> xVal = 1;
                                    case 'a' -> xVal = 2;
                                    case 's' -> xVal = 3;
                                }
                                if(cVal > (xmas[xVal * 2] - op) && cVal < (xmas[xVal * 2 + 1] - op)){
                                    hit = true;
                                    int r1u = cVal - 1 + op;
                                    int r2l = cVal + op;
                                    int[] xmas1 = xmas.clone();
                                    int[] xmas2 = xmas.clone();
                                    xmas1[xVal * 2 + 1] = r1u;
                                    xmas2[xVal * 2] = r2l;
                                    if(op == 0){
                                        evaluate(rp[1], xmas1);
                                        evaluate(wf, xmas2);
                                    } else {
                                        evaluate(wf, xmas1);
                                        evaluate(rp[1], xmas2);
                                    }
                                } else {
                                    ruleNum ++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void updateScore(int[] xmas) {
        long a = (long) (xmas[1] - xmas[0] + 1) * (xmas[3] - xmas[2] + 1) * (xmas[5] - xmas[4] + 1) * (xmas[7] - xmas[6] + 1);
        answer += a;
    }
}