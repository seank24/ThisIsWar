package com.sakram.ThisIsWar;


public class Card {
	
	private int value;
	private int suit;
	private int defaultIndex;

	public Card(int value, int suit) {
		setValue(value);
		setSuit(suit);
	}

	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getSuit() {
		return suit;
	}
	
	public void setSuit(int suit) {
		this.suit = suit;
	}

	public int getDefaultIndex() {
		return defaultIndex;
	}

	public void setDefaultIndex(int defaultIndex) {
		this.defaultIndex = defaultIndex;
	}
}
