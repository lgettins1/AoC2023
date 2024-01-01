import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay12block5 {
    private static int globalAnswer;
    public static void main(String [] args) {
        String thisLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day12input.txt"));
            while ((thisLine = br.readLine()) != null) {
                int[] numList = new int[50];
                String[] line = thisLine.split(" ");
//                String a = line[0] + "?";
 //               String b = line[1] + ",";
 //               for(int c = 0; c < 4; c ++){
//                    line[0] = a  + line[0];
//                    line[1] = b + line[1];
//                }
                String[] n = line[1].split(",");
                int numCount = n.length;
                for (int d = 0; d < numCount; d ++) {
                    numList[d] = Integer.parseInt(n[d]);
                }
                System.out.println(line[0] + " " + line[1]);
                globalAnswer += countArrangements(line[0], numList, numCount);
            }
            System.out.println("The answer is " + globalAnswer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String convertNumberToNewBase (String number,int base, int newBase){
        return Integer.toString(Integer.parseInt(number, base), newBase);
    }

    public static int countArrangements(String line, int[] numList, int numCount){
        int answer = 0;
        int numSum = 0;
        for(int a = 0; a < numCount; a ++){
            numSum += numList[a];
        }

        if(numCount > 1 && line.substring(0,numList[0] + 1).contains("#")) {
            int ld = -1;
            int fh = -1;
            int fd = -1;
            int cp = 0;
            while (fd == -1) {
                if (fh == -1) {
                    switch (line.charAt(cp)) {
                        case ('.') -> ld = cp;
                        case ('#') -> fh = cp;
                    }
                } else {
                    if (line.charAt(cp) == '.') {
                        fd = cp;
                    }
                }
                cp ++;
                if(cp == line.length()) fd = cp;
            }
            int lp = fh;
            if(lp > fd - numList[0]) lp = fd - numList[0];
            int fp = ld + 1;
            if(fp < fh + 1 - numList[0]) fp = fh + 1 - numList[0];
            if( lp > line.length() - numSum - numCount + 1) lp = line.length() - numSum - numCount + 1;
            int[] newNumList = new int[numCount];
            System.arraycopy(numList, 1, newNumList, 0, numCount - 1);
            for (int a = fp; a <= lp; a ++){
                if(line.charAt(a + numList[0]) !='#') {
                    answer += countArrangements(line.substring(a + numList[0] + 1), newNumList, numCount - 1);
                }
            }
        } else {
                int cp = 0;
                int hp = -1;
                int chc = 0;
                while (true) {
                    if (cp == line.length()) break;
                    if (line.charAt(cp) == '#') {
                        if (hp == -1) hp = cp;
                        chc++;
                        if (cp == line.length() - 1) break;
                    } else {
                        if (hp >= 0) break;
                    }
                    cp++;
                }
                int slicePoint = 0;
                int pfn = 0;
                if (chc > 1) {
                    while (numList[pfn] < chc) pfn++;
                    slicePoint = hp + chc - numList[pfn];
                    System.out.println(chc + " " + line + " " + pfn + " " + numCount + " " + numList[pfn] + " $$$ " + slicePoint);
                }
                if (slicePoint > 0 && slicePoint < line.length() - 1 && numCount > 1) {
                    int[] leftNumList = new int[pfn];
                    int[] rightNumList = new int[numCount - pfn];
                    System.arraycopy(numList, 0, leftNumList, 0, pfn);
                    System.arraycopy(numList, pfn, rightNumList, 0, numCount - pfn);
                    System.out.println("& " + line.substring(0, slicePoint) + " " + leftNumList.length + " " + pfn);
                    int leftPoss = countArrangements(line.substring(0, slicePoint), leftNumList, pfn);
                    System.out.println("left possibilities " + leftPoss);
                    System.out.println("^ " + line.substring(slicePoint) + " " + rightNumList.length + " " + (numCount - pfn));
                    int rightPoss = countArrangements(line.substring(slicePoint), rightNumList, numCount - pfn);
                    System.out.println("right possibilities " + rightPoss);
                    answer += (leftPoss * rightPoss);

            } else {
                int qmCount = line.length() - line.replace("?", "").length();
                int possCount = (int) Math.pow(2, qmCount);
                int springCount = line.length() - line.replace("#", "").length();
                for (int a = 0; a < possCount; a++) {
                    StringBuilder b = new StringBuilder(Integer.toString(a));
                    b = new StringBuilder(convertNumberToNewBase(b.toString(), 10, 2));
                    String bb = b.toString();
                    if (springCount + bb.length() - bb.replace("1", "").length() == numSum) {
                        int d = b.length();
                        if (d < qmCount) {
                            for (int c = 0; c < (qmCount - d); c++) {
                                b.insert(0, "0");
                            }
                        }
                        int q = 0;
                        StringBuilder newLine = new StringBuilder();
                        for (int scan = 0; scan < line.length(); scan++) {
                            if (line.charAt(scan) == '?') {
                                if (b.charAt(q) == '0') {
                                    newLine.append(".");
                                } else {
                                    newLine.append("#");
                                }
                                q++;
                            } else {
                                newLine.append(line.charAt(scan));
                            }
                        }
                        String newLine2 = newLine.toString();
                        boolean isItGood = eval(newLine2, numList, numCount);
                        if (isItGood) {
                            answer++;
                        }
                    }
                }
            }
        }
        System.out.println (line + " = " + answer);
        return answer;
    }


    public static boolean eval(String newLine, int[]numList, int numCount){
        boolean isItGood = true;
        newLine = newLine.replaceAll("^\\.+","");
        String[] slice = newLine.split("\\.+");
        if(numCount != slice.length) isItGood = false;
        if(isItGood) {
            for(int a = 0; a < numCount; a ++){
                if (slice[a].length() != numList[a]) {
                    isItGood = false;
                    break;
                }
            }
        }

        return isItGood;
    }
}
