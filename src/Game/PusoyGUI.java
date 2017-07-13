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
	private static ArrayList<Card> currentHand;
	private static int currentHandPlayer;
	private static ArrayList<String> winners = new ArrayList<String>();

	
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
    JLayeredPane handPane = new JLayeredPane();
    JButton[] hand1Buttons = new JButton[13];
    
    
    private static int action = 0;
    private static int index = 0;
    
    //Initializes the GUI by adding all components to the window
	public PusoyGUI() throws IOException
	{
		window.setMinimumSize(new Dimension(500,500));
        newGame.setBounds(50, 350, 95, 30);
        mainPanel.setLocation(0, 0);
        mainPanel.setSize(500, 478);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(handPane, BorderLayout.CENTER);
        playingHand.setBounds(75, 230, 345, 95);
        
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
        
        gameStart();
        
        int x = 6;
        
        for(int i = 0; i < hand1.getSize(); i++)
        {
        	Image img = ImageIO.read(new File(hand1.get(i).fileName));
            Image image = img.getScaledInstance(65, 95, java.awt.Image.SCALE_SMOOTH);
            JButton card = new JButton(new ImageIcon(image));
            card.setBounds(x+=15, 338, 65, 95);
            hand1Buttons[i] = card;
            index = i;
            
            card.addActionListener(new ActionListener()
            	{
  
            		public void actionPerformed(ActionEvent e)
            		{
            			if(card.isEnabled())
            			{
            				if(action < 5)
            				{
            					card.setLocation((playingHand.getX() + 5) + (65 * action++), playingHand.getY());
            				}
            				hand1.addToPlayingHand(hand1.get(index++));
            				
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
        	public void actionPerformed(ActionEvent e)
        	{
        		System.out.println(hand1.playingHand);
        		
        	}
        });
        
      window.getContentPane().add(mainPanel);
      
      handPane.add(playingHand);
      buttonPanel.add(newGame);
      buttonPanel.add(Pass);
      buttonPanel.add(Play);
      
      
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
