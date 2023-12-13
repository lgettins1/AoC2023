import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoCDay4 {
    public static void main(String [] args) {
        int total = 0;
        int totalB = 0;
        int lineCount = 0;
        String thisLine;
        int[] cards = new int [205];
        int[] winners = new int [10];
        int[] myCard = new int [26];
        int cardScore;
        int cardMatches;

        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day4input.txt"));
            while ((thisLine = br.readLine()) != null) {
                lineCount ++;
                cardScore = 0;
                cardMatches = 0;
                cards[lineCount] ++;
                String[] t1 = thisLine.split(":");
                String[] t2 = t1[1].split("\\|");
                t2[0] = t2[0].substring(0,t2[0].length() - 1);
                int winCount = t2[0].length() / 3;
                for(int b = 0; b < winCount; b ++){
                    winners[b] = Integer.parseInt((t2[0].substring(b * 3, (b * 3) + 3).trim()));
                }
                int cardCount = t2[1].length() / 3;
                for(int b = 0; b < cardCount; b ++){
                    myCard[b] = Integer.parseInt((t2[1].substring(b * 3, (b * 3) + 3).trim()));
                    for(int c = 0; c < winCount; c ++){
                        if(winners[c] == myCard[b]) {
                            cardMatches ++;
                            cards[lineCount + cardMatches] += cards[lineCount];
                            if (cardScore == 0) {
                                cardScore = 1;
                            } else {
                                cardScore = cardScore * 2;
                            }
                        }
                    }
                }

            total += cardScore;
            }
            System.out.println("The answer to part 1 is " + total);
            for(int a = 1; a <= lineCount; a ++){
                totalB += cards[a];
            }
            System.out.println("The answer to part 2 is " + totalB);
         } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
