package Game;

import java.util.*;

import Game.Card;
import Game.Hand;

public class Pusoy 
{
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
	
	public static int startingPlayer(Hand w, Hand x, Hand y, Hand z)
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
	
	
	public static int getInput(int start, int remaining, boolean first, int rankMin)
	{
		int numberOfInputs = 0;		
		
		
		if(start < hands.size() && hands.get(start).roundDone == false && hands.get(start).gameDone == false)
		{
			if(first == false)
			{
				System.out.println("Player to beat: " + currentHandPlayer);
				System.out.println("Hand to beat: ");
				for(int i = 0; i < currentHand.size(); i++)
				{
					System.out.println(currentHand.get(i)); 
				}
			}
			
			
			hands.get(start).sortByValue();
			hands.get(start).print();
			System.out.println(hands.get(start).ID + " , select card to play");
			boolean firstTry = true;

			do
			{
				if(hands.get(start).gameDone == false)
				{
					if(firstTry == false)
					{
						System.out.println("rank: " + getRank(playingHands[start]));
						System.out.println("Cards entered are not a valid hand");
						playingHands[start] = hands.get(start).getHand(first);
					}
					firstTry = false;
					playingHands[start] = hands.get(start).getHand(first);
					if(hands.get(start).roundDone == true)
						break;
				}
				

			}while(getRank(playingHands[start]) < 0 && hands.get(start).gameDone == false);
			
			
			
			//check hand
			if(first == false && hands.get(start).roundDone == false)
			{
				isValid(start ,rankMin);
			}
			
			
			if(hands.get(start).roundDone == false)
			{
				for(int i = 0; i < playingHands[start].size(); i++)
			    {
			    	hands.get(start).remove(playingHands[start].get(i));
			    }
				currentHand = playingHands[start];
				currentHandPlayer = start;
				numberOfInputs++;
			}
			else
			{
				remaining--;
			}
			
			if(hands.get(start).getSize() == 0)
			{
				while(hands.get(start).roundDone == true)
				{
					if(start < hands.size())
						start++;
					if(start == hands.size())
						start = 0;
				}
				

				hands.get(start).gameDone = true;
				winners.add(hands.get(start).ID);
				remaining = 1;
				hands.remove(start);
			}
			
			if(first == true)
			{
				rankMin = getRank(playingHands[start]);
				first = false;
			}

			start++;
			
		}
		while(numberOfInputs < remaining && remaining > 1)
		{
			if(start >= hands.size())
				start = 0;
			//doesnt correctly return the winner of the round sometimes
			if(start < hands.size() && hands.get(start).roundDone == true)
			{
				while(hands.get(start).roundDone == true)
				{
					if(start < hands.size())
						start++;
					if(start == hands.size())
						start = 0;
				}
			}
			
			for(int i = 0; i < hands.size(); i++)
			{
				System.out.println(hands.get(i).ID + " rounddone: " + hands.get(i).roundDone);
			}

			if(first == false && hands.get(start).roundDone == false && hands.get(start).gameDone == false)
			{
				
				//Still plays hand this is already out of card and auto ends the round
				System.out.println("Player to beat: " + currentHandPlayer);
				System.out.println("Hand to beat: ");
				for(int i = 0; i < currentHand.size(); i++)
				{
					System.out.println(currentHand.get(i));
				}
			}
			hands.get(start).sortByValue();
			hands.get(start).print();
			System.out.println(hands.get(start).ID + ", select card to play");
			boolean firstTry = true;
			//Sometimes need to input twice
			do
			{
				if(hands.get(start).gameDone == false)
				{
					if(firstTry == false)
					{
						System.out.println("rank: " + getRank(playingHands[start]));
						System.out.println("Cards entered are not a valid hand");
						playingHands[start] = hands.get(start).getHand(first);
					}
					else
					{
						firstTry = false;
						playingHands[start] = hands.get(start).getHand(first);
					}
					if(hands.get(start).roundDone == true)
						break;
				}
				

			}while(getRank(playingHands[start]) < 0 && hands.get(start).gameDone == false);
			if(first == false && hands.get(start).roundDone == false)
			{
				isValid(start ,rankMin);
			}
			
			
			
			
			if(hands.get(start).roundDone == false)
			{
				for(int i = 0; i < playingHands[start].size(); i++)
			    {
			    	hands.get(start).remove(playingHands[start].get(i));
			    }
				currentHand = playingHands[start];
				currentHandPlayer = start;
				numberOfInputs++;
			}
			else
			{
				remaining--;
			}
				
			if(hands.get(start).getSize() == 0)
			{
				while(hands.get(start).roundDone == true)
				{
					if(start < hands.size())
						start++;
					if(start == hands.size())
						start = 0;
				}
				

				hands.get(start).gameDone = true;

				remaining = 1;
				winners.add(hands.get(start).ID);
				hands.remove(start);
			}
			
			
			
			if(first == true)
			{
				rankMin = getRank(playingHands[start]);
				first = false;
			}

			start++;
			if(remaining > 1)
			{
				numberOfInputs = 0;
			}
			
			if(start == 4)
				start = 0;
			
		}
		
		if(start < hands.size())
		{
			while(hands.get(start).roundDone == true)
			{
				if(start < hands.size())
					start++;
				if(start == hands.size())
					start = 0;
			}
		}

		
		System.out.println("End of inputs, winner is Player " + (start+1));

		return start;
	}
	
	
	//When players finish, it doesn't keep track of who is left in the game.
	
	public static void main(String[] args)
	{
		System.out.println("Starting a game of pusoy");
		boolean first = true;
		startGame();
		while(remainingInGame() > 1)
		{
			playRound(first);
			for(int i = 0; i < hands.size(); i++)
			{
				hands.get(i).roundDone = false;
			}
		}	
		
		System.out.println("Game Done");
		System.out.println("Winning Order: ");
		for(int i = 0; i < winners.size(); i++)
		{
			System.out.print(i + ". " + winners.get(i) + " ");
		}
	}
	
	private static int playRound(boolean first)
	{

		do
		{	
			int rankMin = -1;
			System.out.println("------Starting New Round-------");
			winner = getInput(winner, remainingInRound(), first, rankMin);
			for(int i = 0; i < hands.size(); i++)
			{
				hands.get(i).roundDone = false;
			}
			
			first = true;
		} while(remainingInRound() > 1);
		winners.add(hands.get(0).ID);
		return winner;
	}
	
	private static void isValid(int start, int rankMin)
	{
		boolean isValid = false;
		boolean first = false;
		
		if(playingHands[start].size() == 0)
		{
			isValid = true;
		}

		while(!isValid)
		{
			System.out.println("check");
			if(playingHands[start].size() < 1 || currentHand.size() < 1)
			{
				isValid = true;
			}
			else if(getRank(playingHands[start]) > rankMin && hands.get(start).roundDone == false)
			{
				System.out.println("Checking 5 card hand 296");
				if(rankMin == 0)
				{
					System.out.println("Current hand is singles");
					playingHands[start] = hands.get(start).getHand(first);
				}
				if(rankMin == 1)
				{
					System.out.println("Current hand is pairs");
					playingHands[start] = hands.get(start).getHand(first);
				}
				if(rankMin == -1)
					isValid = true;
				if(rankMin > 1)
				{
					isValid = true;
				}
			}
			else if(getRank(playingHands[start]) < rankMin && hands.get(start).roundDone == false)
			{
				System.out.println("Played hand does not beat last played");
				playingHands[start] = hands.get(start).getHand(first);
			}
			else if(getRank(playingHands[start]) == rankMin && playingHands[start].size() != 0)
			{
				//index out of bound
				System.out.println("Checking 5 card hand 386");

				if(playingHands[start].get(0).getValue() < currentHand.get(0).getValue())
				{
					System.out.println("Played hand does not beat last played");
					playingHands[start] = hands.get(start).getHand(first);
				}
				if(playingHands[start].size() != 0)
				{
					if(playingHands[start].get(0).getValue() == currentHand.get(0).getValue())
					{
						if(playingHands[start].get(0).getSuit() < currentHand.get(0).getSuit())
						{
							System.out.println("Played hand does not beat last played");
							playingHands[start] = hands.get(start).getHand(first);
						}
						else
							isValid = true;
					}
					else if(playingHands[start].get(0).getValue() < currentHand.get(0).getValue())
					{
						System.out.println("Played hand does not beat last played");
						playingHands[start] = hands.get(start).getHand(first);
					}
					else
						isValid = true;
				}				
			}	
			
		}
	}
		

	
	
	public static void startGame()
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


	public static int getRank(ArrayList<Card> playingHand) 
	{
		if(playingHand != null)
			playingHand.sort(Card.CardComparator);

		int rank = -1;  //assume its a BUST
		if(playingHand.size() == 1)
		{
			rank = 0;
		}
		
		
		//check for pair
		if(playingHand.size() == 2)
		{
			for(int i = 0; i < playingHand.size() - 1; i++)
			    if(playingHand.get(i).getValue() == playingHand.get(i + 1).getValue()) 
			    {
					rank = 1;
					i = 4;
			    }
		}

		if(playingHand.size() == 5)
		{
			//check for 3 of a kind or full house
			for(int i = 0; i < 3; i++)
			    if(playingHand.get(i).getValue() == playingHand.get(i + 1).getValue() && 
			    playingHand.get(i + 1).getValue() == playingHand.get(i + 2).getValue()) 
			    {
			    	if(i==0 && playingHand.get(3).getValue() == playingHand.get(4).getValue() || 
			    			i==2 && playingHand.get(0).getValue() == playingHand.get(1).getValue())
			    		rank = 4;
			    }
			
			//check for 4 of a kind
			for(int i = 0; i < 2; i++)
			    if(playingHand.get(i).getValue() == playingHand.get(i + 1).getValue() && 
			    playingHand.get(i + 1).getValue() == playingHand.get(i + 2).getValue() &&
			    		playingHand.get(i + 2).getValue() == playingHand.get(i + 3).getValue()) {
				rank = 5;
			    }
	
			//check for straight
			if((playingHand.get(0).getValue() - playingHand.get(4).getValue() == 4) ||
			       (playingHand.get(3).getValue() - playingHand.get(0).getValue() == 3 &&
			       playingHand.get(4).getValue() == 15 && playingHand.get(0).getValue() == 3)) {
				rank = 2;
			    }
	
			//check for flush (if we haven't already found any pairs)
			boolean flush;
			if(rank == 0 || rank == 4) {
			    flush = true;
			    for(int i = 0; i < 4; i++)
				if(playingHand.get(i).getSuit() != playingHand.get(i+1).getSuit())
				    flush = false;
			    if(flush && rank == 4)
			    	rank = 6; //straight flush!
			    else if(flush)
			    	rank = 3;
			}
			    
			//check for royal flush (if it's a straight flush)
			if(rank == 6 && playingHand.get(4).getValue() == 14 && playingHand.get(0).getValue() == 10)
			    rank = 7; //royal flush!
			
//			if(rank == 0)
//			{
//				rank = -1;
//			}
		}
		
		return rank;
	    }//end evalRank
	
}
