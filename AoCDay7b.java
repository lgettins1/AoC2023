import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class AoCDay7b {
    public static void main(String [] args) {
        int total = 0;
        int handCount = 0;
        String thisLine;
        String[] hands = new String[1001];
        String[]bids = new String[1001];

        try {
            BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC23Day7input.txt"));
            while ((thisLine = br.readLine()) != null) {
                String[] pl = thisLine.split(" ");
                hands[handCount] = pl[0];
                bids[handCount] = pl[1];
                handCount ++;
            }
            StringBuilder thisCard;
            ArrayList<String[]> sortableMap = new ArrayList<>();
            for(int a= 0; a < handCount; a++){
                thisCard = new StringBuilder(Character.toString(eval(hands[a]) + 65));
                for(int b = 0; b < 5; b ++){
                    if(hands[a].charAt(b) < 58){
                        thisCard.append(Character.toString(hands[a].charAt(b) + 15));
                    } else {
                        switch (hands[a].charAt(b)) {
                            case 84 -> thisCard.append(Character.toString(76));
                            case 74 -> thisCard.append(Character.toString(64));
                            case 81 -> thisCard.append(Character.toString(78));
                            case 75 -> thisCard.append(Character.toString(79));
                            case 65 -> thisCard.append(Character.toString(80));
                        }
                    }
                }
                String[] thisHand = new String[2];
                thisHand[0] = String.valueOf(thisCard);
                thisHand[1] = bids[a];
                sortableMap.add(thisHand);
            }
            sortableMap.sort(Comparator.comparing(strings -> strings[0]));

            for(int a= 0; a < handCount; a ++){
                total += (a + 1) * Integer.parseInt((sortableMap.get(a)[1]));
            }
            System.out.println("The answer to part 2 is " + total);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int eval(String hand){
        int handType = 1;
        int[] cards = new int[50];
        for(int a = 0; a < 5; a ++){
            cards[hand.charAt(a) - 50] ++;
        }
        int jokers = cards[24];
        cards[24] = 0;
        int maxC = 0;
        int maxP = 0;
        for (int j = 0; j < 35; j ++) {
            if(cards[j] > maxC){
                maxC = cards[j];
                maxP = j;
            }
        }
        cards[maxP] += jokers;

        if(Arrays.stream(cards).anyMatch(i -> i == 2)){
            handType = 2;
            int pairCount = 0;
            for (int card : cards) {
                if (card == 2) pairCount ++;
            }
            if(pairCount == 2) handType = 3;
        }
        if(Arrays.stream(cards).anyMatch(i -> i == 3)) {
            handType = 4;
            if (Arrays.stream(cards).anyMatch(i -> i == 2)) handType = 5;
        }
        if(Arrays.stream(cards).anyMatch(i -> i == 4)) handType = 6;
        if(Arrays.stream(cards).anyMatch(i -> i == 5)) handType = 7;
        return handType;
    }
}
