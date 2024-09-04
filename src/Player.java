import java.util.List;
import java.util.ArrayList;

public class Player {
    private List<Card> hand;
    private String playerName;

    public Player(){
        hand = new ArrayList<Card>();
        this.playerName = "Player";
    }

    public Player(String playerName){
        hand = new ArrayList<Card>();
        this.playerName = playerName;
    }

    public void take(Card deal) {
        this.hand.add(deal);
    }

    public void take(List<Card> deal) {
        for (Card card : deal) {
            this.hand.add(card);
        }
    }

    public List<Card> fold() {
        List<Card> leftoverCards = hand;
        hand = new ArrayList<Card>();
        return leftoverCards;
    }

    public int getScore() {
        // extended by other classes
        return 0;
    }

    public String toString() {
        return String.format("=== Player: %s ===========================\nCurrent Hand: %s\nScore: %d",
                this.playerName,this.hand.toString(),this.getScore());
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return this.playerName;
    }

}
