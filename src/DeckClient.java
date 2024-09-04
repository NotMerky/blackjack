import java.util.ArrayList;

public class DeckClient {
    public static void main(String[] args) {
        // linearBinarySearchTest();
        // testClassEx();
        quickSortEx();
    }

    public static void linearBinarySearchTest() {
        Deck linearDeck = new Deck();
        linearDeck.defaultSort();
        Card SixOfHearts = new Card(6,1);
        Card KingOfDiamonds = new Card(13,2);

        linearDeck.shuffle();
        System.out.println("Testing linear search WITH element present...");
        System.out.println("Index Found At: " + linearDeck.linearSearch(KingOfDiamonds));
        System.out.println("Testing linear search WITHOUT element present...");
        System.out.println("Index Found At: " + linearDeck.linearSearch(linearDeck.deal()));
        
        Deck binaryDeck = new Deck();
        binaryDeck.defaultSort();
        System.out.println("\nTesting binary search WITH element present...");
        System.out.println("Index Found At: " + binaryDeck.binarySearch(SixOfHearts));
        System.out.println("Testing binary search WITHOUT element present...");
        System.out.println("Index Found At: " + binaryDeck.binarySearch(binaryDeck.deal()));
    }

    public static void testClassEx() {
        Deck deck = new Deck();
        deck.shuffle();
        // System.out.println(deck);
        deck.quickSort();
        // System.out.println(deck);
    }

    public static void quickSortEx() {
         // MERGE SORT TEST //
        Deck d = new Deck();
         System.out.println("Running merge sort test...");
         d.shuffle();
         d.mergeSort();
         // System.out.println(d + "\n"); // uncomment to print the results of the sort
         System.out.println("Merge sort works correctly: " + isInOrder(d));
         System.out.println("Size of deck after merge sort: " + d.getCardsLeft());
    }

    private static boolean isInOrder(Deck testDeck) {
        final int COMPARISONS_IN_FULL_DECK = 51;
        int sortCount = 0;
        Card previous = testDeck.deal(); // the only public way to access each Card is by dealing
        ArrayList<Card> discards = new ArrayList<Card>();
        while (testDeck.getCardsLeft() > 0) {
            Card next = testDeck.deal();
            if (previous.compareTo(next) <= 0) {
                sortCount++;
            } else {
                System.err.println("Error: " + next + " should come before " + previous);
            }
            discards.add(previous);
            previous = next;
        }
        discards.add(previous); // add last card
        testDeck.add(discards); // and the only public way to add cards back is this
        return sortCount == COMPARISONS_IN_FULL_DECK;
    }
}