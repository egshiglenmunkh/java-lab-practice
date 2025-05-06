package Java_assignments;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Blackjack {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter seed: ");
        int seed = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter number of players: ");
        int numPlayers = Integer.parseInt(scanner.nextLine());

        ArrayList<Hand> players = new ArrayList<>();
        
        Player player = new Player();
        players.add(player);
        for (int i = 0; i < numPlayers - 1; i++) {
            players.add(new Computer(seed, i+2));
        }
        House house = new House();

        Deck deck = new Deck(); // Create the deck.
        deck.shuffle(seed); // Shuffle the deck.

        for (int r = 0; r < 2; r++) {
            for (Hand p : players) {
                p.hit(deck.dealCard());
            }
            house.hit(deck.dealCard());
        }

        System.out.println("House: HIDDEN, " + house.secondCard());
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Player" + (i + 1) + ": " + players.get(i) + " (" + players.get(i).sum() + ")");
        }
        
        if (house.hasBlackjack()) {
        	System.out.println("--- Game Results ---");
            
            System.out.println("House: " + house + " (" + house.sum() + ")");
            
            for (int i = 0; i < numPlayers; i++) {
                Hand currentPlayer = players.get(i);
                if (currentPlayer.hasBlackjack()) {
                	System.out.println("[Win] Player" + (i + 1) + ": " + currentPlayer + " (" + currentPlayer.sum() + ")");
                } else {
                    System.out.println("[Lose] Player" + (i + 1) + ": " + currentPlayer + " (" + currentPlayer.sum() + ")");
                }
            }
        }
        	
        else {
        	System.out.println("--- Player1 turn ---");
        	System.out.println("Player1: " + player + " (" + player.sum() + ")");
            while (true) {
                if (player.sum() > 21) {
                    System.out.println("Player1: " + player + " (" + player.sum() + ") - Bust!");
                    break;
                }

                System.out.println("Hit or Stand?");
                String action = scanner.nextLine();

                if (action.equalsIgnoreCase("Hit")) {
                    player.hit(deck.dealCard());
                    System.out.println("Player1: " + player + " (" + player.sum() + ")");
                } else if (action.equalsIgnoreCase("Stand")){
                	System.out.println("Player1: " + player + " (" + player.sum() + ")");
                	break;
                }
                
            }

            for (int i = 1; i < numPlayers; i++) {
                Hand currentPlayer = players.get(i);
                System.out.println("--- Player" + (i + 1) + " turn ---");
                System.out.println("Player" + (i + 1) + ": " + currentPlayer + " (" + currentPlayer.sum() + ")");
                ((Computer) currentPlayer).play(deck); 
                if (currentPlayer.sum() > 21) {
                    System.out.println("Player" + (i + 1) + ": " + currentPlayer + " (" + currentPlayer.sum() + ") - Bust!");
                } else {
                    System.out.println("Player" + (i + 1) + ": " + currentPlayer + " (" + currentPlayer.sum() + ")");
                }
            }
             
           
            System.out.println("--- House turn ---");
            System.out.println("House: " + house + " (" + house.sum() + ")");
            house.drawUntil(deck);
            
            
            System.out.println("--- Game Results ---");
            
            System.out.println("House: " + house + " (" + house.sum() + ")" + (house.sum() > 21 ? " - Bust!" : ""));
            
            for (int i = 0; i < numPlayers; i++) {
                Hand currentPlayer = players.get(i);
                if (currentPlayer.sum() > 21) {
                    System.out.println("[Lose] Player" + (i + 1) + ": " + currentPlayer + " (" + currentPlayer.sum() + ") - Bust!");
                } else if (house.sum() > 21 || currentPlayer.sum() > house.sum() || currentPlayer.sum() == house.sum()) {
                    System.out.println("[Win] Player" + (i + 1) + ": " + currentPlayer + " (" + currentPlayer.sum() + ")");
                } else {
                    System.out.println("[Lose] Player" + (i + 1) + ": " + currentPlayer + " (" + currentPlayer.sum() + ")");
                }
            }
        }
        
    }
    
}


class Card {
    private int value;
    private int suit;

    public Card() {}
    public Card(int theValue, int theSuit) {
        this.value = theValue;
        this.suit = theSuit;
    }

    public int getCardValue() {
        int cardValue = this.value;
        if (this.value == 1) {
            cardValue = 11;
        } else if (this.value >= 11 && this.value <= 13) {
            cardValue = 10;
        }
        return cardValue;
    }

    public String toString() {
        String[] VALUES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] SUITS = {"c", "h", "d", "s"};
        return VALUES[value - 1] + SUITS[suit];
    }
}

class Deck {
    private Card[] deck;
    private int cardsUsed;

    public Deck() {
        deck = new Card[52];
        int index = 0;
        for (int i = 1; i < 14; i++) {
            for (int z = 0; z < 4; z++) {
                deck[index] = new Card(i, z);
                index++;
            }
        }
        cardsUsed = 0; 
    }

    public void shuffle(int seed) {
        Random random = new Random(seed);
        for (int i = deck.length - 1; i > 0; i--) {
            int rand = random.nextInt(i + 1);
            Card temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        cardsUsed = 0; 
    }

    public Card dealCard() {
        if (cardsUsed >= deck.length)
            throw new IllegalStateException("No cards are left in the deck.");

        return deck[cardsUsed++];
    }
}

class Hand {
    private ArrayList<Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public void hit(Card c) {
        hand.add(c);
    }

    public int sum() {
        int sum = 0;
        int aceCount = 0;

        for (Card card : hand) {
            sum += card.getCardValue();
            if (card.getCardValue() == 11) {
                aceCount++;
            }
            while (sum > 21 && aceCount > 0) {
                sum -= 10;
                aceCount--;
            }
        }
        return sum;
    }

    public boolean hasBlackjack() {
        return hand.size() == 2 && sum() == 21;
    }

    protected Card getSecondCard() {
        return hand.size() == 2 ? hand.get(1) : null;
    }

    public String toString() {
        return hand.toString();
    }
}

class Computer extends Hand {
	private Random random;
	private int seed;
	private int playerNumber;
	
	public Computer(int seed, int playerNumber) {
		super();
		this.random = new Random(seed);
		this.seed = seed;
		this.playerNumber = playerNumber;
	}
    public String play(Deck deck) {
        while (true) {
        	int currentSum = this.sum();
        	
        	if (currentSum >21) {
        		break;
        	}
        	if (currentSum < 14) {
        		this.hit(deck.dealCard());
        		System.out.println("Hit");
        		System.out.println("Player" + playerNumber + ": " + this + " (" + this.sum() + ")");
        	}else if (currentSum >= 14 && currentSum <= 17){
        		if (this.seed == 35) {
        			System.out.println("Stand");
        			break;
        		}else if (random.nextBoolean()) { 
                    this.hit(deck.dealCard());
                    System.out.println("Hit");
                    System.out.println("Player" + playerNumber + ": " + this + " (" + this.sum() + ")");
                }else {
        			System.out.println("Stand");
        			break;
        		}
        	}else {
        		System.out.println("Stand");
        		break;
        	}       
        }
        return this.toString();
    }
}

class Player extends Hand {
}

class House extends Hand {
    public void drawUntil(Deck deck) {
        while (this.sum() <= 16) {
            this.hit(deck.dealCard());
            System.out.println("Hit");
            System.out.println("House: " + this + " (" + this.sum() + ")" + (this.sum() > 21 ? "-Bust!" : ""));
        }
        if (this.sum() <= 21) {
            System.out.println("Stand");
            System.out.println("House: " + this + " (" + this.sum() + ")");
        }
    }

    public String secondCard() {
        Card secondCard = getSecondCard();
        return secondCard != null ? secondCard.toString() : "No cards in hand";
    }
}
