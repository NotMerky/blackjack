import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.*;

public class BlackjackPanel extends JPanel implements KeyListener, MouseListener {

    static int fps = 10;
    static final int CARD_WIDTH = 84;
    static final int CARD_HEIGHT = 113;
    static int windowWidth;
    static int windowHeight;
    Color[][] background;
    BlackjackPlayer dealer;
    BlackjackPlayer p1;
    Deck deck;
    Deck discard;
    String state;
    String statusMessage;
    String userBustMessage;
    String dealerBustMessage;
    char keyInput;
    int mouseInput;
    int mouseX;
    int mouseY;

    public BlackjackPanel(){
        BufferedImage imageIn = null;
        try {
            imageIn = ImageIO.read(new File("images\\PokerTable.jpg"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        
        deck = new Deck();
        discard = new Deck();
        discard.deal(52); // remove all cards in discard pile
        windowWidth = imageIn.getWidth();
        windowHeight = imageIn.getHeight();
        background = makeColorArray(imageIn);
        setPreferredSize(new Dimension(windowWidth,windowHeight));
        setBackground(Color.BLACK);
        
        keyInput = 0;
        mouseInput = 0;
        mouseX = -1;
        mouseY = -1;

        state = "READY"; // state machine initial state
        p1 = new BlackjackPlayer("Player");
        dealer = new BlackjackPlayer("Dealer");
        deck = new Deck();
        deck.shuffle();

        addKeyListener(this);
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        if (state == "READY") {
            drawTitleScreen(g);
        } else {
            drawStatusMessages(g);
            drawPlayerProfiles(g);
            drawPlayerHand(g);
            drawDealerHand(g, state);
        }
    }

    public void paintBackground(Graphics g) {
        for (int row = 0; row < background.length; row++) {
            for (int col = 0; col < background[0].length; col++) {
                g.setColor(background[row][col]);
                g.fillRect(col, row, 1, 1);
            }
        }
    }

    public void drawPlayerHand(Graphics g) {
        int offset = 0;
        for (int i = 0; i < p1.getHand().size(); i++) {
            BufferedImage imageObject = p1.getHand().get(i).getFace();
            g.drawImage(imageObject, calcCardStartingPoint(p1) + offset, (450), CARD_WIDTH, CARD_HEIGHT, null);
            offset += 90;
        }
    }

    public void drawDealerHand(Graphics g, String state) {
            int offset = 0;
            for (int i = 0; i < dealer.getHand().size(); i++) {
                BufferedImage imageObject = dealer.getHand().get(i).getFace();
                if (i == 0 && (!state.equals("SHOW") && !state.equals("WAIT")) ) {
                    g.drawImage(Card.getBack(), calcCardStartingPoint(dealer) + offset, 50, CARD_WIDTH, CARD_HEIGHT, null);
                } else {
                    g.drawImage(imageObject, calcCardStartingPoint(dealer) + offset, 50, CARD_WIDTH, CARD_HEIGHT, null);
                }
                offset += 90;
            }
        }

    public void drawPlayerProfiles(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(795,80,175,64); // Dealer Box
        g.fillRect(50,480,175,64); // Player Box
        g.setColor(Color.GREEN);
        g.drawRect(795,80,175,64); // Dealer Box
        g.drawRect(50,480,175,64); // Player Box
        g.setFont(new Font("Arial", Font.ITALIC,20));
        g.drawString(String.format(dealer.getName()),873,110);
        g.drawString(String.format("Wins: %d",dealer.getWins()),873,130);
        g.drawString(String.format(p1.getName()),120,510);
        g.drawString(String.format("Wins: %d",p1.getWins()),120,530);  
        try {
            BufferedImage dealerSprite = ImageIO.read(new File("images\\dealer.png"));
            g.drawImage(dealerSprite,800,80,64,64, null);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        try {
            BufferedImage playerSprite = ImageIO.read(new File("images\\player.png"));
            g.drawImage(playerSprite,50,481,64,64, null);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public void drawStatusMessages(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 35));
        FontMetrics metrics = g.getFontMetrics();
        int statusWidth = metrics.stringWidth(statusMessage);
        int userBustWidth = metrics.stringWidth(userBustMessage);
        int dealerBustWidth = metrics.stringWidth(dealerBustMessage);
        g.drawString(statusMessage, ((windowWidth - statusWidth) / 2), (windowHeight / 2));
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.ITALIC, 35));
        g.drawString(userBustMessage, ((windowWidth - userBustWidth) / 2), 400);
        g.drawString(dealerBustMessage, ((windowWidth - dealerBustWidth) / 2), 215);
    }
    
    public void drawTitleScreen(Graphics g) {
        try {
            BufferedImage logo = ImageIO.read(new File("images\\blackjack.png"));
            g.drawImage(logo,((windowWidth / 2) - (logo.getWidth() / 2)), (-20), 512, 512, null);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        String startMessage = "Click Anywhere Or Press Space To Play!";
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 45));
        FontMetrics metrics = g.getFontMetrics();
        int stringWidth = metrics.stringWidth(startMessage);
        g.drawString(startMessage, ((windowWidth - stringWidth) / 2), 525);
    }

    public void drawInstructionBox(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(250,100,500,300);
        g.setColor(Color.BLACK);
        g.drawRect(250,100,500,300);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Blackjack", 250,100);
    }

    public int calcCardStartingPoint(BlackjackPlayer player) {
        int totalSize = (CARD_WIDTH) * player.getHand().size();
        return (windowWidth / 2) - (totalSize / 2);
    }

    public void run() {
        while(true) {
            update();
            repaint();
            delay(1000 / fps);
        }
    }

    public void update() {
        if (state.equals("READY")) {
            if (mouseInput == 1 || keyInput == ' ') {
                state = "DEALING";
                statusMessage = "";
                userBustMessage = "";
                dealerBustMessage = "";
                keyInput = 0;
                mouseInput = 0;
                mouseX = -1;
                mouseY = -1;
            }
        } else if (state.equals("DEALING")) {
            statusMessage = "";
            userBustMessage = "";
            dealerBustMessage = "";
            dealer.take(deck.deal(2));
            p1.take(deck.deal(2));
            state = "PLAYER_TURN";
        } else if (state.equals("PLAYER_TURN")) {
            if (p1.getScore() > 21) {
                state = "SHOW";
                userBustMessage = "You Busted!";
            }
            if (keyInput == 'h') {
                p1.take(deck.deal());
            }
            if (keyInput == 's') {
                state = "DEALER_TURN";
            }
            keyInput = 0;
        } else if (state.equals("DEALER_TURN")) {
            dealer.take(deck.deal());
            if (dealer.getScore() >= 17) {
                if (dealer.getScore() > 21) {
                    dealerBustMessage = "Dealer Busted!";
                }
                state = "SHOW";
            }
        } else if (state.equals("SHOW")) {
            if ((dealer.getScore() > 21) || (p1.getScore() <= 21 && p1.getScore() > dealer.getScore())) {
                p1.addWin();
                dealer.addLoss();
                statusMessage = "Player Won!";
            } else if ((p1.getScore() > 21) || (p1.getScore() <= 21 && p1.getScore() < dealer.getScore())) {
                p1.addLoss();
                dealer.addWin();
                statusMessage = "Dealer Won!";
            } else if (p1.getScore() <= 21 && p1.getScore() == dealer.getScore()) {
                statusMessage = "Draw!";
            }
            // printEdge();
            state = "WAIT";
        } else if (state.equals("WAIT")) {
            if (keyInput == ' ') {
                state = "FOLD";
                keyInput = 0;
            }
        } else if (state.equals("FOLD")) {
            discard.add(p1.fold());
            discard.add(dealer.fold());
            if (deck.getCardsLeft() <= 15) {
                deck.add(discard.deal(discard.getCardsLeft()));
                deck.shuffle();
            }
            state = "DEALING";
        } else {
            throw new RuntimeException("Undefined state: " + state);
        }
    }

    public void delay(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public Color[][] makeColorArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = new Color(image.getRGB(col, row), true);
                result[row][col] = c;
            }
        }
        return result;
    }

    public void printEdge() {
        int totalMatches = p1.getWins() + p1.getLoses();
        double edge = ((double)(p1.getWins() - p1.getLoses()) / totalMatches);
        System.out.printf("Player Edge: %f\nPlayer Wins: %d\nPlayer Loses: %d\nTotal Matches Played: %d\n\n",edge,p1.getWins(),p1.getLoses(),totalMatches);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseInput = 1;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Unused Method
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Unused Method
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Unused Method
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Unused Method
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyInput = e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Unused Method***
    }
}