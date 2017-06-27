package Game;

import java.lang.reflect.Array;
import java.util.*;
import Game.Card;

/**
 * An object of type hand represents a hand of playing cards. A Hand contains up to
 * 13 cards at the start and the cards can be selected from the hand
 * to play in a game. 
 * 
 *	@author Kan Yamamoto
 */
public class Hand 
{
	Scanner scan = new Scanner(System.in);
	
	/**
	 * An arraylist of cards to hold the possible hand
	 */
	ArrayList<Card> hand;
	
	boolean roundDone; //If the player has passed in the round
	boolean gameDone; //If the player has played all of their cards
	boolean start; //If the player starts the game
	String ID;
	
	/**
	 * Constructs an arraylist of cards. Sets the roundDone,
	 * gameDone, and start to false.
	 */
	public Hand()
	{
		hand = new ArrayList<Card>();
		roundDone = false;
		gameDone = false;
		start = false;
		ID = null;
	}
	
	/**
	 * Constructs an arraylist of cards that can be passed
	 * an arraylist. 
	 * @param x arraylist that you want the hand to be
	 */
	public Hand(ArrayList<Card> x)
	{
		hand = x;
		roundDone = false;
		gameDone = false;
		start = false;
		ID = null;
	}
	
	/**
	 * Adds a card to the hand, only adds to the end of the hand
	 * @param c the card that is added to the hand
	 */
	public void addCard(Card c)
	{
		hand.add(c);
	}
	
	public int getSize()
	{
		return hand.size();
	}
	
	/**
	 * Sorts the hand by card value
	 */
	public void sortByValue()
	{
		hand.sort(Card.CardComparator);
	}
	
	/**
	 * Sorts the hand by Suit
	 */
	public void sortBySuit()
	{
		hand.sort(Card.SuitComparator);
	}
	
	/**
	 * Tells if there are any cards left in the hand
	 * @return true if there are no cards left in the hand,
	 * the hand is done playing the game
	 */
	public boolean isDone()
	{
		if(hand.size() > 0)
		{
			gameDone = false;
			return gameDone;
		}
		else
		{
			gameDone = true;
			return gameDone;
		}
	}
	
	/**
	 * Prints all of the cards in the hand with an index
	 */
	public void print()
	{
		System.out.println("\n");
		for(int i = 0; i < hand.size(); i++)
		{
			System.out.printf("%-3d", (i + 1));
			hand.get(i).printCard();
			System.out.println();
		}
	}
	
	/**
	 * Removes card from the hand
	 * @param c the card to be removed
	 */
	public void remove(Card c)
	{
		for(int i = 0; i < hand.size(); i++)
		{
			if(hand.get(i).getValue() == c.getValue() &&
					hand.get(i).getSuit() == c.getSuit())
			{
				hand.remove(i);
			}
		}
	}
	
	/**
	 * Gets which cards are going to be played for
	 * the round
	 * @return returns an arraylist of cards that are selected
	 * from the hand
	 */
	public ArrayList<Card> getHand(boolean first)
	{
		ArrayList<Integer> playingHandInt = new ArrayList<Integer>();
		ArrayList<Card> playingHand = new ArrayList<Card>();
		int choice = 0;
		if(hand.size() == 0)
			gameDone = true;
		
		if(gameDone == false)
		{
			while(choice != 1 && choice != 2 && first == false)
			{
				System.out.println("1. Select cards to play\n" + 
								   "2. Pass");
				choice = scan.nextInt();
			}
			while(first == true)
			{
				System.out.println("----Start of round----");
				choice = 1;
				first = false;
			}
			
			if(choice == 1)
			{
				int count = 0;
				System.out.print("Which card(s) do you want to play(0 for no other card): ");
				for(int i = 0; i < 5; i++)
				{
					int num = scan.nextInt();
					if(num < 0 || num > hand.size())
					{
						System.out.println("Invalid card index, please enter a different number");
						num = scan.nextInt();
					}
					playingHandInt.add(num);
		    		count++;
			    }
				Collections.sort(playingHandInt);
				Collections.reverse(playingHandInt);
			    for(int i = 0; i < playingHandInt.size(); i++)
			    {
			    	if(playingHandInt.get(i)!= 0)
			    	{
			    		playingHand.add(hand.get(playingHandInt.get(i) - 1));
			    	}
			    }
			    
			}
			if(choice == 2)
			{
				roundDone = true;
			}
			playingHand.sort(Card.CardComparator);
		}
		
		
		return playingHand;
	}
	
	/**
	 * Returns if the hand has the 3 of clubs, if so
	 * the hand returns true for start
	 * @return if the hand contains the three of clubs, return true
	 */
	public boolean starts()
	{
		for(Card c : hand)
		{
			if(c.getSuit() == 0 && c.getValue() == 3)
			{
				start = true;
			}
		}
		return start;
	}
	
	public int numLeft()
	{
		return hand.size();
	}

	
}
