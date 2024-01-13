import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AoCDay15b {
    public static void main(String[] args) throws IOException {
        String thisLine;
        int answer = 0;
        LinkedHashMap[]listOfHashes = new LinkedHashMap[256];
        for(int a = 0; a < listOfHashes.length; a ++){
            listOfHashes[a] = new LinkedHashMap();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day15input.txt"));
            thisLine = br.readLine();
            String[] steps = thisLine.split(",");
            String lens = "";
            int focal = 0;
            for (String step : steps) {
                boolean add = true;
                if (step.charAt(step.length() - 1) == '-') {
                    lens = step.substring(0, step.length() - 1);
                    add = false;
                } else {
                    String[] adding = step.split("=");
                    lens = adding[0];
                    focal = Integer.parseInt(adding[1]);
                }
                int boxNum = hAlgo(lens);
                if(add){
                    listOfHashes[boxNum].put(lens, focal);
                } else {
                    listOfHashes[boxNum].remove(lens);
                }
            }
            int thisBoxVal;
            for(int b = 0; b < 256; b ++){
                thisBoxVal = 0;
                Set keySet = listOfHashes[b].keySet();
                List<String> listKeys  = new ArrayList<>(keySet);
                for(int c = 0; c < listOfHashes[b].size(); c ++){
                    if(listOfHashes[b].size() > 0) {
                        String key = listKeys.get(c);
                        int d = (int) listOfHashes[b].get(key);
                        thisBoxVal += (b + 1) * (c + 1) * d;
                    }
                }
                answer += thisBoxVal;
            }
            System.out.println("The answer is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int hAlgo(String lens){
        int response = 0;
        for(int a= 0; a < lens.length(); a ++){
            response += lens.charAt(a);
            response = response * 17;
            response = response % 256;
        }
        return response;
    }
}
