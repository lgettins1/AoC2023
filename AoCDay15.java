import java.io.*;

public class AoCDay15 {
    public static void main(String[] args) throws IOException {
        int answer = 0;
        int r;
        int algo = 0;
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream("c:/users/lance/documents/AoC23Day15input.txt");
            while ((r = fileInput.read()) != -1) {
                if (r == 44) {
                    answer += algo;
                    algo = 0;
                } else {
                    algo += r;
                    algo = algo * 17;
                    algo = algo % 256;
                }
            }
            answer += algo;
            System.out.println("The answer is " + answer);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            assert fileInput != null;
            fileInput.close();
        }
    }
}