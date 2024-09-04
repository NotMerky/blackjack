import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Card implements Comparable<Card> {
    private static final String[] RANKS = {null, "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private static final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private final int suit;
    private final int rank;
    private BufferedImage face;
    private static BufferedImage back;

    static {
        String backFilename = "images\\cards\\back01.png";
        try {
            back = ImageIO.read(new File(backFilename));
        } catch (IOException e) {
            back = null;
            System.err.println(e + " file: " + backFilename);
        }
    }

    public Card(int rank, int suit) {
        if (rank < 1 || rank > 13) {
            throw new IllegalArgumentException("Invalid rank: " + rank);
        }
        if (suit < 0 || suit > 3) {
            throw new IllegalArgumentException("Invalid suit: " + suit);
        }
        this.rank = rank;
        this.suit = suit;
        
        int cardNumber = rank * 4 + suit - 3;
        String filename = String.format("images\\cards\\card%02d.png",cardNumber);
        try {
            this.face = ImageIO.read(new File(filename));
        } catch (IOException e) {
            face = null;
            System.err.println(e + "file: " + filename);
        }
    }

    public String toString() {
        return String.format("%s of %s",RANKS[this.rank],SUITS[this.suit]);
    }

    public boolean equals(Card that) {
        return this.rank == that.rank && this.suit == that.suit;
    }

    public int compareTo(Card that) {
        int thisNum = this.rank * 4 + this.suit;
        int thatNum = that.rank * 4 + that.suit;
        return thisNum - thatNum;
    }

    public static int compare(Card card1, Card card2) {
        int thisNum = card1.rank * 4 + card1.suit;
        int thatNum = card2.rank * 4 + card2.suit;
        return thisNum - thatNum;
    }

    public int getRank() {
        return this.rank;
    }

    public int getSuit() {
        return this.suit;
    }

    public BufferedImage getFace() {
        return this.face;
    }

    public static BufferedImage getBack() {
        return back;
    }
    
}