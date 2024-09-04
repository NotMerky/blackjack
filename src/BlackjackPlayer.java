public class BlackjackPlayer extends Player {

    private int wins;
    private int loses;

    public BlackjackPlayer() {
        this.wins = 0;
        this.loses = 0;
    }
    
    public BlackjackPlayer(String name) {
        super(name);
        this.wins = 0;
        this.loses = 0;
    }

    @Override
    public int getScore() {
        int score = 0;
        int aces = 0;
        for (Card card : this.getHand()) {
            if (card.getRank() == 1) {
                score += 11;
                aces++;
            } else if (card.getRank() < 11) {
                score += card.getRank();
            } else {
                score += 10;
            }
        }

        for (int k = 0; k < aces; k++) {
            if (score > 21) {
                score -= 10;
            }
        }
        return score;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLoses() {
        return this.loses;
    }

    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.loses++;
    }
}