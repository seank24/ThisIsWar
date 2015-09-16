package com.sakram.ThisIsWar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game implements ActionListener {

	private JFrame frame;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel theirPanel;
	private JPanel yourPanel;
	private JPanel scorePanel;
	private JPanel alertPanel;
	private JLabel theirCount;
	private JLabel yourCount;
	private JLabel theirIcon;
	private JLabel yourIcon;
	private JLabel gameLabel;
	private JButton newBtn;
	private JButton drawBtn;
	private JButton quitBtn;

	private Deck d;
	private ArrayList<Card> theirCards;
	private ArrayList<Card> yourCards;
	private int theirCdsLeft;
	private int yourCdsLeft;
	private boolean isWar;
	private int warInt;
	private Card theirPlay;
	private Card yourPlay;

	private String sFormatOneOpp = "<html>\n"
			+ "<center><font size=-1><b>&nbsp;&nbsp;&nbsp;Opponent:&nbsp;&nbsp;&nbsp;</b></font><br>\n"
			+ "<font size=+2>";
	private String sFormatOneYou = "<html>\n"
			+ "<center><font size=-1><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></font><br>\n"
			+ "<font size=+2>";
	private String sFormatTwo = "</font><br>\n" + "cards left\n";

	public Game() {
		createFrame();
		d = new Deck();
	}

	
	// The following methods build up the Swing form, creating separate
	// panels for each player's top card, the score display, the game
	// buttons, and the alert message that informs the user whether they've
	// won or lost.
	
	private void createFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(createPanel());
		frame.setBounds(350, 80, 500, 650);
		frame.setTitle("War");	
		frame.setVisible(true);
	}

	private JPanel createPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(createAlertPanel(), BorderLayout.CENTER);
		mainPanel.add(createButtonPanel(), BorderLayout.EAST);
		mainPanel.add(createTheirPanel(), BorderLayout.NORTH);
		mainPanel.add(createYourPanel(), BorderLayout.SOUTH);
		mainPanel.add(createScorePanel(), BorderLayout.WEST);
		return mainPanel;
	}

	private JPanel createButtonPanel() {
		buttonPanel = new JPanel(new BorderLayout());
		newBtn = new JButton("New Game");
		drawBtn = new JButton("Draw");
		quitBtn = new JButton("Quit");
		newBtn.addActionListener(new NewButtonListener());
		drawBtn.addActionListener(new DrawButtonListener());
		quitBtn.addActionListener(new ExitButtonListener());

		JPanel newExitPanel = new JPanel(new BorderLayout());
		newExitPanel.add(newBtn, BorderLayout.NORTH);
		newExitPanel.add(quitBtn, BorderLayout.SOUTH);

		buttonPanel.add(newExitPanel, BorderLayout.NORTH);
		buttonPanel.add(drawBtn, BorderLayout.SOUTH);
		return buttonPanel;
	}

	private JPanel createTheirPanel() {
		theirPanel = new JPanel(new BorderLayout());

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("img/back.png"));
			ImageIcon icon = new ImageIcon(img);
			theirIcon = new JLabel(icon);

			String emptySpc = "<html>\n" + "&nbsp;\n";
			JLabel empty = new JLabel(emptySpc);

			theirPanel.add(empty, BorderLayout.CENTER);
			theirPanel.add(theirIcon, BorderLayout.SOUTH);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return theirPanel;
	}

	private JPanel createYourPanel() {
		yourPanel = new JPanel(new BorderLayout());

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("img/back.png"));
			ImageIcon icon = new ImageIcon(img);
			yourIcon = new JLabel(icon);

			String emptySpc = "<html>\n" + "&nbsp;\n";
			JLabel empty = new JLabel(emptySpc);

			yourPanel.add(empty, BorderLayout.CENTER);
			yourPanel.add(yourIcon, BorderLayout.NORTH);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return yourPanel;
	}

	private JPanel createScorePanel() {
		scorePanel = new JPanel(new BorderLayout());

		theirCount = new JLabel();
		yourCount = new JLabel();

		scorePanel.add(theirCount, BorderLayout.NORTH);
		scorePanel.add(yourCount, BorderLayout.SOUTH);

		return scorePanel;
	}

	private JPanel createAlertPanel() {
		alertPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8000, 50));
		gameLabel = new JLabel();

		alertPanel.add(gameLabel);
		return alertPanel;
	}
	
	// When a user begins a new game, we use the 'shuffle' method to
	// shuffle up the cards, deal half to each player, and configure
	// the arraylists and card counts to prepare for gameplay.

	private class NewButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Card> cards = d.getDeck();
			theirCards = new ArrayList<Card>();
			yourCards = new ArrayList<Card>();

			Collections.shuffle(cards);

			for (int i = 0; i < 52; i++) {
				Card c = cards.get(i);
				if (i < 26) {
					theirCards.add(c);
				}
				if (i > 25 && i < 52) {
					yourCards.add(c);
				}
			}

			theirCdsLeft = theirCards.size();
			yourCdsLeft = yourCards.size();

			String theirInit = sFormatOneOpp + theirCdsLeft + sFormatTwo;
			String yourInit = sFormatOneYou + yourCdsLeft + sFormatTwo;

			theirCount.setText(theirInit);
			yourCount.setText(yourInit);

			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("img/back.png"));
				ImageIcon icon = new ImageIcon(img);
				theirIcon.setIcon(icon);
				yourIcon.setIcon(icon);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			warInt = 0;
			isWar = false;

			String readyGo = "<html>\n"
					+ "<br><font size=+1>&nbsp;&nbsp;&nbsp;&nbsp;Click Draw to begin...</b></font>\n";
			gameLabel.setText(readyGo);
		}

	}

	private class DrawButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (theirCdsLeft < 1 || yourCdsLeft < 1) {
				return;
			}

			gameLabel.setText("");
			
			// War has many variations for gameplay, but in this app, we're playing by
			// the rule that if war is declared and you don't have enough cards left to
			// lay down three and play a fourth, you lose! Either of the following two
			// methods will convey this sad fact if it comes to pass. A more conventional
			// win or loss will get decided later on in the method.

			if (theirCards.size() <= warInt) {
				String youWin = "<html>\n"
						+ "<br><font size=+1>&nbsp;&nbsp;&nbsp;They've run out of cards.</font>\n"
						+ "<br><font size=+1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You win the game!</font>\n";
				gameLabel.setText(youWin);
				for (int i = 0; i < theirCards.size(); i++) {
					yourCards.add(theirCards.get(i));
				}
				for (int i = 0; i < theirCards.size(); i++) {
					theirCards.remove(0);
				}
				theirCdsLeft = theirCards.size();
				yourCdsLeft = yourCards.size();
				return;
			}

			if (yourCards.size() <= warInt) {
				String theyWin = "<html>\n"
						+ "<br><font size=+1>&nbsp;&nbsp;&nbsp;You've run out of cards.</font>\n"
						+ "<br><font size=+1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;They win the game!</font>\n";
				gameLabel.setText(theyWin);
				for (int i = 0; i < yourCards.size(); i++) {
					theirCards.add(yourCards.get(i));
				}
				for (int i = 0; i < yourCards.size(); i++) {
					yourCards.remove(0);
				}
				theirCdsLeft = theirCards.size();
				yourCdsLeft = yourCards.size();
				return;
			}
			
			// If both players have enough cards to keep playing, we flip up either
			// the top cards of their respective decks, or (in a War situation)
			// make like we're laying the top 3 cards of each user face down and then
			// compare the next cards to determine the outcome of the war.
			//
			// The warInt variable essentially tracks how deep in a user's deck the
			// card we're drawing to display is.

			theirPlay = theirCards.get(warInt);
			yourPlay = yourCards.get(warInt);

			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("img/" + theirPlay.getDefaultIndex() + ".png"));
				//img = ImageIO.read(new File("/Users/seankram/Desktop/cards/"
				//		+ theirPlay.getDefaultIndex() + ".png"));
				ImageIcon icon = new ImageIcon(img);
				theirIcon.setIcon(icon);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				img = ImageIO.read(new File("img/" + yourPlay.getDefaultIndex() + ".png"));
				ImageIcon icon = new ImageIcon(img);
				yourIcon.setIcon(icon);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// Now, after both cards have been flipped, we determine the outcome.
			// If one player's card is higher than the others in a non-war situation,
			// they scoop up both cards and put them on the bottom of their deck.
			// No big deal.

			if (theirPlay.getValue() > yourPlay.getValue() && !isWar) {
				String theyWin = "<html>\n"
						+ "<br><font size=+1>&nbsp;&nbsp;They win!</font>\n";
				gameLabel.setText(theyWin);
				theirCards.add(yourCards.get(0));
				theirCards.add(theirCards.get(0));
				theirCards.remove(0);
				yourCards.remove(0);
				theirCdsLeft = theirCards.size();
				yourCdsLeft = yourCards.size();
			}

			if (yourPlay.getValue() > theirPlay.getValue() && !isWar) {
				String youWin = "<html>\n"
						+ "<br><font size=+1>&nbsp;&nbsp;You win!</font>\n";
				gameLabel.setText(youWin);
				yourCards.add(theirCards.get(0));
				yourCards.add(yourCards.get(0));
				yourCards.remove(0);
				theirCards.remove(0);
				theirCdsLeft = theirCards.size();
				yourCdsLeft = yourCards.size();
			}
			
			// If one player bests another to decide a war, however, they pick up not only
			// the cards on-screen, but in accordance with the rule where each player lays
			// 3 cards face down before playing one face up, the winner collects ALL of these
			// cards. Though the Swing form only shows one at a time, we loop through each
			// player's deck for as many cards as were "played", and place them all in the
			// winner's deck.

			if (theirPlay.getValue() > yourPlay.getValue() && isWar) {
				String theyWin = "<html>\n"
						+ "<br><font size=+1 color=red><b>&nbsp;&nbsp;They win the war.</b></font>\n";
				gameLabel.setText(theyWin);
				for (int i = 0; i < warInt + 1; i++) {
					theirCards.add(yourCards.get(i));
					theirCards.add(theirCards.get(i));
				}
				for (int i = 0; i < warInt + 1; i++) {
					theirCards.remove(0);
					yourCards.remove(0);
				}
				theirCdsLeft = theirCards.size();
				yourCdsLeft = yourCards.size();
				warInt = 0;
				isWar = false;
			}

			if (yourPlay.getValue() > theirPlay.getValue() && isWar) {
				String youWin = "<html>\n"
						+ "<br><font size=+1 color=red><b>&nbsp;&nbsp;You win the war!</b></font>\n";
				gameLabel.setText(youWin);
				for (int i = 0; i < warInt + 1; i++) {
					yourCards.add(theirCards.get(i));
					yourCards.add(yourCards.get(i));
				}
				for (int i = 0; i < warInt + 1; i++) {
					yourCards.remove(0);
					theirCards.remove(0);
				}
				theirCdsLeft = theirCards.size();
				yourCdsLeft = yourCards.size();
				warInt = 0;
				isWar = false;
			}

			// When two cards of equal value are played, War™ is declared! The boolean
			// isWar is made true and the value for warInt goes up by 4, to account for
			// the three cards played face down off-screen before the card that decides
			// the war is flipped up. If a double, or triple war happens, warInt goes up
			// accordingly, and someone's gonna rake in a lot of cards.
			
			if (yourPlay.getValue() == theirPlay.getValue()) {
				String itsWar = "<html>\n"
						+ "<br><font size=+2 color=red><b>&nbsp;&nbsp;WAR!</b></font>\n";
				gameLabel.setText(itsWar);
				isWar = true;
				warInt = warInt + 4;
			}

			String theirScore = sFormatOneOpp + theirCdsLeft + sFormatTwo;
			String yourScore = sFormatOneYou + yourCdsLeft + sFormatTwo;

			theirCount.setText(theirScore);
			yourCount.setText(yourScore);

			// After updating each player's card count, we'll check if either one has run
			// out, and if so, the user is alerted on-screen and the game ends.
			
			if (theirCdsLeft < 1) {
				String youWin = "<html>\n"
						+ "<br><font size=+1 color=green><b>&nbsp;&nbsp;You've won the game!!!</b></font>\n";
				gameLabel.setText(youWin);
			}

			if (yourCdsLeft < 1) {
				String theyWin = "<html>\n"
						+ "<br><font size=+1><b>&nbsp;&nbsp;You've lost the game.</b></font>\n";
				gameLabel.setText(theyWin);
			}

			frame.validate();
			frame.repaint();
		}

	}

	private class ExitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object[] yesNo = { "Yes", "No" };
			int leave = JOptionPane.showOptionDialog(frame,
					"Do you really want to quit?", "Quit",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, yesNo, yesNo[1]);
			if (leave == JOptionPane.YES_OPTION) {

				System.exit(0);
				// }
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
