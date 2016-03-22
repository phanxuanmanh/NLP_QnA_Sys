package main.demo.addQnA;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.demo.DBConnect.QnA_Accessor;

public class InputFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea question, answer;
	JButton submit;

	public InputFrame() {
		createJFrame();
	}

	// create JFrame
	private void createJFrame() {
		setTitle("Input Question and Answer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 400);
		// add main panel
		add(createMainPanel());
		// display
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// create main panel
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.add(createTitlePanel(), BorderLayout.NORTH);
		panel.add(createNamePanel(), BorderLayout.WEST);
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createLoginButtonPanel(), BorderLayout.SOUTH);
		return panel;
	}

	// create title panel
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Input here"));
		return panel;
	}

	// create name item
	private JPanel createNamePanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		panel.add(new JLabel("Question"));
		panel.add(new JLabel("Answer"));
		return panel;
	}

	// create input panel
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		question = new JTextArea();
		answer = new JTextArea();
		panel.add(question);
		panel.add(answer);
		return panel;
	}

	// create login button panel
	private JPanel createLoginButtonPanel() {
		JPanel panel = new JPanel();
		submit = new JButton("Submit");
		panel.add(submit);
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				QnA_Accessor dbAccess = new QnA_Accessor();
				boolean result = false;
				result = dbAccess.insertQnAPair(question.getText(),
						answer.getText());
				if (result == true) {
					JOptionPane.showMessageDialog(null, "Insert succes");
					answer.setText("");
					question.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"Some error occur when insert");

				}

			}
		});
		return panel;
	}

	public static void main(String[] args) {
		new InputFrame();
	}
}
