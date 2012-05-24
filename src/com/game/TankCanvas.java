package com.game;

import java.util.Vector;

import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.TiledLayer;

import com.sprite.Home;
import com.sprite.Player;
import com.sprite.Enemy;
import com.sprite.Shell;

public class TankCanvas extends GameCanvas implements Runnable
{
	private Player player;
	private Vector enemys;
	private Vector shells;
	private Vector walls;
	private Item item;
	private Home home;
	private TiledLayer background;
	private SceneManager scene;
	
	private int status;
	
	private boolean onrun;
	
	protected TankCanvas(boolean suppressKeyEvents)
	{
		super(suppressKeyEvents);
		// TODO Auto-generated constructor stub
		new Thread(this).start();
	}

	public void gameLogic()
	{
		
	}

	public void run()
	{
		// TODO Auto-generated method stub
		while(onrun)
		{
			for(int i = shells.size() - 1; i >= 0; i--)
			{
				Shell s = (Shell)shells.elementAt(i);
				s.collideObject(s); 
				s.doAction();
			}
			for(int i = enemys.size() - 1; i >= 0; i--)
			{
				Enemy e = (Enemy)enemys.elementAt(i);
				
				e.aiAction();
			}
			
			player.waitKeyPress();
			player.doAction();
			
			
		}
	}
	
}
