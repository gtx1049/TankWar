package com.game;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class TankWar extends MIDlet implements CommandListener
{

	//地图数组
	int[][] map1 = 
	{
		{2, 4, 4, 4, 4, 4, 4, 2, 4, 4, 4, 4, 4, 4, 2},
		{4, 6, 6, 6, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		{4, 7, 7, 7, 4, 4, 4, 4, 4, 4, 4, 7, 7, 7, 4},
		{4, 7, 7, 7, 4, 4, 4, 4, 4, 4, 4, 7, 7, 7, 4},
		{4, 7, 7, 7, 4, 4, 4, 4, 4, 4, 4, 7, 7, 7, 4},
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		{4, 8, 8, 8, 8, 8, 4, 4, 4, 8, 8, 8, 8, 8, 4},
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		{4, 4, 4, 4, 7, 7, 4, 4, 4, 7, 7, 4, 4, 4, 4},
		{4, 4, 4, 4, 7, 7, 4, 4, 4, 7, 7, 4, 4, 4, 4},
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		{4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 4, 4, 4, 4, 4},
		{4, 4, 4, 4, 4, 0, 5, 3, 5, 4, 4, 4, 4, 4, 4}
	};
	
	static public Display display;
	private TankCanvas tankcanvas;
	
	static public Form mainForm;
	
	private Command exit;
	private Command startGame;
	
	public TankWar()
	{
		mainForm = new Form("Tank War");
		
		exit = new Command("退出", Command.EXIT, 0);
		startGame = new Command("开始游戏", Command.ITEM, 1);
		
		mainForm.addCommand(exit);
		mainForm.addCommand(startGame);
		mainForm.append("Tank War");
		
		
		mainForm.setCommandListener(this);
		
		display = Display.getDisplay(this);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException
	{
		notifyDestroyed();
		
	}

	protected void pauseApp()
	{
		// TODO Auto-generated method stub
		
	}

	protected void startApp() throws MIDletStateChangeException
	{
		// TODO Auto-generated method stub
		//display.setCurrent(tankcanvas);
		display.setCurrent(mainForm);
	}

	public void commandAction(Command command, Displayable arg1) {
		if (command == exit)
		{
			try
			{
				destroyApp(true);
			}
			catch (MIDletStateChangeException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		else if (command == startGame)
		{
			MainLogicThread.isgameover = false;
			tankcanvas = new TankCanvas(true, map1);
			Display.getDisplay(this).setCurrent(tankcanvas);
		}
		
	}

}
