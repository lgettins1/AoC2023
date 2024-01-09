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
                String a = line[0] + "?";
                String b = line[1] + ",";
                for(int c = 0; c < 4; c ++){
                    line[0] = a  + line[0];
                    line[1] = b + line[1];
                }
                String[] n = line[1].split(",");
                int numCount = n.length;
                for (int d = 0; d < numCount; d ++) {
                    numList[d] = Integer.parseInt(n[d]);
                }
                System.out.println(line[0] + " " + line[1] + " -> ");
                globalAnswer += countArrangements(line[0], numList, numCount);
                System.out.println(globalAnswer);
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
        int springCount = line.length() - line.replace("#", "").length();
        int answer = 0;
        int numSum = 0;
        for(int a = 0; a < numCount; a ++){
            numSum += numList[a];
        }
        if(springCount <= numSum) {
            if(numCount > 0) System.out.println("-> " + line + " " + numList[0] + " " + numCount);

            if (numCount > 1 && line.substring(0, numList[0] + 1).contains("#")) {
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
                    cp++;
                    if (cp == line.length()) fd = cp;
                }
                int lp = fh;
                if (lp > fd - numList[0]) lp = fd - numList[0];
                int fp = ld + 1;
                if (fp < fh + 1 - numList[0]) fp = fh + 1 - numList[0];
                if (lp > line.length() - numSum - numCount + 1) lp = line.length() - numSum - numCount + 1;
                int[] newNumList = new int[numCount];
                System.arraycopy(numList, 1, newNumList, 0, numCount - 1);
                for (int a = fp; a <= lp; a++) {
                    if (line.charAt(a + numList[0]) != '#') {
                        answer += countArrangements(line.substring(a + numList[0] + 1), newNumList, numCount - 1);
                    }
                }
            } else {
                int cp = 0;
                int firstHash = -1;
                int lastPreDot = -1;
                int firstPostDot = -1;
                int consHashCt = 0;
                int maxConsQMCt = 0;
                int consQMCt = 0;
                boolean cqRow = false;
                while (cp < line.length() && line.charAt(cp) != '#') {
                    if (line.charAt(cp) == '.') {
                        cqRow = false;
                        lastPreDot = cp;
                    } else {
                        if (cqRow) {
                            consQMCt++;
                            if (consQMCt > maxConsQMCt) maxConsQMCt = consQMCt;
                        } else {
                            cqRow = true;
                            consQMCt = 1;
                        }
                    }
                    cp++;
                }
                firstHash = cp;
                while (cp < line.length() && line.charAt(cp) == '#') {
                    consHashCt++;
                    cp++;
                }
                while (cp < line.length() && line.charAt(cp) != '.') cp++;
                firstPostDot = cp;
                int minSlicePoint = 0;
                int maxSlicePoint = line.length();
                int pfn = 0;
                if (consHashCt > 1) {
                    while (numList[pfn] < consHashCt) pfn++;
                    maxSlicePoint = firstHash;
                    if (maxSlicePoint + numList[pfn] > firstPostDot) maxSlicePoint = firstPostDot - numList[pfn];
                    minSlicePoint = firstHash + consHashCt - numList[pfn];
                    if (lastPreDot > minSlicePoint - 1) minSlicePoint = lastPreDot + 1;
                    System.out.println(consHashCt + " " + maxConsQMCt + " - " + line + " " + pfn + " " +
                            numCount + " " + numList[pfn] + " $$$ " + minSlicePoint + "->" + maxSlicePoint);
                }
                boolean splitting = minSlicePoint >= 1 && maxSlicePoint <= line.length() - 2;
                if (splitting && numCount <= 1) splitting = false;
                if(numCount > pfn + 1 && splitting && maxConsQMCt >= numList[pfn]){
                    int npfn = 0;
                    boolean another = false;
                    for(int q = pfn + 1; q < numCount; q ++){
                        if(!another && numList[q] >= consHashCt) npfn = q;
                    }
                    if(npfn > 0){
                        int longerTotal = 0;
                        for(int a = 0; a <= npfn; a ++){
                            longerTotal += numList[a];
                        }
                        if(longerTotal < firstHash) splitting = false;
                    }

                }
                if (splitting) {
                    for (int slicePoint = minSlicePoint; slicePoint <= maxSlicePoint; slicePoint++) {
                        int leftPoss = 0;
                        int rightPoss = 0;
                        int[] leftNumList = new int[pfn];
                        int[] rightNumList = new int[numCount - pfn];
                        System.arraycopy(numList, 0, leftNumList, 0, pfn);
                        System.arraycopy(numList, pfn, rightNumList, 0, numCount - pfn);
                        System.out.println("& " + line.substring(0, slicePoint) + " " + leftNumList.length + " " + pfn);
                        int leftNumSum = 0;
                        for (int i : leftNumList) leftNumSum += i;
                        if(leftNumSum < line.substring(0,slicePoint).length()) {
                            String newLine = line.substring(0, slicePoint - 1) + ".";
                            leftPoss = countArrangements(newLine, leftNumList, pfn);
                        }
                        System.out.println("left possibilities " + leftPoss);
                        System.out.println("^ " + line.substring(slicePoint) + " " + rightNumList.length + " " + (numCount - pfn));
                        int rightNumSum = 0;
                        for (int i : rightNumList) rightNumSum += i;
                        if(rightNumSum < line.substring(slicePoint).length()) {
                            String newLine = "#" + line.substring(slicePoint + 1);
                            rightPoss = countArrangements(newLine, rightNumList, numCount - pfn);
                        }
                        System.out.println("right possibilities " + rightPoss);
                        answer += (leftPoss * rightPoss);
                    }
                } else {
                    int qmCount = line.length() - line.replace("?", "").length();
                    int possCount = (int) Math.pow(2, qmCount);

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
