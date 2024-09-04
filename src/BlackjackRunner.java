import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class BlackjackRunner {

  public static void main(String[] args) {
    JFrame f = new JFrame("Blackjack"); 
    BlackjackPanel p = new BlackjackPanel();
    ImageIcon icon = new ImageIcon("images\\blackjack.jpg");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(p);
    f.pack();
    f.setVisible(true);
    p.setFocusable(true);
    p.requestFocusInWindow();
    f.setIconImage(icon.getImage());
    p.run();
  }
}