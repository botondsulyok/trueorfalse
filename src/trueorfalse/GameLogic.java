package trueorfalse;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameLogic {
	
	private ArrayList<String> list = new ArrayList<>();
	private ArrayList<String> questionList = new ArrayList<>();
	private ArrayList<String> answerList = new ArrayList<>();
	
	private String splitChar;
	
	private boolean first = true;
	
	private int allAnswers;
	private int correctAnswers;
	
	public GameLogic()
	{
		try
		{
			readQuestions();
		}
		catch(Exception e1)
		{
            e1.printStackTrace();
		}	
	}
	
	public void readQuestions() throws FileNotFoundException, IOException
	{
		list.clear();
		questionList.clear();
		answerList.clear();
		FileReader fr = new FileReader(System.getProperty("user.dir")+ File.separator + "questions.txt");
		BufferedReader br = new BufferedReader(fr);
		splitChar = br.readLine();
		while(true)
		{
			String line = br.readLine();
			if(line == null)
			{
				break;
			}
			list.add(line);
		}
		br.close();
	}
	
	public void startNewGame(GameFrame gf)
	{
		initQuestions();
		gf.defaultFrame();
		first = true;
		correctAnswers = 0;
	}
	
	public void initQuestions()
	{
		questionList.clear();
		answerList.clear();
		Collections.shuffle(list);
		ListIterator<String> it = list.listIterator();
		while(it.hasNext())
		{
			String line = it.next();
			if(line.contains(splitChar))
			{
				String[] lineParts = line.split(splitChar);
				lineParts[0] = lineParts[0].substring(0, lineParts[0].length()-1);
				questionList.add(lineParts[0]);
				answerList.add(lineParts[1]);
			}
		}
		allAnswers = answerList.size();
	}
	
	public void checkAnswer(GameFrame gf, JButton okButton, String answer)
	{
		String a = answer.substring(0, 1);
		if(!answer.equals(" "))
		{
			if(a.equals(answerList.get(0)))
			{
				okButton.setBackground(Color.GREEN);
				correctAnswers++;
			}
			else
			{
				okButton.setBackground(Color.RED);
			}
			gf.setCorrect(answerList.get(0));
			gf.disableButtons();
			gf.enablenextButton();
		}
	}
	
	public void nextQuestion(GameFrame gf, JTextArea JTA)
	{
		if(!first)
		{
			questionList.remove(0);
			answerList.remove(0);
		}
		if(questionList.isEmpty())
		{
			gf.endGame();
		}
		else		
		{
			JTA.setText(questionList.get(0));
			JTA.setForeground(Color.BLACK);
			JTA.setFont(new Font("Calibri", Font.PLAIN, 24));
			gf.resetButtons();
			gf.disablenextButton();
			first = false;
		}
	}
	
	public void result(GameFrame gf)
	{
		JOptionPane.showMessageDialog(gf, "Result: " + correctAnswers + "/" + allAnswers, "Game over", JOptionPane.PLAIN_MESSAGE);
	}
		

}
