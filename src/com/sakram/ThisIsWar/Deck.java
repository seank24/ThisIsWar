package com.sakram.ThisIsWar;


import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> cards; // To begin building a deck of cards, we start with an ArrayList,

	public Deck() {
		cards = makeDeck();		   // and use our makeDeck method to populate it with 52 items.
	}
	
	public ArrayList<Card> makeDeck() {
		ArrayList<Card> newDeck = new ArrayList<Card>();
		
		for (int i = 0; i < 4; i++) {			// Each card receives a numeric value corresponding to its suit
			for (int j = 0; j < 13; j++) {		// and value in the game, making it easy to run a nested for loop
				Card c = new Card(j, i);		// to populate the deck. But each card also receives a unique identifier
				newDeck.add(c);					// that corresponds to its image file on the disk, called the defaultIndex.
				c.setDefaultIndex(j + (13 * i));
			}
		}
		
		return newDeck;
	}
	
	public ArrayList<Card> getDeck() {
		return cards;
	}
	
	public int[] deal(ArrayList<Card> cards) {
		int[] yourHand = new int[5];			// The deal method simply shuffles the array of cards, and prepares 
		Collections.shuffle(cards);				// another array of five numbers to represent the user's hand. 
		
		for (int i = 0; i < 5; i++) {			// The top five cards in the array are "dealt", meaning that their
			Card c = cards.get(i);				// default indices are stored as ints and easily accessed/displayed
			int index = c.getDefaultIndex();	// on screen. If implemented in a game context, I may want to alter
			yourHand[i] = index;				// this method later on, but for display's sake this is all we need.
		}
		
		return yourHand;
	}
	
}
