public class BlackjackPlayerUnitTest{

    public static void main(String[] args) {
        Player p1 = new BlackjackPlayer("Player 1");
        Player p2 = new BlackjackPlayer("Player 2");
        Player p3 = new BlackjackPlayer("Player 3");
        Player p4 = new BlackjackPlayer("Player 4");
        Player p5 = new BlackjackPlayer("Player 5");
        Player p6 = new BlackjackPlayer("Player 6");
        Player p7 = new BlackjackPlayer("Player 7");
        Player p8 = new BlackjackPlayer("Player 8");
        Player p9 = new BlackjackPlayer("Player 9");
        
        p1.take(new Card(2,2));
        p1.take(new Card(3,2));
        p1.take(new Card(4,2));
        System.out.println("Result =    "+p1.getScore()+ "  Expected =  9");
    
        p2.take(new Card(11,2));
        p2.take(new Card(12,3));
        p2.take(new Card(2,1));
        System.out.println("Result =   "+p2.getScore()+ "  Expected = 22");

        p3.take(new Card(11,2)); // Jack (10)
        p3.take(new Card(1,3)); // Ace (11)
        System.out.println("Result =   "+p3.getScore()+ "  Expected = 21");

        p4.take(new Card(10,2));
        p4.take(new Card(1,3));
        p4.take(new Card(10,1));
        System.out.println("Result =   "+p4.getScore()+ "  Expected = 21");

        p5.take(new Card(1,2));
        p5.take(new Card(1,3));
        System.out.println("Result =   "+p5.getScore()+ "  Expected = 12");

        p6.take(new Card(1,0));
        p6.take(new Card(1,1));
        p6.take(new Card(1,2));
        p6.take(new Card(1,3));
        System.out.println("Result =   "+p6.getScore()+ "  Expected = 14");

        p7.take(new Card(10,2)); // 10
        p7.take(new Card(1,2)); // Ace
        p7.take(new Card(1,3)); // Ace
        System.out.println("Result =   "+p7.getScore()+ "  Expected = 12");

        p8.take(new Card(1,2)); // Ace
        p8.take(new Card(10,2)); // 10
        p8.take(new Card(1,3)); // Ace
        System.out.println("Result =   "+p8.getScore()+ "  Expected = 12");

        p9.take(new Card(1,2)); // Ace
        p9.take(new Card(1,3)); // Ace
        p9.take(new Card(10,3)); // 10
        System.out.println("Result =   "+p9.getScore()+ "  Expected = 12");

    }
    
}