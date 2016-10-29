package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGUI extends JPanel{
	private JTextField guess;
	private JTextField response;
	private JTextField whoseTurn;
	private JTextField dieRoll;
	public ClueGUI(){
		setLayout(new GridLayout(2,3));
		JPanel panel = dieRollPanel();
		add(panel);
		panel = whoseTurnPanel();
		add(panel);
		panel = createMakeAccusationButton();
		add(panel);
		panel = guessPanel();
		add(panel);
		panel = guessResultPanel();
		add(panel);
		panel = createNextPlayerButton();
		add(panel);
	}
	
	private JPanel guessPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel guessLabel = new JLabel("Guess");
		guess = new JTextField(50);
		panel.add(guessLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}
	
	private JPanel guessResultPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel responseLabel = new JLabel("Response");
		response = new JTextField(50);
		panel.add(responseLabel);
		panel.add(response);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	private JPanel whoseTurnPanel(){
		JPanel panel = new JPanel();
		//panel.setLayout(new GridLayout(1,0));
		whoseTurn = new JTextField(20);
		panel.add(whoseTurn);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Whose Turn?"));
		return panel;
	}
	
	private JPanel dieRollPanel(){
		JPanel panel = new JPanel();
		//panel.setLayout(new GridLayout(1,2));
		dieRoll = new JTextField(10);
		panel.add(dieRoll);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die Roll"));
		return panel;
	}
	
	private JPanel createMakeAccusationButton() {
	 	JButton accusation = new JButton("Make Accusation");
	 	accusation.setPreferredSize(new Dimension(300,80));;
	 	JPanel panel = new JPanel();
	 	panel.add(accusation);
	 	return panel;
	}
 
	private JPanel createNextPlayerButton() {
		JButton player = new JButton("Next Player");
		player.setPreferredSize(new Dimension(300,80));;
	 	JPanel panel = new JPanel();
	 	panel.add(player);
	 	return panel;
	}
	
	public static void main(String[] args) {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(800, 200);	
				ClueGUI gui = new ClueGUI();
				frame.add(gui, BorderLayout.CENTER);
				frame.setVisible(true);
	}
}
