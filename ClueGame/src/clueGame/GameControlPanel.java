package clueGame;

import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * @author Ryan Steinglass
 * @author Luke Bailey
 * 
 * GameControlPanel
 */
public class GameControlPanel extends JPanel {

    private JPanel panel_1 = new JPanel();
        private JPanel panel_1_1 = new JPanel();
            private JLabel label_1_1_1 = new JLabel("Whose turn?", SwingConstants.CENTER);
            private JTextField currentTurnField = new JTextField(20);

        private JPanel panel_1_2 = new JPanel();
            private JLabel label_1_2_1 = new JLabel("Roll:");
            private JTextField rollResultField = new JTextField(7);

        private JButton button_1_3 = new JButton("Make Accusation");
        private JButton button_1_4 = new JButton(">>> NEXT! >>>");

    private JPanel panel_2 = new JPanel();
        private JPanel panel_2_1 = new JPanel();
            private JTextField guessField = new JTextField();

        private JPanel panel_2_2 = new JPanel();
            private JTextField guessResultField = new JTextField();


	public GameControlPanel() {
		// Create a layout with 2 rows
		setLayout(new GridLayout(2, 0));

        panel_1.setLayout(new GridLayout(1, 4));
        add(panel_1);
        add(panel_2);

        panel_1.add(panel_1_1);
        panel_1.add(panel_1_2);
        panel_1.add(button_1_3);
        panel_1.add(button_1_4);

        panel_1_1.setLayout(new GridLayout(3, 0));
        panel_1_1.add(label_1_1_1);
        currentTurnField.setEditable(false);
        currentTurnField.setText("placeholder");
        currentTurnField.setBackground(new Color(255, 221, 111));
        panel_1_1.add(currentTurnField);

        panel_1_2.add(label_1_2_1);
        rollResultField.setEditable(false);
        rollResultField.setText("placeholder");
        panel_1_2.add(rollResultField);


        panel_2.setLayout(new GridLayout(0, 2));
        panel_2.add(panel_2_1);
        panel_2.add(panel_2_2);

        panel_2_1.setLayout(new GridLayout(1, 0));
        panel_2_1.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Guess"));
        panel_2_1.add(guessField);

        guessField.setText("placeholder");
        guessField.setEditable(false);

        panel_2_2.setLayout(new GridLayout(1, 0));
        panel_2_2.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Guess Result"));
        panel_2_2.add(guessResultField);

        guessResultField.setText("placeholder");
        guessResultField.setEditable(false);
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

        panel.setCurrentTurn("Crazy Dave");
        panel.setRollResult(5);
        panel.setGuess("Not so Crazy Dave");
        panel.setGuessResult("Nope!");
	}

    public void setGuess(String guess) {
        guessField.setText(guess);
    }

    public void setGuessResult(String guessResult) {
        guessResultField.setText(guessResult);
    }

    public void setRollResult(int roll) {
        rollResultField.setText(Integer.toString(roll));
    }

    public void setCurrentTurn(String currentTurn) {
        currentTurnField.setText(currentTurn);
    }
}
