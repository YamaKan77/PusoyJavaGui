package Game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PusoyGUI
{
	//rank of possible hands in game
	public final static int ROYAL_FLUSH = 7;
	public final static int STRAIGHT_FLUSH = 6;
	public final static int FOUR_KIND = 5;
	public final static int FULL_HOUSE = 4;
	public final static int FLUSH = 3;
	public final static int STRAIGHT = 2;
	public final static int PAIR = 1;
	public final static int SINGLE = 0;
	
	boolean roundPass1 = false, roundPass2 = false, roundPass3 = false, roundPass4 = false;
	
	
	Scanner scan = new Scanner(System.in);
	static int winner;
    static boolean done1 = false, done2 = false, done3 = false, done4 = false;
	private static Hand hand1 = new Hand();
	private static Hand hand2 = new Hand();
	private static Hand hand3 = new Hand();
	private static Hand hand4 = new Hand();
	private static ArrayList<Hand> hands = new ArrayList<Hand>();
	private static ArrayList<Card> playingHand1 = new ArrayList<Card>();
	private static ArrayList<Card> playingHand2 = new ArrayList<Card>();;
	private static ArrayList<Card> playingHand3 = new ArrayList<Card>();;
	private static ArrayList<Card> playingHand4 = new ArrayList<Card>();;
	private static ArrayList<Card>[] playingHands = (ArrayList<Card>[]) new ArrayList[4];
//	private static ArrayList<Card> currentHand;
	private static int currentHandPlayer;
	private static ArrayList<String> winners = new ArrayList<String>();
	private static ArrayList<ArrayList<Card>> currentHand = new ArrayList<ArrayList<Card>>();

	
	public static int remainingInRound()
	{
		int numLeft = 0;
		for(int i = 0; i < hands.size(); i++)
		{
			if(!hands.get(i).roundDone)
				numLeft++;
		}
		
		return numLeft;
	}
	
	public static int remainingInGame()
	{
		int numLeft = 0;
		for(int i = 0; i < hands.size(); i++)
		{
			if(!hands.get(i).gameDone)
				numLeft++;
		}
		
		return numLeft;
	}
	
	public static int cardsInPlay(JPanel playingHand)
	{
//		Component[] comp = playingHand.getComponentCount();
//		for(int i = 0; i < comp.length; i++)
//		{
//			
//		}
		return playingHand.getComponentCount();
	}
	
	public static void gameStart()
	{
		Random random  = new Random();
		Deck deck = new Deck();
		
	    hands.add(hand1);
	    hands.add(hand2);
	    hands.add(hand3);
	    hands.add(hand4);
	    
	    playingHands[0] = playingHand1;
	    playingHands[1] = playingHand2;
	    playingHands[2] = playingHand3;
	    playingHands[3] = playingHand4;
	    
	    hand1.ID = "Player 1";
	    hand2.ID = "Player 2";
	    hand3.ID = "Player 3";
	    hand4.ID = "Player 4";
	    
	    int x = random.nextInt(10);
	    for(int i = 0; i < x; i++)
	    {
	    	deck.shuffle();
	    }
	    
	    int dealTo = 0;
	    while(deck.cardsLeft() != 0)
	    {
	    	if(dealTo == 0)
	    	{
	    		hand1.addCard(deck.dealCard());
	    		dealTo++;
	    	}
	    	if(dealTo == 1)
	    	{
	    		hand2.addCard(deck.dealCard());
	    		dealTo++;
	    	}
	    	if(dealTo == 2)
	    	{
	    		hand3.addCard(deck.dealCard());
	    		dealTo++;
	    	}
	    	if(dealTo == 3)
	    	{
	    		hand4.addCard(deck.dealCard());
	    		dealTo = 0;
	    	}
	    }
	   
	    
	    hand1.sortByValue();
	    hand2.sortByValue();
	    hand3.sortByValue();
	    hand4.sortBySuit();
	    
	    done1 = hand1.isDone();
	    done2 = hand2.isDone();
	    done3 = hand3.isDone();
	    done4 = hand4.isDone();
	    
	    winner = startingPlayer(hand1, hand2, hand3, hand4);
	}
	
	JFrame window = new JFrame("Pusoy");     
    JButton newGame = new JButton( "New Game" );
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton Pass = new JButton("Pass");
    JButton Play = new JButton("Play hand");
    JPanel playingHand = new JPanel();
    static JLayeredPane handPane = new JLayeredPane();
    JButton[] hand1Buttons = new JButton[13];
    JLabel[] playedHand = new JLabel[5];
    
    private static int action = 0;
    private final JTextPane HandToBeat = new JTextPane();

    
//    private static int value = 0;
    
    //Initializes the GUI by adding all components to the window
	public PusoyGUI() throws IOException
	{
		gameStart();
        
        int x = 250;
        
        for(int i = 0; i < hand1.getSize(); i++)
        {
        	Image img = ImageIO.read(new File(hand1.get(i).fileName));
            Image image = img.getScaledInstance(65, 95, java.awt.Image.SCALE_SMOOTH);
            JButton card = new JButton(new ImageIcon(image));
            card.setBounds(x+=15, 630, 65, 95);
            hand1.get(i).x = x;
            hand1.get(i).y = 630;
            hand1.get(i).width = 65;
            hand1.get(i).height = 95;
            
            hand1Buttons[i] = card;
            
            Card c = hand1.get(i);
            int index = i;
            
            card.addActionListener(new ActionListener()
            	{
  
            		public void actionPerformed(ActionEvent e)
            		{
            			if(card.isEnabled() && hand1.get(index).isSelected == false)
            			{
            				if(action < 5)
            				{
            					card.setLocation((playingHand.getX() + 5) + (65 * action++), playingHand.getY());
            				}
            				hand1.get(index).isSelected = true;
            				System.out.println(" CC" + c.toString());
            				playingHand1.add(c);
            			}
            			else if(card.isEnabled() && hand1.get(index).isSelected == true)
            			{
            				//if the card at the beginning is removed then the next cards gets played over one
            				card.setLocation(hand1.get(index).x, hand1.get(index).y);
            				action--;
            				hand1.get(index).isSelected = false;
            				playingHand1.remove(c);
            			}
            			
            		}
            	});
            
        }
        
        Pass.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		hand1.roundDone = true;
        	}
        });
        for(int i = 0; i < hand1Buttons.length; i++)
        {
        	if(hand1Buttons[i] != null)
        		handPane.add(hand1Buttons[i], i);
        }
        
        newGame.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		
        	}
        });
        
        Play.addActionListener(new ActionListener(){
        	int x = 320;
        	public void actionPerformed(ActionEvent e)
        	{
        		//card stays in playingHand after play
<<<<<<< HEAD
        		
=======
        		System.out.println("XXXsize: " + playingHand1.size());
>>>>>>> 0a22bad9da5f49dd717a671f92c126bd619a9fbf
        		if(Pusoy.getRank(playingHand1) > -1)
        		{
        			currentHand.add(playingHand1);
        			
<<<<<<< HEAD
        			System.out.println("XXXsize: " + playingHand1.size());
        			for(int i = 0; i < playingHand1.size(); i++)
        	        {
        				System.out.println("Playinghand i" + playingHand1.get(i));
=======
        			for(int i = 0; i < playingHand1.size(); i++)
        	        {
        				System.out.println("in loop size: " + playingHand1.size());
>>>>>>> 0a22bad9da5f49dd717a671f92c126bd619a9fbf
        	        	Image img = null;
						try {
							img = ImageIO.read(new File(playingHand1.get(i).fileName));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
        	            Image image = img.getScaledInstance(65, 95, java.awt.Image.SCALE_SMOOTH);
        	            JLabel card = new JLabel(new ImageIcon(image));
        	            card.setBounds(x+=15, 300, 65, 95);
        	            playingHand1.get(i).x = x;
        	            playingHand1.get(i).y = 300;
        	            playingHand1.get(i).width = 65;
        	            playingHand1.get(i).height = 95;
        	            
        	            playedHand[i] = card;
        	            
<<<<<<< HEAD
        	            System.out.println("Count: " + playedHand.length);
=======
        	            System.out.println("Count: " + handPane.getComponentCount());
>>>>>>> 0a22bad9da5f49dd717a671f92c126bd619a9fbf
        	            System.out.println(i + " Component: " + handPane.getComponent(i));
        	            
//        	            handPane.remove(14);
//        	            playingHand1.remove(i);
        	            
        	            
        	        }
        			
        			removeFromPlayingHand();
<<<<<<< HEAD
        			
        			for(int i = 0; i < playedHand.length; i++)
        			{
        				System.out.println("OPOP" + playedHand[i]);
=======
        			System.out.println("OOL Count: " + handPane.getComponentCount());
        			for(int i = 0; i < playedHand.length; i++)
        			{
     
>>>>>>> 0a22bad9da5f49dd717a671f92c126bd619a9fbf
        				if(playedHand[i] != null)
        	        		handPane.add(playedHand[i], i);
        				
        			}
<<<<<<< HEAD
        			System.out.println("OOL Count: " + handPane.getComponentCount());
=======
        			
>>>>>>> 0a22bad9da5f49dd717a671f92c126bd619a9fbf
//        			handPane.add(HandToBeat, 1);
        			window.repaint();
        			
        		}
        		else
        		{
        			Play.setText("Invalid Hand");
        			ActionListener backToPlay = new ActionListener(){
        				public void actionPerformed(ActionEvent e)
        				{
        					Play.setText("Play");
        				}
        			};
        			Timer timer = new Timer(3000, backToPlay);
        		    timer.setRepeats(false);
        		    timer.start();
        		}
//       			handPane.add(HandToBeat, 1);
        		System.out.println("currenthand: " + currentHand.size());
        		System.out.println("currenthand: " + currentHand.get(0).size());
        	}
        	
        });
        
      window.setMinimumSize(new Dimension(800,800));
      newGame.setBounds(50, 350, 95, 30);
      mainPanel.setLocation(0, 0);
      mainPanel.setSize(800, 740);
      mainPanel.setLayout(new BorderLayout());
      mainPanel.add(handPane, BorderLayout.CENTER);
      playingHand.setBounds(205, 520, 345, 95);
        
      
      window.getContentPane().add(mainPanel);
      
      
      handPane.add(playingHand);
      HandToBeat.setBackground(SystemColor.window);
      HandToBeat.setText("Hand to beat");
      HandToBeat.setBounds(340, 275, 81, 16);
      
      handPane.add(HandToBeat);
//      handPane.setMinimumSize(new Dimension(800, 760));
      buttonPanel.setBounds(0, 740, 800, 40);
      window.getContentPane().add(buttonPanel);
      buttonPanel.add(newGame);
      buttonPanel.add(Pass);
      buttonPanel.add(Play);
      window.getContentPane().setMinimumSize(new Dimension(800, 800));
      
      hand1.getPlayableHand();
      System.out.println("singles" + hand1.singles);
      System.out.println("pairs" + hand1.pairs);
      System.out.println("five card" + hand1.fiveCard);
	}
	

	
	/**
	 * Sets the window to visible and allows the GUI to be seen
	 */
	public void launch()
	{
		window.getContentPane().setLayout(null);
	    window.pack();
	    window.setVisible(true);
	}
	
	public static void main(String[] args) throws IOException 
	{
		
		PusoyGUI gui = new PusoyGUI();
		gui.launch();
    }
	
	public static void removeFromPlayingHand()
	{
		Component[] comp = handPane.getComponents();
<<<<<<< HEAD
		System.out.println("size: " + comp.length);
		for(int i = 0; i < comp.length; i++)
		{
			
//			System.out.println(i + ". Loc: " + comp[i]);
//			System.out.println(i + ". Y: " + comp[i].getY());
			System.out.println(i + ". c: " + comp[i]);
			if(comp[i].getY() == 520 && comp[i] instanceof JButton)
			{
				System.out.println(i + "button: " + comp[i].getY());
//				if(i < comp.length)
				handPane.remove(i);
				comp = handPane.getComponents();
				i = 0;
			} 
			
			
			System.out.println("size: " + comp.length);
//			System.out.println(i + ". x: " + comp[i].getX());
=======
		for(int i = 0; i < comp.length; i++)
		{
			
			System.out.println(i + ". Loc: " + comp[i]);
			System.out.println(i + ". Y: " + comp[i].getY());
//			System.out.println(i + ". c: " + comp[i]);
			if(comp[i].getY() == 520 && comp[i] instanceof JButton)
			{
				System.out.println(i + "button: " + comp[i].getY());
				if(i < comp.length)
					handPane.remove(i);
			} 
			comp = handPane.getComponents();
>>>>>>> 0a22bad9da5f49dd717a671f92c126bd619a9fbf
		}
		
//		for(int i = 0; i < playingHand1.size(); i++)
//		{
//			playingHand1.remove(i);
//		}
		
	}
	
	private static int startingPlayer(Hand w, Hand x, Hand y, Hand z)
	{
		if(w.starts())
			return 0;
		if(x.starts())
			return 1;
		if(y.starts())
			return 2;
		else
			return 3;
	}
}
