import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class aocgpt {    public static void main(String[] args) {
    long answer = 0;

    try (BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day12inputb.txt"))) {
        String thisLine;

        while ((thisLine = br.readLine()) != null) {
            String[] line = thisLine.split(" ");
            String[] n = line[1].split(",");
            int numCount = n.length;

            int[] numList = new int[numCount];
            int numSum = 0;

            for (int a = 0; a < numCount; a++) {
                numList[a] = Integer.parseInt(n[a]);
                numSum += numList[a];
            }

            int maxDot = line[0].length() - numSum + 1;
            int possCount = (int) Math.pow(maxDot, (numCount + 1));

            for (int a = 0; a < possCount; a++) {
                String b = convertNumberToNewBase(Integer.toString(a), 10, maxDot);

                while (b.length() < numCount + 1) {
                    b = "0" + b;
                }

                boolean candidate = true;
                int len = 0;
                int dataLen = line[0].length();
                int[] dots = new int[numCount + 1];

                for (int e = 0; e < numCount + 1; e++) {
                    dots[e] = Integer.parseInt(convertNumberToNewBase(b.substring(e, e + 1), maxDot, 10));
                    len += dots[e];

                    if (e > 0 && e < numCount && b.charAt(e) == '0') {
                        candidate = false;
                    }
                }

                if (numSum + len != dataLen) {
                    candidate = false;
                }

                if (candidate) {
                    if ((line[0].charAt(0) == '#' && b.charAt(0) != '0') ||
                            (line[0].charAt(0) == '.' && b.charAt(0) == '0') ||
                            (line[0].charAt(dataLen - 1) == '#' && b.charAt(numCount) != '0') ||
                            (line[0].charAt(dataLen - 1) == '.' && b.charAt(numCount) == '0')) {
                        candidate = false;
                    }
                }

                if (candidate) {
                    StringBuilder ourString = new StringBuilder(".".repeat(Math.max(0, dots[0])));

                    for (int f = 0; f < numCount; f++) {
                        ourString.append("#".repeat(Math.max(0, numList[f])));
                        ourString.append(".".repeat(Math.max(0, dots[f + 1])));
                    }

                    if (!ourString.toString().equals(line[0].replaceAll("\\?", "."))) {
                        candidate = false;
                    }
                }

                if (candidate) {
                    answer++;
                }
            }
        }

        System.out.println("The answer is " + answer);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

    public static String convertNumberToNewBase(String number, int base, int newBase) {
        return Integer.toString(Integer.parseInt(number, base), newBase);
    }
}