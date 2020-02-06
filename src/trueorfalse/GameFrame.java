package trueorfalse;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class GameFrame extends JFrame implements ActionListener {
	
	
	GameLogic gameLogic;
	
	private JTextArea questionArea;
	private JLabel correctAnswerLabel;
	private JTextField correctAnswerField;
	
	private JButton trueButton;
	private JButton falseButton;
	private JButton checkButton;
	private JButton nextButton;
	private JButton restartButton;
	private JButton questionsButton;
	private JButton refreshButton;
	
	private JButton[] buttons;
	private static int numberOfAnswers = 2;
	
	
	private JPanel topPanel;
	private JPanel topLeftPanel;
	private JPanel topRightPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==trueButton)
		{
			trueButton.setBackground(Color.BLACK);
			defaultButtonColor(falseButton);
		}
		
		if(ae.getSource()==falseButton)
		{
			falseButton.setBackground(Color.BLACK);
			defaultButtonColor(trueButton);
		}
		
		if(ae.getSource()==nextButton)
		{
			gameLogic.nextQuestion(this, questionArea);
		}
		if(ae.getSource()==checkButton)
		{
			String answer = " ";
			for(int i=0; i<numberOfAnswers; i++)
			{
				if(buttons[i].getBackground()==Color.BLACK)
				{
					answer = buttons[i].getText();
				}
			}
			gameLogic.checkAnswer(this, checkButton, answer);
		}
		if(ae.getSource()==restartButton)
		{
			if(JOptionPane.showConfirmDialog(this, "Are you sure?", "New game", JOptionPane.YES_NO_OPTION)==0)
			{
				gameLogic.startNewGame(this);
			}
		}
		
		if(ae.getSource()==questionsButton)
		{
			if(JOptionPane.showConfirmDialog(this, "Are you sure? The game will be restarted!", "", JOptionPane.YES_NO_OPTION)==0)
			{
				try
				{
					File f = new File(System.getProperty("user.dir")+ File.separator + "questions.txt");
					Desktop desktop = Desktop.getDesktop();
					desktop.open(f);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				gameLogic.startNewGame(this);
			}
		}
		
		if(ae.getSource()==refreshButton)
		{
			refreshButton.setEnabled(false);
			try
			{
				gameLogic.readQuestions();
			}
			catch(Exception e1)
			{
		           e1.printStackTrace();
			}	
				gameLogic.initQuestions();
		}
	}
	
	public GameFrame(GameLogic gl)
	{
		gameLogic = gl;
		
		//init frame
		this.setLayout(new BorderLayout());
		this.setTitle("TRUE or FALSE");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1000, 350));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	
		
		//init panel
		topPanel = new JPanel();
		topLeftPanel = new JPanel();
		topRightPanel = new JPanel();
		topRightPanel.setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		centerPanel = new JPanel();
		
			
		//init textfield and buttons
		
	    questionArea = new JTextArea();
	    questionArea.setEditable(false);
	    questionArea.setFocusable(false);
	    questionArea.setPreferredSize( new Dimension( 800, 200 ) );
	    questionArea.setLineWrap(true);
	    questionArea.setWrapStyleWord(true);
		
	    correctAnswerLabel = new JLabel("Correct answer: "); 
	    correctAnswerLabel.setFocusable(false);
	    correctAnswerLabel.setFont(new Font("Calibri", Font.BOLD, 16));
		correctAnswerField = new JTextField(1); 
		correctAnswerField.setEditable(false);
		correctAnswerField.setFocusable(false);
		correctAnswerField.setFont(new Font("Calibri", Font.BOLD, 20));
		correctAnswerField.setForeground(Color.BLUE);
		correctAnswerField.setBorder(BorderFactory.createEmptyBorder());
		
		
		
		nextButton = new JButton();
		nextButton.addActionListener(this);
		nextButton.setText("Next");
		
		trueButton = new JButton();
		trueButton.addActionListener(this);
		trueButton.setText("True");
		
		falseButton = new JButton();
		falseButton.addActionListener(this);
		falseButton.setText("False");
		
		checkButton = new JButton();
		checkButton.addActionListener(this);
		checkButton.setText("CHECK");
		
		restartButton = new JButton();
		restartButton.addActionListener(this);
		restartButton.setText("RESTART");
		
		questionsButton = new JButton();
		questionsButton.addActionListener(this);
		questionsButton.setText("Manage questions");
		
		refreshButton = new JButton();
		refreshButton.addActionListener(this);
		refreshButton.setText("Refresh questions list");
		
		buttons = new JButton[3];
		buttons[0] = trueButton;
		buttons[1] = falseButton;
		
		//adding buttons to the panels and setting colors
		topLeftPanel.add(questionArea);
		topRightPanel.add(restartButton, BorderLayout.NORTH);
		topRightPanel.add(questionsButton, BorderLayout.CENTER);
		topRightPanel.add(refreshButton, BorderLayout.SOUTH);
		
		topPanel.add(topLeftPanel);
		topPanel.add(topRightPanel);
		centerPanel.add(correctAnswerLabel);
		centerPanel.add(correctAnswerField);
		bottomPanel.add(trueButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		bottomPanel.add(falseButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(80, 0)));
		bottomPanel.add(checkButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		bottomPanel.add(nextButton);
		
		
		defaultFrame();
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		gameLogic.initQuestions();
	}
	
	public void defaultFrame()
	{
		this.resetButtons();
		this.disableButtons();
		nextButton.setEnabled(true);
		refreshButton.setEnabled(true);
		questionArea.setText("If you ready to start press 'Next'!");
		questionArea.setForeground(Color.RED);
		questionArea.setFont(new Font("Calibri", Font.BOLD, 30));
		correctAnswerField.setText(" ");
		restartButton.setBackground(Color.YELLOW);
		questionsButton.setBackground(Color.WHITE);
		refreshButton.setBackground(Color.WHITE);
	}
	
	public void resetButtons()
	{
		for(int i=0; i<numberOfAnswers; i++)
		{
			defaultButtonColor(buttons[i]);
			buttons[i].setEnabled(true);
		}
		nextButton.setForeground(Color.WHITE);
		nextButton.setBackground(Color.GRAY);
		nextButton.setEnabled(true);
		checkButton.setForeground(Color.WHITE);
		checkButton.setBackground(Color.GRAY);
		checkButton.setEnabled(true);
		correctAnswerField.setText(" ");
	}
	
	private void defaultButtonColor(JButton JB)
	{
		JB.setForeground(Color.white);
		JB.setBackground(Color.lightGray);
	}
	
	public void disableButtons()
	{
		for(int i=0; i<numberOfAnswers; i++)
		{
			buttons[i].setEnabled(false);
		}
		checkButton.setEnabled(false);
		nextButton.setEnabled(false);
		refreshButton.setEnabled(false);
	}
	
	public void disablenextButton()
	{
		nextButton.setEnabled(false);
		refreshButton.setEnabled(false);
	}
	
	public void enablenextButton()
	{
		nextButton.setEnabled(true);
	}
	
	public void setCorrect(String a)
	{
		correctAnswerField.setText(a);
	}
	
	public void endGame()
	{
		questionArea.setText("Game over");
		questionArea.setForeground(Color.RED);
		questionArea.setFont(new Font("Calibri", Font.BOLD, 30));
		resetButtons();
		disableButtons();
		gameLogic.result(this);
	}
	
	
	
	
	

}
