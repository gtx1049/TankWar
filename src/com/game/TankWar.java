package com.game;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class TankWar extends MIDlet
{

	//µØÍ¼Êý×é
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
	
	private Display display;
	private TankCanvas tankcanvas;
	
	public TankWar()
	{
		display = Display.getDisplay(this);
		tankcanvas = new TankCanvas(true, map1);
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException
	{
		// TODO Auto-generated method stub
		
	}

	protected void pauseApp()
	{
		// TODO Auto-generated method stub
		
	}

	protected void startApp() throws MIDletStateChangeException
	{
		// TODO Auto-generated method stub
		display.setCurrent(tankcanvas);
	}

}
