import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class Minesweeper implements MouseListener,ActionListener{
	ImageIcon flagIconChange;
	
	boolean isEverythingCorrect=false;
	boolean isGameOver=true;
	Timer timer;
	TimerTask task;
    static int seconds = 0;
	boolean gameStarted=false;
	
	JButton resetButton;
	static JLabel timerLabel;
	JLabel numFlagsLeft;
	JPanel topestPanel;
	int mines=12;
	int flagsLeft=mines;
	JFrame frame;
	JPanel scrollPanel,topPanel, bigGridPanel;
	JMenuBar topBar;
	JMenu game;
	JMenu icons;
	JMenu controls;
	
	int whichState;
	
	int numRows=9;
	int numCols=9;
	
	JToggleButton [][]array;
	int [][]numbersArray;
	
	JMenuItem beginner;
	JMenuItem intermediate;
	JMenuItem expert;
	
	JMenuItem redFlagItem;
	JMenuItem orangeFlagItem;
	JMenuItem blueFlagItem;
	
	
	JMenuItem explanation;

	
	ImageIcon empty;
	ImageIcon flagged;
	ImageIcon orangeFlag;
	ImageIcon blueFlag;
	ImageIcon block;
	ImageIcon mine;
	ImageIcon one;
	ImageIcon two;
	ImageIcon three;
	ImageIcon four;
	ImageIcon five;
	ImageIcon six;
	ImageIcon seven;
	ImageIcon eight;
	
	boolean mineSet=false;

	
	public Minesweeper()
	{
		
		
		scrollPanel=new JPanel();
		scrollPanel.setLayout(new GridLayout(5,1));
		
	/*	try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		timerLabel=new JLabel("TIMER");
		numFlagsLeft=new JLabel("# Flags Left");
		topestPanel=new JPanel();
		
		topBar=new JMenuBar();
		game=new JMenu("GAME");
		icons=new JMenu("ICONS");
		controls=new JMenu("CONTROLS");
		
		redFlagItem=new JMenuItem("RED flag");
		redFlagItem.setForeground(Color.RED);
		redFlagItem.addActionListener(this);
		
		orangeFlagItem=new JMenuItem("ORANGE flag");
		orangeFlagItem.setForeground(Color.ORANGE);
		orangeFlagItem.addActionListener(this);
		
		blueFlagItem=new JMenuItem("BLUE flag");
		blueFlagItem.setForeground(Color.BLUE);
		blueFlagItem.addActionListener(this);
		
		
		
		beginner=new JMenuItem("beginner");
		beginner.addActionListener(this);
		intermediate=new JMenuItem("intermediate");
		intermediate.addActionListener(this);
		expert=new JMenuItem("expert");
		expert.addActionListener(this);
		
		explanation=new JMenuItem("Left-click an empty square to reveal it.\n" + 
				"Right-click (or Ctrl+click) an empty square to flag it.\n" + 
				"Midde-click (or left+right click) a number to reveal\n" + 
				"its adjacent squares.\n" + 
				"Press space bar while hovering over a square to flag\n" + 
				"it or reveal its adjacent squares.\n" + 
				"Press F2 to start a new game.");
		
		flagged=new ImageIcon(getClass().getResource("/resources/flagged.png"));
		flagged = new ImageIcon(
				flagged.getImage().getScaledInstance(
				40,
				40,
				Image.SCALE_SMOOTH));
		
		orangeFlag=new ImageIcon(getClass().getResource("/resources/orangeFlag.png"));
		orangeFlag = new ImageIcon(
				orangeFlag.getImage().getScaledInstance(
				40,
				40,
				Image.SCALE_SMOOTH));
		
		blueFlag=new ImageIcon(getClass().getResource("/resources/blueflag.png"));
		blueFlag = new ImageIcon(
				blueFlag.getImage().getScaledInstance(
				40,
				40,
				Image.SCALE_SMOOTH));
		
		flagIconChange=flagged;
		
		
		
		mine=new ImageIcon(getClass().getResource("/resources/mine.png"));
		mine = new ImageIcon(
				mine.getImage().getScaledInstance(
				40,
				40,
				Image.SCALE_SMOOTH));
		
		topBar.add(game);
		topBar.add(icons);
		topBar.add(controls);
		
		
		game.add(beginner);
		game.add(intermediate);
		game.add(expert);
		
		icons.add(redFlagItem);
		icons.add(orangeFlagItem);
		icons.add(blueFlagItem);
		
		controls.add(explanation);
		
		topestPanel.add(timerLabel);
		topestPanel.add(Box.createHorizontalStrut(30));
		topestPanel.add(topBar);
		topestPanel.add(Box.createHorizontalStrut(30));
		topestPanel.add(numFlagsLeft);
		
		setBoard();
		
		
	}
	 
	public void setBoard()
	{
		gameStarted=false;
		frame=new JFrame("Minesweeper Program");
		if(bigGridPanel!=null)
			frame.remove(bigGridPanel);
		bigGridPanel=new JPanel();
		bigGridPanel.setLayout(new GridLayout(numRows,numCols));		
		array=new JToggleButton[numRows][numCols];
		numbersArray=new int[numRows][numCols];
		
		for (int x=0;x<array.length;x++)
		{
			for (int y=0;y<array[x].length;y++)
			{
				array[x][y]=new JToggleButton();
				array[x][y].putClientProperty("row",x);
				array[x][y].putClientProperty("col", y);
				array[x][y].putClientProperty("state", whichState);
				array[x][y].addMouseListener(this);
				//array[x][y].setIcon(block);
				bigGridPanel.add(array[x][y]);
			}
		}
		//frame.add(topBar,BorderLayout.NORTH);
		frame.add(topestPanel,BorderLayout.NORTH);
		resetButton = new JButton();
		resetButton.setText("RESET");
		resetButton.addActionListener(this);
		resetButton.addMouseListener(this);
		frame.add(resetButton,BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(bigGridPanel,BorderLayout.CENTER);
		if (mines==12)
			frame.setSize(70*numCols, 70*numRows);
		else if (mines==42)
			frame.setSize(50*numCols, 50*numRows);
		else
			frame.setSize(40*numCols, 55*numRows);
		frame.revalidate();

	}
	
	public static void main(String []args)
	{
		
		Minesweeper app = new Minesweeper();
		  
	}
	 public void MyTimer() 
	 {
		 System.out.println("creating a timer");
		 	timer = new Timer();
	        task = new TimerTask() {
	            private final int MAX_SECONDS = 3000;

	            @Override
	            public void run() { 
	            
	                if (seconds < MAX_SECONDS) {
	                    System.out.println("Seconds = " + seconds);
	                    seconds++;
	                    updateTimer(seconds);
	                } else {
	                    // stop the timer
	                	infoBox("GAME OVER, TIME RAN OUT!", "GAME OVER");
	                    cancel();
	                }
	            }
	            
	        };
	         timer.schedule(task, 0, 1000);
	       

	   }

	public static void updateTimer(int secondss)
	{
		timerLabel.setText(secondss+"");
	}
	public void updateFlags(int flagsLeftt)
	{
		numFlagsLeft.setText(flagsLeftt+" ");
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		 
		if (e.getButton()==MouseEvent.BUTTON3)
		{
			return;
		}
	
		
		JToggleButton source = (JToggleButton)e.getSource();
		int xProp = (int)source.getClientProperty("row");
		int yProp = (int)source.getClientProperty("col");
		
		if (array[xProp][yProp].getIcon()==flagIconChange)
		{
			flagsLeft++;
			updateFlags(flagsLeft);
			array[xProp][yProp].setIcon(null);
		}
		
		if (array[xProp][yProp].isEnabled()==false)
			return;
		if (e.getButton()== MouseEvent.BUTTON1)
		{
			System.out.print("IS THIS ONE CLICK ORRRRRR????");
		}
		if (gameStarted==false)
		{
			MyTimer();
			gameStarted=true;
			isGameOver=false;
			
			populateGrid(xProp,yProp);
			array[xProp][yProp].setSelected(false);
			expand(xProp,yProp);
		}
		else
		{
			if (numbersArray[xProp][yProp]==9)
			{
				for (int x=0;x<numbersArray.length;x++)
				{
					for (int y=0;y<numbersArray[x].length;y++)
					{
						if(numbersArray[x][y]==9)
						{
							array[x][y].setText("");
							array[x][y].setIcon(mine);
							
						}
						
					}
				}
				////// DISPLAY GAME OVER MESSAGE
				infoBox("GAME OVER, YOU LOSE", "GAME OVER");
				isGameOver=true;
				
			}
			else if (numbersArray[xProp][yProp]==0)
			{
				//array[xProp][yProp].setSelected(true);
				expand(xProp,yProp);
			}
			else
			{
				array[xProp][yProp].setText(""+numbersArray[xProp][yProp]);
			}
			
			checkingIfEverythingIsCorrect();
			
			
		}
		
		
	}
	public void checkingIfEverythingIsCorrect()
	{
		isEverythingCorrect=true;
		for (int a=0;a<numbersArray.length;a++)
		{
			for (int b=0;b<numbersArray[a].length;b++)
			{
				System.out.println("mouseclicked :" + array[a][b].getText()+","+numbersArray[a][b]);
				if (numbersArray[a][b]==9 && array[a][b].getIcon()!=flagged)
				{
					isEverythingCorrect=false;
					break;
				}
			
			}
		}
		if (isEverythingCorrect==true) 
		{
			infoBox("WINNER", "YOU WIN !!!!");
			isGameOver=true;
		}
	}
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	public boolean isMineAround(int r, int c)
	{
		for (int i=r-1;i<=r+1;i++)
		{
			for (int j=c-1;j<=c+1;j++)
			{
				try {
					//System.out.println(i+","+j+","+numbersArray[i][j]);
					if (numbersArray[i][j]==9)
					{
	
						//System.out.println("returning true from isminearound");
						return true;
						
					}
					
				}catch(IndexOutOfBoundsException e)
				{}
			}
		}
		return false;
	}
	
	public void expand(int r, int c)
	{
		if (array[r][c].isSelected())
			return;
		System.out.println(array[r][c].isSelected()+" "+r+","+c);
		
		boolean isMine = isMineAround(r,c);
		if (isMine==true)
		{
			writeText(r,c,numbersArray[r][c]);
			array[r][c].setSelected(true);
			array[r][c].setEnabled(false);   ///////////////////////////////////////

		}
		else
		{
			array[r][c].setSelected(true);
			for (int i=r-1;i<=r+1;i++)
			{
				for (int j=c-1;j<=c+1;j++)
				{
					
					try {
						
						if( i==r && j==c)
							continue;
						if (array[i][j].isSelected()==false )
						{
							
							expand(i,j);
						}
						
						
					}catch(IndexOutOfBoundsException e)
					{}
				}
			}
		}
	}
	
	public void populateGrid(int rowClicked, int colClicked)
	{
		for (int x=0;x<mines;x++)
		{
			int random1=(int)(Math.random()*(numRows-1))+0;
			int random2=(int)(Math.random()*(numCols-1))+0;
			
			
			if (Math.abs(rowClicked-random1)<=1 && Math.abs(colClicked-random2)<=1)
			{
				x--;
				//continue;
			}
			
			else
				numbersArray[random1][random2]=9;
		}
			
	
		
		
		for (int x=0;x<numbersArray.length;x++)
		{
			for (int y=0;y<numbersArray[x].length;y++)
			{
				int counterForPopulateGrid=0;
				if (numbersArray[x][y]==9)
				{
					continue;
				}
				else
				{
					if (x-1>=0 && numbersArray[x-1][y]==9)
					{
						counterForPopulateGrid++;
					}
					if (x+1<numbersArray.length-1 && numbersArray[x+1][y]==9)
					{
						counterForPopulateGrid++;
					}
					if (y-1>=0 && numbersArray[x][y-1]==9)
					{
						counterForPopulateGrid++;
					}
					if (y+1<numbersArray[0].length-1 && numbersArray[x][y+1]==9)
					{
						counterForPopulateGrid++;
					}
					if (y+1<numbersArray[0].length-1 && x+1<numbersArray.length-1 && numbersArray[x+1][y+1]==9)
					{
						counterForPopulateGrid++;
					}
					if (y-1>=0 && x-1>=0 && numbersArray[x-1][y-1]==9)
					{
						counterForPopulateGrid++;
					}
					if (x<numbersArray.length-1 && y-1>=0 && numbersArray[x+1][y-1]==9)
					{
						counterForPopulateGrid++;
					}
					if (x-1>=0 && y+1<numbersArray[0].length-1 && numbersArray[x-1][y+1]==9)
					{
						counterForPopulateGrid++;
					}
				}
					
					numbersArray[x][y]=counterForPopulateGrid;
					if (numbersArray[x][y]==0)
						array[x][y].setEnabled(false);
					
						
						
				}
			}
		
		for (int x=0;x<numbersArray.length;x++)
		{
			for (int y=0;y<numbersArray[x].length;y++)
			{
				System.out.print(numbersArray[x][y]+", ");
				
			}
			System.out.println();
		}
	}
	
	
	public void writeText(int r,int c, int state)
	{
		switch(state)
		{
			case 1:array[r][c].setForeground(Color.BLACK);
				break;
			case 2:array[r][c].setForeground(Color.GREEN);
				break;
			case 3:array[r][c].setForeground(Color.RED);
				break;
			case 4:array[r][c].setForeground(new Color(0,40,0));
				break;
			case 5:array[r][c].setForeground(new Color(0,0,120));
				break;
			case 6:array[r][c].setForeground(Color.CYAN);
				break;
			case 7:array[r][c].setForeground(Color.BLACK);
				break;
			case 8:array[r][c].setForeground(Color.BLACK);
				break;
			case 9:array[r][c].setIcon(mine);
					array[r][c].setText("");
				break;	
		}
		if (state!=9)
		{
			System.out.println("in WRITE TEXT"+r+","+c+","+state);
			array[r][c].setText(""+state);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
		JToggleButton source = (JToggleButton)e.getSource();
		int xProp = (int)source.getClientProperty("row");
		int yProp = (int)source.getClientProperty("col");
		
		if (array[xProp][yProp].isEnabled()==false)
			return;
		
		if (array[xProp][yProp].isEnabled()==false)
			return;
		if (e.getButton()==MouseEvent.BUTTON3) //// FLAGS
		{
			System.out.println("RIGHT CLICK ACTUALLY WORKS LOL");
			flagsLeft-=1;
			if (flagsLeft>-1)
				updateFlags(flagsLeft);
			//array[xProp][yProp].setText("FLAG");
			array[xProp][yProp].setText("");
			if (flagsLeft>=0)
			{
				array[xProp][yProp].setIcon(flagIconChange);
			}
			else
			{
				flagsLeft++;
				System.out.println("YOU'RE OUT OF FLAGS");
			}
			
			checkingIfEverythingIsCorrect();
		}
		
			
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void changeTheScreenFlags()
	{
		for (int a=0;a<numbersArray.length;a++)
		{
			for (int b=0;b<numbersArray[a].length;b++)
			{
				if (array[a][b].getIcon()==flagged || array[a][b].getIcon()==orangeFlag || array[a][b].getIcon()==blueFlag)
				{
					array[a][b].setIcon(flagIconChange);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==redFlagItem)
		{
			flagIconChange=flagged;
			changeTheScreenFlags();
		
		}
		if (e.getSource()==orangeFlagItem)
		{
			flagIconChange=orangeFlag;
			changeTheScreenFlags();
		
		}
		if (e.getSource()==blueFlagItem)
		{
			flagIconChange=blueFlag;
			changeTheScreenFlags();
		
		}
		if (e.getSource()==resetButton)
		{
			System.out.println("reset is working");
			mineSet=false;
			frame.dispose();
			gameStarted=false;
			if (timer!=null)
				timer.cancel();
			flagsLeft=mines;
			//timer.purge();
			seconds=0;
			
			setBoard();
			
		}
		if (e.getSource()==beginner)
		{
			mines=12;
			flagsLeft=mines;
			mineSet=false;
			numRows=9;
			numCols=9;
			frame.dispose();
			gameStarted=false;
			if (timer!=null)
				timer.cancel();
			
			//timer.purge();
			seconds=0;
			setBoard();
		}
		else if (e.getSource()==intermediate)
		{
			mines=42;
			flagsLeft=mines;
			mineSet=false;
			numRows=16;
			numCols=16;
			frame.dispose();
			gameStarted=false;
			if (timer!=null)
				timer.cancel();
			
			//timer.purge();
			seconds=0;
			setBoard();
		}
		else if (e.getSource()==expert)
		{
			mines=78;
			mineSet=false;
			numRows=16;
			numCols=30;
			frame.dispose();
			gameStarted=false;
			if (timer!=null)
				timer.cancel();
			
			//timer.purge();
			seconds=0;
			setBoard();
			
		}
		
	}
	
	
}
