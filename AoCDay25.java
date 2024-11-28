import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// comments
public class AoCDay25 {
    public static void main(String[] args) {
        String thisLine;
        int answer = 0;
        ArrayList<String> nodes = new ArrayList<>();
        ArrayList<String[]> pairs = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day25input.txt"));
            while ((thisLine = br.readLine()) != null) {
                String[] a = thisLine.split("[ :]+");
                for (int b = 0; b < a.length; b++) {
                    if (!nodes.contains(a[b])) {
                        nodes.add(a[b]);
                    }
                    if (b > 0) {
                        String[] c = new String[2];
                        c[0] = a[0];
                        c[1] = a[b];
                        pairs.add(c);
                    }
                }
            }
            for (int a = 0; a < nodes.size(); a++) {
                System.out.println((a + 1) + " " + nodes.get(a));
            }
            for (int a = 0; a < pairs.size(); a++) {
                System.out.println((a + 1) + " " + pairs.get(a)[0] + "," + pairs.get(a)[1]);
            }
            System.out.println("The answer is " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

