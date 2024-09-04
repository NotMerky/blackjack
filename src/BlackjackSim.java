public class BlackjackSim {
    static BlackjackPlayer dealer;
    static BlackjackPlayer p1;
    static Deck deck;
    static Deck discard;
    static String state;
    static int totalMatches;
    static boolean playerHasBlackjack;
    static boolean dealerHasBlackjack;
    public static void main(String[] args) {
        deck = new Deck();
        discard = new Deck();
        discard.deal(52);
        state = "READY";
        totalMatches = 0;
        playerHasBlackjack = false;
        dealerHasBlackjack = false;
        p1 = new BlackjackPlayer("Player");
        dealer = new BlackjackPlayer("Dealer");
        deck = new Deck();
        deck.shuffle();

        while (totalMatches < 100001) {
            // enable whichever one to simulate
            // simulateStandOn17();
            // simulateOptimalStrat();
            simulateBlackjackRule();           
        }
    }

    public static void simulateStandOn17() {
        if (state.equals("READY")) {
            state = "DEALING";
        } else if (state.equals("DEALING")) {
            p1.take(deck.deal(2));
            dealer.take(deck.deal(2));
            state = "PLAYER_1_TURN";
        } else if (state.equals("PLAYER_1_TURN")) {
            if (p1.getScore() > 21) {
                state = "RESULT";
            } else if (p1.getScore() < 17) {
                p1.take(deck.deal());
            } else {
                state = "DEALER_TURN";
            }
        } else if (state.equals("DEALER_TURN")) {
            if (dealer.getScore() < 17) {
                dealer.take(deck.deal());
            } else {
                state = "RESULT";
            }
        } else if (state.equals("RESULT")) {
            if (p1.getScore() > 21) {
                p1.addLoss();
            } else if (dealer.getScore() > 21) {
                p1.addWin();
            } else if (p1.getScore() < dealer.getScore()) {
                p1.addLoss();
            } else if (p1.getScore() > dealer.getScore()) {
                p1.addWin();
            } else {
                // tied
            }
            totalMatches++;

            discard.add(p1.fold());
            discard.add(dealer.fold());
            if (deck.getCardsLeft() <= 15) {
                deck.add(discard.deal(discard.getCardsLeft()));
                deck.shuffle();
            }
            if (totalMatches % 1000 == 0) {
                printEdge(false);
            }
            state = "DEALING";
        } else {
            throw new RuntimeException("Unexpected state: " + state);
        }
    }

    public static void simulateOptimalStrat() {
        if (state.equals("READY")) {
            state = "DEALING";
        } else if (state.equals("DEALING")) {
            p1.take(deck.deal(2));
            dealer.take(deck.deal());
            state = "PLAYER_1_TURN";
        } else if (state.equals("PLAYER_1_TURN")) {
            if (p1.getScore() > 21) { // bust
                state = "RESULT";
            } else if (p1.getScore() <= 21 && p1.getScore() >= 17) { // 17-21, stand 
                state = "DEALER_TURN"; // stand
            } else if (p1.getScore() <= 16 && p1.getScore() >= 13) { // 13-16, stand / hit
                if (dealer.getHand().get(0).getRank() >= 7) {
                    p1.take(deck.deal()); // hit
                } else {
                    state = "DEALER_TURN"; // stand
                }
            } else if (p1.getScore() == 12) { // 12, stand / hit
                if (dealer.getHand().get(0).getRank() <= 3 && dealer.getHand().get(0).getRank() >= 7) {
                    p1.take(deck.deal()); // hit
                } else {
                    state = "DEALER_TURN"; // stand
                }
            } else if (p1.getScore() <= 11) {
                p1.take(deck.deal());
            } else {
                state = "DEALER_TURN";
            }
        } else if (state.equals("DEALER_TURN")) {
            if (dealer.getScore() < 17) {
                dealer.take(deck.deal());
            } else {
                state = "RESULT";
            }
        } else if (state.equals("RESULT")) {
            if (p1.getScore() > 21) {
                p1.addLoss();
            } else if (dealer.getScore() > 21) {
                p1.addWin();
            } else if (p1.getScore() < dealer.getScore()) {
                p1.addLoss();
            } else if (p1.getScore() > dealer.getScore()) {
                p1.addWin();
            } else {
                // tied
            }
            totalMatches++;

            discard.add(p1.fold());
            discard.add(dealer.fold());
            if (deck.getCardsLeft() <= 15) {
                deck.add(discard.deal(discard.getCardsLeft()));
                deck.shuffle();
            }
            if (totalMatches % 1000 == 0) {
                printEdge(false);
            }
            state = "DEALING";
        } else {
            throw new RuntimeException("Unexpected state: " + state);
        }
    }

    public static void simulateBlackjackRule() {
        if (state.equals("READY")) {
            state = "DEALING";
        } else if (state.equals("DEALING")) {
            p1.take(deck.deal(2));
            dealer.take(deck.deal());
            state = "PLAYER_1_TURN";
        } else if (state.equals("PLAYER_1_TURN")) {
            if (p1.getHand().size() == 2 && p1.getScore() == 21) {
                playerHasBlackjack = true;
            }
            if (p1.getScore() > 21) { // bust
                state = "RESULT";
            } else if (p1.getScore() <= 21 && p1.getScore() >= 17) { // 17-21, stand 
                state = "DEALER_TURN"; // stand
            } else if (p1.getScore() <= 16 && p1.getScore() >= 13) { // 13-16, stand / hit
                if (dealer.getHand().get(0).getRank() >= 7) {
                    p1.take(deck.deal()); // hit
                } else {
                    state = "DEALER_TURN"; // stand
                }
            } else if (p1.getScore() == 12) { // 12, stand / hit
                if (dealer.getHand().get(0).getRank() <= 3 && dealer.getHand().get(0).getRank() >= 7) {
                    p1.take(deck.deal()); // hit
                } else {
                    state = "DEALER_TURN"; // stand
                }
            } else if (p1.getScore() <= 11) {
                p1.take(deck.deal());
            } else {
                state = "DEALER_TURN";
            }
        } else if (state.equals("DEALER_TURN")) {
            if (dealer.getHand().size() == 2 && dealer.getScore() == 21) {
                dealerHasBlackjack = true;
            }
            if (dealer.getScore() < 17) {
                dealer.take(deck.deal());
            } else {
                state = "RESULT";
            }
        } else if (state.equals("RESULT")) {
            if (playerHasBlackjack && !dealerHasBlackjack) {
                p1.addWin();
            } else if (dealerHasBlackjack && !playerHasBlackjack) {
                p1.addLoss();
            } else if (p1.getScore() > 21) {
                p1.addLoss();
            } else if (dealer.getScore() > 21) {
                p1.addWin();
            } else if (p1.getScore() < dealer.getScore()) {
                p1.addLoss();
            } else if (p1.getScore() > dealer.getScore()) {
                p1.addWin();
            } else {
                // tied
            }
            totalMatches++;
            playerHasBlackjack = false;
            dealerHasBlackjack = false;

            discard.add(p1.fold());
            discard.add(dealer.fold());
            if (deck.getCardsLeft() <= 15) {
                deck.add(discard.deal(discard.getCardsLeft()));
                deck.shuffle();
            }
            if (totalMatches % 1000 == 0) {
                printEdge(false);
            }
            state = "DEALING";
        } else {
            throw new RuntimeException("Unexpected state: " + state);
        }
    }

    public static void printEdge(boolean onlyNums) {
        double edge =  ((double) (p1.getWins() - p1.getLoses()) / totalMatches) * 100;
        if (onlyNums) {
            System.out.println(edge);
        } else {
            System.out.printf("Player Edge: %f\nPlayer Wins: %d\nPlayer Loses: %d\nTotal Matches Played: %d\n\n",edge,p1.getWins(),p1.getLoses(),totalMatches);
        }
    }

    /** Response Questions:
     * When using the same strategy, the player is still predicted to loose more than the dealer.
     * This is because the player always plays before the dealer which introduces variability in
     * the players decision making. Plus if the player busts, the game automatically goes to the
     * dealer. All increasing the odds that the dealer will win over the player.
     * 
     * In my opinion, the benefit of using any rules strategy doesn't seem to make much of a
     * difference in the long run. For me personally, I'll take Euchre, or Texas Hold 'em, or
     * a lot of other card games over Blackjack. So committing to memorizing so many different
     * rules for a game that I don't play often with minimal increases in profit seems not worthwile.
    */
}