import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private List<Card> listOfCards;
    private final int SHUFFLE_TIMES = 1000;
    
    public Deck() {
        listOfCards = new ArrayList<Card>();
        for (int suit = 0; suit <= 3; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                this.listOfCards.add(new Card(rank,suit));
            }
        }
    }
    
    public int linearSearch(Card target) {
        for (int i = 0; i < listOfCards.size(); i++) {
            if (target.compareTo(listOfCards.get(i)) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    public int binarySearch(Card target) {
        int low = 0;
        int high = listOfCards.size();

        while (low <= high) {
            int mid = (high + low) / 2;
            if (target.compareTo(listOfCards.get(mid)) == 0) {
                return mid;
            } else if (target.compareTo(listOfCards.get(mid)) > 0) { // target card comes AFTER mid
                low = mid + 1;
            } else { // target card comes BEFORE mid
                high = mid - 1;
            }
        }
        return -1;
    }

    public void defaultSort() {
        Collections.sort(listOfCards);
    }

    public void bubbleSort() {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < listOfCards.size() - 1; i++) {
                if (listOfCards.get(i).compareTo(listOfCards.get(i + 1)) > 0) {
                    swap(i, i + 1);
                    sorted = false;
                }
            }
        }
    }

    public void selectionSort() {
        for (int i = 0; i < listOfCards.size(); i++) {
            int lowestCardIndex = i;
            for (int j = i; j < listOfCards.size(); j++) {
                if (listOfCards.get(lowestCardIndex).compareTo(listOfCards.get(j)) > 0) {
                    lowestCardIndex = j;
                }
            }
            swap(i,lowestCardIndex);
        }
    }

    public void insertionSort() {
        for (int i = 1; i < listOfCards.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (listOfCards.get(j - 1).compareTo(listOfCards.get(j)) > 0) {
                    swap(j,j-1);
                }
            }
        }
    }

    public void mergeSort() {
        listOfCards = mergeSort(listOfCards);
    }
    
    public List<Card> mergeSort(List<Card> unsorted) {
        if (unsorted.size() < 2) {
            return unsorted;
        }

        int midpoint = unsorted.size() / 2;
        List<Card> firstHalf = new ArrayList<Card>();
        List<Card> secondHalf = new ArrayList<Card>();
        
        for (int i = 0; i < unsorted.size(); i++) {
            if (i < midpoint) {
                firstHalf.add(unsorted.get(i));
            } else {
                secondHalf.add(unsorted.get(i));
            }
        }

        firstHalf = mergeSort(firstHalf);
        secondHalf = mergeSort(secondHalf);

        return mergeLists(firstHalf, secondHalf);
    }

    public void quickSort() {
        listOfCards = quickSort(listOfCards);
    }
    
    public List<Card> quickSort(List<Card> unsorted) {
        if (unsorted.size() < 2) {
            return unsorted;
        }
        List<Card> sorted = new ArrayList<Card>();
        List<Card> smallList = new ArrayList<Card>();
        List<Card> bigList = new ArrayList<Card>();
        Card pivotCard = unsorted.remove(unsorted.size() / 2);
        
        for (Card card : unsorted) {
            if (card.compareTo(pivotCard) < 0) {
                smallList.add(card);
            } else {
                bigList.add(card);
            }
        }

        sorted.addAll(quickSort(smallList));
        sorted.add(pivotCard);
        sorted.addAll(quickSort(bigList));
     
        return sorted;
    }

    public void bucketSort() {
        List<List<Card>> buckets = new ArrayList<List<Card>>();
        List<Card> sorted = new ArrayList<Card>();
        // make buckets
        for (int i = 0; i < 13; i++) {
            buckets.add(new ArrayList<Card>());
        }

        // put each card into the correct bucket by rank
        for (Card card : listOfCards) {
            buckets.get(card.getRank() - 1).add(card);
        }

        // sort buckets with merge/quick sort
        for (int i = 0; i < buckets.size(); i++) {
            buckets.set(i, mergeSort(buckets.get(i)));
        }
        
        // add all sorted buckets into big list
        for (List<Card> list : buckets) {
            sorted.addAll(list);
        }
        
        listOfCards = sorted;
    }

    private void swap(int index1, int index2) {
        Card card1 = listOfCards.get(index1);
        Card card2 = listOfCards.get(index2);
        listOfCards.set(index1,card2);
        listOfCards.set(index2,card1);
    }

    private List<Card> mergeLists(List<Card> list1, List<Card> list2) {
        List<Card> sorted = new ArrayList<Card>();
        while (list1.size() > 0 && list2.size() > 0) {
            if (list1.get(0).compareTo(list2.get(0)) < 0) {
                sorted.add(list1.remove(0));
            } else {
                sorted.add(list2.remove(0));
            }
        }
        sorted.addAll(list1);
        sorted.addAll(list2);
        return sorted;
    }

    public boolean equals(Deck that) {
        for (int i = 0; i < listOfCards.size(); i++) {
            if (this.listOfCards.get(i).compareTo(that.listOfCards.get(i)) != 0) {
                return false;
            }
        }
        return true;
    }

    public Card deal() {
        return this.listOfCards.remove(0);
    }

    public List<Card> deal(int handSize) {
        List<Card> hand = new ArrayList<Card>();
        for (int k = 0; k < handSize; k++) {
            hand.add(this.listOfCards.remove(0));
        }
        return hand;
    }

    public int getCardsLeft() {
        return this.listOfCards.size();
    }

    public String toString() {
        String result = "";
        for (Card card : this.listOfCards) {
            result += card + "\n";
        }
        return result;
    }

    public void shuffle() {
        for (int k = 0; k < SHUFFLE_TIMES; k++) {
            int index = (int) (Math.random() * this.listOfCards.size());
            Card temp = this.listOfCards.get(index);
            this.listOfCards.remove(index);
            this.listOfCards.add(temp);
        }
    }

    public void add(Card card) {
        this.listOfCards.add(card);
    }

    public void add(List<Card> cards) {
        this.listOfCards.addAll(cards);
    }
}